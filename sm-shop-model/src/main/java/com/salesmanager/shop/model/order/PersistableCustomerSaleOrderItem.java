package com.salesmanager.shop.model.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.customer.PersistableShippingAddress;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCustomerSaleOrderItem {
  private String channelSaleOrderItemCode;
  private String itemSku;
  private String code;
  private String itemName;
  private Long totalPrice;

  private String shippingMethodCode; // STD
  private boolean giftWrap;
  private String giftMessage;
  private Long sellingPrice;
  private Long discount;
  private String channelProductId;
  private Long shippingCharges;
  private String facilityCode;
  private Long shippingMethodCharges;
  private Long channelTransferPrice;
  private int packetNumber;

  private PersistableShippingAddress shippingAddress = new PersistableShippingAddress();

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

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public Long getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Long totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getShippingMethodCode() {
    return shippingMethodCode;
  }

  public void setShippingMethodCode(String shippingMethodCode) {
    this.shippingMethodCode = shippingMethodCode;
  }

  public boolean isGiftWrap() {
    return giftWrap;
  }

  public void setGiftWrap(boolean giftWrap) {
    this.giftWrap = giftWrap;
  }

  public String getGiftMessage() {
    return giftMessage;
  }

  public void setGiftMessage(String giftMessage) {
    this.giftMessage = giftMessage;
  }

  public Long getSellingPrice() {
    return sellingPrice;
  }

  public void setSellingPrice(Long sellingPrice) {
    this.sellingPrice = sellingPrice;
  }

  public Long getDiscount() {
    return discount;
  }

  public void setDiscount(Long discount) {
    this.discount = discount;
  }

  public String getChannelProductId() {
    return channelProductId;
  }

  public void setChannelProductId(String channelProductId) {
    this.channelProductId = channelProductId;
  }

  public Long getShippingCharges() {
    return shippingCharges;
  }

  public void setShippingCharges(Long shippingCharges) {
    this.shippingCharges = shippingCharges;
  }

  public String getFacilityCode() {
    return facilityCode;
  }

  public void setFacilityCode(String facilityCode) {
    this.facilityCode = facilityCode;
  }

  public PersistableShippingAddress getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(PersistableShippingAddress shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public Long getShippingMethodCharges() {
    return shippingMethodCharges;
  }

  public void setShippingMethodCharges(Long shippingMethodCharges) {
    this.shippingMethodCharges = shippingMethodCharges;
  }

  public Long getChannelTransferPrice() {
    return channelTransferPrice;
  }

  public void setChannelTransferPrice(Long channelTransferPrice) {
    this.channelTransferPrice = channelTransferPrice;
  }

  public int getPacketNumber() {
    return packetNumber;
  }

  public void setPacketNumber(int packetNumber) {
    this.packetNumber = packetNumber;
  }
}
