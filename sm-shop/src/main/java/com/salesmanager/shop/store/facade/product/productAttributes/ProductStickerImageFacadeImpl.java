package com.salesmanager.shop.store.facade.product.productAttributes;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.products.productsticker.ProductStickerFileManager;
import com.salesmanager.core.business.modules.cms.products.productsticker.ProductStickerImageService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.model.catalog.product.image.ProductStickerImage;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.admin.controller.products.attributes.facade.ProductStickerImageFacade;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.mapper.product.ProductStickerImageMapper;
import com.salesmanager.shop.model.catalog.product.attribute.ProductStickerImageList;
import com.salesmanager.shop.model.productAttribute.PersistableProductStickerImage;
import com.salesmanager.shop.store.api.exception.ResourceDuplicateException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.model.paging.PaginationData;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

@Service("productStickerImageFacade")
public class ProductStickerImageFacadeImpl implements ProductStickerImageFacade {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductStickerImageFacadeImpl.class);
  @Inject private ProductStickerImageService productStickerImageService;
  @Inject private ProductStickerFileManager productStickerFileManager;
  @Inject private ProductStickerImageMapper productStickerImageMapper;
  @Inject private ProductService productService;

  @Override
  public PersistableProductStickerImage createProductSticker(
      MultipartFile[] files, ProductStickerImage productStickerImage) {
    String skuId = productStickerImage.getBadgeText();
    if (StringUtils.isNotEmpty(skuId)
        && (productStickerImage.getId() == null || productStickerImage.getId().longValue() == 0)) {
      ProductStickerImage stickerImage = null;
      try {
        stickerImage = productStickerImageService.getBySkuId(skuId);
      } catch (ServiceException e) {
        throwServiceRuntImeException(
            e,
            AttributeErrorCodes.PRODUCT_STICKER_GET_BY_BADGE_TEXT_FAILURE.getErrorCode(),
            AttributeErrorCodes.PRODUCT_STICKER_GET_BY_BADGE_TEXT_FAILURE.getErrorMessage()
                + skuId);
      }
      if (Objects.nonNull(stickerImage)) {
        LOGGER.error("ProductStickerImage Already exists for {}", skuId);
        throw new ResourceDuplicateException("ProductStickerImage Already exists for " + skuId);
      }
    }
    try {
      for (MultipartFile multipartFile : files) {
        if (!multipartFile.isEmpty()) {
          productStickerImage.setImage(multipartFile.getInputStream());
          productStickerImage.setBadgeIcon(multipartFile.getOriginalFilename());
          addProductSticker(productStickerImage);
        }
      }
      productStickerImageService.create(productStickerImage);
    } catch (ServiceException | IOException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_STICKER_CREATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_STICKER_CREATE_FAILURE.getErrorMessage());
    }
    return productStickerImageMapper.toPersistableProductStickerImage(productStickerImage);
  }

  private ProductStickerImage addProductSticker(ProductStickerImage productStickerImage)
      throws ServiceException {
    Assert.notNull(
        productStickerImage.getImage(), "ProductNutrientsInfo ImageContentFile cannot be null");

    InputStream inputStream = productStickerImage.getImage();
    ImageContentFile cmsContentImage = new ImageContentFile();
    cmsContentImage.setFileName(productStickerImage.getBadgeIcon());
    cmsContentImage.setFile(inputStream);
    cmsContentImage.setFileContentType(FileContentType.PRODUCT_STICKER);
    productStickerFileManager.addImage(productStickerImage, cmsContentImage);
    return productStickerImage;
  }

  @Override
  public void deleteProductSticker(Long id) {
    Validate.notNull(id, "ProductStickerId cannot be null");
    ProductStickerImage productStickerImage = productStickerImageService.getById(id);
    try {
      productStickerFileManager.removeImage(productStickerImage);
      productStickerImageService.delete(productStickerImage);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_STICKER_DELETE_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_STICKER_DELETE_FAILURE.getErrorMessage() + id);
    }
  }

  @Override
  public PersistableProductStickerImage getProductSticker(String skuId) {
    Validate.notNull(skuId, "ProductStickerImage id cannot be null");
    ProductStickerImage productStickerImage = getProductStickerImage(skuId);
    return productStickerImageMapper.toPersistableProductStickerImage(productStickerImage);
  }

  private ProductStickerImage getProductStickerImage(String skuId) {
    ProductStickerImage productStickerImage = null;
    try {
      productStickerImage = productService.getBySkuId(skuId).getProductStickerImage();
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_STICKER_GET_BY_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_STICKER_GET_BY_ID_FAILURE.getErrorMessage() + skuId);
    }
    if (Objects.isNull(productStickerImage)) {
      LOGGER.error("productStickerImage with id [" + skuId + " ] not found in DB.");
      throw new ResourceNotFoundException("productStickerImage with id [" + skuId + " ] not found");
    }
    return productStickerImage;
  }

  @Override
  public ProductStickerImageList getAllProductStickers(
      Integer page, Integer pageSize, MerchantStore merchantStore) {
    ProductStickerImageList productStickerImageList = new ProductStickerImageList();
    PaginationData paginationData = new PaginationData(pageSize, page);

    Criteria criteria = new Criteria();
    criteria.setStartIndex(paginationData.getOffset() - 1);
    criteria.setPageSize(pageSize);
    criteria.setMaxCount(pageSize);

    try {
      productStickerImageList.setProductStickerImages(
          productStickerImageService.getAllProductStickers(criteria).stream()
              .map(productStickerImageMapper::toPersistableProductStickerImage)
              .collect(Collectors.toList()));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_STICKER_GET_ALL_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_STICKER_GET_ALL_FAILURE.getErrorMessage());
    }
    return productStickerImageList;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
