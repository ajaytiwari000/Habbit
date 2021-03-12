package com.salesmanager.core.model.order.orderstatus;

public enum UnicommerceOrderStatus {
  CREATED("Created"),
  PROCESSING("processing"),
  CANCELLED("Cancelled"),
  COMPLETE("Complete");

  private String value;

  private UnicommerceOrderStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
