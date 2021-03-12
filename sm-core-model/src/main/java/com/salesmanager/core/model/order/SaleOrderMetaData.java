package com.salesmanager.core.model.order;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "SALE_ORDER_META_DATA", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class SaleOrderMetaData extends SalesManagerEntity<Long, SaleOrderMetaData> {

  /**
   * Table is to maintain unicom sale order details and link to customer sale order as metaData.
   * will add and update entry here when getting any update from unicom regarding order through get
   * api
   */
  // TODO : To figure out how we store the order history ledger etc.

  /** */
  private static final long serialVersionUID = 1L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "SALE_ORDER_META_DATA_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "SALE_ORDER_META_DATA_ID_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;
  //          "returns": [],
  //          "customFieldValues": [],

  @Column(name = "CODE")
  private String code;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "CANCELLABLE")
  private boolean cancellable;

  @Column(name = "REVERSE_PICKABLE")
  private boolean reversePickable;

  @Column(name = "PACKET_CONFIGURABLE")
  private boolean packetConfigurable;

  @Column(name = "C_FORM_PROVIDED")
  private boolean cFormProvided;

  @Column(name = "TOTAL_DISCOUNT")
  private int totalDiscount;

  @Column(name = "TOTAL_SHIPPING_CHARGES")
  private int totalShippingCharges;

  @Column(name = "ADDITIONAL_INFO")
  private String additionalInfo;

  @OneToMany(mappedBy = "saleOrderMetaData", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  @Fetch(value = FetchMode.SUBSELECT)
  private List<SaleOrderShippingPackageMetaData> shipperList =
      new ArrayList<SaleOrderShippingPackageMetaData>();

  @OneToMany(mappedBy = "saleOrderMetaData", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  @Fetch(value = FetchMode.SUBSELECT)
  private List<SaleOrderItemsMetaData> orderItemDetails = new ArrayList<SaleOrderItemsMetaData>();

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

  public boolean isCancellable() {
    return cancellable;
  }

  public void setCancellable(boolean cancellable) {
    this.cancellable = cancellable;
  }

  public boolean isReversePickable() {
    return reversePickable;
  }

  public void setReversePickable(boolean reversePickable) {
    this.reversePickable = reversePickable;
  }

  public boolean isPacketConfigurable() {
    return packetConfigurable;
  }

  public void setPacketConfigurable(boolean packetConfigurable) {
    this.packetConfigurable = packetConfigurable;
  }

  public boolean iscFormProvided() {
    return cFormProvided;
  }

  public void setcFormProvided(boolean cFormProvided) {
    this.cFormProvided = cFormProvided;
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

  public String getAdditionalInfo() {
    return additionalInfo;
  }

  public void setAdditionalInfo(String additionalInfo) {
    this.additionalInfo = additionalInfo;
  }

  public List<SaleOrderShippingPackageMetaData> getShipperList() {
    return shipperList;
  }

  public void setShipperList(List<SaleOrderShippingPackageMetaData> shipperList) {
    this.shipperList = shipperList;
  }

  public List<SaleOrderItemsMetaData> getOrderItemDetails() {
    return orderItemDetails;
  }

  public void setOrderItemDetails(List<SaleOrderItemsMetaData> orderItemDetails) {
    this.orderItemDetails = orderItemDetails;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
