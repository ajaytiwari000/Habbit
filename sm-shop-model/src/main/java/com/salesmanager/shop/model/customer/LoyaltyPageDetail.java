package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoyaltyPageDetail extends Entity {
  private String phoneNumber;
  private TierType tierType;
  private String primaryColor;
  private String secondaryColor;
  private int earnedLifeTimePoint;
  private int earnedPointCurTier;
  private int needPointToNextTier;
  private int availableRewardPoint;
  private TierType nextTierType;
  private String nextTierPrimaryColor;
  private String nextTierSecondaryColor;
  private PersistableRewardConsumptionCriteria persistableRewardConsumptionCriteria;

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public TierType getTierType() {
    return tierType;
  }

  public void setTierType(TierType tierType) {
    this.tierType = tierType;
  }

  public String getPrimaryColor() {
    return primaryColor;
  }

  public void setPrimaryColor(String primaryColor) {
    this.primaryColor = primaryColor;
  }

  public String getSecondaryColor() {
    return secondaryColor;
  }

  public void setSecondaryColor(String secondaryColor) {
    this.secondaryColor = secondaryColor;
  }

  public int getEarnedLifeTimePoint() {
    return earnedLifeTimePoint;
  }

  public void setEarnedLifeTimePoint(int earnedLifeTimePoint) {
    this.earnedLifeTimePoint = earnedLifeTimePoint;
  }

  public int getEarnedPointCurTier() {
    return earnedPointCurTier;
  }

  public void setEarnedPointCurTier(int earnedPointCurTier) {
    this.earnedPointCurTier = earnedPointCurTier;
  }

  public int getNeedPointToNextTier() {
    return needPointToNextTier;
  }

  public void setNeedPointToNextTier(int needPointToNextTier) {
    this.needPointToNextTier = needPointToNextTier;
  }

  public int getAvailableRewardPoint() {
    return availableRewardPoint;
  }

  public void setAvailableRewardPoint(int availableRewardPoint) {
    this.availableRewardPoint = availableRewardPoint;
  }

  public PersistableRewardConsumptionCriteria getPersistableRewardConsumptionCriteria() {
    return persistableRewardConsumptionCriteria;
  }

  public void setPersistableRewardConsumptionCriteria(
      PersistableRewardConsumptionCriteria persistableRewardConsumptionCriteria) {
    this.persistableRewardConsumptionCriteria = persistableRewardConsumptionCriteria;
  }

  public TierType getNextTierType() {
    return nextTierType;
  }

  public void setNextTierType(TierType nextTierType) {
    this.nextTierType = nextTierType;
  }

  public String getNextTierPrimaryColor() {
    return nextTierPrimaryColor;
  }

  public void setNextTierPrimaryColor(String nextTierPrimaryColor) {
    this.nextTierPrimaryColor = nextTierPrimaryColor;
  }

  public String getNextTierSecondaryColor() {
    return nextTierSecondaryColor;
  }

  public void setNextTierSecondaryColor(String nextTierSecondaryColor) {
    this.nextTierSecondaryColor = nextTierSecondaryColor;
  }
}
