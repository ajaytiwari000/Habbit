package com.salesmanager.core.business.modules.cms;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.content.ImageContentFile;

public interface FileImagePut<Child> {

  void addImage(Child e, ImageContentFile contentImage) throws ServiceException;
}
