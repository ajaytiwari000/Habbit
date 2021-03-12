package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.core.model.order.enums.RazorPayOrderStatus;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCustomerRazorPayOrder extends Entity {
  private String orderCode;
  private OrderStatus status;
  private String entity = "order";
  private int amount;
  private int amountPaid;
  private Long amountDue;
  private String currency = "INR";
  private String receipt;
  private RazorPayOrderStatus razorPayOrderStatus;
  private int paytmentAttempts;
  private String razorPayStatus;
  private Long createdAt;
  private String paymentId;
  private String signature;
  private String razorPayOrderIdPostPayment;
  private String razorPayCustomerId;
  private String customerPhone;
  private String customerEmail;
  private String friendPhone;
  private String customerName;
  private String event;

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public String getEntity() {
    return entity;
  }

  public void setEntity(String entity) {
    this.entity = entity;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public int getAmountPaid() {
    return amountPaid;
  }

  public void setAmountPaid(int amountPaid) {
    this.amountPaid = amountPaid;
  }

  public Long getAmountDue() {
    return amountDue;
  }

  public void setAmountDue(Long amountDue) {
    this.amountDue = amountDue;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getReceipt() {
    return receipt;
  }

  public void setReceipt(String receipt) {
    this.receipt = receipt;
  }

  public RazorPayOrderStatus getRazorPayOrderStatus() {
    return razorPayOrderStatus;
  }

  public void setRazorPayOrderStatus(RazorPayOrderStatus razorPayOrderStatus) {
    this.razorPayOrderStatus = razorPayOrderStatus;
  }

  public int getPaytmentAttempts() {
    return paytmentAttempts;
  }

  public void setPaytmentAttempts(int paytmentAttempts) {
    this.paytmentAttempts = paytmentAttempts;
  }

  public Long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
  }

  public String getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getRazorPayOrderIdPostPayment() {
    return razorPayOrderIdPostPayment;
  }

  public void setRazorPayOrderIdPostPayment(String razorPayOrderIdPostPayment) {
    this.razorPayOrderIdPostPayment = razorPayOrderIdPostPayment;
  }

  public String getRazorPayCustomerId() {
    return razorPayCustomerId;
  }

  public void setRazorPayCustomerId(String razorPayCustomerId) {
    this.razorPayCustomerId = razorPayCustomerId;
  }

  public String getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public String getFriendPhone() {
    return friendPhone;
  }

  public void setFriendPhone(String friendPhone) {
    this.friendPhone = friendPhone;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getRazorPayStatus() {
    return razorPayStatus;
  }

  public void setRazorPayStatus(String razorPayStatus) {
    this.razorPayStatus = razorPayStatus;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }
}
