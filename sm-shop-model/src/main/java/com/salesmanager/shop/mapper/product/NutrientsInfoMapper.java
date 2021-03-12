package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.product.NutrientsInfo;
import com.salesmanager.shop.model.productAttribute.PersistableNutrientsInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NutrientsInfoMapper {
  NutrientsInfo toNutrientsInfo(PersistableNutrientsInfo persistableNutrientsFact);

  PersistableNutrientsInfo toPersistableNutrientsInfo(NutrientsInfo nutrientsFact);
}
