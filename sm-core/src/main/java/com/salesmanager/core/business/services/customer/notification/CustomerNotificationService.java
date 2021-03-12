package com.salesmanager.core.business.services.customer.notification;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.customer.CustomerNotification;

public interface CustomerNotificationService
    extends SalesManagerEntityService<Long, CustomerNotification> {

  CustomerNotification getByPhone(String phone) throws ServiceException;
}
