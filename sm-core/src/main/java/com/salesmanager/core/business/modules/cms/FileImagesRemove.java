package com.salesmanager.core.business.modules.cms;

import com.salesmanager.core.business.exception.ServiceException;

public interface FileImagesRemove<E> {
  void removeImages(E e) throws ServiceException;
}
