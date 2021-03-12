package com.salesmanager.core.model.order;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(
    name = "SALE_ORDER_SHIPPING_PACKAGE_ITEM_CODE_META_DATA",
    schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class SaleOrderShippingPackageItemCodeMetaData
    extends SalesManagerEntity<Long, SaleOrderShippingPackageItemCodeMetaData> {

  // TODO : To figure out how we store the order history ledger etc.

  /** */
  private static final long serialVersionUID = 1L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(
      name = "SALE_ORDER_SHIPPING_PACKAGE_ITEM_CODE_META_DATA_ID",
      unique = true,
      nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "SALE_ORDER_SHIPPING_PACKAGE_ITEM_CODE_META_DATA_ID_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "ITEM_SKU")
  private String itemSku;

  @Column(name = "ITEM_NAME")
  private String itemName;

  @Column(name = "ITEM_TYPE_IMAGE_URL")
  private String itemTypeImageUrl;

  @Column(name = "ITEM_TYPE_PAGE_URL")
  private String itemTypePageUrl;

  @Column(name = "QUANTITY")
  private int quantity;

  @ManyToOne(targetEntity = SaleOrderShippingPackageMetaData.class)
  @JoinColumn(name = "SALE_ORDER_SHIPPING_PACKAGE_MD_ID")
  private SaleOrderShippingPackageMetaData saleOrderShippingPackageMetaData;

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

  public String getItemSku() {
    return itemSku;
  }

  public void setItemSku(String itemSku) {
    this.itemSku = itemSku;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getItemTypeImageUrl() {
    return itemTypeImageUrl;
  }

  public void setItemTypeImageUrl(String itemTypeImageUrl) {
    this.itemTypeImageUrl = itemTypeImageUrl;
  }

  public String getItemTypePageUrl() {
    return itemTypePageUrl;
  }

  public void setItemTypePageUrl(String itemTypePageUrl) {
    this.itemTypePageUrl = itemTypePageUrl;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
