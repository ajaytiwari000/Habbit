package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.order.CustomerRazorPayOrder;
import com.salesmanager.shop.model.customer.PersistableCustomerRazorPayOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerRazorPayOrderMapper {
  CustomerRazorPayOrder toCustomerRazorPayOrder(
      PersistableCustomerRazorPayOrder persistableCustomerRazorPayOrder);

  PersistableCustomerRazorPayOrder toPersistableCustomerRazorPayOrder(
      CustomerRazorPayOrder CustomerRazorPayOrder);
}
