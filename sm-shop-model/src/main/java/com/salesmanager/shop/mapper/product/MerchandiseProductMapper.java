package com.salesmanager.shop.mapper.product;

import com.salesmanager.shop.model.catalog.product.ReadableMerchandiseProduct;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MerchandiseProductMapper {
  ReadableProduct toReadableProduct(ReadableMerchandiseProduct readableMerchandiseProduct);

  ReadableMerchandiseProduct toReadableMerchandiseProduct(ReadableProduct readableProduct);
}
