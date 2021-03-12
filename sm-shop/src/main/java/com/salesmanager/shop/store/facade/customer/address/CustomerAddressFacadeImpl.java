/** */
package com.salesmanager.shop.store.facade.customer.address;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.customer.CustomerAddressService;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.pincode.PinCodeService;
import com.salesmanager.core.model.catalog.product.PinCode;
import com.salesmanager.core.model.customer.Address;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.cache.util.CacheUtil;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.error.codes.CustomerErrorCodes;
import com.salesmanager.shop.model.customer.PersistableAddress;
import com.salesmanager.shop.model.customer.PersistableAddressList;
import com.salesmanager.shop.model.customer.PersistablePinCode;
import com.salesmanager.shop.model.customer.mapper.AddressMapper;
import com.salesmanager.shop.model.customer.mapper.CustomerMapper;
import com.salesmanager.shop.model.customer.mapper.PinCodeMapper;
import com.salesmanager.shop.populator.customer.PersistableAddressPopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import java.util.Objects;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerAddressFacade")
public class CustomerAddressFacadeImpl implements CustomerAddressFacade {
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAddressFacadeImpl.class);

  @Inject private CacheUtil cacheUtil;
  @Inject private CustomerAddressService customerAddressService;
  @Inject private PinCodeService pinCodeService;
  @Inject private PinCodeMapper pinCodeMapper;
  @Inject private CustomerService customerService;
  @Inject private CustomerMapper customerMapper;
  @Inject private AddressMapper addressMapper;
  @Inject private PersistableAddressPopulator persistableAddressPopulator;

  @Override
  public PersistableAddress create(
      PersistableAddress persistableAddress,
      MerchantStore merchantStore,
      boolean unSetDefaultAddressFlag) {
    if (persistableAddress.isDefaultAddress() && unSetDefaultAddressFlag) {
      unSetDefaultAddress(persistableAddress.getCustomerId());
    }
    Customer customer = null;
    try {
      customer = customerService.getByUserName(persistableAddress.getCustomerUsername());
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_GET_BY_USERNAME_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_GET_BY_USERNAME_FAILURE.getErrorMessage());
    }
    Address address = addressMapper.toAddress(persistableAddress);
    address.setCustomer(customer);
    try {
      address = customerAddressService.create(address);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ADDRESS_CREATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ADDRESS_CREATE_FAILURE.getErrorMessage());
    }
    persistableAddress.setId(address.getId());
    return persistableAddress;
  }

  @Override
  public PersistableAddress update(
      PersistableAddress persistableAddress,
      MerchantStore merchantStore,
      boolean unSetDefaultAddressFlag) {
    if (persistableAddress.isDefaultAddress() && unSetDefaultAddressFlag) {
      unSetDefaultAddress(persistableAddress.getCustomerId());
    }
    Customer customer = null;
    try {
      customer = customerService.getByUserName(persistableAddress.getCustomerUsername());
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_GET_BY_USERNAME_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_GET_BY_USERNAME_FAILURE.getErrorMessage());
    }
    Address address = addressMapper.toAddress(persistableAddress);
    address.setCustomer(customer);
    try {
      customerAddressService.update(address);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ADDRESS_UPDATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ADDRESS_UPDATE_FAILURE.getErrorMessage());
    }
    return persistableAddress;
  }

  private void unSetDefaultAddress(Long id) {
    try {
      Address address = customerAddressService.getCustomerDefaultAddress(id);
      if (Objects.nonNull(address)) {
        address.setDefaultAddress(false);
        customerAddressService.update(address);
      }
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_DEFAULT_ADDRESS_UPDATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_DEFAULT_ADDRESS_UPDATE_FAILURE.getErrorMessage());
    }
  }

  @Override
  public void deleteById(Long id) {
    Address address = null;
    try {
      try {
        address = customerAddressService.getById(id);
      } catch (Exception e) {
        throwServiceRuntImeException(
            e,
            CustomerErrorCodes.CUSTOMER_ADDRESS_GET_BY_ID_FAILURE.getErrorCode(),
            CustomerErrorCodes.CUSTOMER_ADDRESS_GET_BY_ID_FAILURE.getErrorMessage() + id);
      }
      customerAddressService.delete(address);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ADDRESS_DELETE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ADDRESS_DELETE_FAILURE.getErrorMessage());
    }
  }

  @Override
  public PersistableAddress getCustomerAddressById(
      Long id, MerchantStore merchantStore, Language language) {
    //    cacheUtil.setObjectInCache("ajay", new String("vijay"), 300);
    //    System.out.println(cacheUtil.getObjectFromCache("ajay", String.class));
    PersistableAddress persistableAddress = new PersistableAddress();
    Address address = null;
    try {
      address = customerAddressService.getById(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ADDRESS_GET_BY_ID_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ADDRESS_GET_BY_ID_FAILURE.getErrorMessage() + id);
    }
    try {
      persistableAddress =
          persistableAddressPopulator.populate(
              address, persistableAddress, merchantStore, language);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ADDRESS_POPULATOR_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ADDRESS_POPULATOR_FAILURE.getErrorMessage() + id);
    }
    return persistableAddress;
  }

  @Override
  public PersistableAddressList getCustomerAllAddressByCustomerId(
      Long id, MerchantStore merchantStore, Language language) {
    PersistableAddressList persistableAddressList = new PersistableAddressList();
    try {
      persistableAddressList =
          persistableAddressPopulator.populateAll(
              customerAddressService.getCustomerAllAddressByCustomerId(id),
              persistableAddressList,
              merchantStore,
              language);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ADDRESS_GET_ALL_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ADDRESS_GET_ALL_FAILURE.getErrorMessage() + id);
    }
    return persistableAddressList;
  }

  @Override
  public PersistablePinCode isServiceablePinCode(
      String code, MerchantStore merchantStore, Language language) {
    PinCode pinCode = null;
    try {
      pinCode = pinCodeService.getPinCodeByCode(code);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PINCODE_GET_BY_CODE_FAILURE.getErrorCode(),
          AttributeErrorCodes.PINCODE_GET_BY_CODE_FAILURE.getErrorMessage());
    }
    return Objects.nonNull(pinCode)
        ? pinCodeMapper.toPersistablePinCode(pinCode)
        : new PersistablePinCode();
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
