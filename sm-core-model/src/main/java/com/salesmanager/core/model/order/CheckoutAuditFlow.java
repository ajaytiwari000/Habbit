package com.salesmanager.core.model.order;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "CHECKOUT_AUDIT_FLOW", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class CheckoutAuditFlow extends SalesManagerEntity<Long, CheckoutAuditFlow> {

  /** */
  private static final long serialVersionUID = 1L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "CHECKOUT_AUDIT_FLOW_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CHECKOUT_AUDIT_FLOW_ID_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "ORDER_CODE", unique = true, nullable = false)
  private String orderCode;

  @Column(name = "RAZORPAY_ORDER_CODE")
  private String razorPayOrderCode;

  @Column(name = "RAZORPAY_ORDER_CREATED")
  private boolean razorpayOrderCreated;

  @Column(name = "CUSTOMER_RAZORPAY_ORDER_CREATED")
  private boolean customerRazorpayOrderCreated;

  @Column(name = "CUSTOMER_ORDER_CREATED")
  private boolean customerOrderCreated;

  @Column(name = "CUSTOMER_ORDER_CANCELED")
  private boolean customerOrderCanceled;

  @Column(name = "PAYMENT_VERIFIED")
  private boolean paymentVerified;

  @Column(name = "CUSTOMER_RAZORPAY_ORDER_UPDATED")
  private boolean customerRazorpayOrderUpdated;

  @Column(name = "CUSTOMER_SALE_ORDER_CREATED")
  private boolean customerSaleOrderCreated;

  @Column(name = "CUSTOMER_ORDER_UPDATED")
  private boolean customerOrderUpdated;

  @Column(name = "MAIL_SENT")
  private boolean mailSent;

  @Column(name = "SMS_SENT")
  private boolean smsSent;

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

  public boolean isRazorpayOrderCreated() {
    return razorpayOrderCreated;
  }

  public void setRazorpayOrderCreated(boolean razorpayOrderCreated) {
    this.razorpayOrderCreated = razorpayOrderCreated;
  }

  public boolean isCustomerRazorpayOrderCreated() {
    return customerRazorpayOrderCreated;
  }

  public void setCustomerRazorpayOrderCreated(boolean customerRazorpayOrderCreated) {
    this.customerRazorpayOrderCreated = customerRazorpayOrderCreated;
  }

  public boolean isCustomerOrderCreated() {
    return customerOrderCreated;
  }

  public void setCustomerOrderCreated(boolean customerOrderCreated) {
    this.customerOrderCreated = customerOrderCreated;
  }

  public boolean isCustomerOrderCanceled() {
    return customerOrderCanceled;
  }

  public void setCustomerOrderCanceled(boolean customerOrderCanceled) {
    this.customerOrderCanceled = customerOrderCanceled;
  }

  public boolean isPaymentVerified() {
    return paymentVerified;
  }

  public void setPaymentVerified(boolean paymentVerified) {
    this.paymentVerified = paymentVerified;
  }

  public boolean isCustomerRazorpayOrderUpdated() {
    return customerRazorpayOrderUpdated;
  }

  public void setCustomerRazorpayOrderUpdated(boolean customerRazorpayOrderUpdated) {
    this.customerRazorpayOrderUpdated = customerRazorpayOrderUpdated;
  }

  public boolean isCustomerSaleOrderCreated() {
    return customerSaleOrderCreated;
  }

  public void setCustomerSaleOrderCreated(boolean customerSaleOrderCreated) {
    this.customerSaleOrderCreated = customerSaleOrderCreated;
  }

  public boolean isCustomerOrderUpdated() {
    return customerOrderUpdated;
  }

  public void setCustomerOrderUpdated(boolean customerOrderUpdated) {
    this.customerOrderUpdated = customerOrderUpdated;
  }

  public boolean isMailSent() {
    return mailSent;
  }

  public void setMailSent(boolean mailSent) {
    this.mailSent = mailSent;
  }

  public boolean isSmsSent() {
    return smsSent;
  }

  public void setSmsSent(boolean smsSent) {
    this.smsSent = smsSent;
  }

  public String getRazorPayOrderCode() {
    return razorPayOrderCode;
  }

  public void setRazorPayOrderCode(String razorPayOrderCode) {
    this.razorPayOrderCode = razorPayOrderCode;
  }
}
