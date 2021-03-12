/** */
package com.salesmanager.shop.admin.controller.checkout;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.PersistableCouponCode;
import com.salesmanager.shop.model.customer.PersistableCouponCodeList;
import com.salesmanager.shop.model.customer.PersistableCouponCodeTypeList;
import com.salesmanager.shop.model.customer.PersistableDiscountTypeList;

public interface CouponCodeFacade {

  PersistableCouponCode create(
      PersistableCouponCode persistableCouponCode,
      MerchantStore merchantStore,
      Long unSetDefaultCouponId);

  PersistableCouponCode update(
      PersistableCouponCode persistableCouponCode,
      MerchantStore merchantStore,
      Long unSetDefaultCouponId);

  void deleteById(Long id);

  PersistableCouponCode getCouponCodeById(Long id, MerchantStore merchantStore, Language language);

  PersistableCouponCode getCouponCodeByCode(String CouponCode);

  PersistableCouponCodeList getAllCouponCode(MerchantStore merchantStore, Language language);

  PersistableDiscountTypeList allDiscountType(MerchantStore merchantStore, Language language);

  PersistableCouponCodeTypeList allCouponCodeType(MerchantStore merchantStore, Language language);

  PersistableCouponCode getDefaultCouponCode(MerchantStore merchantStore, Language language);
}
