package com.salesmanager.core.model.order.enums;

public enum DeliveryChargesType {
  DEFAULT(499, 0),
  PREMIUM(500, 0);
  private final int orderAmount;
  private final int deliveryCharges;

  DeliveryChargesType(int orderAmount, int deliveryCharges) {
    this.orderAmount = orderAmount;
    this.deliveryCharges = deliveryCharges;
  }

  public int getOrderAmount() {
    return orderAmount;
  }

  public int getDeliveryCharges() {
    return deliveryCharges;
  }
}
