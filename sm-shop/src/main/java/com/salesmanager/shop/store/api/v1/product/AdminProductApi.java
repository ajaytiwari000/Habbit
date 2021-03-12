package com.salesmanager.shop.store.api.v1.product;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductList;
import com.salesmanager.shop.model.productAttribute.PersistableProduct;
import com.salesmanager.shop.store.controller.product.facade.ProductFacade;
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
public class AdminProductApi {
  private static final Logger LOGGER = LoggerFactory.getLogger(AdminProductApi.class);

  @Inject private ProductFacade productFacade;

  @ApiOperation(
      httpMethod = "POST",
      value = "Create product",
      notes = "",
      produces = "application/json",
      response = PersistableProduct.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/private/product", method = RequestMethod.POST)
  public @ResponseBody PersistableProduct create(
      @Valid @RequestBody PersistableProduct product,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response) {
    // TODO: To add product image field in DB and here!
    try {
      return productFacade.createProduct(product, merchantStore);
    } catch (Exception e) {
      LOGGER.error("Error while creating Product", e);
      try {
        response.sendError(500, "Error while creating Product " + e.getMessage());
      } catch (Exception ignore) {
      }
      return null;
    }
  }

  @ApiOperation(
      httpMethod = "PUT",
      value = "Update Product",
      notes = "",
      produces = "application/json",
      response = PersistableProduct.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/product"},
      method = RequestMethod.PUT)
  public @ResponseBody PersistableProduct update(
      @Valid @RequestBody PersistableProduct product,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response) {
    try {
      return productFacade.updateProduct(product, merchantStore);
    } catch (Exception e) {
      LOGGER.error("Error while updating Product", e);
      try {
        response.sendError(500, "Error while updating Product " + e.getMessage());
      } catch (Exception ignore) {
      }
      return null;
    }
  }

  @ApiOperation(
      httpMethod = "GET",
      value = "Get Product",
      notes = "",
      produces = "application/json",
      response = PersistableProduct.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/product/{id}"},
      method = RequestMethod.GET)
  public @ResponseBody PersistableProduct get(
      @PathVariable Long id,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    return productFacade.getProduct(id, merchantStore);
  }

  @ApiOperation(httpMethod = "DELETE", value = "Delete Product", notes = "", response = Void.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/product/{id}"},
      method = RequestMethod.DELETE)
  public void delete(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language)
      throws Exception {
    productFacade.deleteProduct(id, merchantStore);
  }

  @ApiOperation(
      httpMethod = "GET",
      value = "Get All products based on page size",
      notes = "",
      produces = "application/json",
      response = PersistableProduct.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/products/"},
      method = RequestMethod.GET)
  public @ResponseBody PersistableProductList getAllProducts(
      @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    return productFacade.getAllUniqueProduct(page, pageSize, merchantStore);
  }
}
