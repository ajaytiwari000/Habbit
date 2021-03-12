package com.salesmanager.shop.error.codes;

public enum ErrorCodes {
  ACCESS_NOT_ALLOWED("FORBIDDEN_001", "User not Authorized to access the API"),
  SESSION_NOT_FOUND("FORBIDDEN_002", "User Session details not found"),
  SESSION_EXPIRED("FORBIDDEN_003", "User Session Expired");

  private final String errorCode;
  private final String errorMessage;

  ErrorCodes(String errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
