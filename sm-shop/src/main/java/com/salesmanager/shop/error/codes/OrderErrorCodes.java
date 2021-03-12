package com.salesmanager.shop.error.codes;

public enum OrderErrorCodes {
  CREATE_SALE_ORDER("SALE_ORDER_001", "Error while creating sale order"),
  SEARCH_SALE_ORDER("SALE_ORDER_002", "Error while searching sale order"),
  GET_SALE_ORDER("SALE_ORDER_003", "Error while getting sale order"),
  CANCEL_SALE_ORDER("SALE_ORDER_004", "Error while cancel sale order"),
  GET_CODE_SALE_ORDER("SALE_ORDER_005", "Error while getting sale order using code"),
  UPDATE_SALE_ORDER("SALE_ORDER_006", "Error while updating sale order"),

  CREATE_SALE_ORDER_SHIPPING_DETAIL(
      "SO_SHIPPING_DETAIL_020", "Error while creating sale order shipping details"),
  UPDATE_SALE_ORDER_SHIPPING_DETAIL(
      "SO_SHIPPING_DETAIL_021", "Error while updating sale order shipping details"),

  CREATE_SALE_ORDER_ITEM_DETAIL(
      "SO_ITEM_DETAIL_040", "Error while creating sale order item details"),
  UPDATE_SALE_ORDER_ITEM_DETAIL(
      "SO_ITEM_DETAIL_041", "Error while updating sale order item details"),

  CREATE_SALE_ORDER_META_DATA("SO_META_DATA_060", "Error while creating sale order meta data"),
  UPDATE_SALE_ORDER_META_DATA("SO_META_DATA_061", "Error while updating sale order meta data"),

  CREATE_SALE_ORDER_SHIPPING_DETAIL_META_DATA(
      "SO_SHIPPING_DETAIL_META_DATA_080",
      "Error while creating sale order shipping details meta data"),
  UPDATE_SALE_ORDER_SHIPPING_DETAIL_META_DATA(
      "SO_SHIPPING_DETAIL_META_DATA_081",
      "Error while updating sale order shipping details meta data"),

  CREATE_SALE_ORDER_ITEM_DETAIL_META_DATA(
      "SO_ITEM_DETAIL_META_DATA_100", "Error while creating sale order item details meta data"),
  UPDATE_SALE_ORDER_ITEM_DETAIL_META_DATA(
      "SO_ITEM_DETAIL_META_DATA_101", "Error while updating sale order item details meta data"),
  ;

  private final String errorCode;
  private final String errorMessage;

  OrderErrorCodes(String errorCode, String errorMessage) {
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
