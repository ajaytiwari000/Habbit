package com.salesmanager.core.business.modules.cms.products.productsticker;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.FileImageGet;
import com.salesmanager.core.business.modules.cms.FileImagePut;
import com.salesmanager.core.business.modules.cms.FileImageRemove;
import com.salesmanager.core.business.utils.CoreConfiguration;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.image.ProductStickerImage;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.content.OutputContentFile;
import org.springframework.stereotype.Service;

@Service("productStickerFileManager")
public class ProductStickerFileManagerImpl
    extends ProductStickerFileManager<Product, ProductStickerImage> {
  private FileImagePut uploadImage;
  private FileImageGet getImage;
  private FileImageRemove removeImage;
  private CoreConfiguration configuration;

  public FileImagePut getUploadImage() {
    return uploadImage;
  }

  public void setUploadImage(FileImagePut uploadImage) {
    this.uploadImage = uploadImage;
  }

  public FileImageGet getGetImage() {
    return getImage;
  }

  public void setGetImage(FileImageGet getImage) {
    this.getImage = getImage;
  }

  public FileImageRemove getRemoveImage() {
    return removeImage;
  }

  public void setRemoveImage(FileImageRemove removeImage) {
    this.removeImage = removeImage;
  }

  public CoreConfiguration getConfiguration() {
    return configuration;
  }

  public void setConfiguration(CoreConfiguration configuration) {
    this.configuration = configuration;
  }

  @Override
  public OutputContentFile getImage(String key) throws ServiceException {
    return null;
  }

  @Override
  public OutputContentFile getImage(Product p) throws ServiceException {
    return null;
  }

  @Override
  public OutputContentFile getImageFromChildEntity(ProductStickerImage c) throws ServiceException {
    return null;
  }

  @Override
  public void addImage(ProductStickerImage e, ImageContentFile contentImage)
      throws ServiceException {
    uploadImage.addImage(e, contentImage);
  }

  @Override
  public void removeImage(ProductStickerImage productStickerImage) throws ServiceException {
    removeImage.removeImage(productStickerImage);
  }
}
