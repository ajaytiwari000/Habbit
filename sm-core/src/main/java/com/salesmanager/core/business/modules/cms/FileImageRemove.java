package com.salesmanager.core.business.modules.cms;

import com.salesmanager.core.business.exception.ServiceException;

public interface FileImageRemove<E> {
  void removeImage(E e) throws ServiceException;
}
