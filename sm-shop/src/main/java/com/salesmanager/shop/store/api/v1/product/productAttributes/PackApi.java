package com.salesmanager.shop.store.api.v1.product.productAttributes;

import com.salesmanager.core.business.services.catalog.attribute.PackService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.attributes.PackAttributeFacade;
import com.salesmanager.shop.model.catalog.product.attribute.PersistablePack;
import com.salesmanager.shop.model.catalog.product.attribute.PersistablePackList;
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
public class PackApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(PackApi.class);

  @Inject private PackService packService;

  @Inject private PackAttributeFacade packAttributeFacade;

  @ApiOperation(
      httpMethod = "POST",
      value = "Create pack",
      notes = "",
      produces = "application/json",
      response = PersistablePack.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/private/pack", method = RequestMethod.POST)
  public @ResponseBody PersistablePack create(
      @Valid @RequestBody PersistablePack pack,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response) {
    try {
      packAttributeFacade.createPack(pack, merchantStore);
      return pack;
    } catch (Exception e) {
      LOGGER.error("Error while creating pack", e);
      try {
        response.sendError(500, "Error while creating pack " + e.getMessage());
      } catch (Exception ignore) {
      }
      return null;
    }
  }

  @ApiOperation(
      httpMethod = "PUT",
      value = "Update pack",
      notes = "",
      produces = "application/json",
      response = PersistablePack.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/pack"},
      method = RequestMethod.PUT)
  public @ResponseBody PersistablePack update(
      @Valid @RequestBody PersistablePack pack,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response) {
    try {
      packAttributeFacade.updatePack(pack, merchantStore);
      return pack;
    } catch (Exception e) {
      LOGGER.error("Error while updating pack", e);
      try {
        response.sendError(500, "Error while updating pack " + e.getMessage());
      } catch (Exception ignore) {
      }
      return null;
    }
  }

  @ApiOperation(
      httpMethod = "GET",
      value = "Get pack",
      notes = "",
      produces = "application/json",
      response = PersistablePack.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/pack/{id}"},
      method = RequestMethod.GET)
  public @ResponseBody PersistablePack get(
      @PathVariable Long id,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    return packAttributeFacade.getPack(id, merchantStore);
  }

  @ApiOperation(
      httpMethod = "GET",
      value = "Get All packs based on page size",
      notes = "",
      produces = "application/json",
      response = PersistablePackList.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/packs"},
      method = RequestMethod.GET)
  public @ResponseBody PersistablePackList getAllPacks(
      @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "pageSize", required = false, defaultValue = "100") Integer pageSize,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    return packAttributeFacade.getAllUniquePack(page, pageSize, merchantStore);
  }

  @ApiOperation(httpMethod = "DELETE", value = "Delete pack", notes = "", response = Void.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/pack/delete/{id}"},
      method = RequestMethod.DELETE)
  public void delete(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language)
      throws Exception {
    packAttributeFacade.deletePack(id, merchantStore);
  }
}
