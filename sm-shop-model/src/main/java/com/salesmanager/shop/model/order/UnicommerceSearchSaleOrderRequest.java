package com.salesmanager.shop.model.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnicommerceSearchSaleOrderRequest {
  private String displayOrderCode;
  private String channel;
  private String status;
  private String customerEmailOrMobile;
  private String customerName;
  private boolean cashOnDelivery;
  private Date fromDate;
  private Date toDate;
  private List<String> facilityCodes = new ArrayList<>();
  private UnicommerceSaleOrderSearchOptionRequest searchOptions;
  private int updatedSinceInMinutes;
  private boolean onHold;

  public String getDisplayOrderCode() {
    return displayOrderCode;
  }

  public void setDisplayOrderCode(String displayOrderCode) {
    this.displayOrderCode = displayOrderCode;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCustomerEmailOrMobile() {
    return customerEmailOrMobile;
  }

  public void setCustomerEmailOrMobile(String customerEmailOrMobile) {
    this.customerEmailOrMobile = customerEmailOrMobile;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public boolean isCashOnDelivery() {
    return cashOnDelivery;
  }

  public void setCashOnDelivery(boolean cashOnDelivery) {
    this.cashOnDelivery = cashOnDelivery;
  }

  public Date getFromDate() {
    return fromDate;
  }

  public void setFromDate(Date fromDate) {
    this.fromDate = fromDate;
  }

  public Date getToDate() {
    return toDate;
  }

  public void setToDate(Date toDate) {
    this.toDate = toDate;
  }

  public List<String> getFacilityCodes() {
    return facilityCodes;
  }

  public void setFacilityCodes(List<String> facilityCodes) {
    this.facilityCodes = facilityCodes;
  }

  public UnicommerceSaleOrderSearchOptionRequest getSearchOptions() {
    return searchOptions;
  }

  public void setSearchOptions(UnicommerceSaleOrderSearchOptionRequest searchOptions) {
    this.searchOptions = searchOptions;
  }

  public int getUpdatedSinceInMinutes() {
    return updatedSinceInMinutes;
  }

  public void setUpdatedSinceInMinutes(int updatedSinceInMinutes) {
    this.updatedSinceInMinutes = updatedSinceInMinutes;
  }

  public boolean isOnHold() {
    return onHold;
  }

  public void setOnHold(boolean onHold) {
    this.onHold = onHold;
  }
}
