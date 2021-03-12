package com.salesmanager.core.business.services.order.orderproduct;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.order.orderproduct.CustomerOrderProductRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.order.orderproduct.CustomerOrderProduct;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerOrderProductService")
public class CustomerOrderProductServiceImpl
    extends SalesManagerEntityServiceImpl<Long, CustomerOrderProduct>
    implements CustomerOrderProductService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomerOrderProductServiceImpl.class);

  CustomerOrderProductRepository customerOrderProductRepository;

  @Inject
  public CustomerOrderProductServiceImpl(
      CustomerOrderProductRepository customerOrderProductRepository) {
    super(customerOrderProductRepository);
    this.customerOrderProductRepository = customerOrderProductRepository;
  }

  @Override
  public CustomerOrderProduct getById(Long id) {
    return customerOrderProductRepository.findById(id).orElse(null);
  }

  @Override
  public CustomerOrderProduct findByOrderProductCode(String orderProductCode)
      throws ServiceException {
    try {
      return customerOrderProductRepository.findByOrderProductCode(orderProductCode).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
