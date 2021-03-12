package com.salesmanager.shop.store.security;

import com.salesmanager.core.business.constants.Constants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CorrelationInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(
      final HttpServletRequest request, final HttpServletResponse response, final Object handler)
      throws Exception {

    final String correlationId = getCorrelationIdFromHeader(request);
    MDC.put(Constants.CORRELATION_ID_LOG_VAR_NAME, correlationId);
    return true;
  }

  @Override
  public void afterCompletion(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final Object handler,
      final Exception ex)
      throws Exception {
    MDC.remove(Constants.CORRELATION_ID_LOG_VAR_NAME);
  }

  private String getCorrelationIdFromHeader(final HttpServletRequest request) {
    String correlationId = request.getHeader(Constants.CORRELATION_ID_HEADER_NAME);
    if (StringUtils.isBlank(correlationId)) {
      correlationId = generateUniqueCorrelationId();
    }
    return correlationId;
  }

  private String generateUniqueCorrelationId() {
    return RequestCorrelation.getId();
  }
}
