package com.salesmanager.shop.admin.controller.products.attributes.facade;

import com.salesmanager.core.model.catalog.product.image.ProductStickerImage;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.catalog.product.attribute.ProductStickerImageList;
import com.salesmanager.shop.model.productAttribute.PersistableProductStickerImage;
import org.springframework.web.multipart.MultipartFile;

public interface ProductStickerImageFacade {

  /**
   * @param files
   * @param productStickerImage
   * @return
   */
  PersistableProductStickerImage createProductSticker(
      MultipartFile[] files, ProductStickerImage productStickerImage);

  /** @param productNutrientsInfoId */
  void deleteProductSticker(Long productNutrientsInfoId);

  /**
   * @param skuId
   * @return
   */
  PersistableProductStickerImage getProductSticker(String skuId);

  /**
   * @param page
   * @param pageSize
   * @param merchantStore
   * @return
   */
  ProductStickerImageList getAllProductStickers(
      Integer page, Integer pageSize, MerchantStore merchantStore);
}
