package com.salesmanager.shop.model.customer;

public class PersistablePaytmTxn {
  private String value;
  private String currency;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }
}
