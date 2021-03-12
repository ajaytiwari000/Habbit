package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.order.orderstatus.TrackingUrlType;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCustomerOrderStatusHistory extends Entity {
  private String orderCode;
  private Long customerOrderId;
  private OrderStatus status;
  private TrackingUrlType trackingUrlType;
  private Long dateAdded;
  private Long orderedDate;
  private Long deliveredDate;
  private String comments;
  private String trackingUrl;

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public Long getDateAdded() {
    return dateAdded;
  }

  public void setDateAdded(Long dateAdded) {
    this.dateAdded = dateAdded;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public Long getOrderedDate() {
    return orderedDate;
  }

  public void setOrderedDate(Long orderedDate) {
    this.orderedDate = orderedDate;
  }

  public Long getDeliveredDate() {
    return deliveredDate;
  }

  public void setDeliveredDate(Long deliveredDate) {
    this.deliveredDate = deliveredDate;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public Long getCustomerOrderId() {
    return customerOrderId;
  }

  public void setCustomerOrderId(Long customerOrderId) {
    this.customerOrderId = customerOrderId;
  }

  public String getTrackingUrl() {
    return trackingUrl;
  }

  public void setTrackingUrl(String trackingUrl) {
    this.trackingUrl = trackingUrl;
  }

  public TrackingUrlType getTrackingUrlType() {
    return trackingUrlType;
  }

  public void setTrackingUrlType(TrackingUrlType trackingUrlType) {
    this.trackingUrlType = trackingUrlType;
  }
}
