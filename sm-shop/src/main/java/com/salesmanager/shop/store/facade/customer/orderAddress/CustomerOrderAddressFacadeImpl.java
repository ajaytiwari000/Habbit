/** */
package com.salesmanager.shop.store.facade.customer.orderAddress;

import com.salesmanager.core.business.services.customer.CustomerOrderAddressService;
import com.salesmanager.core.model.customer.CustomerOrderAddress;
import com.salesmanager.shop.error.codes.CustomerErrorCodes;
import com.salesmanager.shop.model.customer.PersistableCustomerOrderAddress;
import com.salesmanager.shop.model.customer.mapper.CustomerOrderAddressMapper;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerOrderAddressFacade")
public class CustomerOrderAddressFacadeImpl implements CustomerOrderAddressFacade {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomerOrderAddressFacadeImpl.class);

  @Inject private CustomerOrderAddressMapper customerOrderAddressMapper;
  @Inject private CustomerOrderAddressService customerOrderAddressService;

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }

  @Override
  public CustomerOrderAddress convertOrderAddressFromAddress(
      PersistableCustomerOrderAddress address) {
    CustomerOrderAddress customerOrderAddress =
        customerOrderAddressMapper.toCustomerOrderAddress(address);
    customerOrderAddress.setId(null);
    try {
      customerOrderAddress = customerOrderAddressService.create(customerOrderAddress);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ORDER_ADDRESS_CREATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ORDER_ADDRESS_CREATE_FAILURE.getErrorMessage());
    }
    return customerOrderAddress;
  }
}
