package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCreditCard extends Entity {
  private PersistableCustomer customer;
  private String ownerName;
  private String cardNumber;
  private String expiryMonth;
  private String expiryYear;
  private String cvv;
  private boolean defaultCreditCard;

  public PersistableCustomer getCustomer() {
    return customer;
  }

  public void setCustomer(PersistableCustomer customer) {
    this.customer = customer;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getExpiryMonth() {
    return expiryMonth;
  }

  public void setExpiryMonth(String expiryMonth) {
    this.expiryMonth = expiryMonth;
  }

  public String getExpiryYear() {
    return expiryYear;
  }

  public void setExpiryYear(String expiryYear) {
    this.expiryYear = expiryYear;
  }

  public String getCvv() {
    return cvv;
  }

  public void setCvv(String cvv) {
    this.cvv = cvv;
  }

  public boolean isDefaultCreditCard() {
    return defaultCreditCard;
  }

  public void setDefaultCreditCard(boolean defaultCreditCard) {
    this.defaultCreditCard = defaultCreditCard;
  }
}
