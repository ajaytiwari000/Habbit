package com.salesmanager.shop.store.api.v1.category;

import com.salesmanager.core.model.catalog.category.CategoryBanner;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.product.CategoryBannerMapper;
import com.salesmanager.shop.model.catalog.category.CategoryBannerList;
import com.salesmanager.shop.store.controller.category.facade.CategoryBannerFacade;
import io.swagger.annotations.*;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
public class CategoryBannerApi {
  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryBannerApi.class);
  @Inject private CategoryBannerMapper categoryBannerMapper;
  @Inject private CategoryBannerFacade categoryBannerFacade;

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(
      value = {"/private/category/{id}/images"},
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
      method = RequestMethod.POST)
  @ApiOperation(
      httpMethod = "POST",
      value = "upload category Banner images",
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
      @RequestParam(value = "bannerLinkedSkuId", required = true) String bannerLinkedSkuId,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language) {
    categoryBannerFacade.addCategoryBanners(id, files, bannerLinkedSkuId);
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/category/images/{categoryBannerId}"},
      method = RequestMethod.DELETE)
  @ApiOperation(
      httpMethod = "DELETE",
      value = "Remove category Banner image",
      notes = "",
      produces = "application/json",
      response = void.class)
  public void deleteImage(
      @PathVariable(value = "categoryBannerId", required = true) Long categoryBannerId,
      @RequestParam(value = "categoryId", required = true) Long categoryId,
      HttpServletRequest request,
      HttpServletResponse response) {
    try {
      CategoryBanner categoryBanner = categoryBannerFacade.getCategoryBannerById(categoryBannerId);
      if (categoryBanner != null) {
        categoryBannerFacade.removeCategoryBanner(categoryBanner, categoryId);
      } else {
        response.sendError(404, "No Category Banner Image found for ID : " + categoryBannerId);
      }
    } catch (Exception e) {
      LOGGER.error("Error while deleting Category Banner Image", e);
      try {
        response.sendError(503, "Error while deleting Category Banner Image " + e.getMessage());
      } catch (Exception ignore) {
      }
    }
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/category/{id}/images"},
      method = RequestMethod.GET)
  @ApiOperation(
      httpMethod = "GET",
      value = "Get Category Banner images by CategoryId",
      notes = "",
      produces = "application/json",
      response = CategoryBannerList.class)
  public @ResponseBody CategoryBannerList getImages(
      @PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
    CategoryBannerList categoryBanners = new CategoryBannerList();
    categoryBanners.setCategoryBanners(
        Optional.ofNullable(categoryBannerFacade.getCategoryBannersByCategoryId(id))
            .map(Collection::stream)
            .orElseGet(Stream::empty)
            .map(categoryBannerMapper::toPersistableCategoryDetails)
            .collect(Collectors.toList()));
    return categoryBanners;
  }
}
