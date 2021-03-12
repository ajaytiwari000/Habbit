package com.salesmanager.core.business.modules.cms.products.productsticker;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.ProductStickerImageRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.image.ProductStickerImage;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("productStickerImageService")
public class ProductStickerImageServiceImpl
    extends SalesManagerEntityServiceImpl<Long, ProductStickerImage>
    implements ProductStickerImageService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ProductStickerImageServiceImpl.class);

  ProductStickerImageRepository productStickerImageRepository;

  @Inject
  public ProductStickerImageServiceImpl(ProductStickerImageRepository stickerImageRepository) {
    super(stickerImageRepository);
    this.productStickerImageRepository = stickerImageRepository;
  }

  @Override
  public ProductStickerImage getBySkuId(String skuId) throws ServiceException {
    try {
      return productStickerImageRepository.getBySkuId(skuId).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public List<ProductStickerImage> getAllProductStickers(Criteria criteria)
      throws ServiceException {
    try {
      return productStickerImageRepository.getProductStickerImages(criteria).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
