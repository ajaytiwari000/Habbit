package com.salesmanager.core.business.modules.cms.attributes.Boost;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.FileImageGet;
import com.salesmanager.core.business.modules.cms.FileImagePut;
import com.salesmanager.core.business.modules.cms.FileImageRemove;
import com.salesmanager.core.business.utils.CoreConfiguration;
import com.salesmanager.core.model.catalog.product.Boost;
import com.salesmanager.core.model.catalog.product.BoostIcon;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.content.OutputContentFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoostFileManagerImpl extends BoostFileManager<Boost, BoostIcon> {

  private static final Logger LOGGER = LoggerFactory.getLogger(BoostFileManagerImpl.class);

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
  public OutputContentFile getImage(Boost p) throws ServiceException {
    return null;
  }

  @Override
  public OutputContentFile getImageFromChildEntity(BoostIcon c) throws ServiceException {
    return null;
  }

  @Override
  public void addImage(BoostIcon boostIcon, ImageContentFile contentImage) throws ServiceException {
    uploadImage.addImage(boostIcon, contentImage);
  }

  @Override
  public void removeImage(BoostIcon boostIcon) throws ServiceException {
    removeImage.removeImage(boostIcon);
  }
}
