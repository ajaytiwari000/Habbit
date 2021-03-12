package com.salesmanager.shop.populator.customer;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.customer.Address;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.CustomerOrderAddress;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.PersistableAddress;
import com.salesmanager.shop.model.customer.PersistableAddressList;
import com.salesmanager.shop.model.customer.PersistableCustomer;
import com.salesmanager.shop.model.customer.PersistableCustomerOrderAddress;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class PersistableAddressPopulator
    extends AbstractDataPopulator<Address, PersistableAddress> {

  @Override
  protected PersistableAddress createTarget() {
    return null;
  }

  @Override
  public PersistableAddress populate(
      Address source, PersistableAddress target, MerchantStore store, Language language)
      throws ConversionException {
    if (Objects.nonNull(source)) {
      if (Objects.nonNull(source.getCustomer())) {
        target.setCustomerId(source.getCustomer().getId());
        target.setCustomerUsername(source.getCustomer().getUserName());
      }
      target.setId(source.getId());
      target.setAddressType(source.getAddressType());
      target.setCity(source.getCity());
      target.setState(source.getState());
      target.setCountry(source.getCountry());
      target.setFirstName(source.getFirstName());
      target.setLastName(source.getLastName());
      target.setDefaultAddress(source.isDefaultAddress());
      target.setLine1(source.getLine1());
      target.setLine2(source.getLine2());
      target.setPhoneNumber(source.getPhoneNumber());
      target.setPinCode(source.getPinCode());
    }
    return target;
  }

  public PersistableCustomerOrderAddress populate(
      CustomerOrderAddress source,
      PersistableCustomerOrderAddress target,
      MerchantStore store,
      Language language)
      throws ConversionException {
    if (Objects.nonNull(source)) {
      target.setId(source.getId());
      target.setAddressType(source.getAddressType());
      target.setCity(source.getCity());
      target.setState(source.getState());
      target.setCountry(source.getCountry());
      target.setFirstName(source.getFirstName());
      target.setLastName(source.getLastName());
      target.setDefaultAddress(source.isDefaultAddress());
      target.setLine1(source.getLine1());
      target.setLine2(source.getLine2());
      target.setPhoneNumber(source.getPhoneNumber());
      target.setPinCode(source.getPinCode());
    }
    return target;
  }

  public PersistableAddressList populateAll(
      List<Address> source, PersistableAddressList target, MerchantStore store, Language language)
      throws ConversionException {
    if (Objects.nonNull(source)) {
      Customer customer = source.get(0).getCustomer();
      if (Objects.nonNull(customer)) {
        PersistableCustomer persistableCustomer = new PersistableCustomer();
        persistableCustomer.setId(customer.getId());
        persistableCustomer.setUserName(customer.getUserName());
        target.setPersistableCustomer(persistableCustomer);
      }

      for (Address address : source) {
        PersistableAddress persistableAddress = new PersistableAddress();
        persistableAddress.setId(address.getId());
        persistableAddress.setAddressType(address.getAddressType());
        persistableAddress.setCity(address.getCity());
        persistableAddress.setState(address.getState());
        persistableAddress.setCountry(address.getCountry());
        persistableAddress.setFirstName(address.getFirstName());
        persistableAddress.setLastName(address.getLastName());
        persistableAddress.setDefaultAddress(address.isDefaultAddress());
        persistableAddress.setLine1(address.getLine1());
        persistableAddress.setLine2(address.getLine2());
        persistableAddress.setPhoneNumber(address.getPhoneNumber());
        persistableAddress.setPinCode(address.getPinCode());
        target.getPersistableAddressList().add(persistableAddress);
      }
    }
    return target;
  }
}
