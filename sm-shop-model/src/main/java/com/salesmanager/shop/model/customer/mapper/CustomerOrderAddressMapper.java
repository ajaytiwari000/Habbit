package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.customer.CustomerOrderAddress;
import com.salesmanager.shop.model.customer.PersistableCustomerOrderAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerOrderAddressMapper {
  CustomerOrderAddress toCustomerOrderAddress(PersistableCustomerOrderAddress persistableAddress);

  PersistableCustomerOrderAddress toPersistableCustomerOrderAddress(CustomerOrderAddress address);
}
