package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.order.orderproduct.CustomerOrderProduct;
import com.salesmanager.shop.mapper.product.BoostMapper;
import com.salesmanager.shop.mapper.product.FlavourMapper;
import com.salesmanager.shop.mapper.product.PackMapper;
import com.salesmanager.shop.model.customer.PersistableCustomerOrderProduct;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {FlavourMapper.class, PackMapper.class, BoostMapper.class})
public interface CustomerOrderProductMapper {
  CustomerOrderProduct toCustomerOrderProduct(
      PersistableCustomerOrderProduct persistableCustomerOrderProduct);

  PersistableCustomerOrderProduct toPersistableCustomerOrderProduct(
      CustomerOrderProduct customerOrderProduct);
}
