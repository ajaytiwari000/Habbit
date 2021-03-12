package com.salesmanager.core.model.order;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.customer.CustomerOrderAddress;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.order.orderproduct.CustomerSaleOrderItem;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "CUSTOMER_SALE_ORDER", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class CustomerSaleOrder extends SalesManagerEntity<Long, CustomerSaleOrder> {

  // TODO : To figure out how we store the order history ledger etc.

  /** Table is to maintain order habbit side after placing sale order at unicom */
  private static final long serialVersionUID = 1L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "CUS_SALE_ORDER_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CUS_SALE_ORDER_ID_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "CODE", unique = true, nullable = false)
  private String code;

  @Column(name = "DISPLAY_ORDER_CODE", unique = true, nullable = false)
  private String displayOrderCode;

  @Column(name = "CHANNEL")
  private String channel;

  @Column(name = "CASH_ON_DELIVERY")
  private boolean cashOnDelivery;

  @OneToMany(mappedBy = "customerSaleOrder", cascade = CascadeType.ALL)
  private Set<CustomerSaleOrderItem> saleOrderItems = new LinkedHashSet<>();

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "ADDRESSES", nullable = true, updatable = true)
  private CustomerOrderAddress addresses;

  @Column(name = "CUSTOMER_EMAIL_ADDRESS")
  private String customerEmailAddress;

  @Column(name = "CUSTOMER_CODE")
  private String customerCode;

  @Column(name = "CUSTOMER_PHONE")
  private String customerPhone;

  @Column(name = "CUSTOMER_NAME")
  private String customerName;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "PRIORITY")
  private int priority;

  @Column(name = "ONE_BILLING_SHIPPING_ADDR")
  private boolean oneBillingShippingAddress;

  @Column(name = "DISPLAY_PRICE")
  private Long displayPrice;

  @Column(name = "TOTAL_PRICE")
  private Long totalPrice;

  @Column(name = "DISCOUNT")
  private int totalDiscount;

  @Column(name = "SHIPPING_CHARGES")
  private int totalShippingCharges;

  @Column(name = "CASH_ON_DELIVERY_CHARGES")
  private Long totalCashOnDeliveryCharges;

  @Column(name = "GIFT_WRAP_CHARGES")
  private int totalGiftWrapCharges = 0;

  @Column(name = "PREPAID_AMOUNT")
  private int totalPrepaidAmount = 0;

  @Column(name = "GIFT_MESSAGE")
  private String giftMessage;

  @Column(name = "GIFT_WRAP")
  private boolean giftWrap;

  @OneToMany(mappedBy = "customerSaleOrder", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  @Fetch(value = FetchMode.SUBSELECT)
  private List<SaleOrderShipperDetails> shipperList = new ArrayList<SaleOrderShipperDetails>();

  @OneToMany(mappedBy = "customerSaleOrder", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  @Fetch(value = FetchMode.SUBSELECT)
  private List<SaleOrderItemDetails> orderItemDetails = new ArrayList<SaleOrderItemDetails>();

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "SALE_ORDER_MD_ID", nullable = true, updatable = true)
  private SaleOrderMetaData saleOrderMetaData;

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

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

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

  public boolean isCashOnDelivery() {
    return cashOnDelivery;
  }

  public void setCashOnDelivery(boolean cashOnDelivery) {
    this.cashOnDelivery = cashOnDelivery;
  }

  public Set<CustomerSaleOrderItem> getSaleOrderItems() {
    return saleOrderItems;
  }

  public void setSaleOrderItems(Set<CustomerSaleOrderItem> saleOrderItems) {
    this.saleOrderItems = saleOrderItems;
  }

  public String getCustomerEmailAddress() {
    return customerEmailAddress;
  }

  public void setCustomerEmailAddress(String customerEmailAddress) {
    this.customerEmailAddress = customerEmailAddress;
  }

  public boolean isOneBillingShippingAddress() {
    return oneBillingShippingAddress;
  }

  public void setOneBillingShippingAddress(boolean oneBillingShippingAddress) {
    this.oneBillingShippingAddress = oneBillingShippingAddress;
  }

  public Long getDisplayPrice() {
    return displayPrice;
  }

  public void setDisplayPrice(Long displayPrice) {
    this.displayPrice = displayPrice;
  }

  public Long getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Long totalPrice) {
    this.totalPrice = totalPrice;
  }

  public int getTotalDiscount() {
    return totalDiscount;
  }

  public void setTotalDiscount(int totalDiscount) {
    this.totalDiscount = totalDiscount;
  }

  public int getTotalShippingCharges() {
    return totalShippingCharges;
  }

  public void setTotalShippingCharges(int totalShippingCharges) {
    this.totalShippingCharges = totalShippingCharges;
  }

  public Long getTotalCashOnDeliveryCharges() {
    return totalCashOnDeliveryCharges;
  }

  public void setTotalCashOnDeliveryCharges(Long totalCashOnDeliveryCharges) {
    this.totalCashOnDeliveryCharges = totalCashOnDeliveryCharges;
  }

  public int getTotalGiftWrapCharges() {
    return totalGiftWrapCharges;
  }

  public void setTotalGiftWrapCharges(int totalGiftWrapCharges) {
    this.totalGiftWrapCharges = totalGiftWrapCharges;
  }

  public int getTotalPrepaidAmount() {
    return totalPrepaidAmount;
  }

  public void setTotalPrepaidAmount(int totalPrepaidAmount) {
    this.totalPrepaidAmount = totalPrepaidAmount;
  }

  public String getGiftMessage() {
    return giftMessage;
  }

  public void setGiftMessage(String giftMessage) {
    this.giftMessage = giftMessage;
  }

  public boolean isGiftWrap() {
    return giftWrap;
  }

  public void setGiftWrap(boolean giftWrap) {
    this.giftWrap = giftWrap;
  }

  public CustomerOrderAddress getAddresses() {
    return addresses;
  }

  public void setAddresses(CustomerOrderAddress addresses) {
    this.addresses = addresses;
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public List<SaleOrderShipperDetails> getShipperList() {
    return shipperList;
  }

  public void setShipperList(List<SaleOrderShipperDetails> shipperList) {
    this.shipperList = shipperList;
  }

  public List<SaleOrderItemDetails> getOrderItemDetails() {
    return orderItemDetails;
  }

  public void setOrderItemDetails(List<SaleOrderItemDetails> orderItemDetails) {
    this.orderItemDetails = orderItemDetails;
  }

  public SaleOrderMetaData getSaleOrderMetaData() {
    return saleOrderMetaData;
  }

  public void setSaleOrderMetaData(SaleOrderMetaData saleOrderMetaData) {
    this.saleOrderMetaData = saleOrderMetaData;
  }

  public String getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }
}
