package com.salesmanager.shop.store.api.v1.product;

import com.salesmanager.core.model.catalog.product.image.ProductStickerImage;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.products.attributes.facade.ProductStickerImageFacade;
import com.salesmanager.shop.model.catalog.product.attribute.ProductStickerImageList;
import com.salesmanager.shop.model.productAttribute.PersistableProductStickerImage;
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
class ProductStickerImageApi {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductStickerImageApi.class);
  @Inject private ProductStickerImageFacade productStickerImageFacade;

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(
      value = {"/private/product/sticker"},
      method = RequestMethod.POST)
  @ApiOperation(
      httpMethod = "POST",
      value = "Create Product StickerImage",
      notes = "",
      produces = "application/json",
      response = PersistableProductStickerImage.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody PersistableProductStickerImage createProductStickerImage(
      @RequestParam(value = "file[]", required = true) MultipartFile[] files,
      @RequestParam(value = "skuId", required = true) String skuId,
      @RequestParam(value = "badgeText", required = true) String badgeText,
      @RequestParam(value = "badgeColorCode", required = true) String badgeColorCode,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language) {
    ProductStickerImage productStickerImage = new ProductStickerImage();
    productStickerImage.setSkuId(skuId);
    productStickerImage.setBadgeText(badgeText);
    productStickerImage.setBadgeColorCode(badgeColorCode);
    return productStickerImageFacade.createProductSticker(files, productStickerImage);
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/delete/product/sticker/{stickerId}"},
      method = RequestMethod.DELETE)
  @ApiOperation(
      httpMethod = "DELETE",
      value = "Delete ProductSticker ",
      notes = "",
      produces = "application/json")
  public void deleteProductNutrientsInfo(
      @PathVariable(value = "stickerId", required = true) Long stickerId,
      HttpServletRequest request,
      HttpServletResponse response) {
    try {
      productStickerImageFacade.deleteProductSticker(stickerId);
    } catch (Exception e) {
      LOGGER.error("Error while deleting ProductSticker", e);
      try {
        response.sendError(503, "Error while deleting ProductSticker " + e.getMessage());
      } catch (Exception ignore) {
      }
    }
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/get/product/sticker/{skuId}"},
      method = RequestMethod.GET)
  @ApiOperation(
      httpMethod = "GET",
      value = "Get ProductStickerImage by product skuId",
      notes = "",
      produces = "application/json",
      response = PersistableProductStickerImage.class)
  public @ResponseBody PersistableProductStickerImage getProductStickerImage(
      @PathVariable String skuId, HttpServletRequest request, HttpServletResponse response) {
    return productStickerImageFacade.getProductSticker(skuId);
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/get/product/stickers"},
      method = RequestMethod.GET)
  @ApiOperation(
      httpMethod = "GET",
      value = "Get All ProductSticker",
      notes = "",
      produces = "application/json",
      response = ProductStickerImageList.class)
  public @ResponseBody ProductStickerImageList getAllProductStickerImage(
      @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
      HttpServletRequest request,
      HttpServletResponse response,
      @ApiIgnore MerchantStore merchantStore) {
    return productStickerImageFacade.getAllProductStickers(page, pageSize, merchantStore);
  }
}
