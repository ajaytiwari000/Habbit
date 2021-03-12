package com.salesmanager.shop.admin.controller.category.attributes.facade;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.catalog.category.CategoryReview;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.catalog.category.CategoryReviewList;
import com.salesmanager.shop.model.productAttribute.PersistableCategoryReview;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryReviewFacade {

  /**
   * @param files
   * @param categoryReview
   * @return
   */
  PersistableCategoryReview createCategoryReview(
      MultipartFile[] files, CategoryReview categoryReview) throws ServiceException;

  /** @param productNutrientsInfoId */
  void deleteCategoryReview(Long productNutrientsInfoId);

  /**
   * @param id
   * @return
   */
  PersistableCategoryReview getCategoryReview(Long id);

  /**
   * @param page
   * @param pageSize
   * @param merchantStore
   * @param categoryName
   * @return
   */
  CategoryReviewList getAllCategoryReview(
      Integer page, Integer pageSize, MerchantStore merchantStore, String categoryName);
}
