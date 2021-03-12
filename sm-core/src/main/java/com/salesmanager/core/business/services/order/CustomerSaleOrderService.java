package com.salesmanager.core.business.services.order;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.order.CustomerSaleOrder;

public interface CustomerSaleOrderService
    extends SalesManagerEntityService<Long, CustomerSaleOrder> {

  CustomerSaleOrder getByCode(String code) throws ServiceException;
}
