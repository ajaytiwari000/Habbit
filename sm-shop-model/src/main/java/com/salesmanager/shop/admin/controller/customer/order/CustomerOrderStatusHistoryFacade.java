/** */
package com.salesmanager.shop.admin.controller.customer.order;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.customer.PersistableCustomerOrderStatusHistory;
import com.salesmanager.shop.model.customer.PersistableCustomerOrderStatusHistoryList;

public interface CustomerOrderStatusHistoryFacade {

  PersistableCustomerOrderStatusHistory createOrUpdate(
      PersistableCustomerOrderStatusHistory persistableCustomerOrderStatusHistory,
      MerchantStore merchantStore,
      String phone);

  PersistableCustomerOrderStatusHistoryList getOrderStatusHistoryList(
      MerchantStore merchantStore, String phone);
}
