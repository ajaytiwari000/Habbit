package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistablePaytmDetail extends Entity {

  private String requestType;
  private String mid;
  private String websiteName;
  private String orderId;
  private String channelId;
  private String industryTypeId;
  private String callbackUrl;
  private String checksum;

  private PersistablePaytmTxn txnAmount = new PersistablePaytmTxn();
  private PersistablePaytmUserInfo userInfo = new PersistablePaytmUserInfo();

  public String getRequestType() {
    return requestType;
  }

  public void setRequestType(String requestType) {
    this.requestType = requestType;
  }

  public String getMid() {
    return mid;
  }

  public void setMid(String mid) {
    this.mid = mid;
  }

  public String getWebsiteName() {
    return websiteName;
  }

  public void setWebsiteName(String websiteName) {
    this.websiteName = websiteName;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getChannelId() {
    return channelId;
  }

  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }

  public String getIndustryTypeId() {
    return industryTypeId;
  }

  public void setIndustryTypeId(String industryTypeId) {
    this.industryTypeId = industryTypeId;
  }

  public String getCallbackUrl() {
    return callbackUrl;
  }

  public void setCallbackUrl(String callbackUrl) {
    this.callbackUrl = callbackUrl;
  }

  public String getChecksum() {
    return checksum;
  }

  public void setChecksum(String checksum) {
    this.checksum = checksum;
  }

  public PersistablePaytmTxn getTxnAmount() {
    return txnAmount;
  }

  public void setTxnAmount(PersistablePaytmTxn txnAmount) {
    this.txnAmount = txnAmount;
  }

  public PersistablePaytmUserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(PersistablePaytmUserInfo userInfo) {
    this.userInfo = userInfo;
  }
}
