/** */
package com.salesmanager.shop.store.facade.customer.address;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.PersistableAddress;
import com.salesmanager.shop.model.customer.PersistableAddressList;
import com.salesmanager.shop.model.customer.PersistablePinCode;

public interface CustomerAddressFacade {

  PersistableAddress create(
      PersistableAddress persistableAddress,
      MerchantStore merchantStore,
      boolean unSetDefaultAddressFlag);

  PersistableAddress update(
      PersistableAddress persistableAddress,
      MerchantStore merchantStore,
      boolean unSetDefaultAddressFlag);

  void deleteById(Long id);

  PersistableAddress getCustomerAddressById(
      Long id, MerchantStore merchantStore, Language language);

  PersistableAddressList getCustomerAllAddressByCustomerId(
      Long id, MerchantStore merchantStore, Language language);

  PersistablePinCode isServiceablePinCode(
      String pinCode, MerchantStore merchantStore, Language language);
}
