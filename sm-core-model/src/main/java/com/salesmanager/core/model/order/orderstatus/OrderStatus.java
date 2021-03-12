package com.salesmanager.core.model.order.orderstatus;
/** having order status at habbit side as well as unicom side */
public enum OrderStatus {
  CREATED("Created"),
  PENDING("Pending"),
  CONFIRMED("Confirmed"),
  TRANSIT("Transit"),
  ORDERED("Ordered"),
  PROCESSED("Processed"),
  DELIVERED("Delivered"),
  REFUNDED("Refunded"),
  CANCELLED("Cancelled"),
  RETURNED("Returned"),
  DISPATCHED("Dispatched"),
  LOCATION_NOT_SERVICEABLE("Location not serviceable"),
  UNFULFILLABLE("Unfulfillable"),
  FULFILLABLE("Fulfillable"),
  PICKING_FOR_INVOICING("Picking for invoicing");

  private String value;

  private OrderStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
