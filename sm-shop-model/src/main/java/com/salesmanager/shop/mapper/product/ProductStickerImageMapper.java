package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.product.image.ProductStickerImage;
import com.salesmanager.shop.model.productAttribute.PersistableProductStickerImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductStickerImageMapper {
  ProductStickerImage toProductStickerImage(
      PersistableProductStickerImage persistableNutrientsFact);

  PersistableProductStickerImage toPersistableProductStickerImage(
      ProductStickerImage nutrientsFact);
}
