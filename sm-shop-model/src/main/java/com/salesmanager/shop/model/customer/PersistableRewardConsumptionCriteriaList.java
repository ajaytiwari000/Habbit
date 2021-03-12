package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableRewardConsumptionCriteriaList {
  private static final long serialVersionUID = 1L;

  private List<PersistableRewardConsumptionCriteria> persistableRewardConsumptionCriterias =
      new ArrayList<PersistableRewardConsumptionCriteria>();

  public List<PersistableRewardConsumptionCriteria> getPersistableRewardConsumptionCriterias() {
    return persistableRewardConsumptionCriterias;
  }

  public void setPersistableRewardConsumptionCriterias(
      List<PersistableRewardConsumptionCriteria> persistableRewardConsumptionCriterias) {
    this.persistableRewardConsumptionCriterias = persistableRewardConsumptionCriterias;
  }
}
