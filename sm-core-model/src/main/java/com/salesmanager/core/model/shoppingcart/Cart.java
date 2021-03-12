/** */
package com.salesmanager.core.model.shoppingcart;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.common.enumerator.CouponDiscountType;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.order.enums.DeliveryChargesType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "CART", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class Cart extends SalesManagerEntity<Long, Cart> implements Auditable {

  private static final long serialVersionUID = 1L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "CART_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CRT_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cart")
  private List<CartItem> cartItems = new ArrayList<>();

  @Column(name = "PROMO_CODE")
  private String promoCode;

  @Column(name = "DISCOUNT_TYPE")
  @Enumerated(EnumType.STRING)
  private CouponDiscountType couponDiscountType;

  @Column(name = "DISCOUNT_VALUE")
  private int couponDiscountVal;

  @Column(name = "TOTAL_DISCOUNT_VALUE")
  private int totalDiscountVal;

  @Column(name = "COUPON_CODE_APPLIED")
  private boolean couponCodeApplied;

  @Column(name = "CUSTOMER_COUPON_APPLIED")
  private boolean customerCouponApplied;

  @Column(name = "COUPON_ERROR_MSG")
  private String couponCodeErrorMsg;

  @Column(name = "RAZOR_PAY_CUSTOMER_ID")
  private String razorPayCustomerId;

  @Column(name = "CUSTOMER_PHONE", nullable = true)
  private String customerPhone;

  @Column(name = "CUSTOMER_NAME", nullable = true)
  private String customerName;

  @Column(name = "CUSTOMER_EMAIL", nullable = true)
  private String customerEmail;

  @Column(name = "FRIEND_PHONE", nullable = true)
  private String friendPhone;

  @Column(name = "CART_CODE", unique = true, nullable = false)
  private String cartCode;

  @Column(name = "DISPLAY_PRICE")
  private Long displayPrice;

  @Column(name = "TOTAL_PRICE")
  private Long totalPrice;

  @Column(name = "EARN_POINTS")
  private int earnPoints;

  @Column(name = "REWARD_POINTS")
  private int rewardPoint;

  @Column(name = "APPLIED_REWARD_POINTS")
  private int appliedRewardPoint;

  @Column(name = "TIER_TYPE")
  @Enumerated(EnumType.STRING)
  private TierType tierType;

  @Column(name = "DELIVERY_CHARGES_TYPE")
  @Enumerated(value = EnumType.STRING)
  private DeliveryChargesType deliveryChargesType;

  @Column(name = "DELIVERY_CHARGES")
  private int deliveryCharges;

  @Column(name = "MIN_CART_VAL_DELIVERY_CHARGES")
  private int minCartValDeliveryCharges;

  @Column(name = "GIFT_WRAPPED")
  private boolean giftWrapped;

  @Column(name = "GIFT_WRAPPING_CHARGES")
  private int giftWrappingCharges;

  @Column(name = "GIFT_WRAP_MESSAGE")
  private String giftWrapMessage;

  @Override
  public AuditSection getAuditSection() {
    return auditSection;
  }

  @Override
  public void setAuditSection(AuditSection audit) {
    this.auditSection = audit;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public List<CartItem> getCartItems() {
    return cartItems;
  }

  public void setCartItems(List<CartItem> cartItems) {
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

  public String getRazorPayCustomerId() {
    return razorPayCustomerId;
  }

  public void setRazorPayCustomerId(String razorPayCustomerId) {
    this.razorPayCustomerId = razorPayCustomerId;
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

  public int getDeliveryCharges() {
    return deliveryCharges;
  }

  public void setDeliveryCharges(int deliveryCharges) {
    this.deliveryCharges = deliveryCharges;
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
