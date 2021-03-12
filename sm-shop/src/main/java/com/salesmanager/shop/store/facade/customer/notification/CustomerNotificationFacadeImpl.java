/** */
package com.salesmanager.shop.store.facade.customer.notification;

import com.salesmanager.core.business.HabbitCoreConstant;
import com.salesmanager.core.business.services.customer.notification.CustomerNotificationService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.CustomerNotification;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.cache.util.CacheUtil;
import com.salesmanager.shop.error.codes.CustomerErrorCodes;
import com.salesmanager.shop.mapper.product.CustomerNotificationMapper;
import com.salesmanager.shop.model.customer.PersistableCustomerNotification;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.facade.customer.CustomerFacade;
import com.salesmanager.shop.utils.SessionUtil;
import java.util.Objects;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerNotificationFacade")
public class CustomerNotificationFacadeImpl implements CustomerNotificationFacade {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomerNotificationFacadeImpl.class);
  @Inject private CustomerNotificationService customerNotificationService;
  @Inject private CustomerFacade customerFacade;
  @Inject private SessionUtil sessionUtil;
  @Inject private CustomerNotificationMapper customerNotificationMapper;
  @Inject private CacheUtil cacheUtil;

  @Override
  public CustomerNotification createCustomerNotification(
      PersistableCustomerNotification persistableCustomerNotification) {
    CustomerNotification customerNotification = null;
    customerNotification = getByPhone(persistableCustomerNotification.getPhone());
    if (Objects.nonNull(customerNotification)) {
      return customerNotification;
    }
    customerNotification =
        customerNotificationMapper.toCustomerNotification(persistableCustomerNotification);
    try {
      customerNotification = customerNotificationService.create(customerNotification);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_NOTIFICATION_CREATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_NOTIFICATION_CREATE_FAILURE.getErrorMessage());
    }
    cacheUtil.setObjectInCache(
        persistableCustomerNotification.getPhone() + HabbitCoreConstant._NOTIFICATION,
        customerNotification);
    return customerNotification;
  }

  private CustomerNotification getByPhone(String phone) {
    CustomerNotification customerNotification = null;
    try {
      customerNotification = customerNotificationService.getByPhone(phone);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_NOTIFICATION_GET_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_NOTIFICATION_GET_FAILURE.getErrorMessage());
    }
    return customerNotification;
  }

  @Override
  public PersistableCustomerNotification getCustomerNotification(
      HttpServletRequest request, MerchantStore store) {
    String phone = sessionUtil.getPhoneByAuthToken(request);
    Customer customer = customerFacade.getCustomerByUserName(phone, store);
    return customerNotificationMapper.toPersistableCustomerNotification(
        customer.getCustomerNotification());
  }

  @Override
  public PersistableCustomerNotification updateCustomerNotification(
      PersistableCustomerNotification customerNotification,
      HttpServletRequest request,
      MerchantStore store) {
    String phone = sessionUtil.getPhoneByAuthToken(request);
    Customer customer = customerFacade.getCustomerByUserName(phone, store);
    CustomerNotification customerNotificationModel = customer.getCustomerNotification();
    updatePopulator(customerNotification, customerNotificationModel);
    try {
      customerNotificationModel = customerNotificationService.update(customerNotificationModel);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_NOTIFICATION_UPDATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_NOTIFICATION_UPDATE_FAILURE.getErrorMessage());
    }
    cacheUtil.setObjectInCache(
        customerNotification.getPhone() + HabbitCoreConstant._NOTIFICATION,
        customerNotificationModel);
    return customerNotificationMapper.toPersistableCustomerNotification(customerNotificationModel);
  }

  private void updatePopulator(
      PersistableCustomerNotification source, CustomerNotification target) {
    target.setEmailEnable(source.isEmailEnable());
    target.setSmsEnable(source.isSmsEnable());
    target.setWhatsAppEnable(source.isWhatsAppEnable());
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
