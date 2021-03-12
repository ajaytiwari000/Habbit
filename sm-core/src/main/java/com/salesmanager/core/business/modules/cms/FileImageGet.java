package com.salesmanager.core.business.modules.cms;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.content.OutputContentFile;

public interface FileImageGet<Parent, Child> {

  OutputContentFile getImage(final String key) throws ServiceException;

  OutputContentFile getImage(Parent p) throws ServiceException;

  OutputContentFile getImageFromChildEntity(Child c) throws ServiceException;
}
