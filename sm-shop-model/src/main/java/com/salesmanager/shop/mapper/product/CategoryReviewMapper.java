package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.category.CategoryReview;
import com.salesmanager.shop.model.productAttribute.PersistableCategoryReview;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryReviewMapper {
  CategoryReview toCategoryReview(PersistableCategoryReview persistableCategoryReview);

  PersistableCategoryReview toPersistableCategoryReview(CategoryReview categoryReview);
}
