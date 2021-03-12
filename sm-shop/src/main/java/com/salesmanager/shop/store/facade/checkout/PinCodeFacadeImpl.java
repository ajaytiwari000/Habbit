/** */
package com.salesmanager.shop.store.facade.checkout;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.pincode.PinCodeService;
import com.salesmanager.core.model.catalog.product.PinCode;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.checkout.PinCodeFacade;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.model.customer.PersistablePinCode;
import com.salesmanager.shop.model.customer.PersistablePinCodeList;
import com.salesmanager.shop.model.customer.mapper.CustomerMapper;
import com.salesmanager.shop.model.customer.mapper.PinCodeMapper;
import com.salesmanager.shop.store.api.exception.ResourceDuplicateException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("pinCodeFacade")
public class PinCodeFacadeImpl implements PinCodeFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(PinCodeFacadeImpl.class);
  @Inject private PinCodeService pinCodeService;
  @Inject private CustomerService customerService;
  @Inject private CustomerMapper customerMapper;
  @Inject private PinCodeMapper pinCodeMapper;

  @Override
  public PersistablePinCode create(
      PersistablePinCode persistablePinCode, MerchantStore merchantStore) {
    PinCode pinCode = null;
    try {
      pinCode = pinCodeService.getPinCodeByCode(persistablePinCode.getPinCode());
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PINCODE_GET_BY_CODE_FAILURE.getErrorCode(),
          AttributeErrorCodes.PINCODE_GET_BY_CODE_FAILURE.getErrorMessage());
    }
    if (Objects.nonNull(pinCode)) {
      LOGGER.error("PinCode Already exists for" + persistablePinCode.getPinCode());
      throw new ResourceDuplicateException(
          "PinCode Already exists for " + persistablePinCode.getPinCode());
    }
    pinCode = pinCodeMapper.toPinCode(persistablePinCode);
    try {
      pinCode = pinCodeService.create(pinCode);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PINCODE_CREATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.PINCODE_CREATE_FAILURE.getErrorMessage());
    }
    persistablePinCode.setId(pinCode.getId());
    return persistablePinCode;
  }

  @Override
  public PersistablePinCode update(
      PersistablePinCode persistablePinCode, MerchantStore merchantStore) {
    PinCode pinCode = pinCodeMapper.toPinCode(persistablePinCode);
    try {
      pinCodeService.update(pinCode);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PINCODE_UPDATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.PINCODE_UPDATE_FAILURE.getErrorMessage());
    }
    return persistablePinCode;
  }

  @Override
  public void deleteById(Long id) {
    Validate.notNull(id, "pinCode id cannot be null");
    PinCode pinCode = null;
    try {
      pinCode = getById(id);
      pinCodeService.delete(pinCode);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PINCODE_DELETE_FAILURE.getErrorCode(),
          AttributeErrorCodes.PINCODE_DELETE_FAILURE.getErrorMessage());
    }
  }

  @Override
  public PersistablePinCode getPinCodeById(
      Long id, MerchantStore merchantStore, Language language) {
    PinCode pinCode = getById(id);
    return pinCodeMapper.toPersistablePinCode(pinCode);
  }

  private PinCode getById(Long id) {
    PinCode pinCode = null;
    try {
      pinCode = pinCodeService.getById(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PINCODE_GET_BY_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.PINCODE_GET_BY_ID_FAILURE.getErrorMessage() + id);
    }
    if (Objects.isNull(pinCode)) {
      LOGGER.error("pinCode with id [" + id + " ] not found in DB.");
      throw new ResourceNotFoundException("pinCode with id [" + id + " ] not found");
    }
    return pinCode;
  }

  @Override
  public PersistablePinCodeList getAllPinCode(MerchantStore merchantStore, Language language) {
    PersistablePinCodeList persistablePinCodeList = new PersistablePinCodeList();
    try {
      persistablePinCodeList.setPersistablePinCodes(
          Optional.ofNullable(pinCodeService.getAllPinCode())
              .map(Collection::stream)
              .orElse(Stream.empty())
              .map(pinCodeMapper::toPersistablePinCode)
              .collect(Collectors.toList()));
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PINCODE_GET_ALL_FAILURE.getErrorCode(),
          AttributeErrorCodes.PINCODE_GET_ALL_FAILURE.getErrorMessage());
    }
    return persistablePinCodeList;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
