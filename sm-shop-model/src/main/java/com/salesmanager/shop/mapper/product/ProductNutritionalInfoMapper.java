package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.product.ProductNutritionalInfo;
import com.salesmanager.shop.model.productAttribute.PersistableProductNutritionalInfo;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {NutrientsInfoMapper.class, ProductNutrientsFactMapper.class})
public interface ProductNutritionalInfoMapper {
  ProductNutritionalInfo toProductNutritionalInfo(
      PersistableProductNutritionalInfo persistableProductNutritionalInfo);

  PersistableProductNutritionalInfo toPersistableProductNutritionalInfo(
      ProductNutritionalInfo productNutritionalInfo);
}
