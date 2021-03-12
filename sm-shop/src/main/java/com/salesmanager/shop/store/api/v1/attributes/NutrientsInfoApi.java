package com.salesmanager.shop.store.api.v1.attributes;

import com.salesmanager.core.model.catalog.product.NutrientsInfo;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.attributes.NutrientsInfoFacade;
import com.salesmanager.shop.model.catalog.category.NutrientsInfoList;
import com.salesmanager.shop.model.productAttribute.PersistableNutrientsInfo;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import io.swagger.annotations.*;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api/v1")
@Api(
    value = "All Attributes Image API",
    tags = {"Product Attribute Image management resource (Product Attribute Image Management Api)"})
@SwaggerDefinition(
    tags = {
      @Tag(
          name = "Product attributes Image management resource",
          description = "Add attributes Image, edit attributes Image and delete attributes Image")
    })
public class NutrientsInfoApi {
  private static final Logger LOGGER = LoggerFactory.getLogger(NutrientsInfoApi.class);
  @Inject private NutrientsInfoFacade nutrientsInfoFacade;

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(
      value = {"/private/nutrientsInfo"},
      method = RequestMethod.POST)
  @ApiOperation(
      httpMethod = "POST",
      value = "Create NutrientsInfo",
      notes = "",
      produces = "application/json",
      response = PersistableNutrientsInfo.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody PersistableNutrientsInfo createNutrientsInfo(
      @RequestParam(value = "file[]", required = true) MultipartFile[] files,
      @RequestParam(value = "description", required = true) String description,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language) {
    NutrientsInfo nutrientsInfo = new NutrientsInfo();
    try {
      nutrientsInfo.setDescription(description);
      return nutrientsInfoFacade.createNutrientsInfo(files, nutrientsInfo);
    } catch (Exception e) {
      LOGGER.error("Error while creating nutrientsInfoImage", e);
      throw new ServiceRuntimeException("Error while creating image");
    }
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/nutrientsInfo/{NutrientsInfoId}"},
      method = RequestMethod.DELETE)
  @ApiOperation(
      httpMethod = "DELETE",
      value = "Delete NutrientsInfo ",
      notes = "",
      produces = "application/json")
  public void deleteNutrientsInfo(
      @PathVariable(value = "NutrientsInfoId", required = true) Long NutrientsInfoId,
      HttpServletRequest request,
      HttpServletResponse response) {
    try {
      nutrientsInfoFacade.deleteNutrientsInfo(NutrientsInfoId);
    } catch (Exception e) {
      LOGGER.error("Error while deleting NutrientsInfo", e);
      try {
        response.sendError(503, "Error while deleting NutrientsInfo " + e.getMessage());
      } catch (Exception ignore) {
      }
    }
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/nutrientsInfo/{id}"},
      method = RequestMethod.GET)
  @ApiOperation(
      httpMethod = "GET",
      value = "Get categoryNutrientsInfo by id",
      notes = "",
      produces = "application/json",
      response = PersistableNutrientsInfo.class)
  public @ResponseBody PersistableNutrientsInfo getNutrientsInfo(
      @PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
    PersistableNutrientsInfo nutrientsInfo = null;
    try {
      nutrientsInfo = nutrientsInfoFacade.getNutrientsInfo(id);
    } catch (Exception e) {
      LOGGER.error("Error while getting NutrientsInfo", e);
    }
    return nutrientsInfo;
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/nutrientsInfos"},
      method = RequestMethod.GET)
  @ApiOperation(
      httpMethod = "GET",
      value = "Get All ProductNutrientsInfo",
      notes = "",
      produces = "application/json",
      response = NutrientsInfoList.class)
  public @ResponseBody NutrientsInfoList getAllNutrientsInfo(
      @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
      HttpServletRequest request,
      HttpServletResponse response,
      @ApiIgnore MerchantStore merchantStore) {
    NutrientsInfoList nutrientsInfoList = new NutrientsInfoList();
    try {
      nutrientsInfoList = nutrientsInfoFacade.getAllNutrientsInfos(page, pageSize, merchantStore);
    } catch (Exception e) {
      LOGGER.error("Error while getting All NutrientsInfo", e);
    }
    return nutrientsInfoList;
  }
}
