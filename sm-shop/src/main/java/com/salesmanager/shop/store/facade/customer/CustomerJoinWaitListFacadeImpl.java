/** */
package com.salesmanager.shop.store.facade.customer;

import com.salesmanager.core.business.services.customer.CustomerJoinWaitListService;
import com.salesmanager.core.model.customer.CustomerJoinWaitList;
import com.salesmanager.shop.error.codes.CustomerErrorCodes;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import java.util.Objects;
import javax.inject.Inject;
import org.elasticsearch.ResourceAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerJoinWaitListFacade")
public class CustomerJoinWaitListFacadeImpl implements CustomerJoinWaitListFacade {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomerJoinWaitListFacadeImpl.class);

  @Inject private CustomerJoinWaitListService customerJoinWaitListService;

  @Override
  public CustomerJoinWaitList joinWaitList(String phone) {
    CustomerJoinWaitList customerJoinWaitList = null;
    try {
      customerJoinWaitList = customerJoinWaitListService.getByPhone(phone);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_JOIN_WAIT_LIST_GET_BY_PHONE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_JOIN_WAIT_LIST_GET_BY_PHONE_FAILURE.getErrorMessage()
              + phone);
    }
    if (Objects.nonNull(customerJoinWaitList)) {
      throw new ResourceAlreadyExistsException("customer already exist in join wait list " + phone);
    }
    customerJoinWaitList = new CustomerJoinWaitList();
    customerJoinWaitList.setPhone(phone);
    try {
      customerJoinWaitList = customerJoinWaitListService.create(customerJoinWaitList);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_JOIN_WAIT_LIST_CREATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_JOIN_WAIT_LIST_CREATE_FAILURE.getErrorMessage() + phone);
    }
    return customerJoinWaitList;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
