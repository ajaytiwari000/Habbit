package com.salesmanager.shop.error.codes;

public enum CustomerErrorCodes {

  // Error codes related to API's for Customer

  CUSTOMER_AUTHENTICATION_GET_FAILURE("CUS_1000", "Error while fetching customerAuthentication"),
  CUSTOMER_AUTHENTICATION_CREATE_FAILURE("CUS_1002", "Error while creating customerAuthentication"),
  CUSTOMER_AUTHENTICATION_SEND_OTP_FAILURE(
      "CUS_1003", "Error while sending otp customerAuthentication"),
  CUSTOMER_AUTHENTICATION_UPDATE_FAILURE("CUS_1004", "Error while updating customerAuthentication"),
  CUSTOMER_AUTHENTICATION_RESEND_OTP_FAILURE(
      "CUS_1006", "Error while Resending otp customerAuthentication"),

  CUSTOMER_ADDRESS_CREATE_FAILURE("CAD_1020", "Error while creating customer address"),
  CUSTOMER_ADDRESS_UPDATE_FAILURE("CAD_1021", "Error while updating customer address"),
  CUSTOMER_ADDRESS_GET_FAILURE("CAD_1022", "Error while getting customer address"),
  CUSTOMER_ADDRESS_DELETE_FAILURE("CAD_1023", "Error while deleting customer address"),
  CUSTOMER_ADDRESS_GET_ALL_FAILURE("CAD_1024", "Error while getting all customer address"),
  CUSTOMER_ADDRESS_GET_BY_ID_FAILURE("CAD_1025", "Error while getting all customer address"),
  CUSTOMER_ADDRESS_POPULATOR_FAILURE("CAD_1026", "Error while populating customer address"),
  CUSTOMER_DEFAULT_ADDRESS_UPDATE_FAILURE(
      "CAD_1027", "Error while updating default customer address to normal"),
  CUSTOMER_ORDER_ADDRESS_CREATE_FAILURE("CAD_1028", "Error while creating customer order address"),

  CUSTOMER_CREDIT_CARD_CREATE_FAILURE("CC_1040", "Error while creating customer credit card"),
  CUSTOMER_CREDIT_CARD_UPDATE_FAILURE("CC_1041", "Error while updating customer credit card"),
  CUSTOMER_CREDIT_CARD_GET_FAILURE("CC_1042", "Error while getting customer credit card"),
  CUSTOMER_CREDIT_CARD_DELETE_FAILURE("CC_1043", "Error while deleting customer credit card"),
  CUSTOMER_CREDIT_CARD_GET_ALL_FAILURE("CC_1044", "Error while getting all customer credit card"),
  CUSTOMER_CREDIT_CARD_GET_BY_ID_FAILURE("CC_1045", "Error while getting all customer credit card"),
  CUSTOMER_CREDIT_CARD_POPULATOR_FAILURE("CC_1046", "Error while populating customer credit card"),

  CUSTOMER_CREATE_FAILURE("CUS_1060", "Error while creating customer "),
  CUSTOMER_UPDATE_FAILURE("CUS_1061", "Error while updating customer "),
  CUSTOMER_GET_FAILURE("CUS_1062", "Error while getting customer "),
  CUSTOMER_DELETE_FAILURE("CUS_1063", "Error while deleting customer "),
  CUSTOMER_GET_ALL_FAILURE("CUS_1064", "Error while getting all customer "),
  CUSTOMER_GET_BY_ID_FAILURE("CUS_1065", "Error while getting all customer "),
  CUSTOMER_GET_BY_USERNAME_FAILURE("CUS_1066", "Error while getting all customer "),
  CUSTOMER_POPULATOR_FAILURE("CUS_1067", "Error while populating customer "),

  SEND_OTP_SMS_FAILURE_THROUGH_KARIX("CUS_1080", "Error while sending OTP sms through karix"),
  SEND_ORDER_CONFIRMED_SMS_FAILURE_THROUGH_KARIX(
      "CUS_1081", "Error while sending order confirmation sms through karix"),
  SEND_ORDER_SHIPPED_SMS_FAILURE_THROUGH_KARIX(
      "CUS_1082", "Error while sending order shipped sms through karix"),

  CUSTOMER_PRODUCT_CONSUMPTION_PENDING_CREATE_FAILURE(
      "CPCP_2000", "Error while creating customer product consumption pending"),
  CUSTOMER_PRODUCT_CONSUMPTION_PENDING_UPDATE_FAILURE(
      "CPCP_2001", "Error while updating customer product consumption pending"),
  CUSTOMER_PRODUCT_CONSUMPTION_PENDING_GET_FAILURE(
      "CPCP_2002", "Error while getting customer product consumption pending"),
  CUSTOMER_PRODUCT_CONSUMPTION_PENDING_DELETE_FAILURE(
      "CPCP_2003", "Error while deleting customer product consumption pending"),
  CUSTOMER_PRODUCT_CONSUMPTION_PENDING_GET_ALL_FAILURE(
      "CPCP_2004", "Error while getting all customer product consumption pending"),
  CUSTOMER_PRODUCT_CONSUMPTION_PENDING_GET_BY_ID_FAILURE(
      "CPCP_2005", "Error while getting all customer product consumption pending"),
  CUSTOMER_PRODUCT_CONSUMPTION_PENDING_GET_BY_USERNAME_FAILURE(
      "CPCP_2006", "Error while getting all customer product consumption pending"),
  CUSTOMER_PRODUCT_CONSUMPTION_PENDING_POPULATOR_FAILURE(
      "CPCP_2007", "Error while populating customer product consumption pending"),

