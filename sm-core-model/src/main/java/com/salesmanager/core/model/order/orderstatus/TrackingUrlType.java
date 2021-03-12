package com.salesmanager.core.model.order.orderstatus;

public enum TrackingUrlType {
  URL("URL"),
  PHONE_NUMBER("PHONE_NUMBER");

  private String value;

  private TrackingUrlType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
