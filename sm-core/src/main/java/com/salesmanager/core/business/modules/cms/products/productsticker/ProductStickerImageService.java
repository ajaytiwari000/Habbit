package com.salesmanager.core.business.modules.cms.products.productsticker;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.image.ProductStickerImage;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;

public interface ProductStickerImageService
    extends SalesManagerEntityService<Long, ProductStickerImage> {

  /**
   * @param skuId
   * @return
   */
  ProductStickerImage getBySkuId(String skuId) throws ServiceException;

  /**
   * @param criteria
   * @return
   */
  List<ProductStickerImage> getAllProductStickers(Criteria criteria) throws ServiceException;
}
