package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.customer.CustomerNotification;
import com.salesmanager.shop.model.customer.PersistableCustomerNotification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerNotificationMapper {
  CustomerNotification toCustomerNotification(
      PersistableCustomerNotification persistableCustomerNotification);

  PersistableCustomerNotification toPersistableCustomerNotification(
      CustomerNotification customerNotification);
}
