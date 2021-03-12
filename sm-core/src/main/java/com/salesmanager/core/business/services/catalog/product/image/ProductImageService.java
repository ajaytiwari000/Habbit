package com.salesmanager.core.business.services.catalog.product.image;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.file.ProductImageSize;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.content.OutputContentFile;
import java.util.List;
import java.util.Set;

public interface ProductImageService extends SalesManagerEntityService<Long, ProductImage> {

  /**
   * Add a ProductImage to the persistence and an entry to the CMS
   *
   * @param product
   * @param productImage
   * @param file
   * @throws ServiceException
   */
  void addProductImage(Product product, ProductImage productImage, ImageContentFile inputImage)
      throws ServiceException;

  /**
   * Get the image ByteArrayOutputStream and content description from CMS
   *
   * @param productImage
   * @return
   * @throws ServiceException
   */
  OutputContentFile getProductImage(ProductImage productImage, ProductImageSize size)
      throws ServiceException;

  /**
   * Returns all Images for a given product
   *
   * @param product
   * @return
   * @throws ServiceException
   */
  List<OutputContentFile> getProductImages(Product product) throws ServiceException;

  void removeProductImage(ProductImage productImage) throws ServiceException;

  void saveOrUpdate(ProductImage productImage) throws ServiceException;

  /**
   * Returns an image file from required identifier. This method is used by the image servlet
   *
   * @param store
   * @param product
   * @param fileName
   * @param size
   * @return
   * @throws ServiceException
   */
  OutputContentFile getProductImage(
      String storeCode, String productCode, String fileName, final ProductImageSize size)
      throws ServiceException;

  void addProductImages(Product product, List<ProductImage> productImages) throws ServiceException;

  /**
   * @param skuId
   * @return
   * @throws ServiceException
   */
  Set<ProductImage> getProductImagesByProductSkuId(String skuId) throws ServiceException;

  Set<ProductImage> getProductImagesByProductId(Long skuId) throws ServiceException;
}
