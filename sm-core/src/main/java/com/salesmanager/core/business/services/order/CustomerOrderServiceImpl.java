package com.salesmanager.core.business.services.order;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.order.CustomerOrderRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.order.CustomerOrder;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerOrderService")
public class CustomerOrderServiceImpl extends SalesManagerEntityServiceImpl<Long, CustomerOrder>
    implements CustomerOrderService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerOrderServiceImpl.class);

  CustomerOrderRepository customerOrderRepository;

  @Inject
  public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository) {
    super(customerOrderRepository);
    this.customerOrderRepository = customerOrderRepository;
  }

  @Override
  public CustomerOrder getById(Long id) {
    return customerOrderRepository.findById(id).orElse(null);
  }

  @Override
  public CustomerOrder findByOrderCode(String orderCode) throws ServiceException {
    try {
      return customerOrderRepository.findByOrderCode(orderCode).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
