package com.salesmanager.shop.store.security.common;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {

  /** */
  private static final long serialVersionUID = 1L;

  private String errorCode;
  private String errorMsg;

  public CustomAuthenticationException(String errorMsg) {
    super(errorMsg);
  }

  public CustomAuthenticationException(String errorMsg, Throwable t, String errorCode) {
    super(errorMsg, t);
    this.errorCode = errorCode;
  }

  public CustomAuthenticationException(String errorCode, String errorMsg) {
    super(errorMsg);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }
}
