package com.salesmanager.shop.store.api.v1.category;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableCategoryList;
import com.salesmanager.shop.model.productAttribute.PersistableCategory;
import com.salesmanager.shop.store.controller.category.facade.CategoryFacade;
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
public class AdminCategoryApi {
  private static final Logger LOGGER = LoggerFactory.getLogger(AdminCategoryApi.class);
  @Inject private CategoryFacade categoryFacade;

  @ApiOperation(
      httpMethod = "POST",
      value = "Create category",
      notes = "",
      produces = "application/json",
      response = PersistableCategory.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/private/category", method = RequestMethod.POST)
  public @ResponseBody PersistableCategory create(
      @Valid @RequestBody PersistableCategory category,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response) {
    try {
      return categoryFacade.createCategory(category, merchantStore);
    } catch (Exception e) {
      LOGGER.error("Error while creating Category", e);
      try {
        response.sendError(500, "Error while creating Category " + e.getMessage());
      } catch (Exception ignore) {
      }
      return null;
    }
  }

  @ApiOperation(
      httpMethod = "PUT",
      value = "Update Category",
      notes = "",
      produces = "application/json",
      response = PersistableCategory.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/category"},
      method = RequestMethod.PUT)
  public @ResponseBody PersistableCategory update(
      @Valid @RequestBody PersistableCategory category,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response) {
    try {
      return categoryFacade.updateCategory(category, merchantStore);
    } catch (Exception e) {
      LOGGER.error("Error while updating Category", e);
      try {
        response.sendError(500, "Error while updating Category " + e.getMessage());
      } catch (Exception ignore) {
      }
      return null;
    }
  }

  @ApiOperation(
      httpMethod = "GET",
      value = "Get Category",
      notes = "",
      produces = "application/json",
      response = PersistableCategory.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/category/{id}"},
      method = RequestMethod.GET)
  public @ResponseBody PersistableCategory get(
      @PathVariable Long id,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    return categoryFacade.getCategory(id, merchantStore, language);
  }

  @ApiOperation(httpMethod = "DELETE", value = "Delete Category", notes = "", response = Void.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/category/delete/{id}"},
      method = RequestMethod.DELETE)
  public void delete(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language)
      throws Exception {
    categoryFacade.deleteCategory(id);
  }

  @ApiOperation(
      httpMethod = "GET",
      value = "Get All categories based on page size",
      notes = "",
      produces = "application/json",
      response = PersistableCategory.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/categorys/"},
      method = RequestMethod.GET)
  public @ResponseBody PersistableCategoryList getAllCategory(
      @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    return categoryFacade.getAllUniqueCategory(page, pageSize, merchantStore);
  }
}
