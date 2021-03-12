package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableRewardConsumptionCriteria extends Entity {
  private TierType tierType;
  private int minCartValue;
  private int minRewardPoint;

  public TierType getTierType() {
    return tierType;
  }

  public void setTierType(TierType tierType) {
    this.tierType = tierType;
  }

  public int getMinCartValue() {
    return minCartValue;
  }

  public void setMinCartValue(int minCartValue) {
    this.minCartValue = minCartValue;
  }

  public int getMinRewardPoint() {
    return minRewardPoint;
  }

  public void setMinRewardPoint(int minRewardPoint) {
    this.minRewardPoint = minRewardPoint;
  }
}
