package com.salesmanager.core.business.services.customer.checkoutAudit;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.order.CheckoutAuditFlow;

public interface CheckoutAuditFlowService
    extends SalesManagerEntityService<Long, CheckoutAuditFlow> {

  CheckoutAuditFlow getByOrderCode(String orderCode) throws ServiceException;
}
