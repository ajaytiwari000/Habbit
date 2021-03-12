package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.catalog.product.PinCode;
import com.salesmanager.shop.model.customer.PersistablePinCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PinCodeMapper {
  PinCode toPinCode(PersistablePinCode persistablePinCode);

  PersistablePinCode toPersistablePinCode(PinCode pinCode);
}
