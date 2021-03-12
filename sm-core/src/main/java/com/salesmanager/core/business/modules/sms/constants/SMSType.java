package com.salesmanager.core.business.modules.sms.constants;

public enum SMSType {
  SEND_OTP("sendOtp"),
  ORDER_CONFIRMATION("orderConfirmation"),
  ORDER_SHIPPED("orderShipped");

  private String value;

  SMSType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
