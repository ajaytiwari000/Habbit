package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.shop.model.customer.PersistableAddress;
import com.salesmanager.shop.model.customer.PersistableCustomerOrderAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersistableOrderAddressAndPersistableAddressMapper {
  PersistableAddress toPersistableAddress(
      PersistableCustomerOrderAddress persistableCustomerOrderAddress);

  PersistableCustomerOrderAddress toPersistableCustomerOrderAddress(
      PersistableAddress persistableAddress);
}
