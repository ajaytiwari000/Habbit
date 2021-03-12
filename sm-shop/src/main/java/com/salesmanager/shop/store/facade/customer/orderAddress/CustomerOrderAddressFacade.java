/** */
package com.salesmanager.shop.store.facade.customer.orderAddress;

import com.salesmanager.core.model.customer.CustomerOrderAddress;
import com.salesmanager.shop.model.customer.PersistableCustomerOrderAddress;

public interface CustomerOrderAddressFacade {

  CustomerOrderAddress convertOrderAddressFromAddress(PersistableCustomerOrderAddress address);
}
