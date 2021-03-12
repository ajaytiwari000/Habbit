package com.salesmanager.core.model.common.enumerator;

public enum CouponDiscountType {
  PERCENTAGE("percentage"),
  AMOUNT_VALUE("amountValue");
  private final String value;

  CouponDiscountType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
