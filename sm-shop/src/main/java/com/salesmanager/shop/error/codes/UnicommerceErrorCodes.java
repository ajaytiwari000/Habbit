package com.salesmanager.shop.error.codes;

public enum UnicommerceErrorCodes {
  GET_ACTIVE_UNICOM_AUTH_TOKEN("UNICOM_001", "Error while fetch active token"),
  CREATE_UNICOM_AUTH_TOKEN("UNICOM_002", "Error while creating token"),
  UPDATE_UNICOM_AUTH_TOKEN("UNICOM_003", "Error while updating token"),
  CREATE_UNICOM_SALE_ORDER("UNICOM_004", "Error while creating sale order"),
  SEARCH_UNICOM_SALE_ORDER("UNICOM_005", "Error while searching sale order"),
  GET_UNICOM_SALE_ORDER("UNICOM_006", "Error while getting sale order"),
  CANCEL_UNICOM_SALE_ORDER("UNICOM_007", "Error while cancel sale order"),

  UNICOM_ACCESS_TOKEN("UNICOM_020", "Error while creating access token"),
  UNICOM_REFRESH_TOKEN("UNICOM_021", "Error while refreshing access token");

  private final String errorCode;
  private final String errorMessage;

  UnicommerceErrorCodes(String errorCode, String errorMessage) {
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
