package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.order.orderstatus.CustomerOrderStatusHistory;
import com.salesmanager.shop.model.customer.PersistableCustomerOrderStatusHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerOrderStatusHistoryMapper {
  CustomerOrderStatusHistory toCustomerOrderStatusHistory(
      PersistableCustomerOrderStatusHistory persistableCustomerOrderStatusHistory);

  PersistableCustomerOrderStatusHistory toPersistableCustomerOrderStatusHistory(
      CustomerOrderStatusHistory customerOrderStatusHistory);
}
