package com.salesmanager.core.business.services.membership;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.shoppingcart.CustomerPreAllottedCheckoutQuota;

public interface CustomerPreAllottedCheckoutQuotaService
    extends SalesManagerEntityService<Long, CustomerPreAllottedCheckoutQuota> {
  CustomerPreAllottedCheckoutQuota getById(Long id);

  CustomerPreAllottedCheckoutQuota getCustomerProductConsumptionPendingByCartItemCode(
      String productSku, String phone) throws ServiceException;
}
