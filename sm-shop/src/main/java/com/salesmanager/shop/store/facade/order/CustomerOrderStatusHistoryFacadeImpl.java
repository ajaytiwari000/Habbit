/** */
package com.salesmanager.shop.store.facade.order;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.order.CustomerOrderStatusHistoryService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.orderstatus.CustomerOrderStatusHistory;
import com.salesmanager.shop.admin.controller.customer.order.CustomerOrderStatusHistoryFacade;
import com.salesmanager.shop.error.codes.CustomerErrorCodes;
import com.salesmanager.shop.model.customer.PersistableCustomerOrderStatusHistory;
import com.salesmanager.shop.model.customer.PersistableCustomerOrderStatusHistoryList;
import com.salesmanager.shop.model.customer.mapper.CustomerOrderStatusHistoryMapper;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerOrderStatusHistoryFacade")
public class CustomerOrderStatusHistoryFacadeImpl implements CustomerOrderStatusHistoryFacade {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomerOrderStatusHistoryFacadeImpl.class);
  @Inject private CustomerOrderStatusHistoryService customerOrderStatusHistoryService;
  @Inject private CustomerService customerService;
  @Inject private CustomerOrderStatusHistoryMapper customerOrderStatusHistoryMapper;

  @Override
  public PersistableCustomerOrderStatusHistory createOrUpdate(
      PersistableCustomerOrderStatusHistory persistableCustomerOrderStatusHistory,
      MerchantStore merchantStore,
      String phone) {
    CustomerOrderStatusHistory customerOrderStatusHistoryExist =
        getOrderStatusHistory(persistableCustomerOrderStatusHistory.getOrderCode());
    if (Objects.nonNull(customerOrderStatusHistoryExist)) {
      customerOrderStatusHistoryExist.setComments(
          persistableCustomerOrderStatusHistory.getComments());
      customerOrderStatusHistoryExist.setTrackingUrl(
          persistableCustomerOrderStatusHistory.getTrackingUrl());
      customerOrderStatusHistoryExist.setTrackingUrlType(
          persistableCustomerOrderStatusHistory.getTrackingUrlType());
      customerOrderStatusHistoryExist.setStatus(persistableCustomerOrderStatusHistory.getStatus());
      return update(customerOrderStatusHistoryExist);
    }

    Customer customerModel = getCustomer(phone);
    CustomerOrderStatusHistory customerOrderStatusHistory =
        customerOrderStatusHistoryMapper.toCustomerOrderStatusHistory(
            persistableCustomerOrderStatusHistory);
    customerOrderStatusHistory.setCustomer(customerModel);
    try {
      customerOrderStatusHistory =
          customerOrderStatusHistoryService.create(customerOrderStatusHistory);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ORDER_STATUS_HISTORY_CREATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ORDER_STATUS_HISTORY_CREATE_FAILURE.getErrorMessage());
    }

    return customerOrderStatusHistoryMapper.toPersistableCustomerOrderStatusHistory(
        customerOrderStatusHistory);
  }

  private PersistableCustomerOrderStatusHistory update(
      CustomerOrderStatusHistory customerOrderStatusHistory) {
    try {
      customerOrderStatusHistory =
          customerOrderStatusHistoryService.update(customerOrderStatusHistory);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ORDER_STATUS_HISTORY_UPDATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ORDER_STATUS_HISTORY_UPDATE_FAILURE.getErrorMessage());
    }
    return customerOrderStatusHistoryMapper.toPersistableCustomerOrderStatusHistory(
        customerOrderStatusHistory);
  }

  private CustomerOrderStatusHistory getOrderStatusHistory(String orderCode) {
    CustomerOrderStatusHistory customerOrderStatusHistory = null;
    try {
      customerOrderStatusHistory = customerOrderStatusHistoryService.findByOrderCode(orderCode);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ORDER_STATUS_HISTORY_BY_CODE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ORDER_STATUS_HISTORY_BY_CODE_FAILURE.getErrorMessage());
    }
    return customerOrderStatusHistory;
  }

  private Customer getCustomer(String phone) {
    Customer customerModel = null;
    try {
      customerModel = customerService.getByUserName(phone);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_GET_BY_USERNAME_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_GET_BY_USERNAME_FAILURE.getErrorMessage());
    }
    return customerModel;
  }

  @Override
  public PersistableCustomerOrderStatusHistoryList getOrderStatusHistoryList(
      MerchantStore merchantStore, String phone) {
    PersistableCustomerOrderStatusHistoryList persistableCustomerOrderStatusHistoryList =
        new PersistableCustomerOrderStatusHistoryList();
    Customer customerModel = getCustomer(phone);
    try {
      persistableCustomerOrderStatusHistoryList.setCustomerOrderStatusHistories(
          Optional.ofNullable(
                  customerOrderStatusHistoryService.findAllOrderByCustomerId(customerModel.getId()))
              .map(Collection::stream)
              .orElseGet(Stream::empty)
              .map(customerOrderStatusHistoryMapper::toPersistableCustomerOrderStatusHistory)
              .collect(Collectors.toList()));
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ORDER_STATUS_HISTORY_GET_ALL_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ORDER_STATUS_HISTORY_GET_ALL_FAILURE.getErrorMessage());
    }
    return persistableCustomerOrderStatusHistoryList;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
