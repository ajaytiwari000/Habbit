package com.salesmanager.shop.store.api.v1.user;

import com.salesmanager.shop.model.security.AuthenticationRequest;
import com.salesmanager.shop.model.security.AuthenticationResponse;
import com.salesmanager.shop.store.security.JWTTokenUtil;
import com.salesmanager.shop.store.security.user.JWTUser;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.http.auth.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
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
public class AuthenticateUserTestApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateUserTestApi.class);

  @Value("${authToken.header}")
  private String tokenHeader;

  @Inject private AuthenticationManager jwtAdminAuthenticationManager;

  @Inject private UserDetailsService jwtAdminDetailsService;

  @Inject private JWTTokenUtil jwtTokenUtil;

  /**
   * Authenticate a user using username & password
   *
   * @param authenticationRequest
   * @param device
   * @return
   * @throws AuthenticationException
   */
  @RequestMapping(value = "/private/test/login", method = RequestMethod.POST)
  public ResponseEntity<?> authenticate(
      @RequestBody @Valid AuthenticationRequest authenticationRequest, HttpServletRequest request)
      throws AuthenticationException {
    String requestSource = request.getHeader("requestSource");

    // Perform the security
    Authentication authentication = null;
    try {
      LOGGER.info("Authentication - " + SecurityContextHolder.getContext().getAuthentication());

      // to be used when username and password are set
      authentication =
          jwtAdminAuthenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  authenticationRequest.getUsername(), authenticationRequest.getPassword()));

    } catch (Exception e) {
      LOGGER.error("Error during authentication " + e.getMessage());
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    if (authentication == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    SecurityContextHolder.getContext().setAuthentication(authentication);

    JWTUser userDetails =
        (JWTUser) jwtAdminDetailsService.loadUserByUsername(authenticationRequest.getUsername());

    final String jwtToken = jwtTokenUtil.generateToken(userDetails, requestSource);
    LOGGER.info("Authentication - " + SecurityContextHolder.getContext().getAuthentication());

    // Return the token
    return ResponseEntity.ok(new AuthenticationResponse(userDetails.getId(), jwtToken));
  }

  @RequestMapping(value = "/private/test/refresh", method = RequestMethod.GET)
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
