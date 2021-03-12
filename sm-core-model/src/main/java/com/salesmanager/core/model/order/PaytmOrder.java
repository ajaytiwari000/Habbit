package com.salesmanager.core.model.order;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import javax.persistence.*;

@Entity
@Table(name = "PAYTM_ORDER", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class PaytmOrder extends SalesManagerEntity<Long, PaytmOrder> {

  private static final long serialVersionUID = 1L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "PAYTM_ORDER_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "PAYTM_ORDER_ID_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "TXN_ID")
  private String txnId;

  @Column(name = "BANK_TXN_ID")
  private String bankTxnId;

  @Column(name = "ORDER_CODE", unique = true, nullable = false)
  private String orderCode;

  @Column(name = "TXN_AMOUNT")
  private String txnAmount;

  @Column(name = "TXN_TYPE")
  private String txnType;

  @Column(name = "GATEWAY_NAME")
  private String gatewayName;

  @Column(name = "BANK_NAME")
  private String bankName;

  @Column(name = "PAYMENT_MODE")
  private String paymentMode;

  @Column(name = "REFUND_AMT")
  private String refundAmt;

  @Column(name = "TXN_DATE")
  private String txnDate;

  @Column(name = "RESULT_STATUS")
  private String resultStatus;

  @Column(name = "RESULT_CODE")
  private String resultCode;

  @Column(name = "RESULT_MSG")
  private String resultMsg;

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

  public String getTxnId() {
    return txnId;
  }

  public void setTxnId(String txnId) {
    this.txnId = txnId;
  }

  public String getBankTxnId() {
    return bankTxnId;
  }

  public void setBankTxnId(String bankTxnId) {
    this.bankTxnId = bankTxnId;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getTxnAmount() {
    return txnAmount;
  }

  public void setTxnAmount(String txnAmount) {
    this.txnAmount = txnAmount;
  }

  public String getTxnType() {
    return txnType;
  }

  public void setTxnType(String txnType) {
    this.txnType = txnType;
  }

  public String getGatewayName() {
    return gatewayName;
  }

  public void setGatewayName(String gatewayName) {
    this.gatewayName = gatewayName;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getPaymentMode() {
    return paymentMode;
  }

  public void setPaymentMode(String paymentMode) {
    this.paymentMode = paymentMode;
  }

  public String getRefundAmt() {
    return refundAmt;
  }

  public void setRefundAmt(String refundAmt) {
    this.refundAmt = refundAmt;
  }

  public String getTxnDate() {
    return txnDate;
  }

  public void setTxnDate(String txnDate) {
    this.txnDate = txnDate;
  }

  public AuditSection getAuditSection() {
    return auditSection;
  }

  public String getResultStatus() {
    return resultStatus;
  }

  public void setResultStatus(String resultStatus) {
    this.resultStatus = resultStatus;
  }

  public String getResultCode() {
    return resultCode;
  }

  public void setResultCode(String resultCode) {
    this.resultCode = resultCode;
  }

  public String getResultMsg() {
    return resultMsg;
  }

  public void setResultMsg(String resultMsg) {
    this.resultMsg = resultMsg;
  }
}
