package com.salesmanager.core.model.order;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.order.orderstatus.UnicommerceOrderItemStatus;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "SALE_ORDER_ITEM_DETAIL", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class SaleOrderItemDetails extends SalesManagerEntity<Long, SaleOrderItemDetails> {

  /** Table is to maintain order item details habbit side */
  // TODO : To figure out how we store the order history ledger etc.

  /** */
  private static final long serialVersionUID = 1L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "SALE_ORDER_ITEM_DETAIL_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "SALE_ORDER_ITEM_DETAIL_ID_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "STATUS_CODE")
  private UnicommerceOrderItemStatus statusCode;

  @Column(name = "CODE")
  private String code;

  @Column(name = "SELLER_SKU_CODE")
  private String sellerSkuCode;

  @Column(name = "CHANNEL_SALE_ORDER_ITEM_CODE")
  private String channelSaleOrderItemCode;

  @Column(name = "ITEM_SKU") // yess !!! rename to code
  private String itemSku;

  @Column(name = "PRODUCT_NAME", length = 64, nullable = false)
  private String productName;

  @Column(name = "DISPLAY_PRICE")
  private Long displayPrice;

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

  public UnicommerceOrderItemStatus getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(UnicommerceOrderItemStatus statusCode) {
    this.statusCode = statusCode;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getSellerSkuCode() {
    return sellerSkuCode;
  }

  public void setSellerSkuCode(String sellerSkuCode) {
    this.sellerSkuCode = sellerSkuCode;
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
