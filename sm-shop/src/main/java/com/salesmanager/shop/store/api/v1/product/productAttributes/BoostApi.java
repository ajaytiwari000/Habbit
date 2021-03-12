package com.salesmanager.shop.store.api.v1.product.productAttributes;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.attributes.BoostAttributeFacade;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableBoostList;
import com.salesmanager.shop.model.productAttribute.PersistableBoost;
import io.swagger.annotations.*;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api/v1")
@Api(
    value = "All Attributes API",
    tags = {"Product Attribute management resource (Product Attribute Management Api)"})
@SwaggerDefinition(
    tags = {
      @Tag(
          name = "Product attributes management resource",
          description = "Add attributes, edit attributes and delete attributes")
    })
public class BoostApi {
  private static final Logger LOGGER = LoggerFactory.getLogger(BoostApi.class);
  @Inject private BoostAttributeFacade boostAttributeFacade;

  @ApiOperation(
      httpMethod = "POST",
      value = "Create boost",
      notes = "",
      produces = "application/json",
      response = PersistableBoost.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/private/boost", method = RequestMethod.POST)
  public @ResponseBody PersistableBoost create(
      @Valid @RequestBody PersistableBoost boost,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response) {
    // TODO: To add boost image field in DB and here!
    try {
      boostAttributeFacade.createBoost(boost, merchantStore);
      return boost;
    } catch (Exception e) {
      LOGGER.error("Error while creating Boost", e);
      try {
        response.sendError(500, "Error while creating Boost " + e.getMessage());
      } catch (Exception ignore) {
      }
      return null;
    }
  }

  @ApiOperation(
      httpMethod = "PUT",
      value = "Update Boost",
      notes = "",
      produces = "application/json",
      response = PersistableBoost.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/boost"},
      method = RequestMethod.PUT)
  public @ResponseBody PersistableBoost update(
      @Valid @RequestBody PersistableBoost boost,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response) {
    try {
      boostAttributeFacade.updateBoost(boost, merchantStore);
      return boost;
    } catch (Exception e) {
      LOGGER.error("Error while updating Boost", e);
      try {
        response.sendError(500, "Error while updating Boost " + e.getMessage());
      } catch (Exception ignore) {
      }
      return null;
    }
  }

  @ApiOperation(
      httpMethod = "GET",
      value = "Get Boost",
      notes = "",
      produces = "application/json",
      response = PersistableBoost.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/boost/{id}"},
      method = RequestMethod.GET)
  public @ResponseBody PersistableBoost get(
      @PathVariable Long id,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    return boostAttributeFacade.getBoost(id, merchantStore);
  }

  @ApiOperation(httpMethod = "DELETE", value = "Delete Boost", notes = "", response = Void.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/boost/delete/{id}"},
      method = RequestMethod.DELETE)
  public void delete(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language)
      throws Exception {
    boostAttributeFacade.deleteBoost(id, merchantStore);
  }

  @ApiOperation(
      httpMethod = "GET",
      value = "Get All packs based on page size",
      notes = "",
      produces = "application/json",
      response = PersistableBoostList.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/boosts/"},
      method = RequestMethod.GET)
  public @ResponseBody PersistableBoostList getAllPacks(
      @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "pageSize", required = false, defaultValue = "100") Integer pageSize,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    return boostAttributeFacade.getAllUniqueBoost(page, pageSize, merchantStore);
  }
}
