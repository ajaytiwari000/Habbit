package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.order.CustomerOrder;
import com.salesmanager.shop.model.customer.PersistableCustomerOrder;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {
      CustomerOrderAddressMapper.class,
      CustomerOrderProductMapper.class,
      CustomerOrderStatusHistoryMapper.class
    })
public interface CustomerOrderMapper {
  CustomerOrder toCustomerOrder(PersistableCustomerOrder persistableCustomerOrder);

  PersistableCustomerOrder toPersistableCustomerOrder(CustomerOrder customerOrder);
}
