package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.order.orderproduct.CustomerSaleOrderItem;
import com.salesmanager.shop.model.order.PersistableCustomerSaleOrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerSaleOrderItemMapper {
  CustomerSaleOrderItem toCustomerSaleOrderItem(
      PersistableCustomerSaleOrderItem persistableCustomerSaleOrderItem);

  PersistableCustomerSaleOrderItem toPersistableCustomerSaleOrderItem(
      CustomerSaleOrderItem customerSaleOrderItem);
}
