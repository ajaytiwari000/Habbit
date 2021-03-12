package com.salesmanager.shop.store.controller.product.facade;

import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.type.ReadableProductType;
import java.util.List;

public interface ProductTypeFacade {

  List<ReadableProductType> getByMerchant(String merchant, Language language);
}
