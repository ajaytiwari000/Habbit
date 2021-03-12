package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
  ProductImage toProductImage(PersistableProductImage persistableProductImage);

  PersistableProductImage toPersistableProductImage(ProductImage productImage);
}
