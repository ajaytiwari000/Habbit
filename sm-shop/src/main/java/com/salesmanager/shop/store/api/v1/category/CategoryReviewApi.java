package com.salesmanager.shop.store.api.v1.category;

import com.salesmanager.core.model.catalog.category.CategoryReview;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.category.attributes.facade.CategoryReviewFacade;
import com.salesmanager.shop.model.catalog.category.CategoryReviewList;
import com.salesmanager.shop.model.productAttribute.PersistableCategoryReview;
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
class CategoryReviewApi {
  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryReviewApi.class);
  @Inject private CategoryReviewFacade categoryReviewFacade;

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(
      value = {"/private/category/review"},
      method = RequestMethod.POST)
  @ApiOperation(
      httpMethod = "POST",
      value = "Create CategoryReview",
      notes = "",
      produces = "application/json",
      response = PersistableCategoryReview.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody PersistableCategoryReview createCategoryReview(
      @RequestParam(value = "file[]", required = true) MultipartFile[] files,
      @RequestParam(value = "name", required = true) String name,
      @RequestParam(value = "achievement", required = false) String achievement,
      @RequestParam(value = "review", required = false) String review,
      @RequestParam(value = "categoryName", required = true) String categoryName,
      @RequestParam(value = "reviewRating", required = false) Double reviewRating,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletResponse response) {
    CategoryReview categoryReview = new CategoryReview();
    categoryReview.setName(name);
    categoryReview.setCategoryName(categoryName);
    categoryReview.setAchievement(achievement);
    categoryReview.setReview(review);
    categoryReview.setReviewRating(reviewRating);
    PersistableCategoryReview persistableCategoryReview = null;
    try {
      persistableCategoryReview = categoryReviewFacade.createCategoryReview(files, categoryReview);
    } catch (Exception e) {
      LOGGER.error("Error while creating CategoryReview", e);
      try {
        response.sendError(503, "Error while deleting CategoryReview " + e.getMessage());
      } catch (Exception ignore) {
      }
    }
    return persistableCategoryReview;
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/delete/category/review/{id}"},
      method = RequestMethod.DELETE)
  @ApiOperation(
      httpMethod = "DELETE",
      value = "Delete CategoryReview ",
      notes = "",
      produces = "application/json",
      response = void.class)
  public void deleteCategoryReview(
      @PathVariable(value = "id") Long id,
      HttpServletRequest request,
      HttpServletResponse response) {
    try {
      categoryReviewFacade.deleteCategoryReview(id);
    } catch (Exception e) {
      LOGGER.error("Error while deleting CategoryReview", e);
      try {
        response.sendError(503, "Error while deleting CategoryReview " + e.getMessage());
      } catch (Exception ignore) {
      }
    }
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/get/category/review/{id}"},
      method = RequestMethod.GET)
  @ApiOperation(
      httpMethod = "GET",
      value = "Get CategoryReview",
      notes = "",
      produces = "application/json",
      response = PersistableCategoryReview.class)
  public @ResponseBody PersistableCategoryReview getCategoryReview(
      @PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
    return categoryReviewFacade.getCategoryReview(id);
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/get/category/reviews"},
      method = RequestMethod.GET)
  @ApiOperation(
      httpMethod = "GET",
      value = "Get All CategoryReview",
      notes = "",
      produces = "application/json",
      response = CategoryReviewList.class)
  public @ResponseBody CategoryReviewList getAllCategoryReview(
      @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
      @RequestParam(value = "categoryName", required = false) String categoryName,
      HttpServletRequest request,
      HttpServletResponse response,
      @ApiIgnore MerchantStore merchantStore) {
    return categoryReviewFacade.getAllCategoryReview(page, pageSize, merchantStore, categoryName);
  }
}
