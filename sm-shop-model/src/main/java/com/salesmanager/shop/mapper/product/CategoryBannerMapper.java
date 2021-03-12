package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.category.CategoryBanner;
import com.salesmanager.shop.model.catalog.category.PersistableCategoryBanner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryBannerMapper {
  CategoryBanner toCategoryDetails(PersistableCategoryBanner persistableCategoryBanner);

  PersistableCategoryBanner toPersistableCategoryDetails(CategoryBanner categoryBanner);
}
