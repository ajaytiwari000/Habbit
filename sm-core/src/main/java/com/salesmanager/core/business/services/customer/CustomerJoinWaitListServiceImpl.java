package com.salesmanager.core.business.services.customer;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.customer.CustomerJoinWaitListRepository;
import com.salesmanager.core.business.repositories.customer.aws.CustomerFileManager;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.services.customer.attribute.CustomerAttributeService;
import com.salesmanager.core.model.customer.CustomerJoinWaitList;
import com.salesmanager.core.modules.utils.GeoLocation;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerJoinWaitListService")
public class CustomerJoinWaitListServiceImpl
    extends SalesManagerEntityServiceImpl<Long, CustomerJoinWaitList>
    implements CustomerJoinWaitListService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomerJoinWaitListServiceImpl.class);

  private CustomerJoinWaitListRepository customerJoinWaitListRepository;

  @Inject private CustomerAttributeService customerAttributeService;

  @Inject private GeoLocation geoLocation;

  @Inject private CustomerFileManager customerFileManager;

  @Inject
  public CustomerJoinWaitListServiceImpl(
      CustomerJoinWaitListRepository customerJoinWaitListRepository) {
    super(customerJoinWaitListRepository);
    this.customerJoinWaitListRepository = customerJoinWaitListRepository;
  }

  @Override
  public CustomerJoinWaitList getByPhone(String phone) throws ServiceException {
    try {
      return customerJoinWaitListRepository.findByPhone(phone).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
