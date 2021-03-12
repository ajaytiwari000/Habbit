package com.salesmanager.core.business.services.customer.notification;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.customer.notification.CustomerNotificationRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.customer.CustomerNotification;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerNotificationService")
public class CustomerNotificationServiceImpl
    extends SalesManagerEntityServiceImpl<Long, CustomerNotification>
    implements CustomerNotificationService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomerNotificationServiceImpl.class);

  private CustomerNotificationRepository customerNotificationRepository;

  @Inject
  public CustomerNotificationServiceImpl(
      CustomerNotificationRepository customerNotificationRepository) {
    super(customerNotificationRepository);
    this.customerNotificationRepository = customerNotificationRepository;
  }

  @Override
  public CustomerNotification getByPhone(String phone) throws ServiceException {
    try {
      return customerNotificationRepository.getByPhone(phone).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
