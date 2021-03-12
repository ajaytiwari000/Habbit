package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.product.NutrientsFact;
import com.salesmanager.shop.model.productAttribute.PersistableNutrientsFact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NutrientsFactMapper {
  NutrientsFact toNutrientsFact(PersistableNutrientsFact persistableNutrientsFact);

  PersistableNutrientsFact toPersistableNutrientsFact(NutrientsFact nutrientsFact);
}
