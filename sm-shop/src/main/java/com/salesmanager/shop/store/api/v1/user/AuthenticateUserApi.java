package com.salesmanager.shop.store.api.v1.user;

import com.salesmanager.core.business.services.user.UserService;
import com.salesmanager.core.model.user.User;
import com.salesmanager.shop.model.security.AuthenticationRequest;
import com.salesmanager.shop.model.security.AuthenticationResponse;
import com.salesmanager.shop.store.controller.authentication.authenticationToken.facade.AuthenticationTokenFacade;
import com.salesmanager.shop.store.security.JWTTokenUtil;
import com.salesmanager.shop.store.security.user.JWTUser;
import java.util.Objects;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.http.auth.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Authenticates a User (Administration purpose)
 *
 * @author c.samson
 */
@Controller
@RequestMapping("/api/v1")
public class AuthenticateUserApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateUserApi.class);

  @Value("${authToken.header}")
  private String tokenHeader;

  @Inject private AuthenticationManager jwtAdminAuthenticationManager;

  @Inject private UserDetailsService jwtAdminDetailsService;
  @Inject private PasswordEncoder passwordEncoder;

  @Inject private JWTTokenUtil jwtTokenUtil;
  @Inject private UserService userService;

  @Autowired private AuthenticationTokenFacade authenticationTokenFacade;

  /**
   * Authenticate a user using username & password
   *
   * @param authenticationRequest
   * @return
   * @throws AuthenticationException
   */
  @RequestMapping(value = "/private/login", method = RequestMethod.POST)
  public ResponseEntity<?> authenticate(
      @RequestBody @Valid AuthenticationRequest authenticationRequest, HttpServletRequest request)
      throws Exception {
    String requestSource = request.getHeader("requestSource");
    User user = userService.getByUserName(authenticationRequest.getUsername());
    BCryptPasswordEncoder b = (BCryptPasswordEncoder) passwordEncoder;
    if (Objects.isNull(user)
        || !b.matches(authenticationRequest.getPassword(), user.getAdminPassword())) {
      throw new Exception("Bad Credentials");
    }
    JWTUser userDetails =
        (JWTUser) jwtAdminDetailsService.loadUserByUsername(authenticationRequest.getUsername());

    final String jwtToken = jwtTokenUtil.generateToken(userDetails, requestSource);
    LOGGER.info("Authentication - " + SecurityContextHolder.getContext().getAuthentication());

    // Return the token
    return ResponseEntity.ok(new AuthenticationResponse(userDetails.getId(), jwtToken));
  }

  @RequestMapping(value = "/auth/refresh", method = RequestMethod.GET)
  public ResponseEntity<AuthenticationResponse> refreshAndGetAuthenticationToken(
      HttpServletRequest request) {
    String token = request.getHeader(tokenHeader);

    if (token != null && token.contains("Bearer")) {
      token = token.substring("Bearer ".length(), token.length());
    }

    String username = jwtTokenUtil.getUsernameFromToken(token);
    JWTUser user = (JWTUser) jwtAdminDetailsService.loadUserByUsername(username);

    if (jwtTokenUtil.canTokenBeRefreshedWithGrace(token, user.getLastPasswordResetDate())) {
      String refreshedToken = jwtTokenUtil.refreshToken(token);
      return ResponseEntity.ok(new AuthenticationResponse(user.getId(), refreshedToken));
    } else {
      return ResponseEntity.badRequest().body(null);
    }
  }
}
