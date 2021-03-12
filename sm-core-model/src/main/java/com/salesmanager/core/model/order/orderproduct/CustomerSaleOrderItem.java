package com.salesmanager.core.model.order.orderproduct;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.order.CustomerSaleOrder;
import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER_SALE_ORDER_ITEM", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class CustomerSaleOrderItem extends SalesManagerEntity<Long, CustomerSaleOrderItem> {
  private static final long serialVersionUID = 176131742783954627L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "CUS_SALE_ORDER_ITEM_ID")
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CUS_SALE_ORDER_ITEM_ID_NEXT_VALUE")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "CHANNEL_SALE_ORDER_ITEM_CODE")
  private String channelSaleOrderItemCode;

  @Column(name = "ITEM_SKU") // yess !!! rename to code
  private String itemSku;

  @Column(name = "CODE")
  private String code;

  @Column(name = "PRODUCT_NAME", length = 64, nullable = false)
  private String productName;

  @Column(name = "DISPLAY_PRICE")
  private Long displayPrice;

  @JsonIgnore
  @ManyToOne(targetEntity = CustomerSaleOrder.class)
  @JoinColumn(name = "CUS_SALE_ORDER_ID", nullable = false)
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

  public String getChannelSaleOrderItemCode() {
    return channelSaleOrderItemCode;
  }

  public void setChannelSaleOrderItemCode(String channelSaleOrderItemCode) {
    this.channelSaleOrderItemCode = channelSaleOrderItemCode;
  }

  public String getItemSku() {
    return itemSku;
  }

  public void setItemSku(String itemSku) {
    this.itemSku = itemSku;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Long getDisplayPrice() {
    return displayPrice;
  }

  public void setDisplayPrice(Long displayPrice) {
    this.displayPrice = displayPrice;
  }

  public CustomerSaleOrder getCustomerSaleOrder() {
    return customerSaleOrder;
  }

  public void setCustomerSaleOrder(CustomerSaleOrder customerSaleOrder) {
    this.customerSaleOrder = customerSaleOrder;
  }
}
