package com.salesmanager.core.model.common;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(
    name = "MEMBERSHIP",
    schema = SchemaConstant.SALESMANAGER_SCHEMA,
    uniqueConstraints = @UniqueConstraint(columnNames = {"MEMBERSHIP_ID"}))
public class Membership extends SalesManagerEntity<Long, Membership> implements Auditable {

  private static final long serialVersionUID = 1230456009807L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "MEMBERSHIP_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "MEMBERSHIP_SEQ_NEX_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "PHONE_NUMBER", unique = true)
  private String phoneNumber;

  @Column(name = "TIER_TYPE")
  @Enumerated(EnumType.STRING)
  private TierType tierType;

  @Column(name = "START_DATE_OF_CUR_TIER")
  private Long startDateOfCurTier;

  @Column(name = "END_DATE_OF_CUR_TIER")
  private Long endDateOfCurTier;

  @Column(name = "EARNED_POINT_CUR_TIER")
  private int earnedPointCurTier;

  @Column(name = "REWARD_POINT")
  private int rewardPoint;

  @Column(name = "MIN_DOWNGRADE_POINT")
  private int minDowngradePoint;

  @Column(name = "MIN_UPGRADE_POINT")
  private int minUpgradePoint;

  @Column(name = "SUCCESSFUL_REFERRALS")
  private int successfulReferrals;

  @Column(name = "EARNED_THROUGH_REFERRAL")
  private int earnedThroughReferrals;

  @Column(name = "LIFE_TIME_POINT")
  private int liftTimePoint;

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

  public int getRewardPoint() {
    return rewardPoint;
  }

  public void setRewardPoint(int rewardPoint) {
    this.rewardPoint = rewardPoint;
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
}
