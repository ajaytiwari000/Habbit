package com.salesmanager.core.model.common.enumerator;

public enum OwnerType {
  ADMIN("admin"),
  INFLUENCER("influencer"),
  CUSTOMER("customer");

  private final String value;

  OwnerType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
