package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.shop.model.customer.PersistableCustomer;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {AddressMapper.class})
public interface CustomerMapper {
  Customer toCustomer(PersistableCustomer persistableCustomer);

  PersistableCustomer toPersistableCustomer(Customer customer);
}
