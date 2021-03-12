package com.salesmanager.core.business.services.order;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.order.CustomerSaleOrderRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.order.CustomerSaleOrder;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerSaleOrderService")
public class CustomerSaleOrderServiceImpl
    extends SalesManagerEntityServiceImpl<Long, CustomerSaleOrder>
    implements CustomerSaleOrderService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerSaleOrderServiceImpl.class);

  CustomerSaleOrderRepository customerSaleOrderRepository;

  @Inject
  public CustomerSaleOrderServiceImpl(CustomerSaleOrderRepository customerSaleOrderRepository) {
    super(customerSaleOrderRepository);
    this.customerSaleOrderRepository = customerSaleOrderRepository;
  }

  @Override
  public CustomerSaleOrder getById(Long id) {
    return customerSaleOrderRepository.findById(id).orElse(null);
  }

  @Override
  public CustomerSaleOrder getByCode(String code) throws ServiceException {
    try {
      return customerSaleOrderRepository.getByCode(code).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
