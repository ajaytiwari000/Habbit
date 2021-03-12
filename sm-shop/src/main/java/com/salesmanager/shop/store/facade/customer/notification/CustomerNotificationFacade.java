/** */
package com.salesmanager.shop.store.facade.customer.notification;

import com.salesmanager.core.model.customer.CustomerNotification;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.customer.PersistableCustomerNotification;
import javax.servlet.http.HttpServletRequest;

public interface CustomerNotificationFacade {
  PersistableCustomerNotification updateCustomerNotification(
      PersistableCustomerNotification customerNotification,
      HttpServletRequest request,
      MerchantStore store);

  CustomerNotification createCustomerNotification(
      PersistableCustomerNotification customerNotification);

  PersistableCustomerNotification getCustomerNotification(
      HttpServletRequest request, MerchantStore store);
}
