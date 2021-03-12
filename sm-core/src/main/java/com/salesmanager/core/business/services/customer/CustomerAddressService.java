package com.salesmanager.core.business.services.customer;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.customer.Address;
import java.util.List;

public interface CustomerAddressService extends SalesManagerEntityService<Long, Address> {
  List<Address> getCustomerAllAddressByCustomerId(Long id) throws ServiceException;

  Address getById(Long id);

  Address getCustomerDefaultAddress(Long id) throws ServiceException;
}
