package com.salesmanager.shop.model.catalog.product.attribute;

import com.salesmanager.shop.model.productAttribute.PersistableProductStickerImage;
import java.util.ArrayList;
import java.util.List;

public class ProductStickerImageList {
  private static final long serialVersionUID = 1L;

  private List<PersistableProductStickerImage> productStickerImages =
      new ArrayList<PersistableProductStickerImage>();

  public List<PersistableProductStickerImage> getProductStickerImages() {
    return productStickerImages;
  }

  public void setProductStickerImages(List<PersistableProductStickerImage> productStickerImages) {
    this.productStickerImages = productStickerImages;
  }
}
