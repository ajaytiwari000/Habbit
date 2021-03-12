package com.salesmanager.shop.store.security;

import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.core.model.common.UserContext;
import com.salesmanager.shop.store.security.common.CustomAuthenticationManager;
import com.salesmanager.shop.utils.GeoLocationUtils;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;
import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

  @Value("${authToken.header}")
  private String tokenHeader;

  private static final String BEARER_TOKEN = "Bearer ";

  private static final String FACEBOOK_TOKEN = "FB ";

  @Inject private CustomAuthenticationManager jwtCustomCustomerAuthenticationManager;

  @Inject private CustomAuthenticationManager jwtCustomAdminAuthenticationManager;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    String origin = "*";
    if (!StringUtils.isBlank(request.getHeader("origin"))) {
      origin = request.getHeader("origin");
    }
    // in flight
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
    response.setHeader("Access-Control-Allow-Origin", origin);
    response.setHeader("Access-Control-Allow-Headers", "X-Auth-Token, Content-Type, Authorization");
    response.setHeader("Access-Control-Allow-Credentials", "true");

    try {
      String ipAddress = GeoLocationUtils.getClientIpAddress(request);
      UserContext userContext = UserContext.create();
      userContext.setIpAddress(ipAddress);

    } catch (Exception s) {
      LOGGER.error("Error while getting ip address ", s);
    }

    if (request.getRequestURL().toString().contains("/api/v1/auth")) {
      // setHeader(request,response);
      final String requestHeader = request.getHeader(this.tokenHeader); // token
      LOGGER.info(
          "Authentication value before validation - "
              + SecurityContextHolder.getContext().getAuthentication());

      try {
        if (requestHeader != null && requestHeader.startsWith(BEARER_TOKEN)) { // Bearer

          jwtCustomCustomerAuthenticationManager.authenticateRequest(request, response);

        } else if (requestHeader != null && requestHeader.startsWith(FACEBOOK_TOKEN)) {
          // Facebook
          // facebookCustomerAuthenticationManager.authenticateRequest(request, response);
        } else {
          LOGGER.warn("couldn't find any authorization token, will ignore the header");
        }

      } catch (Exception e) {
        throw new ServletException(e);
      }
    }

    if (request.getRequestURL().toString().contains("/api/v1/private")) {
      LOGGER.info(
          "Authentication value before validation - "
              + SecurityContextHolder.getContext().getAuthentication());

      // setHeader(request,response);

      Enumeration<String> headers = request.getHeaderNames();
      while (headers.hasMoreElements()) {
        LOGGER.debug(headers.nextElement());
      }

      final String requestHeader = request.getHeader(this.tokenHeader); // token

      try {
        if (requestHeader != null && requestHeader.startsWith(BEARER_TOKEN)) { // Bearer

          jwtCustomAdminAuthenticationManager.authenticateRequest(request, response);

        } else {
          LOGGER.warn(
              "couldn't find any authorization token, will ignore the header, might be a preflight check");
        }

      } catch (Exception e) {
        throw new ServletException(e);
      }
    }
    String currentCorrId = request.getHeader(Constants.CORRELATION_ID_HEADER_NAME);
    if (StringUtils.isEmpty(currentCorrId)) {
      currentCorrId = UUID.randomUUID().toString();
      LOGGER.info("No correlationId found in Header. Generated : " + currentCorrId);
    } else {
      LOGGER.info("Found correlationId in Header : " + currentCorrId);
    }
    RequestCorrelation.setId(currentCorrId);
    response.setHeader(Constants.CORRELATION_ID_HEADER_NAME, RequestCorrelation.getId());

    chain.doFilter(request, response);
    postFilter(request, response, chain);
  }

  private void postFilter(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    try {

      UserContext userContext = UserContext.getCurrentInstance();
      if (userContext != null) {
        userContext.close();
      }

    } catch (Exception s) {
      LOGGER.error("Error while getting ip address ", s);
    }
  }
}