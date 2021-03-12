package com.salesmanager.core.model.order.orderstatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER_ORDER_STATUS_HISTORY", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class CustomerOrderStatusHistory extends SalesManagerEntity<Long, CustomerOrderStatusHistory>
    implements Auditable {
  private static final long serialVersionUID = 3438730310126102187L;
  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "CUS_ORDER_STATUS_HISTORY_ID")
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CUS_STATUS_HIST_ID_NEXT_VALUE")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @JsonIgnore
  @ManyToOne(targetEntity = Customer.class)
  @JoinColumn(name = "CUSTOMERS_ID")
  private Customer customer;

  @JoinColumn(name = "ORDER_CODE", nullable = false)
  private String orderCode;

  @JoinColumn(name = "CUSTOMER_ORDER_ID", nullable = false)
  private Long customerOrderId;

  @Enumerated(value = EnumType.STRING)
  private OrderStatus status;

  @Enumerated(value = EnumType.STRING)
  private TrackingUrlType trackingUrlType;

  @Column(name = "DATE_ADDED")
  private Long dateAdded;

  @Column(name = "ORDERED_DATE")
  private Long orderedDate;

  @Column(name = "DELIVERED_DATE")
  private Long deliveredDate;

  @Column(name = "COMMENTS")
  private String comments;

  @Column(name = "TRACKING_URL")
  private String trackingUrl;

  @Column(name = "ACTIVE_ENTITY")
  private boolean activeEntity;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  @Override
  public AuditSection getAuditSection() {
    return auditSection;
  }

  @Override
  public void setAuditSection(AuditSection auditSection) {
    this.auditSection = auditSection;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public boolean isActiveEntity() {
    return activeEntity;
  }

  public void setActiveEntity(boolean activeEntity) {
    this.activeEntity = activeEntity;
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
