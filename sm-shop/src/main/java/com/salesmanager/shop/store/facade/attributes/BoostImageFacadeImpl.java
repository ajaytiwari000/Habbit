package com.salesmanager.shop.store.facade.attributes;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.attribute.image.BoostIconService;
import com.salesmanager.core.model.catalog.product.BoostIcon;
import com.salesmanager.shop.admin.controller.attributes.BoostImageFacade;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("boostImageFacade")
public class BoostImageFacadeImpl implements BoostImageFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(BoostImageFacadeImpl.class);

  @Inject private BoostIconService boostIconService;

  @Override
  public void removeBoostIcon(Long boostIconId, Long boostId) {
    BoostIcon boostIcon = null;
    try {
      boostIcon = boostIconService.getById(boostIconId);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.BOOST_IMAGE_GET_BY_BOOST_ICON_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.BOOST_IMAGE_GET_BY_BOOST_ICON_ID_FAILURE.getErrorMessage()
              + boostIconId);
    }
    try {
      boostIconService.removeBoostIcon(boostIcon, boostId);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.BOOST_IMAGE_REMOVE_BOOST_ICON.getErrorCode(),
          AttributeErrorCodes.BOOST_IMAGE_REMOVE_BOOST_ICON.getErrorMessage() + boostId);
    }
  }

  @Override
  public void addBoostIcon(Long id, MultipartFile[] files) {
    try {
      boostIconService.addBoostIcon(id, files);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.BOOST_IMAGE_ADD_BOOST_ICON.getErrorCode(),
          AttributeErrorCodes.BOOST_IMAGE_ADD_BOOST_ICON.getErrorMessage());
    }
  }

  @Override
  public BoostIcon getBoostImageByBoostId(Long id) {
    try {
      return boostIconService.getBoostImageByBoostId(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.BOOST_IMAGE_GET_BY_BOOST_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.BOOST_IMAGE_GET_BY_BOOST_ID_FAILURE.getErrorMessage());
    }
    return null;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
