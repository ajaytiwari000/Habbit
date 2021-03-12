package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.category.CategoryBanner;
import com.salesmanager.shop.model.catalog.category.ReadableCategoryBanner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryReadableBannerMapper {
  CategoryBanner toCategoryBanner(ReadableCategoryBanner readableCategoryBanner);

  ReadableCategoryBanner toReadableCategoryBanner(CategoryBanner categoryBanner);
}
