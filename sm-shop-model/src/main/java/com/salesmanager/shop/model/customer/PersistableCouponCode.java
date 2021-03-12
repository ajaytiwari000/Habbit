package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.core.model.common.enumerator.CouponCodeType;
import com.salesmanager.core.model.common.enumerator.CouponDiscountType;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.shop.model.entity.Entity;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCouponCode extends Entity {
  private CouponCodeType couponCodeType;
  private String codeName;
  private Long startDate;
  private Long endDate;
  private boolean couponExpires;
  private boolean tierSpecific;
  List<TierType> allowedTierTypes;
  private int minCartValue;
  private boolean unlimitedUse;
  private int limitPerUser;
  private CouponDiscountType couponDiscountType;
  private int discountVal;
  private int maxDiscountVal;
  private boolean preApplied;

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

  public List<TierType> getAllowedTierTypes() {
    return allowedTierTypes;
  }

  public void setAllowedTierTypes(List<TierType> allowedTierTypes) {
    this.allowedTierTypes = allowedTierTypes;
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
