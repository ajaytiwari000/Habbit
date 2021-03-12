package com.salesmanager.core.business.modules.cms.category.categoryReviews;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.category.CategoryReviewRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.category.CategoryReview;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("categoryReviewService")
public class CategoryReviewServiceImpl extends SalesManagerEntityServiceImpl<Long, CategoryReview>
    implements CategoryReviewService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryReviewServiceImpl.class);

  CategoryReviewRepository categoryReviewRepository;

  @Inject
  public CategoryReviewServiceImpl(CategoryReviewRepository categoryReviewRepository) {
    super(categoryReviewRepository);
    this.categoryReviewRepository = categoryReviewRepository;
  }

  @Override
  public CategoryReview getByCategoryAndReviewerName(String revierwerName, String categoryName)
      throws ServiceException {
    try {
      return categoryReviewRepository
          .getByCategoryAndReviewerName(revierwerName, categoryName)
          .orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public List<CategoryReview> getAllCategoryReviews(Criteria criteria) throws ServiceException {
    try {
      return categoryReviewRepository.getAllCategoryReview(criteria).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
