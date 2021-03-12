package com.salesmanager.core.business.services.payments.gateway.paytm;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.order.PaytmOrder;

public interface PaytmOrderService extends SalesManagerEntityService<Long, PaytmOrder> {

  PaytmOrder getByPaytmOrderCode(String orderCode) throws ServiceException;
}
