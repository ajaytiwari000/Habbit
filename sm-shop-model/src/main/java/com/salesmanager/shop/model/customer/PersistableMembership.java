package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableMembership extends Entity {
  private String phoneNumber;
  private TierType tierType;
  private Long startDateOfCurTier;
  private Long endDateOfCurTier;
  private int earnedPointCurTier;
  private int rewardPoint;
  private int minDowngradePoint;
  private int minUpgradePoint;
  private int successfulReferrals;
  private int earnedThroughReferrals;
  private int liftTimePoint;
  private int referrerPoints;
  private int refereePoints;
  private String referralLink;

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

  public Long getStartDateOfCurTier() {
    return startDateOfCurTier;
  }

  public void setStartDateOfCurTier(Long startDateOfCurTier) {
    this.startDateOfCurTier = startDateOfCurTier;
  }

  public Long getEndDateOfCurTier() {
    return endDateOfCurTier;
  }

  public void setEndDateOfCurTier(Long endDateOfCurTier) {
    this.endDateOfCurTier = endDateOfCurTier;
  }

  public int getEarnedPointCurTier() {
    return earnedPointCurTier;
  }

  public void setEarnedPointCurTier(int earnedPointCurTier) {
    this.earnedPointCurTier = earnedPointCurTier;
  }

  public int getRewardPoint() {
    return rewardPoint;
  }

  public void setRewardPoint(int rewardPoint) {
    this.rewardPoint = rewardPoint;
  }

  public int getMinDowngradePoint() {
    return minDowngradePoint;
  }

  public void setMinDowngradePoint(int minDowngradePoint) {
    this.minDowngradePoint = minDowngradePoint;
  }

  public int getMinUpgradePoint() {
    return minUpgradePoint;
  }

  public void setMinUpgradePoint(int minUpgradePoint) {
    this.minUpgradePoint = minUpgradePoint;
  }

  public int getSuccessfulReferrals() {
    return successfulReferrals;
  }

  public void setSuccessfulReferrals(int successfulReferrals) {
    this.successfulReferrals = successfulReferrals;
  }

  public int getEarnedThroughReferrals() {
    return earnedThroughReferrals;
  }

  public void setEarnedThroughReferrals(int earnedThroughReferrals) {
    this.earnedThroughReferrals = earnedThroughReferrals;
  }

  public int getLiftTimePoint() {
    return liftTimePoint;
  }

  public void setLiftTimePoint(int liftTimePoint) {
    this.liftTimePoint = liftTimePoint;
  }

  public int getReferrerPoints() {
    return referrerPoints;
  }

  public void setReferrerPoints(int referrerPoints) {
    this.referrerPoints = referrerPoints;
  }

  public int getRefereePoints() {
    return refereePoints;
  }

  public void setRefereePoints(int refereePoints) {
    this.refereePoints = refereePoints;
  }

  public String getReferralLink() {
    return referralLink;
  }

  public void setReferralLink(String referralLink) {
    this.referralLink = referralLink;
  }
}
