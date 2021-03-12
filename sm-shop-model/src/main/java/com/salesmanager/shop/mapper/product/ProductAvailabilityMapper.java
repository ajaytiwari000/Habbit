package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.shop.model.productAttribute.PersistableProductAvailability;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductAvailabilityMapper {
  ProductAvailability toProductAvailability(
      PersistableProductAvailability persistableProductAvailability);

  PersistableProductAvailability toPersistableProductAvailability(
      ProductAvailability productAvailability);
}
