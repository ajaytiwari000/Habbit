package com.salesmanager.core.model.order.orderstatus;

public enum UnicommerceShippingStatus {
  CREATED("Created"),
  CANCELLED("Cancelled"),
  PICKED("Picked"),
  PACKED("Packed"),
  READY_TO_SHIP("Read to ship"),
  DISPATCHED("Dispatched"),
  SHIPPED("Shipped"),
  DELIVERED("Delivered"),
  RETURN_EXPECTED("RETURN_EXPECTED");

  private String value;

  private UnicommerceShippingStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
