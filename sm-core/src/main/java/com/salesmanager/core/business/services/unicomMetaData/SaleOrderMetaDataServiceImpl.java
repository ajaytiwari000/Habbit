package com.salesmanager.core.business.services.unicomMetaData;

import com.salesmanager.core.business.repositories.unicomMetaData.SaleOrderMetaDataRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.order.SaleOrderMetaData;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("saleOrderMetaDataService")
public class SaleOrderMetaDataServiceImpl
    extends SalesManagerEntityServiceImpl<Long, SaleOrderMetaData>
    implements SaleOrderMetaDataService {

  SaleOrderMetaDataRepository saleOrderMetaDataRepository;

  @Inject
  public SaleOrderMetaDataServiceImpl(SaleOrderMetaDataRepository saleOrderMetaDataRepository) {
    super(saleOrderMetaDataRepository);
    this.saleOrderMetaDataRepository = saleOrderMetaDataRepository;
  }
  //
  //  @Override
  //  public List<Boost> getBoostsByCriteria(Criteria criteria) throws ServiceException {
  //    try {
  //      return boostRepository.getBoosts(criteria).orElse(null);
  //    } catch (Exception e) {
  //      throw new ServiceException(e);
  //    }
  //  }
}
