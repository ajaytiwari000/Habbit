package com.salesmanager.core.model.order;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.order.orderstatus.UnicommerceShippingStatus;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "SALE_ORDER_SHIPPER_DETAIL", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class SaleOrderShipperDetails extends SalesManagerEntity<Long, SaleOrderShipperDetails> {

  /** Table is to maintain shipping details habbit side */
  // TODO : To figure out how we store the order history ledger etc.

  /** */
  private static final long serialVersionUID = 1L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "SALE_ORDER_SHIPPER_DETAIL_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "SALE_ORDER_SHIPPER_DETAIL_ID_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "NAME")
  private String name;

  @Column(name = "STATUS")
  private UnicommerceShippingStatus unicommerceShippingStatus;

  @Column(name = "SHIPPING_PROVIDER")
  private String shippingProvider;

  @Column(name = "TRACKING_NUMBER")
  private String trackingNumber;

  @Column(name = "TRACKING_STATUS")
  private String trackingStatus;

  @Column(name = "COURIER_STATUS")
  private String courierStatus;

  @Column(name = "CODE")
  private String code;

  @ManyToOne(targetEntity = CustomerSaleOrder.class)
  @JoinColumn(name = "CUSTOMER_SALE_ORDER_ID")
  private CustomerSaleOrder customerSaleOrder;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UnicommerceShippingStatus getUnicommerceShippingStatus() {
    return unicommerceShippingStatus;
  }

  public void setUnicommerceShippingStatus(UnicommerceShippingStatus unicommerceShippingStatus) {
    this.unicommerceShippingStatus = unicommerceShippingStatus;
  }

  public String getTrackingNumber() {
    return trackingNumber;
  }

  public void setTrackingNumber(String trackingNumber) {
    this.trackingNumber = trackingNumber;
  }

  public String getTrackingStatus() {
    return trackingStatus;
  }

  public void setTrackingStatus(String trackingStatus) {
    this.trackingStatus = trackingStatus;
  }

  public String getCourierStatus() {
    return courierStatus;
  }

  public void setCourierStatus(String courierStatus) {
    this.courierStatus = courierStatus;
  }

  public CustomerSaleOrder getCustomerSaleOrder() {
    return customerSaleOrder;
  }

  public void setCustomerSaleOrder(CustomerSaleOrder customerSaleOrder) {
    this.customerSaleOrder = customerSaleOrder;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getShippingProvider() {
    return shippingProvider;
  }

  public void setShippingProvider(String shippingProvider) {
    this.shippingProvider = shippingProvider;
  }
}
