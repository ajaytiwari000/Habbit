package com.salesmanager.core.business.services.unicomMetaData;

import com.salesmanager.core.business.repositories.unicomMetaData.SaleOrderShippingPackageMetaDataRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.order.SaleOrderShippingPackageMetaData;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("saleOrderShippingPackageMetaDataService")
public class SaleOrderShippingPackageMetaDataServiceImpl
    extends SalesManagerEntityServiceImpl<Long, SaleOrderShippingPackageMetaData>
    implements SaleOrderShippingPackageMetaDataService {

  SaleOrderShippingPackageMetaDataRepository saleOrderShippingPackageMetaDataRepository;

  @Inject
  public SaleOrderShippingPackageMetaDataServiceImpl(
      SaleOrderShippingPackageMetaDataRepository saleOrderShippingPackageMetaDataRepository) {
    super(saleOrderShippingPackageMetaDataRepository);
    this.saleOrderShippingPackageMetaDataRepository = saleOrderShippingPackageMetaDataRepository;
  }
}
