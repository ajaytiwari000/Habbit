package com.salesmanager.core.model.payments;

public enum PaymentStatus {
  PENDING("pending"),
  SUCCESSFUL("successful"),
  CANCELLED("cancelled"),
  UNKNOWN("unknown"),
  REFUNDED("refunded");

  private String value;

  private PaymentStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
