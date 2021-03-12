package com.salesmanager.core.business.modules.cms.attributes.NutrientsInfo;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.FileImageGet;
import com.salesmanager.core.business.modules.cms.FileImagePut;
import com.salesmanager.core.business.modules.cms.FileImageRemove;
import com.salesmanager.core.business.modules.cms.FileImagesRemove;
import com.salesmanager.core.business.utils.CoreConfiguration;
import com.salesmanager.core.model.catalog.product.NutrientsInfo;
import com.salesmanager.core.model.catalog.product.NutritionalInfo;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.content.OutputContentFile;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NutrientsInfoManagerImpl extends NutrientsInfoManager<NutritionalInfo, NutrientsInfo> {

  private static final Logger LOGGER = LoggerFactory.getLogger(NutrientsInfoManagerImpl.class);

  private FileImagePut uploadImage;
  private FileImageGet getImage;
  private FileImageRemove removeImage;
  private FileImagesRemove removeImages;

  private CoreConfiguration configuration;

  public static Logger getLOGGER() {
    return LOGGER;
  }

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

  public FileImagesRemove getRemoveImages() {
    return removeImages;
  }

  public void setRemoveImages(FileImagesRemove removeImages) {
    this.removeImages = removeImages;
  }

  public CoreConfiguration getConfiguration() {
    return configuration;
  }

  public void setConfiguration(CoreConfiguration configuration) {
    this.configuration = configuration;
  }

  @Override
  public void addImage(NutrientsInfo e, ImageContentFile contentImage) throws ServiceException {
    uploadImage.addImage(e, contentImage);
  }

  @Override
  public void removeImage(NutrientsInfo nutrientsInfo) throws ServiceException {
    removeImage.removeImage(nutrientsInfo);
  }

  @Override
  public List<OutputContentFile> getImages(NutritionalInfo p) throws ServiceException {
    return null;
  }

  @Override
  public List<OutputContentFile> getImagesFromLeafEntity(NutrientsInfo c) throws ServiceException {
    return null;
  }

  @Override
  public void removeImages(NutritionalInfo nutritionalInfo) throws ServiceException {}
}
