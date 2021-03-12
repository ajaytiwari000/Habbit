package com.salesmanager.core.business.services.order.orderproduct;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.order.orderproduct.CustomerOrderProduct;

public interface CustomerOrderProductService
    extends SalesManagerEntityService<Long, CustomerOrderProduct> {

  CustomerOrderProduct findByOrderProductCode(String orderProductCode) throws ServiceException;
}
