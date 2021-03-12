package com.salesmanager.shop.store.api.v1.attributes;

import com.salesmanager.core.model.catalog.product.PackIcon;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.attributes.PackImageFacade;
import io.swagger.annotations.*;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class PackImageApi {
  private static final Logger LOGGER = LoggerFactory.getLogger(PackImageApi.class);
  @Inject private PackImageFacade packImageFacade;

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(
      value = {"/private/pack/{id}/image"},
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
      method = RequestMethod.POST)
  @ApiOperation(
      httpMethod = "POST",
      value = "upload pack image",
      notes = "",
      produces = "application/json",
      response = void.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public void uploadImages(
      @PathVariable Long id,
      @RequestParam(value = "file[]", required = true) MultipartFile[] files,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language) {
    packImageFacade.addPackIcon(id, files);
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/pack/image/{packIconId}"},
      method = RequestMethod.DELETE)
  @ApiOperation(
      httpMethod = "DELETE",
      value = "Remove pack image",
      notes = "",
      produces = "application/json")
  public void deleteImage(
      @PathVariable Long packIconId,
      @RequestParam(value = "packId", required = true) Long packId,
      HttpServletRequest request,
      HttpServletResponse response) {
    try {
      packImageFacade.removePackIcon(packIconId, packId);
    } catch (Exception e) {
      LOGGER.error("Error while deleting productsticker Image", e);
      try {
        response.sendError(503, "Error while deleting PackImage " + e.getMessage());
      } catch (Exception ignore) {
      }
    }
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/pack/{id}/image"},
      method = RequestMethod.GET)
  @ApiOperation(
      httpMethod = "GET",
      value = "Get pack image by packId",
      notes = "",
      produces = "application/json",
      response = PackIcon.class)
  public @ResponseBody PackIcon getImageByPackId(
      @PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
    return packImageFacade.getPackImageByPackId(id);
  }
}
