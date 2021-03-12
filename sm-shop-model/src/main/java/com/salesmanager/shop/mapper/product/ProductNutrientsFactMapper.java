package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.product.ProductNutrientsFact;
import com.salesmanager.shop.model.productAttribute.PersistableProductNutrientsFact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductNutrientsFactMapper {
  ProductNutrientsFact toProductNutrientsFact(
      PersistableProductNutrientsFact persistableProductNutrientsFact);

  PersistableProductNutrientsFact toPersistableProductNutrientsFact(
      ProductNutrientsFact productNutrientsFact);
}
