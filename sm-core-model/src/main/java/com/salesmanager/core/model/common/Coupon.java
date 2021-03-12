package com.salesmanager.core.model.common;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.common.enumerator.CouponCodeType;
import com.salesmanager.core.model.common.enumerator.CouponDiscountType;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import java.util.List;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(
    name = "COUPON",
    schema = SchemaConstant.SALESMANAGER_SCHEMA,
    uniqueConstraints = @UniqueConstraint(columnNames = {"COUPON_ID"}))
public class Coupon extends SalesManagerEntity<Long, Coupon> implements Auditable {

  private static final long serialVersionUID = 1230456009807L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "COUPON_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "COUPON_SEQ_NEX_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "COUPON_CODE_TYPE")
  @Enumerated(EnumType.STRING)
  private CouponCodeType couponCodeType;

  @Column(name = "CODE_NAME")
  private String codeName;

  @Column(name = "START_DATE")
  private Long startDate;

  @Column(name = "END_DATE")
  private Long endDate;

  @Column(name = "COUPON_EXPIRES")
  private boolean couponExpires;

  @Column(name = "IS_TIER_SPECIFIC")
  private boolean tierSpecific;

  /*@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
  @JoinTable(
      name = "COUPON_TIER",
      joinColumns = {@JoinColumn(name = "COUPON_ID")},
      inverseJoinColumns = {@JoinColumn(name = "TIER_ID")})
  @Cascade({
    org.hibernate.annotations.CascadeType.DETACH,
    org.hibernate.annotations.CascadeType.LOCK,
    org.hibernate.annotations.CascadeType.REFRESH,
    org.hibernate.annotations.CascadeType.REPLICATE
  })
  private List<Tier> tierList;*/

  @ElementCollection
  @CollectionTable(name = "COUPON_TIER_LIST", joinColumns = @JoinColumn(name = "id"))
  @Column(name = "ALLOWED_TIERS")
  @Enumerated(EnumType.STRING)
  List<TierType> allowedTierTypes;

  @Column(name = "MIN_CART_VALUE")
  private int minCartValue;

  @Column(name = "UNLIMITED_USE")
  private boolean unlimitedUse;

  @Column(name = "LIMIT_PER_USER")
  private int limitPerUser;

  @Column(name = "DISCOUNT_TYPE")
  @Enumerated(EnumType.STRING)
  private CouponDiscountType couponDiscountType;

  @Column(name = "DISCOUNT_VALUE")
  private int discountVal;

  @Column(name = "MAX_DISCOUNT_VALUE")
  private int maxDiscountVal;

  @Column(name = "IS_PRE_APPLIED")
  private boolean preApplied;

  @Override
  public AuditSection getAuditSection() {
    return auditSection;
  }

  @Override
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

  public CouponCodeType getCouponCodeType() {
    return couponCodeType;
  }

  public void setCouponCodeType(CouponCodeType couponCodeType) {
    this.couponCodeType = couponCodeType;
  }

  public String getCodeName() {
    return codeName;
  }

  public void setCodeName(String codeName) {
    this.codeName = codeName;
  }

  public Long getStartDate() {
    return startDate;
  }

  public void setStartDate(Long startDate) {
    this.startDate = startDate;
  }

  public Long getEndDate() {
    return endDate;
  }

  public void setEndDate(Long endDate) {
    this.endDate = endDate;
  }

  public boolean isCouponExpires() {
    return couponExpires;
  }

  public void setCouponExpires(boolean couponExpires) {
    this.couponExpires = couponExpires;
  }

  public boolean isTierSpecific() {
    return tierSpecific;
  }

  public void setTierSpecific(boolean tierSpecific) {
    this.tierSpecific = tierSpecific;
  }

  /*public List<Tier> getTierList() {
    return tierList;
  }

  public void setTierList(List<Tier> tierList) {
    this.tierList = tierList;
  }*/

  public List<TierType> getAllowedTierTypes() {
    return allowedTierTypes;
  }

  public void setAllowedTierTypes(List<TierType> alowedTierTypes) {
    this.allowedTierTypes = alowedTierTypes;
  }

  public int getMinCartValue() {
    return minCartValue;
  }

  public void setMinCartValue(int minCartValue) {
    this.minCartValue = minCartValue;
  }

  public boolean isUnlimitedUse() {
    return unlimitedUse;
  }

  public void setUnlimitedUse(boolean unlimitedUse) {
    this.unlimitedUse = unlimitedUse;
  }

  public int getLimitPerUser() {
    return limitPerUser;
  }

  public void setLimitPerUser(int limitPerUser) {
    this.limitPerUser = limitPerUser;
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

  public int getMaxDiscountVal() {
    return maxDiscountVal;
  }

  public void setMaxDiscountVal(int maxDiscountVal) {
    this.maxDiscountVal = maxDiscountVal;
  }

  public boolean isPreApplied() {
    return preApplied;
  }

  public void setPreApplied(boolean preApplied) {
    this.preApplied = preApplied;
  }
}
