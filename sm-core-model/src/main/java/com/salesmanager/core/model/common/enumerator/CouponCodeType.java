package com.salesmanager.core.model.common.enumerator;

public enum CouponCodeType {
  CART("cart"),
  CATEGORY("category"),
  PRODUCT("product");
  private final String value;

  CouponCodeType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
