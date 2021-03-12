/** */
package com.salesmanager.shop.utils;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.removeStart;

import com.salesmanager.shop.error.codes.ErrorCodes;
import com.salesmanager.shop.store.api.exception.ForbiddenException;
import com.salesmanager.shop.store.security.JWTTokenUtil;
import com.salesmanager.shop.store.security.common.CustomAuthenticationException;
import io.jsonwebtoken.ExpiredJwtException;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SessionUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(SessionUtil.class);

  @Value("${authToken.header}")
  private String tokenHeader;

  @Inject private JWTTokenUtil jwtTokenUtil;

  private static final String BEARER = "Bearer";

  @SuppressWarnings("unchecked")
  public static <T> T getSessionAttribute(final String key, HttpServletRequest request) {
    return (T) request.getSession().getAttribute(key);
  }

  public static void removeSessionAttribute(final String key, HttpServletRequest request) {
    request.getSession().removeAttribute(key);
  }

  public static void setSessionAttribute(
      final String key, final Object value, HttpServletRequest request) {
    request.getSession().setAttribute(key, value);
  }

  public String getPhoneByAuthToken(HttpServletRequest request) throws ForbiddenException {
    String requestHeader = request.getHeader(this.tokenHeader);
    final String authToken;
    authToken =
        ofNullable(requestHeader)
            .map(value -> removeStart(value, BEARER))
            .map(String::trim)
            .orElseThrow(() -> new CustomAuthenticationException("Missing Authentication Token"));
    try {
      return jwtTokenUtil.getUsernameFromToken(authToken);
    } catch (IllegalArgumentException e) {
      LOGGER.error("an error occurred during getting username from token", e);
      throw new ForbiddenException(
          ErrorCodes.SESSION_NOT_FOUND.getErrorCode(),
          ErrorCodes.SESSION_NOT_FOUND.getErrorMessage());
    } catch (ExpiredJwtException e) {
      LOGGER.error("the token is expired and not valid anymore", e);
      throw new ForbiddenException(
          ErrorCodes.SESSION_EXPIRED.getErrorCode(), ErrorCodes.SESSION_EXPIRED.getErrorMessage());
    }
  }
}
