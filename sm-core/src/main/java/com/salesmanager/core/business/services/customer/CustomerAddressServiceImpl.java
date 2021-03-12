package com.salesmanager.core.business.services.customer;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.customer.CustomerAddressRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.customer.Address;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerAddressService")
public class CustomerAddressServiceImpl extends SalesManagerEntityServiceImpl<Long, Address>
    implements CustomerAddressService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAddressServiceImpl.class);
  private CustomerAddressRepository customerAddressRepository;

  @Inject
  public CustomerAddressServiceImpl(CustomerAddressRepository customerAddressRepository) {
    super(customerAddressRepository);
    this.customerAddressRepository = customerAddressRepository;
  }

  @Override
  public List<Address> getCustomerAllAddressByCustomerId(Long id) throws ServiceException {
    try {
      return customerAddressRepository.getCustomerAllAddressByCustomerId(id).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public Address getById(Long id) {
    return customerAddressRepository.getById(id).orElse(null);
  }

  @Override
  public Address getCustomerDefaultAddress(Long id) throws ServiceException {
    try {
      return customerAddressRepository.getCustomerDefaultAddress(id).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
