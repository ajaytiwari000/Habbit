package com.salesmanager.core.model.order;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.order.enums.RazorPayOrderStatus;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "CUSTOMER_RAZOR_PAY_ORDER", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class CustomerRazorPayOrder extends SalesManagerEntity<Long, CustomerRazorPayOrder> {

  // TODO : To figure out how we store the order history ledger etc.

  /** */
  private static final long serialVersionUID = 1L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "CUS_RAZOR_PAY_ORDER_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CUS_RAZOR_PAY_ORDER_ID_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "ORDER_CODE", unique = true, nullable = false)
  private String orderCode;

  @Column(name = "ORDER_STATUS")
  @Enumerated(value = EnumType.STRING)
  private OrderStatus status;

  @Column(name = "ENTITY")
  private String entity = "order";

  @Column(name = "AMOUNT")
  private int amount;

  @Column(name = "AMOUNT_PAID")
  private int amountPaid;

  @Column(name = "CUSTOMER_PHONE", nullable = true)
  private String customerPhone;

  @Column(name = "CUSTOMER_EMAIL", nullable = true)
  private String customerEmail;

  @Column(name = "FRIEND_PHONE", nullable = true)
  private String friendPhone;

  @Column(name = "CUSTOMER_NAME", nullable = true)
  private String customerName;

  @Column(name = "AMOUNT_DUE")
  private Long amountDue;

  @Column(name = "CURRENCY")
  private String currency = "INR";

  @Column(name = "RECEIPT")
  private String receipt;

  @Column(name = "STATUS")
  @Enumerated(value = EnumType.STRING)
  private RazorPayOrderStatus razorPayOrderStatus;

  @Column(name = "PAYMENT_ATTEMPTS")
  private int paytmentAttempts;

  @Column(name = "CREATED_AT")
  private Long createdAt;

  @Column(name = "RAZOR_PAY_CUSTOMER_ID")
  private String razorPayCustomerId;

  @Column(name = "PAYMENT_ID")
  private String razorPayPaymentId;

  @Column(name = "RAZORPAY_SIGNATURE")
  private String signature;

  @Column(name = "RAZORPAY_ORDER_ID_POST_PAYMENT")
  private String razorPayOrderIdPostPayment;

  @Column(name = "REFUND_ID")
  private String refundId;

  @Column(name = "REFUNDED_AMOUNT")
  private int refundedAmount;

  @Column(name = "REFUND_CREATED_AT")
  private Long refundCreatedAt;

  @Column(name = "REFUND_STATUS")
  private OrderStatus refundStatus;

  @Column(name = "REFUND_SPEED_PROCESSED")
  private String refundSpeedProcessed;

  @Column(name = "REFUND_SPEED_REQUESTED")
  private String refundSpeedRequested;

  @Column(name = "WEBHOOKS_UPDATED")
  private boolean webhooksUpdated = false;

  public AuditSection getAuditSection() {
    return auditSection;
  }

  public void setAuditSection(AuditSection auditSection) {
    this.auditSection = auditSection;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

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

  public String getRazorPayCustomerId() {
    return razorPayCustomerId;
  }

  public void setRazorPayCustomerId(String razorPayCustomerId) {
    this.razorPayCustomerId = razorPayCustomerId;
  }

  public String getRazorPayPaymentId() {
    return razorPayPaymentId;
  }

  public void setRazorPayPaymentId(String razorPayPaymentId) {
    this.razorPayPaymentId = razorPayPaymentId;
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

  public String getRefundId() {
    return refundId;
  }

  public void setRefundId(String refundId) {
    this.refundId = refundId;
  }

  public int getRefundedAmount() {
    return refundedAmount;
  }

  public void setRefundedAmount(int refundedAmount) {
    this.refundedAmount = refundedAmount;
  }

  public Long getRefundCreatedAt() {
    return refundCreatedAt;
  }

  public void setRefundCreatedAt(Long refundCreatedAt) {
    this.refundCreatedAt = refundCreatedAt;
  }

  public OrderStatus getRefundStatus() {
    return refundStatus;
  }

  public void setRefundStatus(OrderStatus refundStatus) {
    this.refundStatus = refundStatus;
  }

  public String getRefundSpeedProcessed() {
    return refundSpeedProcessed;
  }

  public void setRefundSpeedProcessed(String refundSpeedProcessed) {
    this.refundSpeedProcessed = refundSpeedProcessed;
  }

  public String getRefundSpeedRequested() {
    return refundSpeedRequested;
  }

  public void setRefundSpeedRequested(String refundSpeedRequested) {
    this.refundSpeedRequested = refundSpeedRequested;
  }

  public boolean isWebhooksUpdated() {
    return webhooksUpdated;
  }

  public void setWebhooksUpdated(boolean webhooksUpdated) {
    this.webhooksUpdated = webhooksUpdated;
  }
}
