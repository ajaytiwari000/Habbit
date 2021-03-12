package com.salesmanager.shop.error.codes;

public enum CustomerMetaDataErrorCodes {
  CUSTOMER_META_DATA_CREATE_FAILURE("CMD_1000", "Error while creating customer meta data"),
  CUSTOMER_META_DATA_UPDATE_FAILURE("CMD_1001", "Error while updating customer meta data"),
  CUSTOMER_META_DATA_GET_FAILURE("CMD_1002", "Error while getting customer meta data"),
  CUSTOMER_META_DATA_GET_LIST_FAILURE("CMD_1003", "Error while getting customer meta data list");

  private final String errorCode;
  private final String errorMessage;

  CustomerMetaDataErrorCodes(String errorCode, String errorMessage) {
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
