package com.salesmanager.core.business.modules.cms.attributes.Pack;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.FileImageGet;
import com.salesmanager.core.business.modules.cms.FileImagePut;
import com.salesmanager.core.business.modules.cms.FileImageRemove;
import com.salesmanager.core.business.utils.CoreConfiguration;
import com.salesmanager.core.model.catalog.product.Pack;
import com.salesmanager.core.model.catalog.product.PackIcon;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.content.OutputContentFile;

public class PackFileManagerImpl extends PackFileManager<Pack, PackIcon> {
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
  public OutputContentFile getImage(Pack p) throws ServiceException {
    return null;
  }

  @Override
  public OutputContentFile getImageFromChildEntity(PackIcon c) throws ServiceException {
    return null;
  }

  @Override
  public void addImage(PackIcon packIcon, ImageContentFile contentImage) throws ServiceException {
    uploadImage.addImage(packIcon, contentImage);
  }

  @Override
  public void removeImage(PackIcon packIcon) throws ServiceException {
    removeImage.removeImage(packIcon);
  }
}
