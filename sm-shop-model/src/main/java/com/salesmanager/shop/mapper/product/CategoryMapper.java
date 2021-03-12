package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.shop.model.productAttribute.PersistableCategory;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {CategoryDetailsMapper.class, BoostMapper.class})
public interface CategoryMapper {
  Category toCategory(PersistableCategory persistableCategory);

  PersistableCategory toPersistableCategory(Category category);
}
