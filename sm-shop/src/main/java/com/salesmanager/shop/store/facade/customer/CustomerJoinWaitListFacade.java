/** */
package com.salesmanager.shop.store.facade.customer;

import com.salesmanager.core.model.customer.CustomerJoinWaitList;

public interface CustomerJoinWaitListFacade {

  CustomerJoinWaitList joinWaitList(String phone);
}
