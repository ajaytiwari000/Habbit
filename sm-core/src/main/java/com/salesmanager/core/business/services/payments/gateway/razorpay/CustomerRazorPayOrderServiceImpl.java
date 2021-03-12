package com.salesmanager.core.business.services.payments.gateway.razorpay;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.payments.gateway.razorpay.CustomerRazorPayOrderRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.services.order.CustomerOrderServiceImpl;
import com.salesmanager.core.model.order.CustomerRazorPayOrder;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerRazorPayOrderService")
public class CustomerRazorPayOrderServiceImpl
    extends SalesManagerEntityServiceImpl<Long, CustomerRazorPayOrder>
    implements CustomerRazorPayOrderService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerOrderServiceImpl.class);

  CustomerRazorPayOrderRepository customerRazorPayOrderRepository;

  @Inject
  public CustomerRazorPayOrderServiceImpl(
      CustomerRazorPayOrderRepository customerRazorPayOrderRepository) {
    super(customerRazorPayOrderRepository);
    this.customerRazorPayOrderRepository = customerRazorPayOrderRepository;
  }

  @Override
  public CustomerRazorPayOrder getById(Long id) {
    return customerRazorPayOrderRepository.findById(id).orElse(null);
  }

  @Override
  public CustomerRazorPayOrder getByRazorPayOrderCode(String orderCode) throws ServiceException {
    try {
      return customerRazorPayOrderRepository.findByRazorPayOrderCode(orderCode).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public CustomerRazorPayOrder getByReceipt(String receipt) throws ServiceException {
    try {
      return customerRazorPayOrderRepository.findByReceipt(receipt).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
