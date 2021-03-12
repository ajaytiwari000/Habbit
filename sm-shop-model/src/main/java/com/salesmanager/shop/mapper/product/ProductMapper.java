package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.shop.model.productAttribute.PersistableProduct;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {
      ProductStickerImageMapper.class,
      FlavourMapper.class,
      PackMapper.class,
      CategoryMapper.class,
      ProductAvailabilityMapper.class,
      ProductNutritionalInfoMapper.class
    })
public interface ProductMapper {
  Product toProduct(PersistableProduct persistableProduct);

  PersistableProduct toPersistableProduct(Product product);
}
