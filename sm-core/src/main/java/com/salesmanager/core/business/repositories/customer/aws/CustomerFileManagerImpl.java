package com.salesmanager.core.business.repositories.customer.aws;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.FileImagePut;
import com.salesmanager.core.business.modules.cms.FileImageRemove;
import com.salesmanager.core.business.utils.CoreConfiguration;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerFileManagerImpl extends CustomerFileManager<Customer> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerFileManagerImpl.class);

  private FileImagePut uploadImage;
  private FileImageRemove removeImage;

  private CoreConfiguration configuration;

  public FileImagePut getUploadImage() {
    return uploadImage;
  }

  public void setUploadImage(FileImagePut uploadImage) {
    this.uploadImage = uploadImage;
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
  public void addImage(Customer e, ImageContentFile contentImage) throws ServiceException {
    uploadImage.addImage(e, contentImage);
  }

  @Override
  public void removeImage(Customer customer) throws ServiceException {
    removeImage.removeImage(customer);
  }
}
