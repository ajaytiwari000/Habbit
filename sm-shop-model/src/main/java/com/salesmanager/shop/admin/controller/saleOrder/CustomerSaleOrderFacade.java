package com.salesmanager.shop.admin.controller.saleOrder;

import com.salesmanager.core.model.order.CustomerSaleOrder;
import com.salesmanager.core.model.order.SaleOrderMetaData;

public interface CustomerSaleOrderFacade {

  CustomerSaleOrder createCustomerSaleOrder(CustomerSaleOrder saleOrder);

  void updateSaleOrderAndMetaData(
      CustomerSaleOrder customerSaleOrder, SaleOrderMetaData saleOrderMetaData);

  CustomerSaleOrder getByCode(String code);

  CustomerSaleOrder updateOrder(CustomerSaleOrder customerSaleOrder);
}
