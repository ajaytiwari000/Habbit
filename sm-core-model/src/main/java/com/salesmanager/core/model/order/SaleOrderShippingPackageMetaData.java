package com.salesmanager.core.model.order;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "SALE_ORDER_SHIPPING_PACKAGE_META_DATA", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class SaleOrderShippingPackageMetaData
    extends SalesManagerEntity<Long, SaleOrderShippingPackageMetaData> {

  /**
   * Table is to maintain unicom shipping package metaData. will add and update entry here when
   * getting any update from unicom regarding order through get api
   */
  private static final long serialVersionUID = 1L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "SALE_ORDER_SHIPPING_PACKAGE_META_DATA_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "SALE_ORDER_SHIPPING_PACKAGE_META_DATA_ID_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "CODE")
  private String code;

  @Column(name = "SALE_ORDER_CODE")
  private String saleOrderCode;

  @Column(name = "CHANNEL")
  private String channel;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "SHIPPING_PACKAGE_TYPE")
  private String shippingPackageType;

  @Column(name = "SHIPPING_PROVIDER")
  private String shippingProvider;

  @Column(name = "SHIPPING_METHOD")
  private String shippingMethod;

  @Column(name = "TRACKING_NUMBER")
  private String trackingNumber;

  @Column(name = "TRACKING_STATUS")
  private String trackingStatus;

  @Column(name = "COURIER_STATUS")
  private String courierStatus;

  @Column(name = "ESTIMATE_WEIGHT")
  private int estimatedWeight;

  @Column(name = "ACTUAL_WEIGHT")
  private int actualWeight;

  @Column(name = "CUSTOMER")
  private String customer;

  @Column(name = "CREATED")
  private Long created;

  @Column(name = "UPDATED")
  private Long updated;

  @Column(name = "DISPATCHED")
  private Long dispatched;

  @Column(name = "DELIVERED")
  private Long delivered;

  //    // todo : check for
  //    @Column(name = "INVOICE")
  //    private boolean invoice;
  //          "invoice": 0,

  @Column(name = "INVOICE_CODE")
  private String invoiceCode;

  @Column(name = "INVOICE_DISPLAY_CODE")
  private String invoiceDisplayCode;

  @Column(name = "NUMBER_OF_ITEMS")
  private int noOfItems;

  @Column(name = "CITY")
  private String city;

  @Column(name = "COLLECTABLE_AMOUNT")
  private int collectableAmount;

  @Column(name = "COLLECTED_AMOUNT")
  private int collectedAmount;

  @Column(name = "PAYMENT_RECONCILED")
  private boolean paymentReconciled;

  @Column(name = "POD_CODE")
  private String podCode;

  @Column(name = "SHIPPING_MANIFEST_CODE")
  private String shippingManifestCode;
  // todo : need to link item one to many

  //          "items": {
  //            "itemCode": {
  //              "itemSku": "sushant183",
  //                      "itemName": "sushant183",
  //                      "itemTypeImageUrl": null,
  //                      "itemTypePageUrl": null,
  //                      "quantity": 2 // int
  //            }
  //        },
  //    @Column(name = "CANCELLABLE")
  //    private boolean cancellable;
  //    "customFieldValues": [],

  @Column(name = "SHIPPING_LABEL_LINK")
  private String shippingLabelLink;

  @ManyToOne(targetEntity = SaleOrderMetaData.class)
  @JoinColumn(name = "SALE_ORDER_META_DATA_ID")
  private SaleOrderMetaData saleOrderMetaData;

  @OneToMany(
      mappedBy = "saleOrderShippingPackageMetaData",
      fetch = FetchType.LAZY,
      cascade = CascadeType.REMOVE)
  private List<SaleOrderShippingPackageItemCodeMetaData> itemCodeMetaDataList = new ArrayList<>();

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

  public String getSaleOrderCode() {
    return saleOrderCode;
  }

  public void setSaleOrderCode(String saleOrderCode) {
    this.saleOrderCode = saleOrderCode;
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

  public SaleOrderMetaData getSaleOrderMetaData() {
    return saleOrderMetaData;
  }

  public void setSaleOrderMetaData(SaleOrderMetaData saleOrderMetaData) {
    this.saleOrderMetaData = saleOrderMetaData;
  }

  public List<SaleOrderShippingPackageItemCodeMetaData> getItemCodeMetaDataList() {
    return itemCodeMetaDataList;
  }

  public void setItemCodeMetaDataList(
      List<SaleOrderShippingPackageItemCodeMetaData> itemCodeMetaDataList) {
    this.itemCodeMetaDataList = itemCodeMetaDataList;
  }

  public String getShippingPackageType() {
    return shippingPackageType;
  }

  public void setShippingPackageType(String shippingPackageType) {
    this.shippingPackageType = shippingPackageType;
  }

  public String getShippingProvider() {
    return shippingProvider;
  }

  public void setShippingProvider(String shippingProvider) {
    this.shippingProvider = shippingProvider;
  }

  public String getShippingMethod() {
    return shippingMethod;
  }

  public void setShippingMethod(String shippingMethod) {
    this.shippingMethod = shippingMethod;
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

  public int getEstimatedWeight() {
    return estimatedWeight;
  }

  public void setEstimatedWeight(int estimatedWeight) {
    this.estimatedWeight = estimatedWeight;
  }

  public int getActualWeight() {
    return actualWeight;
  }

  public void setActualWeight(int actualWeight) {
    this.actualWeight = actualWeight;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
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

  public Long getDispatched() {
    return dispatched;
  }

  public void setDispatched(Long dispatched) {
    this.dispatched = dispatched;
  }

  public Long getDelivered() {
    return delivered;
  }

  public void setDelivered(Long delivered) {
    this.delivered = delivered;
  }

  public String getInvoiceCode() {
    return invoiceCode;
  }

  public void setInvoiceCode(String invoiceCode) {
    this.invoiceCode = invoiceCode;
  }

  public String getInvoiceDisplayCode() {
    return invoiceDisplayCode;
  }

  public void setInvoiceDisplayCode(String invoiceDisplayCode) {
    this.invoiceDisplayCode = invoiceDisplayCode;
  }

  public int getNoOfItems() {
    return noOfItems;
  }

  public void setNoOfItems(int noOfItems) {
    this.noOfItems = noOfItems;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public int getCollectableAmount() {
    return collectableAmount;
  }

  public void setCollectableAmount(int collectableAmount) {
    this.collectableAmount = collectableAmount;
  }

  public int getCollectedAmount() {
    return collectedAmount;
  }

  public void setCollectedAmount(int collectedAmount) {
    this.collectedAmount = collectedAmount;
  }

  public boolean isPaymentReconciled() {
    return paymentReconciled;
  }

  public void setPaymentReconciled(boolean paymentReconciled) {
    this.paymentReconciled = paymentReconciled;
  }

  public String getPodCode() {
    return podCode;
  }

  public void setPodCode(String podCode) {
    this.podCode = podCode;
  }

  public String getShippingManifestCode() {
    return shippingManifestCode;
  }

  public void setShippingManifestCode(String shippingManifestCode) {
    this.shippingManifestCode = shippingManifestCode;
  }

  public String getShippingLabelLink() {
    return shippingLabelLink;
  }

  public void setShippingLabelLink(String shippingLabelLink) {
    this.shippingLabelLink = shippingLabelLink;
  }
}
