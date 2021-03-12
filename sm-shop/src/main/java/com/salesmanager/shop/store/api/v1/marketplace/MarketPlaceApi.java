package com.salesmanager.shop.store.api.v1.marketplace;

import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.marketplace.ReadableMarketPlace;
import com.salesmanager.shop.store.controller.store.facade.StoreFacade;
import com.salesmanager.shop.store.facade.marketplace.MarketPlaceFacade;
import com.salesmanager.shop.utils.LanguageUtils;
import io.swagger.annotations.ApiOperation;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MarketPlaceApi {

  @Inject private MarketPlaceFacade marketPlaceFacade;

  @Inject private StoreFacade storeFacade;

  @Inject private LanguageUtils languageUtils;

  /** Get a marketplace from storeCode returns market place details and merchant store */
  @GetMapping("/private/marketplace/{store}")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get market place meta-data",
      notes = "",
      produces = "application/json",
      response = ReadableMarketPlace.class)
  public ReadableMarketPlace marketPlace(
      @PathVariable String store, @RequestParam(value = "lang", required = false) String lang) {

    Language language = languageUtils.getServiceLanguage(lang);
    return marketPlaceFacade.get(store, language);
  }
}
