package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.customer.Address;
import com.salesmanager.shop.model.customer.PersistableAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
  Address toAddress(PersistableAddress persistableAddress);

  PersistableAddress toPersistableAddress(Address address);
}
