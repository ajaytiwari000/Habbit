package com.salesmanager.core.business.services.order.sale;

import com.salesmanager.core.business.repositories.order.sale.SaleOrderShipperDetailsRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.order.SaleOrderShipperDetails;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("saleOrderShipperDetailsService")
public class SaleOrderShipperDetailsServiceImpl
    extends SalesManagerEntityServiceImpl<Long, SaleOrderShipperDetails>
    implements SaleOrderShipperDetailsService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(SaleOrderShipperDetailsServiceImpl.class);

  SaleOrderShipperDetailsRepository saleOrderShipperDetailsRepository;

  @Inject
  public SaleOrderShipperDetailsServiceImpl(
      SaleOrderShipperDetailsRepository saleOrderShipperDetailsRepository) {
    super(saleOrderShipperDetailsRepository);
    this.saleOrderShipperDetailsRepository = saleOrderShipperDetailsRepository;
  }
}
