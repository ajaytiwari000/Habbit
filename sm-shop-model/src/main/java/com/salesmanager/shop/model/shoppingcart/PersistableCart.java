package com.salesmanager.shop.model.shoppingcart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.core.model.common.enumerator.CouponDiscountType;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.core.model.order.enums.DeliveryChargesType;
import com.salesmanager.shop.model.customer.PersistableRewardConsumptionCriteria;
import com.salesmanager.shop.model.entity.Entity;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCart extends Entity {

  private List<PersistableCartItem> cartItems = new ArrayList<PersistableCartItem>();
  private String promoCode;
  private CouponDiscountType couponDiscountType;
  private int couponDiscountVal;
  private int totalDiscountVal;
  private boolean couponCodeApplied;
  private boolean customerCouponApplied;
  private String couponCodeErrorMsg;
  private String customerEmail;
  private String customerPhone;
  private String friendPhone;
  private String razorPayCustomerId;
  private String customerName;
  private String cartCode;
  private int appliedRewardPoint;
  private Long displayPrice;
  private Long totalPrice;
  private int earnPoints;
  private int rewardPoint;
  private TierType tierType;
  private PersistableRewardConsumptionCriteria persistableRewardConsumptionCriteria;
  private DeliveryChargesType deliveryChargesType;
  private int deliveryCharges;
  private int minCartValDeliveryCharges;
  private boolean giftWrapped;
  private int giftWrappingCharges;
  private String giftWrapMessage;

  public List<PersistableCartItem> getCartItems() {
    return cartItems;
  }

  public void setCartItems(List<PersistableCartItem> cartItems) {
    this.cartItems = cartItems;
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

  public int getDeliveryCharges() {
    return deliveryCharges;
  }

  public void setDeliveryCharges(int deliveryCharges) {
    this.deliveryCharges = deliveryCharges;
  }

  public int getCouponDiscountVal() {
    return couponDiscountVal;
  }

  public void setCouponDiscountVal(int couponDiscountVal) {
    this.couponDiscountVal = couponDiscountVal;
  }

  public int getTotalDiscountVal() {
    return totalDiscountVal;
  }

  public void setTotalDiscountVal(int totalDiscountVal) {
    this.totalDiscountVal = totalDiscountVal;
  }

  public String getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
  }

  public String getFriendPhone() {
    return friendPhone;
  }

  public void setFriendPhone(String friendPhone) {
    this.friendPhone = friendPhone;
  }

  public String getCartCode() {
    return cartCode;
  }

  public void setCartCode(String cartCode) {
    this.cartCode = cartCode;
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

  public int getEarnPoints() {
    return earnPoints;
  }

  public void setEarnPoints(int earnPoints) {
    this.earnPoints = earnPoints;
  }

  public TierType getTierType() {
    return tierType;
  }

  public void setTierType(TierType tierType) {
    this.tierType = tierType;
  }

  public PersistableRewardConsumptionCriteria getPersistableRewardConsumptionCriteria() {
    return persistableRewardConsumptionCriteria;
  }

  public void setPersistableRewardConsumptionCriteria(
      PersistableRewardConsumptionCriteria persistableRewardConsumptionCriteria) {
    this.persistableRewardConsumptionCriteria = persistableRewardConsumptionCriteria;
  }

  public DeliveryChargesType getDeliveryChargesType() {
    return deliveryChargesType;
  }

  public void setDeliveryChargesType(DeliveryChargesType deliveryChargesType) {
    this.deliveryChargesType = deliveryChargesType;
  }

  public boolean isGiftWrapped() {
    return giftWrapped;
  }

  public void setGiftWrapped(boolean giftWrapped) {
    this.giftWrapped = giftWrapped;
  }

  public int getGiftWrappingCharges() {
    return giftWrappingCharges;
  }

  public void setGiftWrappingCharges(int giftWrappingCharges) {
    this.giftWrappingCharges = giftWrappingCharges;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public String getGiftWrapMessage() {
    return giftWrapMessage;
  }

  public void setGiftWrapMessage(String giftWrapMessage) {
    this.giftWrapMessage = giftWrapMessage;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getRazorPayCustomerId() {
    return razorPayCustomerId;
  }

  public void setRazorPayCustomerId(String razorPayCustomerId) {
    this.razorPayCustomerId = razorPayCustomerId;
  }

  public int getAppliedRewardPoint() {
    return appliedRewardPoint;
  }

  public void setAppliedRewardPoint(int appliedRewardPoint) {
    this.appliedRewardPoint = appliedRewardPoint;
  }

  public int getRewardPoint() {
    return rewardPoint;
  }

  public void setRewardPoint(int rewardPoint) {
    this.rewardPoint = rewardPoint;
  }

  public boolean isCouponCodeApplied() {
    return couponCodeApplied;
  }

  public void setCouponCodeApplied(boolean couponCodeApplied) {
    this.couponCodeApplied = couponCodeApplied;
  }

  public String getCouponCodeErrorMsg() {
    return couponCodeErrorMsg;
  }

  public void setCouponCodeErrorMsg(String couponCodeErrorMsg) {
    this.couponCodeErrorMsg = couponCodeErrorMsg;
  }

  public int getMinCartValDeliveryCharges() {
    return minCartValDeliveryCharges;
  }

  public void setMinCartValDeliveryCharges(int minCartValDeliveryCharges) {
    this.minCartValDeliveryCharges = minCartValDeliveryCharges;
  }

  public boolean isCustomerCouponApplied() {
    return customerCouponApplied;
  }

  public void setCustomerCouponApplied(boolean customerCouponApplied) {
    this.customerCouponApplied = customerCouponApplied;
  }
}
