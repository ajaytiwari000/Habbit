package com.salesmanager.core.business.services.order;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.order.orderstatus.CustomerOrderStatusHistory;
import java.util.List;

public interface CustomerOrderStatusHistoryService
    extends SalesManagerEntityService<Long, CustomerOrderStatusHistory> {

  CustomerOrderStatusHistory findByOrderCode(String orderCode) throws ServiceException;

  List<CustomerOrderStatusHistory> findAllOrderByCustomerId(Long id) throws ServiceException;
}
