package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.core.model.common.enumerator.OwnerType;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableReferralCode extends Entity {
  private OwnerType ownerType;
  private String codeName;
  private String phoneNumber;
  private Long endDate;
  private int referrerPoints;
  private int refereePoints;
  private int allowedUsageLimit;
  private boolean unlimitedUse;
  private boolean active;

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

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Long getEndDate() {
    return endDate;
  }

  public void setEndDate(Long endDate) {
    this.endDate = endDate;
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

  public void setAllowedUsageLimit(int allowedUsageLimit) {
    this.allowedUsageLimit = allowedUsageLimit;
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