  CUSTOMER_ORDER_CREATE_FAILURE("CO_2020", "Error while creating customer order "),
  CUSTOMER_ORDER_UPDATE_FAILURE("CO_2021", "Error while updating customer order  "),
  CUSTOMER_ORDER_GET_FAILURE("CO_2022", "Error while getting customer order "),
  CUSTOMER_ORDER_DELETE_FAILURE("CO_2023", "Error while deleting customer order "),
  CUSTOMER_ORDER_GET_ALL_FAILURE("CO_2024", "Error while getting all customer order  "),
  CUSTOMER_ORDER_GET_BY_ID_FAILURE("CO_2025", "Error while getting customer order by id "),
  CUSTOMER_ORDER_GET_BY_CODE_FAILURE("CO_2026", "Error while getting customer order by code "),
  CUSTOMER_ORDER_POPULATOR_FAILURE("CO_2027", "Error while populating customer order  "),
  CUSTOMER_ORDER_CANCEL_EMAIL_FAILURE("CO_2028", "Error while sending order cancel email  "),
  CUSTOMER_SUCCESSFUL_REGISTRATION_EMAIL_FAILURE(
      "CO_2029", "Error while sending successful registration email  "),
  CUSTOMER_ORDER_CONFIRM_EMAIL_FAILURE("CO_2030", "Error while sending order confirm email  "),

  CUSTOMER_EMAIL_VERIFICATION_MAIL_FAILURE(
      "CO_2030", "Error while sending customer email verification mail "),
  CUSTOMER_ORDER_SHIPPED_EMAIL_FAILURE("CO_2031", "Error while sending order shipped email  "),
  CUSTOMER_ORDER_DELIVERED_MAIL_FAILURE("CO_2032", "Error while sending order delivered mail "),
  CUSTOMER_COMPLETE_YOUR_PROFILE_MAIL_FAILURE(
      "CO_2033", "Error while sending customer complete your profile mail "),
  CUSTOMER_ORDER_CONFIRMED_EMAIL_FAILURE("CO_2034", "Error while sending order confirmed email  "),

  CUSTOMER_ORDER_PRODUCT_CREATE_FAILURE("COP_2040", "Error while creating customer order product "),
  CUSTOMER_ORDER_PRODUCT_UPDATE_FAILURE("COP_2041", "Error while updating customer order product "),
  CUSTOMER_ORDER_PRODUCT_GET_FAILURE("COP_2042", "Error while getting customer order product"),
  CUSTOMER_ORDER_PRODUCT_DELETE_FAILURE("COP_2043", "Error while deleting customer order product"),
  CUSTOMER_ORDER_PRODUCT_GET_ALL_FAILURE(
      "COP_2044", "Error while getting all customer order product"),
  CUSTOMER_ORDER_PRODUCT_GET_BY_ID_FAILURE(
      "COP_2045", "Error while getting customer order product by id"),
  CUSTOMER_ORDER_PRODUCT_GET_BY_CODE_FAILURE(
      "COP_2046", "Error while getting customer order product by code"),
  CUSTOMER_ORDER_PRODUCT_POPULATOR_FAILURE(
      "COP_2047", "Error while populating customer order product "),

  CUSTOMER_MULTI_CART_FAILURE("CUS_MULTI_CART_2047", "Error while validating customer multi cart "),

  CUSTOMER_ORDER_STATUS_HISTORY_CREATE_FAILURE(
      "COSH_2060", "Error while creating customer order status history "),
  CUSTOMER_ORDER_STATUS_HISTORY_UPDATE_FAILURE(
      "COSH_2061", "Error while updating customer order status history  "),
  CUSTOMER_ORDER_STATUS_HISTORY_GET_FAILURE(
      "COSH_2062", "Error while getting customer order status history"),
  CUSTOMER_ORDER_STATUS_HISTORY_DELETE_FAILURE(
      "COSH_2063", "Error while deleting customer order status history "),
  CUSTOMER_ORDER_STATUS_HISTORY_GET_ALL_FAILURE(
      "COSH_2064", "Error while getting all customer order status history  "),
  CUSTOMER_ORDER_STATUS_HISTORY_GET_BY_ID_FAILURE(
      "COSH_2065", "Error while getting customer order status history by id "),
  CUSTOMER_ORDER_STATUS_HISTORY_BY_CODE_FAILURE(
      "COSH_2066", "Error while getting customer order status history by code "),

  CUSTOMER_IMAGE_CREATE_FAILURE("CI_2080", "Error while creating customer image "),
  CUSTOMER_IMAGE_DELETE_FAILURE("CI_2081", "Error while deleting customer image for "),

  CUSTOMER_JOIN_WAIT_LIST_CREATE_FAILURE(
      "CUS_JWL_2100", "Error while creating customer join wait list"),
  CUSTOMER_JOIN_WAIT_LIST_GET_BY_PHONE_FAILURE(
      "CUS_JWL_2101", "Error while getting customer join wait list"),

  CUSTOMER_NOTIFICATION_UPDATE_FAILURE(
      "CUS_NOTIFY_2121", "Error while updating customer notification"),
  CUSTOMER_NOTIFICATION_CREATE_FAILURE(
      "CUS_NOTIFY_2122", "Error while creating customer notification"),
  CUSTOMER_NOTIFICATION_GET_FAILURE("CUS_NOTIFY_2123", "Error while getting customer notification"),

  CHECKOUT_AUDIT_CREATE_FAILURE("CHEKCOUT_2200", "Error while creating checkout audit flow"),
  CHECKOUT_AUDIT_UPDATE_FAILURE("CHECKOUT_2201", "Error while updating checkout audit flow"),
  CHECKOUT_AUDIT_GET_FAILURE(
      "CHEKCOUT_2202", "Error while getting checkout audit flow for orderCode");

  private final String errorCode;
  private final String errorMessage;

  CustomerErrorCodes(String errorCode, String errorMessage) {
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
