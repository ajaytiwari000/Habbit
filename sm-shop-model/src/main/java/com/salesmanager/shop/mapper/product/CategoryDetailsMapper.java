package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.category.CategoryDetails;
import com.salesmanager.shop.model.productAttribute.PersistableCategoryDetails;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {PackMapper.class, NutritionalInfoMapper.class, CategoryReviewMapper.class})
public interface CategoryDetailsMapper {
  CategoryDetails toCategoryDetails(PersistableCategoryDetails persistableCategoryDetails);

  PersistableCategoryDetails toPersistableCategoryDetails(CategoryDetails categoryDetails);
}
