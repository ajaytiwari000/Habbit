package com.salesmanager.core.business.services.rewardconsumptioncreteria;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.RewardConsumptionCriteriaRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.common.RewardConsumptionCriteria;
import com.salesmanager.core.model.common.enumerator.TierType;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("rewardConsumptionCriteriaService")
public class RewardConsumptionCreteriaServiceImpl
    extends SalesManagerEntityServiceImpl<Long, RewardConsumptionCriteria>
    implements RewardConsumptionCriteriaService {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(RewardConsumptionCreteriaServiceImpl.class);
  private RewardConsumptionCriteriaRepository rewardConsumptionCriteriaRepository;

  @Inject
  public RewardConsumptionCreteriaServiceImpl(
      RewardConsumptionCriteriaRepository rewardConsumptionCriteriaRepository) {
    super(rewardConsumptionCriteriaRepository);
    this.rewardConsumptionCriteriaRepository = rewardConsumptionCriteriaRepository;
  }

  @Override
  public List<RewardConsumptionCriteria> getAllRewardConsumptionCriteria() throws ServiceException {
    try {
      return rewardConsumptionCriteriaRepository.getAllRewardConsumptionCriteria().orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public RewardConsumptionCriteria getById(Long id) {
    return rewardConsumptionCriteriaRepository.getById(id).orElse(null);
  }

  @Override
  public RewardConsumptionCriteria getRewardConsumptionCriteriaByType(TierType tierType)
      throws ServiceException {
    try {
      return rewardConsumptionCriteriaRepository
          .getRewardConsumptionCriteriaByType(tierType)
          .orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
