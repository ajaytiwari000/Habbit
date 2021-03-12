package com.salesmanager.core.business.repositories.catalog.category;

import com.salesmanager.core.model.catalog.category.CategoryReview;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;
import java.util.Optional;

public interface CategoryReviewRepositoryCustom {
  /**
   * @param criteria
   * @return
   */
  Optional<List<CategoryReview>> getAllCategoryReview(Criteria criteria);
}
