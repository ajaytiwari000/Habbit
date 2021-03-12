package com.salesmanager.core.business.repositories.catalog.attribute;

import com.salesmanager.core.model.catalog.product.Flavour;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;
import java.util.Optional;

public interface FlavourRepositoryCustom {

  /**
   * Fetch List of Flavours based on criteria
   *
   * @param criteria
   * @return
   */
  Optional<List<Flavour>> getFlavours(Criteria criteria);
}
