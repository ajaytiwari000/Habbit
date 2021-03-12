package com.salesmanager.shop.store.facade.attributes;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.attribute.image.PackIconService;
import com.salesmanager.core.model.catalog.product.PackIcon;
import com.salesmanager.shop.admin.controller.attributes.PackImageFacade;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("packImageFacade")
public class PackImageFacadeImpl implements PackImageFacade {
  private static final Logger LOGGER = LoggerFactory.getLogger(PackImageFacadeImpl.class);

  @Inject PackIconService packIconService;

  @Override
  public void addPackIcon(Long id, MultipartFile[] files) {
    LOGGER.info("Inside method :: addPackIcon");
    try {
      packIconService.addPackIcon(id, files);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PACK_IMAGE_ADD_PACK_ICON_FAILURE.getErrorCode(),
          AttributeErrorCodes.PACK_IMAGE_ADD_PACK_ICON_FAILURE.getErrorMessage());
    }
  }

  @Override
  public void removePackIcon(Long packIconId, Long packId) {
    PackIcon packIcon = null;
    try {
      packIcon = packIconService.getById(packIconId);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PACK_IMAGE_GET_BY_PACK_ICON_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.PACK_IMAGE_GET_BY_PACK_ICON_ID_FAILURE.getErrorMessage());
    }
    try {
      packIconService.removePackIcon(packIcon, packId);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PACK_IMAGE_REMOVE_PACK_ICON_FAILURE.getErrorCode(),
          AttributeErrorCodes.PACK_IMAGE_REMOVE_PACK_ICON_FAILURE.getErrorMessage());
    }
  }

  @Override
  public PackIcon getPackImageByPackId(Long id) {
    try {
      return packIconService.getPackImageByPackId(id);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PACK_IMAGE_GET_BY_PACK_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.PACK_IMAGE_GET_BY_PACK_ID_FAILURE.getErrorMessage());
    }
    return null;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
