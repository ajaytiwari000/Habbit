/** */
package com.salesmanager.shop.admin.controller.customer.membership;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.customer.PersistableCustomerPreAllottedCheckoutQuota;

public interface CustomerPreAllottedCheckoutQuotaFacade {

  PersistableCustomerPreAllottedCheckoutQuota create(
      PersistableCustomerPreAllottedCheckoutQuota persistableCustomerPreAllottedCheckoutQuota,
      MerchantStore merchantStore);

  PersistableCustomerPreAllottedCheckoutQuota update(
      PersistableCustomerPreAllottedCheckoutQuota persistableCustomerPreAllottedCheckoutQuota,
      MerchantStore merchantStore);

  void deleteById(Long id);

  PersistableCustomerPreAllottedCheckoutQuota getCustomerProductConsumptionPendingByCartItemCode(
      String productSku, String phone);
}
