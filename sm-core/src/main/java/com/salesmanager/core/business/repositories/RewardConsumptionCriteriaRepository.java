package com.salesmanager.core.business.repositories;

import com.salesmanager.core.model.common.RewardConsumptionCriteria;
import com.salesmanager.core.model.common.enumerator.TierType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RewardConsumptionCriteriaRepository
    extends JpaRepository<RewardConsumptionCriteria, Long> {
  @Query("select distinct rcc from RewardConsumptionCriteria rcc where rcc.id= ?1")
  Optional<RewardConsumptionCriteria> getById(Long id);

  @Query("select distinct rcc from RewardConsumptionCriteria rcc where rcc.tierType= ?1")
  Optional<RewardConsumptionCriteria> getRewardConsumptionCriteriaByType(TierType tierType);

  @Query("select distinct rcc from RewardConsumptionCriteria rcc")
  Optional<List<RewardConsumptionCriteria>> getAllRewardConsumptionCriteria();
}
