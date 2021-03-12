package com.salesmanager.shop.store.facade.country;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.references.ReadableCountry;
import java.util.List;

public interface CountryFacade {
  List<ReadableCountry> getListCountryZones(Language language, MerchantStore merchantStore);
}
