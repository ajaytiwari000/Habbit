package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.customer.CustomerOrderAddress;
import com.salesmanager.core.model.order.CustomerSaleOrder;
import com.salesmanager.shop.model.customer.PersistableCustomerSaleOrderAddress;
import com.salesmanager.shop.model.order.UnicommerceCreateSaleOrderRequest;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
    componentModel = "spring",
    uses = {
      CustomerSaleOrderItemMapper.class,
      CustomerOrderAddressMapper.class,
    })
public interface CustomerSaleOrderMapper {

  @Mapping(source = "addresses", target = "addresses", qualifiedByName = "listToObj")
  CustomerSaleOrder toCustomerSaleOrder(
      UnicommerceCreateSaleOrderRequest unicommerceCreateSaleOrderRequest);

  @Mapping(source = "addresses", target = "addresses", qualifiedByName = "objToList")
  UnicommerceCreateSaleOrderRequest toPersistableCustomerSaleOrder(
      CustomerSaleOrder customerSaleOrder);

  @Named("listToObj")
  static CustomerOrderAddress listToObj(List<PersistableCustomerSaleOrderAddress> addresses) {
    CustomerOrderAddress address = new CustomerOrderAddress();
    toCustomerOrderAddress(address, addresses.get(0));
    return address;
  }

  static void toCustomerOrderAddress(
      CustomerOrderAddress target, PersistableCustomerSaleOrderAddress source) {

    target.setId(source.getId());
    target.setPhoneNumber(source.getPhone());
    target.setFirstName(source.getName());
    target.setLine1(source.getAddressLine1());
    target.setLine2(source.getAddressLine2());
    target.setPinCode(source.getPincode());
    target.setCity(source.getCity());
    target.setCountry(source.getCountry());
    target.setState(source.getState());
  }

  @Named("objToList")
  static List<PersistableCustomerSaleOrderAddress> objToList(CustomerOrderAddress addresses) {
    List<PersistableCustomerSaleOrderAddress> l = new ArrayList<>();
    PersistableCustomerSaleOrderAddress address = new PersistableCustomerSaleOrderAddress();
    toPersistableCustomerOrderAddress(address, addresses);
    l.add(address);
    return l;
  }

  static void toPersistableCustomerOrderAddress(
      PersistableCustomerSaleOrderAddress target, CustomerOrderAddress source) {
    target.setId(source.getId());
    target.setPhone(source.getPhoneNumber());
    target.setName(source.getFirstName());
    target.setAddressLine1(source.getLine1());
    target.setAddressLine2(source.getLine2());
    target.setPincode(source.getPinCode());
    target.setCity(source.getCity());
    target.setCountry(source.getCountry());
    target.setState(source.getState());
  }
}
