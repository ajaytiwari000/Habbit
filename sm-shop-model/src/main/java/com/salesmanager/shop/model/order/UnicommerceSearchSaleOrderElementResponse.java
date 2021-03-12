package com.salesmanager.shop.model.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnicommerceSearchSaleOrderElementResponse extends Entity {
  private String displayOrderCode;
  private String code;
  private String channel;
  private Long displayOrderDateTime;
  private String status;
  private Long created;
  private Long updated;
  private String notificationEmail;
  private String notificationMobile;

  public String getDisplayOrderCode() {
    return displayOrderCode;
  }

  public void setDisplayOrderCode(String displayOrderCode) {
    this.displayOrderCode = displayOrderCode;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public Long getDisplayOrderDateTime() {
    return displayOrderDateTime;
  }

  public void setDisplayOrderDateTime(Long displayOrderDateTime) {
    this.displayOrderDateTime = displayOrderDateTime;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Long getCreated() {
    return created;
  }

  public void setCreated(Long created) {
    this.created = created;
  }

  public Long getUpdated() {
    return updated;
  }

  public void setUpdated(Long updated) {
    this.updated = updated;
  }

  public String getNotificationEmail() {
    return notificationEmail;
  }

  public void setNotificationEmail(String notificationEmail) {
    this.notificationEmail = notificationEmail;
  }

  public String getNotificationMobile() {
    return notificationMobile;
  }

  public void setNotificationMobile(String notificationMobile) {
    this.notificationMobile = notificationMobile;
  }
}
