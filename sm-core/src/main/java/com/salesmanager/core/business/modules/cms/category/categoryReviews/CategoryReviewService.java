package com.salesmanager.core.business.modules.cms.category.categoryReviews;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.category.CategoryReview;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;

public interface CategoryReviewService extends SalesManagerEntityService<Long, CategoryReview> {

  /**
   * @param reviewerName
   * @param categoryName
   * @return
   */
  CategoryReview getByCategoryAndReviewerName(String reviewerName, String categoryName)
      throws ServiceException;

  /**
   * @param criteria
   * @return
   */
  List<CategoryReview> getAllCategoryReviews(Criteria criteria) throws ServiceException;
}
