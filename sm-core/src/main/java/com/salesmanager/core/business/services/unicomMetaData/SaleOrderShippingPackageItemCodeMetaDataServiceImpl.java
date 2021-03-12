package com.salesmanager.core.business.services.unicomMetaData;

import com.salesmanager.core.business.repositories.unicomMetaData.SaleOrderShippingPackageItemCodeMetaDataRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.order.SaleOrderShippingPackageItemCodeMetaData;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("saleOrderShippingPackageItemCodeMetaDataService")
public class SaleOrderShippingPackageItemCodeMetaDataServiceImpl
    extends SalesManagerEntityServiceImpl<Long, SaleOrderShippingPackageItemCodeMetaData>
    implements SaleOrderShippingPackageItemCodeMetaDataService {

  SaleOrderShippingPackageItemCodeMetaDataRepository
      saleOrderShippingPackageItemCodeMetaDataRepository;

  @Inject
  public SaleOrderShippingPackageItemCodeMetaDataServiceImpl(
      SaleOrderShippingPackageItemCodeMetaDataRepository
          saleOrderShippingPackageItemCodeMetaDataRepository) {
    super(saleOrderShippingPackageItemCodeMetaDataRepository);
    this.saleOrderShippingPackageItemCodeMetaDataRepository =
        saleOrderShippingPackageItemCodeMetaDataRepository;
  }
}
