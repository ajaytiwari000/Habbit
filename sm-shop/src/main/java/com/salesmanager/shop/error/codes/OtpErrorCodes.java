package com.salesmanager.shop.error.codes;

public enum OtpErrorCodes {

  // Error codes related to API's for OTP

  OTP_INVALID("OTP_1000", "Incorrect OTP "),
  OTP_EXPIRED("OTP_1001", "OTP Expired "),
  OTP_ALREADY_USED("OTP_1002", "OTP Already Used "),

  OTP_DEVICE_ID_MISMATCH("DI_1020", "Device id mismatch ");

  private final String errorCode;
  private final String errorMessage;

  OtpErrorCodes(String errorCode, String errorMessage) {
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
