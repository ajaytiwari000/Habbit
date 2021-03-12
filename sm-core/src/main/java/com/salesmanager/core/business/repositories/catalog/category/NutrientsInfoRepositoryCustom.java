package com.salesmanager.core.business.repositories.catalog.category;

import com.salesmanager.core.model.catalog.product.NutrientsInfo;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;
import java.util.Optional;

public interface NutrientsInfoRepositoryCustom {
  /**
   * @param criteria
   * @return
   */
  Optional<List<NutrientsInfo>> getNutrientsInfos(Criteria criteria);
}
