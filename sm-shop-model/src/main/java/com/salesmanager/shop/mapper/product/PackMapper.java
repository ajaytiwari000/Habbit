package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.product.Pack;
import com.salesmanager.shop.model.catalog.product.attribute.PersistablePack;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PackMapper {
  Pack toPack(PersistablePack persistablePack);

  PersistablePack toPersistablePack(Pack pack);
}
