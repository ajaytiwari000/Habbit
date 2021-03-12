package com.salesmanager.core.business.modules.cms.category.categoryReviews;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.FileImageGet;
import com.salesmanager.core.business.modules.cms.FileImagePut;
import com.salesmanager.core.business.modules.cms.FileImageRemove;
import com.salesmanager.core.business.modules.cms.FileImagesRemove;
import com.salesmanager.core.business.utils.CoreConfiguration;
import com.salesmanager.core.model.catalog.category.CategoryDetails;
import com.salesmanager.core.model.catalog.category.CategoryReview;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.content.OutputContentFile;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoryReviewManagerImpl
    extends CategoryReviewManager<CategoryDetails, CategoryReview> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryReviewManagerImpl.class);

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
  public void addImage(CategoryReview e, ImageContentFile contentImage) throws ServiceException {
    uploadImage.addImage(e, contentImage);
  }

  @Override
  public void removeImage(CategoryReview categoryReview) throws ServiceException {
    removeImage.removeImage(categoryReview);
  }

  @Override
  public List<OutputContentFile> getImages(CategoryDetails p) throws ServiceException {
    return null;
  }

  @Override
  public List<OutputContentFile> getImagesFromLeafEntity(CategoryReview c) throws ServiceException {
    return null;
  }

  @Override
  public void removeImages(CategoryDetails categoryDetails) throws ServiceException {}
}
