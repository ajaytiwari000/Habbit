package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.common.RewardConsumptionCriteria;
import com.salesmanager.shop.model.customer.PersistableRewardConsumptionCriteria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RewardConsumptionCriteriaMapper {
  RewardConsumptionCriteria toRewardConsumptionCriteria(
      PersistableRewardConsumptionCriteria persistableRewardConsumptionCriteria);

  PersistableRewardConsumptionCriteria toPersistableRewardConsumptionCriteria(
      RewardConsumptionCriteria RewardConsumptionCriteria);
}
