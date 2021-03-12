/** */
package com.salesmanager.shop.admin.controller.checkout;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.PersistablePinCode;
import com.salesmanager.shop.model.customer.PersistablePinCodeList;

public interface PinCodeFacade {

  PersistablePinCode create(PersistablePinCode persistablePinCode, MerchantStore merchantStore);

  PersistablePinCode update(PersistablePinCode persistablePinCode, MerchantStore merchantStore);

  void deleteById(Long id);

  PersistablePinCode getPinCodeById(Long id, MerchantStore merchantStore, Language language);

  PersistablePinCodeList getAllPinCode(MerchantStore merchantStore, Language language);
}
