package com.salesmanager.shop.error.codes;

public enum PaymentErrorCodes {

  // Error codes related to API's for Admin Portal

  CART_CREATE_FAILURE("CT_1000", "Error while creating cart"),
  CART_UPDATE_FAILURE("CT_1001", "Error while updating cart"),
  CART_DELETE_FAILURE("CT_1002", "Error while deleting cart for Id "),
  CART_GET_BY_ID_FAILURE("CT_1003", "Error while getting cart for Id "),
  CART_GET_BY_CODE_FAILURE("CT_1004", "Error while getting cart for Id "),
  CART_INVALID_EVENT("CT_1005", "Cart invalid event"),
  CART_CUSTOMER_MINIMUM_REWARD_FAILURE("CT_1006", "Minimum Habbit Points required "),
  CART_CUSTOMER_MINIMUM_CART_VALUE("CT_1007", "Minimum Cart Value required Rs."),

  CART_ITEM_CREATE_FAILURE("CI_1020", "Error while creating cart item"),
  CART_ITEM_UPDATE_FAILURE("CI_1021", "Error while updating cart item"),
  CART_ITEM_DELETE_FAILURE("CI_1022", "Error while deleting cart item for Id "),
  CART_ITEM_GET_BY_ID_FAILURE("CI_1023", "Error while getting cart item for Id "),
  CART_ITEM_GET_BY_CODE_FAILURE("CT_1024", "Error while getting cart for Id "),
  CART_ITEM_DELETE_ALL_FAILURE("CI_1025", "Error while deleting all cart item for Id "),

  RAZORPAY_ORDER_CREATE_FAILURE("RP_1040", "Error while creating razorpay order"),
  RAZORPAY_GET_BY_RECEIPT_FAILURE("RP_1041", "Error while getting razorpay for order "),
  RAZORPAY_ORDER_UPDATE_FAILURE("RP_1042", "Error while updating razorpay order"),
  RAZORPAY_VERIFY_SIGNATURE_FAILURE("RP_1043", "Error while verifying razorpay signature"),
  RAZORPAY_ORDER_CANCELLED("RP_1044", "razorPay order cancelled"),
  OUR_SIDE_RAZORPAY_ORDER_CREATE_FAILURE("RP_1045", "Error while creating our side razorpay order"),
  RAZORPAY_ORDER_REFUND("RP_1046", "razorPay refund failure"),
  RAZORPAY_GET_BY_ORDER_CDOE_FAILURE("RP_1047", "Error while getting razorpay for order code "),

  PAYTM_CREATE_FAILURE("PAYTM_1060", "Error while creating paytm order"),
  PAYTM_VALIDATE_CHECKSUM_FAILURE("PAYTM_1061", "Error while validating paytm checksum"),
  PAYTM_ORDER_CREATE_FAILURE("PAYTM_1062", "Error while creating paytm order"),
  ;

  private final String errorCode;
  private final String errorMessage;

  PaymentErrorCodes(String errorCode, String errorMessage) {
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
