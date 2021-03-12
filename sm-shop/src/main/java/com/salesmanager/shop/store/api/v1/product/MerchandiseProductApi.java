package com.salesmanager.shop.store.api.v1.product;

import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.product.MerchandiseCategoryMapper;
import com.salesmanager.shop.mapper.product.MerchandiseProductMapper;
import com.salesmanager.shop.model.catalog.product.ReadableMerchandiseProductDetails;
import com.salesmanager.shop.store.controller.product.facade.MerchandiseProductFacade;
import com.salesmanager.shop.store.controller.product.facade.ProductFacade;
import com.salesmanager.shop.utils.ImageFilePath;
import com.salesmanager.shop.utils.SuccessResponse;
import io.swagger.annotations.*;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * API to create, read, update and delete a Merchandise Product API to create Manufacturer
 *
 * @author Carl Samson
 */
@Controller
@RequestMapping("/api/v1")
@Api(tags = {"Merchandise Product management resource (Product Management Api)"})
@SwaggerDefinition(
    tags = {
      @Tag(
          name = "Merchandise Product management resource",
          description = "Add product, edit product and delete product")
    })
public class MerchandiseProductApi {

  @Inject private CategoryService categoryService;

  @Inject private ProductService productService;

  @Inject private MerchandiseProductFacade merchandiseProductFacade;
  @Inject private ProductFacade productFacade;
  @Inject private MerchandiseProductMapper merchandiseProductMapper;
  @Inject private MerchandiseCategoryMapper merchandiseCategoryMapper;

  @Inject
  @Qualifier("img")
  private ImageFilePath imageUtils;

  private static final Logger LOGGER = LoggerFactory.getLogger(MerchandiseProductApi.class);

  /**
   * Filtering product lists based on product attributes ?category=1 &manufacturer=2 &type=...
   * &lang=en|fr NOT REQUIRED, will use request language &start=0 NOT REQUIRED, can be used for
   * pagination &count=10 NOT REQUIRED, can be used to limit item count
   *
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/merchandiseCategory/{id}/products", method = RequestMethod.GET)
  @ResponseBody
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public SuccessResponse getCategoryProductLists // view more product
      (
      @PathVariable final Long id,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletResponse response) {
    try {
      return new SuccessResponse(
          merchandiseProductFacade.getProductList(id, merchantStore, language));
    } catch (Exception e) {
      LOGGER.error("Error while fetching products for category", e);
      try {
        response.sendError(503, "Error while fetching products for category " + e.getMessage());
      } catch (Exception ignore) {
      }
      return null;
    }
  }

  /**
   * API for getting a product
   *
   * @param id
   * @param response
   * @return ReadableProductDetails
   * @throws Exception
   *     <p>/api/v1/productDetail/123
   */
  @RequestMapping(value = "/merchandiseProductDetail", method = RequestMethod.GET)
  @ResponseBody
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public SuccessResponse getProductDetail(
      @RequestParam(value = "id", required = false, defaultValue = "-1") Long id,
      @RequestParam(value = "skuId", required = false) String skuId,
      @RequestParam(value = "productName", required = false) String productName,
      @RequestParam(value = "categoryId", required = false, defaultValue = "-1") Long categoryId,
      @RequestParam(value = "packId", required = false) Long packId,
      @ApiIgnore Language language,
      @ApiIgnore MerchantStore merchantStore,
      HttpServletResponse response)
      throws Exception {
    ReadableMerchandiseProductDetails product;
    if (id != -1 || skuId != null) {
      product =
          merchandiseProductFacade.getProductDetail(
              merchantStore, id, language, skuId, productName);
    } else {
      product =
          merchandiseProductFacade.getProductDetailsByCategoryFlavorAndPack(
              merchantStore, categoryId, packId, language, merchantStore, productName);
    }
    if (product == null && id != -1) {
      response.sendError(404, "Product not fount for id " + id);
      return null;
    }
    if (product == null && id == -1) {
      response.sendError(404, "Product not fount for categoryId " + categoryId);
      return null;
    }
    return new SuccessResponse(product);
  }
}
