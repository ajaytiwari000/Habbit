package com.salesmanager.shop.admin.controller.unicomMetaData;

import com.salesmanager.core.model.order.CustomerSaleOrder;
import com.salesmanager.core.model.order.SaleOrderMetaData;

public interface SaleOrderMetaDataFacade {
  void createOrUpdate(SaleOrderMetaData saleOrderMetaData, CustomerSaleOrder customerSaleOrder);
}
