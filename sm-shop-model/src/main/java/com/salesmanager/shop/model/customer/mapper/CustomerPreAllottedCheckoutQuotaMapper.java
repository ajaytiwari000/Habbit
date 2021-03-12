package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.shoppingcart.CustomerPreAllottedCheckoutQuota;
import com.salesmanager.shop.model.customer.PersistableCustomerPreAllottedCheckoutQuota;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerPreAllottedCheckoutQuotaMapper {
  CustomerPreAllottedCheckoutQuota toCustomerPreAllottedCheckoutQuota(
      PersistableCustomerPreAllottedCheckoutQuota persistableCustomerPreAllottedCheckoutQuota);

  PersistableCustomerPreAllottedCheckoutQuota toPersistableCustomerPreAllottedCheckoutQuota(
      CustomerPreAllottedCheckoutQuota CustomerPreAllottedCheckoutQuota);
}
