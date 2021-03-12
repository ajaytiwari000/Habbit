/** */
package com.salesmanager.shop.admin.controller.customer.membership;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.PersistableOwnerTypeList;
import com.salesmanager.shop.model.customer.PersistableReferralCode;
import com.salesmanager.shop.model.customer.PersistableReferralCodeList;

public interface ReferralCodeFacade {

  PersistableReferralCode create(
      PersistableReferralCode persistableReferralCode, MerchantStore merchantStore);

  PersistableReferralCode update(
      PersistableReferralCode persistableReferralCode, MerchantStore merchantStore);

  void deleteById(Long id);

  PersistableReferralCode getReferralCodeById(
      Long id, MerchantStore merchantStore, Language language);

  PersistableReferralCode getReferralCodeByCode(String referralCode);

  PersistableReferralCode getReferralCodeByPhone(String phoneNumber);

  PersistableReferralCodeList getAllReferralCode(MerchantStore merchantStore, Language language);

  PersistableOwnerTypeList allOwnerType(MerchantStore merchantStore, Language language);
}
