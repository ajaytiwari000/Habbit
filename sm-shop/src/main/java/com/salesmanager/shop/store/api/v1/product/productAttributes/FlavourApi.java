package com.salesmanager.shop.store.api.v1.product.productAttributes;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.attributes.FlavourAttributeFacade;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableFlavourList;
import com.salesmanager.shop.model.productAttribute.PersistableFlavour;
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
@Api(tags = {"Product Attribute management resource (Product Attribute Management Api)"})
@SwaggerDefinition(
    tags = {
      @Tag(
          name = "Product attributes management resource",
          description = "Add attributes, edit attributes and delete attributes")
    })
public class FlavourApi {
  private static final Logger LOGGER = LoggerFactory.getLogger(FlavourApi.class);
  @Inject private FlavourAttributeFacade flavourAttributeFacade;

  @ApiOperation(
      httpMethod = "POST",
      value = "Create flavour",
      notes = "",
      produces = "application/json",
      response = PersistableFlavour.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/private/flavour", method = RequestMethod.POST)
  public @ResponseBody PersistableFlavour create(
      @Valid @RequestBody PersistableFlavour flavour,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response) {
    try {
      flavourAttributeFacade.createFlavour(flavour, merchantStore);
      return flavour;
    } catch (Exception e) {
      LOGGER.error("Error while creating Flavour", e);
      try {
        response.sendError(500, "Error while creating Flavour " + e.getMessage());
      } catch (Exception ignore) {
      }
      return null;
    }
  }

  @ApiOperation(
      httpMethod = "PUT",
      value = "Update Flavour",
      notes = "",
      produces = "application/json",
      response = PersistableFlavour.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/private/flavour", method = RequestMethod.PUT)
  public @ResponseBody PersistableFlavour update(
      @Valid @RequestBody PersistableFlavour flavour,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response) {
    try {
      flavourAttributeFacade.updateFlavour(flavour, merchantStore);
      return flavour;
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
      value = "Get Flavour",
      notes = "",
      produces = "application/json",
      response = PersistableFlavour.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/flavour/{id}"},
      method = RequestMethod.GET)
  public @ResponseBody PersistableFlavour get(
      @PathVariable Long id,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    return flavourAttributeFacade.getFlavour(id, merchantStore);
  }

  @ApiOperation(httpMethod = "DELETE", value = "Delete Flavour", notes = "", response = Void.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/flavour/delete/{id}"},
      method = RequestMethod.DELETE)
  public void delete(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language)
      throws Exception {
    flavourAttributeFacade.deleteFlavour(id, merchantStore);
  }

  @ApiOperation(
      httpMethod = "GET",
      value = "Get All Flavour based on page size",
      notes = "",
      produces = "application/json",
      response = PersistableFlavourList.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/flavours/"},
      method = RequestMethod.GET)
  public @ResponseBody PersistableFlavourList getAllPacks(
      @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "pageSize", required = false, defaultValue = "100") Integer pageSize,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    return flavourAttributeFacade.getAllUniqueFlavour(page, pageSize, merchantStore);
  }
}
