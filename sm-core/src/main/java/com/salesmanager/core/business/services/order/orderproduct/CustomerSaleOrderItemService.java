package com.salesmanager.core.business.services.order.orderproduct;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.order.orderproduct.CustomerSaleOrderItem;

public interface CustomerSaleOrderItemService
    extends SalesManagerEntityService<Long, CustomerSaleOrderItem> {

  CustomerSaleOrderItem findByOrderTemCode(String channelSaleOrderItemCode) throws ServiceException;
}
