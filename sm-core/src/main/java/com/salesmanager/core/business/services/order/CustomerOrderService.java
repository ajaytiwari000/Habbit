package com.salesmanager.core.business.services.order;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.order.CustomerOrder;

public interface CustomerOrderService extends SalesManagerEntityService<Long, CustomerOrder> {

  CustomerOrder findByOrderCode(String orderCode) throws ServiceException;
}
