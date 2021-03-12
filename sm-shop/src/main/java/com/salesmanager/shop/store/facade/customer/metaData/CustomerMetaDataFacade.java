/** */
package com.salesmanager.shop.store.facade.customer.metaData;

import com.salesmanager.core.model.customer.CustomerMetaData;
import java.util.Date;
import java.util.List;

public interface CustomerMetaDataFacade {
  CustomerMetaData createCustomerMetaData(CustomerMetaData customerMetaData);

  CustomerMetaData getCustomerMetaData(String phone);

  CustomerMetaData updateCustomerMetaData(CustomerMetaData customerMetaData);

  List<CustomerMetaData> getMetaDataList(Date from, Date to);
}
