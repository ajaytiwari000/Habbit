package com.salesmanager.core.business.modules.sms.constants;

public enum EmailType {
  SEND_LOGIN_SUCCESS("sendLoginSuccess"),
  ORDER_CANCEL("order cancelled"),
  SUCCESSFUL_REGISTRATION("successful registration"),
  EMAIL_VERIFICATION("email verification"),
  ORDER_CONFIRMED("order confirmed"),
  ORDER_SHIPPED("order shipped"),
  ORDER_DELIVERED("order delivered"),
  COMPLETE_YOUR_PROFILE("complete your profile");

  private String value;

  EmailType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
