package com.salesmanager.shop.store.api.v1.category;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.salesmanager.core.business.services.aws.sqs.AwsSqsService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.product.MerchandiseCategoryMapper;
import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.catalog.category.ReadableMerchandiseCategory;
import com.salesmanager.shop.store.controller.category.facade.CategoryFacade;
import com.salesmanager.shop.store.controller.user.facade.UserFacade;
import com.salesmanager.shop.utils.SuccessResponse;
import io.swagger.annotations.*;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = {"Merchandise Category management resource (Category Management Api)"})
@SwaggerDefinition(
    tags = {
      @Tag(
          name = "Merchandise Category management resource",
          description = "Manage category and attached products")
    })
public class MerchandiseCategoryApi {

  private static final int DEFAULT_CATEGORY_DEPTH = 0;

  @Inject private CategoryFacade categoryFacade;

  @Inject private UserFacade userFacade;
  @Inject private MerchandiseCategoryMapper merchandiseCategoryMapper;
  @Inject private AwsSqsService awsSqsService;

  @GetMapping(
      value = "/merchandiseCategory/{id}",
      produces = {APPLICATION_JSON_VALUE})
  @ApiOperation(
      httpMethod = "GET",
      value = "Get category details for a given Category id",
      notes = "List current Category details")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Category found", response = ReadableCategory.class)
      })
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public SuccessResponse getCategory(
      @PathVariable(name = "id") Long categoryId,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language) {
    ReadableCategory category = categoryFacade.getByCategoryId(merchantStore, categoryId, language);
    ReadableMerchandiseCategory readableMerchandiseCategory =
        merchandiseCategoryMapper.toReadableMerchandiseCategory(category);
    return new SuccessResponse(readableMerchandiseCategory);
  }

  @GetMapping(
      value = "/merchandiseCategory/sqs",
      produces = {APPLICATION_JSON_VALUE})
  @ApiOperation(
      httpMethod = "GET",
      value = "Get category details for a given Category id",
      notes = "List current Category details")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Category found", response = ReadableCategory.class)
      })
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public SuccessResponse getsqs(
      @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) throws Exception {
    awsSqsService.postMessageToSQS(null);
    //        awsSqsService.getMessageFromSQS(10, 10);
    //    return new SuccessResponse(awsSqsService.createQueue());
    return null;
  }
}
