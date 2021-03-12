package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.product.Flavour;
import com.salesmanager.shop.model.productAttribute.PersistableFlavour;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlavourMapper {
  Flavour toFlavour(PersistableFlavour persistableFlavour);

  PersistableFlavour toPersistableFlavour(Flavour flavour);
}
