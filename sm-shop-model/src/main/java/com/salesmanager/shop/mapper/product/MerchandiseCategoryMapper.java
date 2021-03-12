package com.salesmanager.shop.mapper.product;

import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.catalog.category.ReadableMerchandiseCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MerchandiseCategoryMapper {
  ReadableCategory toReadableCategory(ReadableMerchandiseCategory readableMerchandiseCategory);

  ReadableMerchandiseCategory toReadableMerchandiseCategory(ReadableCategory readableCategory);
}
