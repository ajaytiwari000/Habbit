package com.salesmanager.core.model.order;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "SALE_ORDER_ITEMS_META_DATA", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class SaleOrderItemsMetaData extends SalesManagerEntity<Long, SaleOrderItemsMetaData> {

  /**
   * Table is to maintain unicom sale order item . will add and update entry here when getting any
   * update from unicom regarding order through get api
   */
  private static final long serialVersionUID = 1L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "SALE_ORDER_ITEMS_META_DATA_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "SALE_ORDER_ITEMS_META_DATA_ID_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "SHIPPING_PACKAGE_CODE")
  private String shippingPackageCode;

  @Column(name = "SHIPPING_PACKAGE_STATUS")
  private String shippingPackageStatus;

  @Column(name = "FACILITY_CODE")
  private String facilityCode;

  @Column(name = "FACILITY_NAME")
  private String facilityName;

  @Column(name = "ALTERNATE_FACILITY_CODE")
  private String alternateFacilityCode;

  @Column(name = "REVERSE_PICKUP_CODE")
  private String reversePickupCode;

  @Column(name = "SHIPPING_ADDRESS_ID")
  private String shippingAddressId;

  @Column(name = "PACKET_NUMBER")
  private int packetNumber;

  @Column(name = "COMBINATION_IDENTIFIER")
  private String combinationIdentifier;

  @Column(name = "COMBINATION_DESCRIPTION")
  private String combinationDescription;

  @Column(name = "TYPE")
  private String type;

  // TODO : ITEM was total null

  @Column(name = "SHIPPING_METHOD_CODE")
  private String shippingMethodCode;

  @Column(name = "ITEM_NAME")
  private String itemName;

  @Column(name = "ITEM_SKU")
  private String itemSku;

  @Column(name = "SELLER_SKU_CODE")
  private String sellerSkuCode;

  @Column(name = "CHANNEL_PRODUCT_ID")
  private String channelProductId;

  @Column(name = "IMAGE_URL")
  private String imageUrl;

  @Column(name = "STATUS_CODE")
  private String statusCode;

  @Column(name = "CODE")
  private String code;

  @Column(name = "SHELF_CODE")
  private String shelfCode;

  @Column(name = "TOTAL_PRICE")
  private int totalPrice;

  @Column(name = "SELLING_PRICE")
  private int sellingPrice;

  @Column(name = "SHIPPING_CHARGES")
  private int shippingCharges;

  @Column(name = "SHIPPING_METHOD_CHARGES")
  private int shippingMethodCharges;

  @Column(name = "CASH_ON_DELIVERY_CHARGES")
  private int cashOnDeliveryCharges;

  @Column(name = "PREPAID_AMOUNT")
  private int prepaidAmount;

  @Column(name = "VOUCHER_CODE")
  private String voucherCode;

  @Column(name = "VOUCHER_VALUE")
  private int voucherValue;

  @Column(name = "STORE_CREDIT")
  private int storeCredit;

  @Column(name = "DISCOUNT")
  private int discount;

  @Column(name = "GIFT_WRAP")
  private boolean giftWrap;

  @Column(name = "GIFT_WRAP_CHARGES")
  private int giftWrapCharges;

  @Column(name = "TAX_PERCENTAGE")
  private int taxPercentage;

  @Column(name = "GIFT_MESSAGE")
  private String giftMessage;

  @Column(name = "CANCELLABLE")
  private boolean cancellable;

  @Column(name = "EDIT_ADDRESS")
  private boolean editAddress;

  @Column(name = "REVERSE_PICKABLE")
  private boolean reversePickable;

  @Column(name = "PACKET_CONFIGURABLE")
  private boolean packetConfigurable;

  @Column(name = "CREATED")
  private Long created;

  @Column(name = "UPDATED")
  private Long updated;

  @Column(name = "ON_HOLD")
  private boolean onHold;

  @Column(name = "SALE_ORDER_ITEM_ALTERNATE_ID")
  private String saleOrderItemAlternateId;

  @Column(name = "CANCELLATION_REASON")
  private String cancellationReason;

  // todo : null in list
  //  @Column(name = "PAGE_URL")
  //  private String pageUrl;

  @Column(name = "COLOR")
  private String color;

  @Column(name = "BRAND")
  private String brand;

  @Column(name = "SIZE")
  private String size;

  @Column(name = "REPLACEMENT_SALE_ORDER_CODE")
  private String replacementSaleOrderCode;

  @Column(name = "BUNDLE_SKU_CODE")
  private String bundleSkuCode;

  //  // todo :check for list
  //  @Column(name = "CUSTOM_FIELD_VALUES")
  //  private List customFieldValues;

  //  // todo :check for list
  //  @Column(name = "ITEM_DETAIL_FIELD_TO_LIST")
  //  private List itemDetailFieldDTOList;

  @Column(name = "HSN_CODE")
  private String hsnCode;

  @Column(name = "TOTAL_INTEGRATE_GST")
  private int totalIntegratedGst;

  @Column(name = "INTEGRATE_GST_PERCENTAGE")
  private int integratedGstPercentage;

  @Column(name = "TOTAL_UNION_TERRITORY_GST")
  private int totalUnionTerritoryGst;

  @Column(name = "UNION_TERRITORY_GST_PERCENTAGE")
  private int unionTerritoryGstPercentage;

  @Column(name = "TOTAL_STATE_GST")
  private int totalStateGst;

  @Column(name = "STATE_GST_PERCENTAGE")
  private int stateGstPercentage;

  @Column(name = "TOTAL_CENTRAL_GST")
  private int totalCentralGst;

  @Column(name = "CENTRAL_GST_PERCENTAGE")
  private int centralGstPercentage;

  @Column(name = "MAX_RETAIL_PRICE")
  private int maxRetailPrice;

  @Column(name = "ITEM_DETAIL_FIELDS")
  private String itemDetailFields;

  @ManyToOne(targetEntity = SaleOrderMetaData.class)
  @JoinColumn(name = "SALE_ORDER_META_DATA_ID")
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

  public String getShippingPackageCode() {
    return shippingPackageCode;
  }

  public void setShippingPackageCode(String shippingPackageCode) {
    this.shippingPackageCode = shippingPackageCode;
  }

  public String getShippingPackageStatus() {
    return shippingPackageStatus;
  }

  public void setShippingPackageStatus(String shippingPackageStatus) {
    this.shippingPackageStatus = shippingPackageStatus;
  }

  public String getFacilityCode() {
    return facilityCode;
  }

  public void setFacilityCode(String facilityCode) {
    this.facilityCode = facilityCode;
  }

  public String getFacilityName() {
    return facilityName;
  }

  public void setFacilityName(String facilityName) {
    this.facilityName = facilityName;
  }

  public String getAlternateFacilityCode() {
    return alternateFacilityCode;
  }

  public void setAlternateFacilityCode(String alternateFacilityCode) {
    this.alternateFacilityCode = alternateFacilityCode;
  }

  public String getReversePickupCode() {
    return reversePickupCode;
  }

  public void setReversePickupCode(String reversePickupCode) {
    this.reversePickupCode = reversePickupCode;
  }

  public String getShippingAddressId() {
    return shippingAddressId;
  }

  public void setShippingAddressId(String shippingAddressId) {
    this.shippingAddressId = shippingAddressId;
  }

  public int getPacketNumber() {
    return packetNumber;
  }

  public void setPacketNumber(int packetNumber) {
    this.packetNumber = packetNumber;
  }

  public String getCombinationIdentifier() {
    return combinationIdentifier;
  }

  public void setCombinationIdentifier(String combinationIdentifier) {
    this.combinationIdentifier = combinationIdentifier;
  }

  public String getCombinationDescription() {
    return combinationDescription;
  }

  public void setCombinationDescription(String combinationDescription) {
    this.combinationDescription = combinationDescription;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getShippingMethodCode() {
    return shippingMethodCode;
  }

  public void setShippingMethodCode(String shippingMethodCode) {
    this.shippingMethodCode = shippingMethodCode;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getItemSku() {
    return itemSku;
  }

  public void setItemSku(String itemSku) {
    this.itemSku = itemSku;
  }

  public String getSellerSkuCode() {
    return sellerSkuCode;
  }

  public void setSellerSkuCode(String sellerSkuCode) {
    this.sellerSkuCode = sellerSkuCode;
  }

  public String getChannelProductId() {
    return channelProductId;
  }

  public void setChannelProductId(String channelProductId) {
    this.channelProductId = channelProductId;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public SaleOrderMetaData getSaleOrderMetaData() {
    return saleOrderMetaData;
  }

  public void setSaleOrderMetaData(SaleOrderMetaData saleOrderMetaData) {
    this.saleOrderMetaData = saleOrderMetaData;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getShelfCode() {
    return shelfCode;
  }

  public void setShelfCode(String shelfCode) {
    this.shelfCode = shelfCode;
  }

  public int getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(int totalPrice) {
    this.totalPrice = totalPrice;
  }

  public int getSellingPrice() {
    return sellingPrice;
  }

  public void setSellingPrice(int sellingPrice) {
    this.sellingPrice = sellingPrice;
  }

  public int getShippingCharges() {
    return shippingCharges;
  }

  public void setShippingCharges(int shippingCharges) {
    this.shippingCharges = shippingCharges;
  }

  public int getShippingMethodCharges() {
    return shippingMethodCharges;
  }

  public void setShippingMethodCharges(int shippingMethodCharges) {
    this.shippingMethodCharges = shippingMethodCharges;
  }

  public int getCashOnDeliveryCharges() {
    return cashOnDeliveryCharges;
  }

  public void setCashOnDeliveryCharges(int cashOnDeliveryCharges) {
    this.cashOnDeliveryCharges = cashOnDeliveryCharges;
  }

  public int getPrepaidAmount() {
    return prepaidAmount;
  }

  public void setPrepaidAmount(int prepaidAmount) {
    this.prepaidAmount = prepaidAmount;
  }

  public String getVoucherCode() {
    return voucherCode;
  }

  public void setVoucherCode(String voucherCode) {
    this.voucherCode = voucherCode;
  }

  public int getVoucherValue() {
    return voucherValue;
  }

  public void setVoucherValue(int voucherValue) {
    this.voucherValue = voucherValue;
  }

  public int getStoreCredit() {
    return storeCredit;
  }

  public void setStoreCredit(int storeCredit) {
    this.storeCredit = storeCredit;
  }

  public int getDiscount() {
    return discount;
  }

  public void setDiscount(int discount) {
    this.discount = discount;
  }

  public boolean isGiftWrap() {
    return giftWrap;
  }

  public void setGiftWrap(boolean giftWrap) {
    this.giftWrap = giftWrap;
  }

  public int getGiftWrapCharges() {
    return giftWrapCharges;
  }

  public void setGiftWrapCharges(int giftWrapCharges) {
    this.giftWrapCharges = giftWrapCharges;
  }

  public int getTaxPercentage() {
    return taxPercentage;
  }

  public void setTaxPercentage(int taxPercentage) {
    this.taxPercentage = taxPercentage;
  }

  public String getGiftMessage() {
    return giftMessage;
  }

  public void setGiftMessage(String giftMessage) {
    this.giftMessage = giftMessage;
  }

  public boolean isCancellable() {
    return cancellable;
  }

  public void setCancellable(boolean cancellable) {
    this.cancellable = cancellable;
  }

  public boolean isEditAddress() {
    return editAddress;
  }

  public void setEditAddress(boolean editAddress) {
    this.editAddress = editAddress;
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

  public boolean isOnHold() {
    return onHold;
  }

  public void setOnHold(boolean onHold) {
    this.onHold = onHold;
  }

  public String getSaleOrderItemAlternateId() {
    return saleOrderItemAlternateId;
  }

  public void setSaleOrderItemAlternateId(String saleOrderItemAlternateId) {
    this.saleOrderItemAlternateId = saleOrderItemAlternateId;
  }

  public String getCancellationReason() {
    return cancellationReason;
  }

  public void setCancellationReason(String cancellationReason) {
    this.cancellationReason = cancellationReason;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getReplacementSaleOrderCode() {
    return replacementSaleOrderCode;
  }

  public void setReplacementSaleOrderCode(String replacementSaleOrderCode) {
    this.replacementSaleOrderCode = replacementSaleOrderCode;
  }

  public String getBundleSkuCode() {
    return bundleSkuCode;
  }

  public void setBundleSkuCode(String bundleSkuCode) {
    this.bundleSkuCode = bundleSkuCode;
  }

  public String getHsnCode() {
    return hsnCode;
  }

  public void setHsnCode(String hsnCode) {
    this.hsnCode = hsnCode;
  }

  public int getTotalIntegratedGst() {
    return totalIntegratedGst;
  }

  public void setTotalIntegratedGst(int totalIntegratedGst) {
    this.totalIntegratedGst = totalIntegratedGst;
  }

  public int getIntegratedGstPercentage() {
    return integratedGstPercentage;
  }

  public void setIntegratedGstPercentage(int integratedGstPercentage) {
    this.integratedGstPercentage = integratedGstPercentage;
  }

  public int getTotalUnionTerritoryGst() {
    return totalUnionTerritoryGst;
  }

  public void setTotalUnionTerritoryGst(int totalUnionTerritoryGst) {
    this.totalUnionTerritoryGst = totalUnionTerritoryGst;
  }

  public int getUnionTerritoryGstPercentage() {
    return unionTerritoryGstPercentage;
  }

  public void setUnionTerritoryGstPercentage(int unionTerritoryGstPercentage) {
    this.unionTerritoryGstPercentage = unionTerritoryGstPercentage;
  }

  public int getTotalStateGst() {
    return totalStateGst;
  }

  public void setTotalStateGst(int totalStateGst) {
    this.totalStateGst = totalStateGst;
  }

  public int getStateGstPercentage() {
    return stateGstPercentage;
  }

  public void setStateGstPercentage(int stateGstPercentage) {
    this.stateGstPercentage = stateGstPercentage;
  }

  public int getTotalCentralGst() {
    return totalCentralGst;
  }

  public void setTotalCentralGst(int totalCentralGst) {
    this.totalCentralGst = totalCentralGst;
  }

  public int getCentralGstPercentage() {
    return centralGstPercentage;
  }

  public void setCentralGstPercentage(int centralGstPercentage) {
    this.centralGstPercentage = centralGstPercentage;
  }

  public int getMaxRetailPrice() {
    return maxRetailPrice;
  }

  public void setMaxRetailPrice(int maxRetailPrice) {
    this.maxRetailPrice = maxRetailPrice;
  }

  public String getItemDetailFields() {
    return itemDetailFields;
  }

  public void setItemDetailFields(String itemDetailFields) {
    this.itemDetailFields = itemDetailFields;
  }
}
