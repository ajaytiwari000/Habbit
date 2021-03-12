package com.salesmanager.core.business.services.order;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.order.CustomerOrderStatusHistoryRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.order.orderstatus.CustomerOrderStatusHistory;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerOrderStatusHistoryService")
public class CustomerOrderStatusHistoryServiceImpl
    extends SalesManagerEntityServiceImpl<Long, CustomerOrderStatusHistory>
    implements CustomerOrderStatusHistoryService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomerOrderStatusHistoryServiceImpl.class);

  CustomerOrderStatusHistoryRepository customerOrderStatusHistoryRepository;

  @Inject
  public CustomerOrderStatusHistoryServiceImpl(
      CustomerOrderStatusHistoryRepository customerOrderStatusHistoryRepository) {
    super(customerOrderStatusHistoryRepository);
    this.customerOrderStatusHistoryRepository = customerOrderStatusHistoryRepository;
  }

  @Override
  public CustomerOrderStatusHistory getById(Long id) {
    return customerOrderStatusHistoryRepository.findById(id).orElse(null);
  }

  @Override
  public CustomerOrderStatusHistory findByOrderCode(String orderCode) throws ServiceException {
    try {
      return customerOrderStatusHistoryRepository.findByOrderCode(orderCode).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public List<CustomerOrderStatusHistory> findAllOrderByCustomerId(Long id)
      throws ServiceException {
    try {
      return customerOrderStatusHistoryRepository.findAllOrderByCustomerId(id).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
