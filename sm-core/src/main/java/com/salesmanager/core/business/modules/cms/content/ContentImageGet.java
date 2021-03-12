package com.salesmanager.core.business.modules.cms.content;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.common.ImageGet;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.content.OutputContentFile;
import java.util.List;

public interface ContentImageGet extends ImageGet {

  public OutputContentFile getImage(
      final String merchantStoreCode, String imageName, FileContentType imageContentType)
      throws ServiceException;

  public List<String> getImageNames(
      final String merchantStoreCode, FileContentType imageContentType) throws ServiceException;
}
