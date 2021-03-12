package com.salesmanager.shop.model.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.customer.PersistableBillingAddress;
import com.salesmanager.shop.model.customer.PersistableCustomerSaleOrderAddress;
import com.salesmanager.shop.model.customer.PersistableShippingAddress;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnicommerceCreateSaleOrderRequest {
  private String code;
  private String displayOrderCode;
  private String channel;
  private boolean cashOnDelivery;
  private Set<PersistableCustomerSaleOrderItem> saleOrderItems = new LinkedHashSet<>();
  private List<PersistableCustomerSaleOrderAddress> addresses;
  private String customerEmailAddress;
  private int totalDiscount;
  private int totalShippingCharges;
  private Long totalCashOnDeliveryCharges;
  private int totalGiftWrapCharges = 0;
  private int totalPrepaidAmount = 0;
  private PersistableBillingAddress billingAddress = new PersistableBillingAddress();
  private PersistableShippingAddress shippingAddress = new PersistableShippingAddress();

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

  public Set<PersistableCustomerSaleOrderItem> getSaleOrderItems() {
    return saleOrderItems;
  }

  public void setSaleOrderItems(Set<PersistableCustomerSaleOrderItem> saleOrderItems) {
    this.saleOrderItems = saleOrderItems;
  }

  public List<PersistableCustomerSaleOrderAddress> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<PersistableCustomerSaleOrderAddress> addresses) {
    this.addresses = addresses;
  }

  public String getCustomerEmailAddress() {
    return customerEmailAddress;
  }

  public void setCustomerEmailAddress(String customerEmailAddress) {
    this.customerEmailAddress = customerEmailAddress;
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

  public PersistableBillingAddress getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(PersistableBillingAddress billingAddress) {
    this.billingAddress = billingAddress;
  }

  public PersistableShippingAddress getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(PersistableShippingAddress shippingAddress) {
    this.shippingAddress = shippingAddress;
  }
}
