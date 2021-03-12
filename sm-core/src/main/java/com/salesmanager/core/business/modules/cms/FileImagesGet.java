package com.salesmanager.core.business.modules.cms;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.content.OutputContentFile;
import java.util.List;

public interface FileImagesGet<Parent, Child> {

  List<OutputContentFile> getImages(Parent p) throws ServiceException;

  List<OutputContentFile> getImagesFromLeafEntity(Child c) throws ServiceException;
}
