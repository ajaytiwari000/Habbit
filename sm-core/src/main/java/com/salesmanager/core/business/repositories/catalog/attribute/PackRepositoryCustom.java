package com.salesmanager.core.business.repositories.catalog.attribute;

import com.salesmanager.core.model.catalog.product.Pack;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;
import java.util.Optional;

public interface PackRepositoryCustom {

  /**
   * Fetch List of Packs based on criteria
   *
   * @param criteria
   * @return
   */
  Optional<List<Pack>> getPacks(Criteria criteria);
}
