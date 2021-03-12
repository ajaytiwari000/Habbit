package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.core.model.common.enumerator.CouponDiscountType;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.core.model.order.OrderType;
import com.salesmanager.core.model.order.enums.DeliveryChargesType;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.shop.model.entity.Entity;
import java.util.LinkedHashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCustomerOrder extends Entity {
  private String orderCode;
  private OrderStatus status;
  private String razorPayCustomerId;
  private String customerPhone;
  private String cartCode;
  private String customerFriendPhone;
  private String trackingUrl;
  private String customerName;
  private int appliedRewardPoint;
  private TierType tierType;
  private Long datePurchased;
  private Long orderDelivered;
  private OrderType orderType = OrderType.ORDER;
  private PaymentType paymentType;
  private Set<PersistableCustomerOrderProduct> orderProducts = new LinkedHashSet<>();
  private Set<PersistableCustomerOrderStatusHistory> orderHistory = new LinkedHashSet<>();
  private String customerEmailAddress;
  private PersistableCustomerOrderAddress billingAddress;
  private PersistableCustomerOrderAddress shippingAddress;
  private String promoCode;
  private CouponDiscountType couponDiscountType;
  private int discountVal;
  private Long displayPrice;
  private int totalDiscountVal;
  private Long totalPrice;
  private int earnedPoints;
  private DeliveryChargesType deliveryChargesType;
  private int deliveryCharges;
  private int giftWrapCharges = 0;
  private String giftWrapMessage;
  private boolean giftWrap;

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public String getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
  }

  public String getCustomerFriendPhone() {
    return customerFriendPhone;
  }

  public void setCustomerFriendPhone(String customerFriendPhone) {
    this.customerFriendPhone = customerFriendPhone;
  }

  public TierType getTierType() {
    return tierType;
  }

  public void setTierType(TierType tierType) {
    this.tierType = tierType;
  }

  public Long getDatePurchased() {
    return datePurchased;
  }

  public void setDatePurchased(Long datePurchased) {
    this.datePurchased = datePurchased;
  }

  public Long getOrderDelivered() {
    return orderDelivered;
  }

  public void setOrderDelivered(Long orderDelivered) {
    this.orderDelivered = orderDelivered;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
  }

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(PaymentType paymentType) {
    this.paymentType = paymentType;
  }

  public Set<PersistableCustomerOrderProduct> getOrderProducts() {
    return orderProducts;
  }

  public void setOrderProducts(Set<PersistableCustomerOrderProduct> orderProducts) {
    this.orderProducts = orderProducts;
  }

  public Set<PersistableCustomerOrderStatusHistory> getOrderHistory() {
    return orderHistory;
  }

  public void setOrderHistory(Set<PersistableCustomerOrderStatusHistory> orderHistory) {
    this.orderHistory = orderHistory;
  }

  public String getCustomerEmailAddress() {
    return customerEmailAddress;
  }

  public void setCustomerEmailAddress(String customerEmailAddress) {
    this.customerEmailAddress = customerEmailAddress;
  }

  public PersistableCustomerOrderAddress getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(PersistableCustomerOrderAddress billingAddress) {
    this.billingAddress = billingAddress;
  }

  public PersistableCustomerOrderAddress getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(PersistableCustomerOrderAddress shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public String getPromoCode() {
    return promoCode;
  }

  public void setPromoCode(String promoCode) {
    this.promoCode = promoCode;
  }

  public CouponDiscountType getCouponDiscountType() {
    return couponDiscountType;
  }

  public void setCouponDiscountType(CouponDiscountType couponDiscountType) {
    this.couponDiscountType = couponDiscountType;
  }

  public int getDiscountVal() {
    return discountVal;
  }

  public void setDiscountVal(int discountVal) {
    this.discountVal = discountVal;
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

  public int getEarnedPoints() {
    return earnedPoints;
  }

  public void setEarnedPoints(int earnedPoints) {
    this.earnedPoints = earnedPoints;
  }

  public DeliveryChargesType getDeliveryChargesType() {
    return deliveryChargesType;
  }

  public void setDeliveryChargesType(DeliveryChargesType deliveryChargesType) {
    this.deliveryChargesType = deliveryChargesType;
  }

  public int getGiftWrapCharges() {
    return giftWrapCharges;
  }

  public void setGiftWrapCharges(int giftWrapCharges) {
    this.giftWrapCharges = giftWrapCharges;
  }

  public String getGiftWrapMessage() {
    return giftWrapMessage;
  }

  public void setGiftWrapMessage(String giftWrapMessage) {
    this.giftWrapMessage = giftWrapMessage;
  }

  public boolean isGiftWrap() {
    return giftWrap;
  }

  public void setGiftWrap(boolean giftWrap) {
    this.giftWrap = giftWrap;
  }

  public String getRazorPayCustomerId() {
    return razorPayCustomerId;
  }

  public void setRazorPayCustomerId(String razorPayCustomerId) {
    this.razorPayCustomerId = razorPayCustomerId;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCartCode() {
    return cartCode;
  }

  public void setCartCode(String cartCode) {
    this.cartCode = cartCode;
  }

  public int getAppliedRewardPoint() {
    return appliedRewardPoint;
  }

  public void setAppliedRewardPoint(int appliedRewardPoint) {
    this.appliedRewardPoint = appliedRewardPoint;
  }

  public int getDeliveryCharges() {
    return deliveryCharges;
  }

  public void setDeliveryCharges(int deliveryCharges) {
    this.deliveryCharges = deliveryCharges;
  }

  public int getTotalDiscountVal() {
    return totalDiscountVal;
  }

  public void setTotalDiscountVal(int totalDiscountVal) {
    this.totalDiscountVal = totalDiscountVal;
  }

  public String getTrackingUrl() {
    return trackingUrl;
  }

  public void setTrackingUrl(String trackingUrl) {
    this.trackingUrl = trackingUrl;
  }
}
