package com.salesmanager.core.business.services.customer;

import com.salesmanager.core.business.repositories.customer.CustomerOrderAddressRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.customer.CustomerOrderAddress;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerOrderAddressService")
public class CustomerOrderAddressServiceImpl
    extends SalesManagerEntityServiceImpl<Long, CustomerOrderAddress>
    implements CustomerOrderAddressService {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomerOrderAddressServiceImpl.class);
  private CustomerOrderAddressRepository customerOrderAddressRepository;

  @Inject
  public CustomerOrderAddressServiceImpl(
      CustomerOrderAddressRepository customerOrderAddressRepository) {
    super(customerOrderAddressRepository);
    this.customerOrderAddressRepository = customerOrderAddressRepository;
  }
}
