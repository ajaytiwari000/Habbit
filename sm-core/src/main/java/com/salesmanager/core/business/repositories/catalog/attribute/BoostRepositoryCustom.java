package com.salesmanager.core.business.repositories.catalog.attribute;

import com.salesmanager.core.model.catalog.product.Boost;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;
import java.util.Optional;

public interface BoostRepositoryCustom {

  /**
   * Fetch List of Boosts based on criteria
   *
   * @param criteria
   * @return
   */
  Optional<List<Boost>> getBoosts(Criteria criteria);
}
