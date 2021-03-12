package com.salesmanager.core.business.services.rewardconsumptioncreteria;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.common.RewardConsumptionCriteria;
import com.salesmanager.core.model.common.enumerator.TierType;
import java.util.List;

public interface RewardConsumptionCriteriaService
    extends SalesManagerEntityService<Long, RewardConsumptionCriteria> {
  List<RewardConsumptionCriteria> getAllRewardConsumptionCriteria() throws ServiceException;

  RewardConsumptionCriteria getById(Long id);

  RewardConsumptionCriteria getRewardConsumptionCriteriaByType(TierType tierType)
      throws ServiceException;
}
