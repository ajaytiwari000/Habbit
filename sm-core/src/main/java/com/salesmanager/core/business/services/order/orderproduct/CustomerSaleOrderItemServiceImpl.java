package com.salesmanager.core.business.services.order.orderproduct;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.order.orderproduct.CustomerSaleOrderItemRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.order.orderproduct.CustomerSaleOrderItem;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerSaleOrderItemService")
public class CustomerSaleOrderItemServiceImpl
    extends SalesManagerEntityServiceImpl<Long, CustomerSaleOrderItem>
    implements CustomerSaleOrderItemService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomerSaleOrderItemServiceImpl.class);

  CustomerSaleOrderItemRepository customerSaleOrderItemRepository;

  @Inject
  public CustomerSaleOrderItemServiceImpl(
      CustomerSaleOrderItemRepository customerSaleOrderItemRepository) {
    super(customerSaleOrderItemRepository);
    this.customerSaleOrderItemRepository = customerSaleOrderItemRepository;
  }

  @Override
  public CustomerSaleOrderItem getById(Long id) {
    return customerSaleOrderItemRepository.findById(id).orElse(null);
  }

  @Override
  public CustomerSaleOrderItem findByOrderTemCode(String channelSaleOrderItemCode)
      throws ServiceException {
    try {
      return customerSaleOrderItemRepository
          .findByOrderTemCode(channelSaleOrderItemCode)
          .orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
