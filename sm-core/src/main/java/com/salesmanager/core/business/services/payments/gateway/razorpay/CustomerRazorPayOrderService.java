package com.salesmanager.core.business.services.payments.gateway.razorpay;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.order.CustomerRazorPayOrder;

public interface CustomerRazorPayOrderService
    extends SalesManagerEntityService<Long, CustomerRazorPayOrder> {

  CustomerRazorPayOrder getByRazorPayOrderCode(String orderCode) throws ServiceException;

  CustomerRazorPayOrder getByReceipt(String receipt) throws ServiceException;
}
