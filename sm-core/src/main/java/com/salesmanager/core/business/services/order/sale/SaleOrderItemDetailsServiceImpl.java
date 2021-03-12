package com.salesmanager.core.business.services.order.sale;

import com.salesmanager.core.business.repositories.order.sale.SaleOrderItemDetailsRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.order.SaleOrderItemDetails;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("saleOrderItemDetailsService")
public class SaleOrderItemDetailsServiceImpl
    extends SalesManagerEntityServiceImpl<Long, SaleOrderItemDetails>
    implements SaleOrderItemDetailsService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(SaleOrderItemDetailsServiceImpl.class);

  SaleOrderItemDetailsRepository saleOrderItemDetailsRepository;

  @Inject
  public SaleOrderItemDetailsServiceImpl(
      SaleOrderItemDetailsRepository saleOrderItemDetailsRepository) {
    super(saleOrderItemDetailsRepository);
    this.saleOrderItemDetailsRepository = saleOrderItemDetailsRepository;
  }

  //  @Override
  //  public CustomerSaleOrder getById(Long id) {
  //    return customerSaleOrderRepository.findById(id).orElse(null);
  //  }
  //
  //  @Override
  //  public CustomerSaleOrder findByOrderCode(String orderCode) throws ServiceException {
  //    try {
  //      return customerSaleOrderRepository.findByOrderCode(orderCode).orElse(null);
  //    } catch (Exception e) {
  //      throw new ServiceException(e);
  //    }
  //  }
}
