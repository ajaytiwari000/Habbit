package com.salesmanager.core.business.repositories.catalog.product;

import com.salesmanager.core.model.catalog.product.image.ProductStickerImage;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;
import java.util.Optional;

public interface ProductStickerImageRepositoryCustom {
  /**
   * @param criteria
   * @return
   */
  Optional<List<ProductStickerImage>> getProductStickerImages(Criteria criteria);
}
