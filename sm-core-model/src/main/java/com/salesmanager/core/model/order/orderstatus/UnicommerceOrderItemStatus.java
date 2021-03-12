package com.salesmanager.core.model.order.orderstatus;

public enum UnicommerceOrderItemStatus {
  CREATED("Created"),
  CANCELLED("Cancelled"),
  DISPATCHED("Dispatched"),
  DELIVERED("Delivered"),
  LOCATION_NOT_SERVICEABLE("Location not serviceable"),
  UNFULFILLABLE("Unfulfillable"),
  FULFILLABLE("Fulfillable"),
  PICKING_FOR_INVOICING("Picking for invoicing");

  private String value;

  private UnicommerceOrderItemStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
