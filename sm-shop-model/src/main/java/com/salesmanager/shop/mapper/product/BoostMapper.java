package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.product.Boost;
import com.salesmanager.shop.model.productAttribute.PersistableBoost;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoostMapper {
  Boost toBoost(PersistableBoost persistableBoost);

  PersistableBoost toPersistableBoost(Boost boost);
}
