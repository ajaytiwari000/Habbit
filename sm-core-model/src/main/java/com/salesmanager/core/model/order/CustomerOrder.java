package com.salesmanager.core.model.order;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.enumerator.CouponDiscountType;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.core.model.customer.CustomerOrderAddress;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.order.enums.DeliveryChargesType;
import com.salesmanager.core.model.order.orderproduct.CustomerOrderProduct;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.payments.PaymentStatus;
import com.salesmanager.core.model.payments.PaymentType;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER_ORDER", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class CustomerOrder extends SalesManagerEntity<Long, CustomerOrder> {

  // TODO : To figure out how we store the order history ledger etc.

  /** */
  private static final long serialVersionUID = 1L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "CUS_ORDER_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CUS_ORDER_ID_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "ORDER_CODE", unique = true, nullable = false)
  private String orderCode;

  @Column(name = "ORDER_STATUS")
  @Enumerated(value = EnumType.STRING)
  private OrderStatus status;

  @Column(name = "RAZOR_PAY_CUSTOMER_ID")
  private String razorPayCustomerId;

  @Column(name = "CUSTOMER_PHONE")
  private String customerPhone;

  @Column(name = "CUSTOMER_FRIEND_PHONE")
  private String customerFriendPhone;

  @Column(name = "CUSTOMER_NAME", nullable = true)
  private String customerName;

  @Column(name = "TRACKING_URL")
  private String trackingUrl;

  @Column(name = "TIER_TYPE")
  @Enumerated(EnumType.STRING)
  private TierType tierType;

  @Column(name = "DATE_PURCHASED")
  private Long datePurchased;

  // used for an order payable on multiple installment
  @Column(name = "ORDER_DELIVERED")
  private Long orderDelivered;

  @Column(name = "ORDER_TYPE")
  @Enumerated(value = EnumType.STRING)
  private OrderType orderType = OrderType.ORDER;

  @Column(name = "PAYMENT_TYPE")
  @Enumerated(value = EnumType.STRING)
  private PaymentType paymentType;

  @Column(name = "PAYMENT_STATUS")
  @Enumerated(value = EnumType.STRING)
  private PaymentStatus paymentStatus;

  // TODO : Need to be in In sync with CartIems  (done)
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "customerOrder")
  private Set<CustomerOrderProduct> orderProducts = new HashSet<CustomerOrderProduct>();

  @Column(name = "CUSTOMER_EMAIL_ADDRESS", length = 50, nullable = false)
  private String customerEmailAddress;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "BILLING_ADDRESS", nullable = true, updatable = true)
  private CustomerOrderAddress billingAddress;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "SHIPPING_ADDRESS", nullable = true, updatable = true)
  private CustomerOrderAddress shippingAddress;

  //  @OneToOne(fetch = FetchType.EAGER)
  //  @JoinColumn(name = "BILLING_ADDRESS", nullable = true, updatable = true)
  //  private Address billingAddress;
  //
  //  @OneToOne(fetch = FetchType.EAGER)
  //  @JoinColumn(name = "SHIPPING_ADDRESS", nullable = true, updatable = true)
  //  private Address shippingAddress;

  @Column(name = "PROMO_CODE")
  private String promoCode;

  @Column(name = "DISCOUNT_TYPE")
  @Enumerated(EnumType.STRING)
  private CouponDiscountType couponDiscountType;

  @Column(name = "DISCOUNT_VALUE")
  private int discountVal;

  @Column(name = "DISPLAY_PRICE")
  private Long displayPrice;

  @Column(name = "TOTAL_PRICE")
  private Long totalPrice;

  @Column(name = "TOTAL_DISCOUNT_VALUE")
  private int totalDiscountVal;

  @Column(name = "EARNED_POINTS")
  private int earnedPoints;

  @Column(name = "APPLIED_REWARD_POINTS")
  private int appliedRewardPoint;

  @Column(name = "DELIVERY_CHARGES_TYPE")
  @Enumerated(value = EnumType.STRING)
  private DeliveryChargesType deliveryChargesType;

  @Column(name = "DELIVERY_CHARGES")
  private int deliveryCharges;

  @Column(name = "GIFT_WRAP_CHARGES")
  private int giftWrapCharges = 0;

  @Column(name = "GIFT_WRAP_MESSAGE")
  private String giftWrapMessage;

  @Column(name = "GIFT_WRAP")
  private boolean giftWrap;

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

  public PaymentStatus getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(PaymentStatus paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public Set<CustomerOrderProduct> getOrderProducts() {
    return orderProducts;
  }

  public void setOrderProducts(Set<CustomerOrderProduct> orderProducts) {
    this.orderProducts = orderProducts;
  }

  public String getCustomerEmailAddress() {
    return customerEmailAddress;
  }

  public void setCustomerEmailAddress(String customerEmailAddress) {
    this.customerEmailAddress = customerEmailAddress;
  }

  public CustomerOrderAddress getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(CustomerOrderAddress billingAddress) {
    this.billingAddress = billingAddress;
  }

  public CustomerOrderAddress getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(CustomerOrderAddress shippingAddress) {
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
