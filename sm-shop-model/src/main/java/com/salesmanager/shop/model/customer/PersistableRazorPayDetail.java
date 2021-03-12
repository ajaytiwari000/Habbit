package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableRazorPayDetail extends Entity {
  private String orderCode;
  private String razorPayOrderCode;
  private String currency;
  private Long amount;
  private String userPhone;
  private String friendPhone;
  private String email;
  private String razorPayCustomerId;
  private String razorPayAccessKey;
  private String razorPaySecretKey;
  private String razorPaySignature;

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getRazorPayOrderCode() {
    return razorPayOrderCode;
  }

  public void setRazorPayOrderCode(String razorPayOrderCode) {
    this.razorPayOrderCode = razorPayOrderCode;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRazorPayAccessKey() {
    return razorPayAccessKey;
  }

  public void setRazorPayAccessKey(String razorPayAccessKey) {
    this.razorPayAccessKey = razorPayAccessKey;
  }

  public String getRazorPaySecretKey() {
    return razorPaySecretKey;
  }

  public void setRazorPaySecretKey(String razorPaySecretKey) {
    this.razorPaySecretKey = razorPaySecretKey;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public String getFriendPhone() {
    return friendPhone;
  }

  public void setFriendPhone(String friendPhone) {
    this.friendPhone = friendPhone;
  }

  public String getRazorPayCustomerId() {
    return razorPayCustomerId;
  }

  public void setRazorPayCustomerId(String razorPayCustomerId) {
    this.razorPayCustomerId = razorPayCustomerId;
  }

  public String getUserPhone() {
    return userPhone;
  }

  public String getRazorPaySignature() {
    return razorPaySignature;
  }

  public void setRazorPaySignature(String razorPaySignature) {
    this.razorPaySignature = razorPaySignature;
  }
}
