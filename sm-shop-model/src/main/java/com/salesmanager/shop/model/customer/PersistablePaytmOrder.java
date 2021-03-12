package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistablePaytmOrder extends Entity {
  private String txnId;
  private String bankTxnId;
  private String orderCode;
  private String txnAmount;
  private String txnType;
  private String gatewayName;
  private String bankName;
  private String paymentMode;
  private String refundAmt;
  private String txnDate;
  private String resultStatus;
  private String resultCode;
  private String resultMsg;

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
