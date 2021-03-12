package com.salesmanager.core.business.services.payments.gateway.paytm;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.payments.paytm.PaytmOrderRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.order.PaytmOrder;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("paytmOrderService")
public class PaytmOrderServiceImpl extends SalesManagerEntityServiceImpl<Long, PaytmOrder>
    implements PaytmOrderService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PaytmOrderServiceImpl.class);

  PaytmOrderRepository paytmOrderRepository;

  @Inject
  public PaytmOrderServiceImpl(PaytmOrderRepository PaytmOrderRepository) {
    super(PaytmOrderRepository);
    this.paytmOrderRepository = PaytmOrderRepository;
  }

  @Override
  public PaytmOrder getById(Long id) {
    return paytmOrderRepository.findById(id).orElse(null);
  }

  @Override
  public PaytmOrder getByPaytmOrderCode(String orderCode) throws ServiceException {
    try {
      return paytmOrderRepository.findByPaytmOrderCode(orderCode).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
