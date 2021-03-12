package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.core.model.common.enumerator.TierType;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableTierNameTypeList {
  private static final long serialVersionUID = 1L;

  private List<TierType> persistableRewardConsumptionCriterias = new ArrayList<TierType>();

  public List<TierType> getPersistableRewardConsumptionCriterias() {
    return persistableRewardConsumptionCriterias;
  }

  public void setPersistableRewardConsumptionCriterias(
      List<TierType> persistableRewardConsumptionCriterias) {
    this.persistableRewardConsumptionCriterias = persistableRewardConsumptionCriterias;
  }
}
