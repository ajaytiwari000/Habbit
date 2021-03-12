package com.salesmanager.core.model.common;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.common.enumerator.OwnerType;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(
    name = "REFERRAL",
    schema = SchemaConstant.SALESMANAGER_SCHEMA,
    uniqueConstraints = @UniqueConstraint(columnNames = {"REFERRAL_ID"}))
public class ReferralCode extends SalesManagerEntity<Long, ReferralCode> implements Auditable {

  private static final long serialVersionUID = 1230456009807L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "REFERRAL_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "REFERRAL_SEQ_NEX_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "OWNER_TYPE")
  @Enumerated(EnumType.STRING)
  private OwnerType ownerType;

  @Column(name = "CODE_NAME", unique = true)
  private String codeName;

  @Column(name = "PHONE_NUMBER")
  private String phoneNumber;

  @Column(name = "END_DATE")
  private Long endDate;

  @Column(name = "REFERRER_POINTS")
  private int referrerPoints;

  @Column(name = "REFEREE_POINTS")
  private int refereePoints;

  @Column(name = "ALLOWED_USAGE_LIMIT")
  private int allowedUsageLimit;

  @Column(name = "UNLIMITED_USE")
  private boolean unlimitedUse;

  @Column(name = "ACTIVE")
  private boolean active;

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

  public OwnerType getOwnerType() {
    return ownerType;
  }

  public void setOwnerType(OwnerType ownerType) {
    this.ownerType = ownerType;
  }

  public String getCodeName() {
    return codeName;
  }

  public void setCodeName(String codeName) {
    this.codeName = codeName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String username) {
    this.phoneNumber = username;
  }

  public Long getEndDate() {
    return endDate;
  }

  public void setEndDate(Long expiredDate) {
    this.endDate = expiredDate;
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

  public int getAllowedUsageLimit() {
    return allowedUsageLimit;
  }

  public void setAllowedUsageLimit(int useLimitNumber) {
    this.allowedUsageLimit = useLimitNumber;
  }

  public boolean isUnlimitedUse() {
    return unlimitedUse;
  }

  public void setUnlimitedUse(boolean unlimitedUse) {
    this.unlimitedUse = unlimitedUse;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
