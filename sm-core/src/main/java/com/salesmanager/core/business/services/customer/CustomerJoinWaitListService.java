package com.salesmanager.core.business.services.customer;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.customer.CustomerJoinWaitList;

public interface CustomerJoinWaitListService
    extends SalesManagerEntityService<Long, CustomerJoinWaitList> {

  CustomerJoinWaitList getByPhone(String phone) throws ServiceException;
}
