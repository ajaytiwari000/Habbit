package com.salesmanager.shop.mapper.product;

import com.salesmanager.shop.model.catalog.product.ReadableMerchandiseProductDetails;
import com.salesmanager.shop.model.catalog.product.ReadableProductDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MerchandiseProductDetailsMapper {
  ReadableProductDetails toReadableProductDetails(
      ReadableMerchandiseProductDetails readableMerchandiseProductDetails);

  ReadableMerchandiseProductDetails toReadableMerchandiseProductDetails(
      ReadableProductDetails readableProductDetails);
}
