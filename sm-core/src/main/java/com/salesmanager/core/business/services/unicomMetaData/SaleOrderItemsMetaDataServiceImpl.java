package com.salesmanager.core.business.services.unicomMetaData;

import com.salesmanager.core.business.repositories.unicomMetaData.SaleOrderItemsMetaDataRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.order.SaleOrderItemsMetaData;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("saleOrderItemsMetaDataService")
public class SaleOrderItemsMetaDataServiceImpl
    extends SalesManagerEntityServiceImpl<Long, SaleOrderItemsMetaData>
    implements SaleOrderItemsMetaDataService {

  SaleOrderItemsMetaDataRepository saleOrderItemsMetaDataRepository;

  @Inject
  public SaleOrderItemsMetaDataServiceImpl(
      SaleOrderItemsMetaDataRepository saleOrderItemsMetaDataRepository) {
    super(saleOrderItemsMetaDataRepository);
    this.saleOrderItemsMetaDataRepository = saleOrderItemsMetaDataRepository;
  }
}
