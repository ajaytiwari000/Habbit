package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.order.PaytmOrder;
import com.salesmanager.shop.model.customer.PersistablePaytmOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaytmOrderMapper {
  PaytmOrder toPaytmOrder(PersistablePaytmOrder persistablePaytmOrder);

  PersistablePaytmOrder toPersistablePaytmOrder(PaytmOrder PaytmOrder);
}
