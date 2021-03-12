package com.salesmanager.shop.store.controller.product.facade;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.category.ReadableMerchandiseCategoryProducts;
import com.salesmanager.shop.model.catalog.product.ReadableMerchandiseProductDetails;

public interface MerchandiseProductFacade {

  ReadableMerchandiseProductDetails getProductDetail(
      MerchantStore merchantStore, Long id, Language language, String skuId, String productName)
      throws Exception;

  ReadableMerchandiseProductDetails getProductDetailsByCategoryFlavorAndPack(
      MerchantStore merchantStore,
      Long categoryId,
      Long packId,
      Language language,
      MerchantStore store,
      String productName)
      throws Exception;

  ReadableMerchandiseCategoryProducts getProductList(
      Long id, MerchantStore merchantStore, Language language)
      throws ServiceException, ConversionException;
}
