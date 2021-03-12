package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.product.NutritionalInfo;
import com.salesmanager.shop.model.productAttribute.PersistableNutritionalInfo;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {NutrientsInfoMapper.class, NutrientsFactMapper.class})
public interface NutritionalInfoMapper {
  NutritionalInfo toNutritionalInfo(PersistableNutritionalInfo persistableNutrientsFact);

  PersistableNutritionalInfo toPersistableNutritionalInfo(NutritionalInfo nutrientsFact);
}
