package com.salesmanager.core.business.services.customer.metaData;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.customer.CustomerMetaData;
import java.util.Date;
import java.util.List;

public interface CustomerMetaDataService extends SalesManagerEntityService<Long, CustomerMetaData> {
  CustomerMetaData getByPhone(String phone) throws ServiceException;

  List<CustomerMetaData> getMetaDataList(Date from, Date to) throws ServiceException;
}
