package com.salesmanager.shop.store.api.v1.product;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.checkout.CouponCodeFacade;
import com.salesmanager.shop.admin.controller.customer.membership.RewardConsumptionCriteriaFacade;
import com.salesmanager.shop.model.customer.PersistableCouponCode;
import com.salesmanager.shop.utils.SuccessResponse;
import io.swagger.annotations.*;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = {" Coupon code management resource ( Coupon code  Management Api)"})
@SwaggerDefinition(
    tags = {@Tag(name = " Coupon code  management resource", description = "Manage  CouponCode")})
public class AdminCouponCodeApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(AdminCouponCodeApi.class);

  @Inject private CouponCodeFacade couponCodeFacade;
  @Inject private RewardConsumptionCriteriaFacade rewardConsumptionCriteriaFacade;

  /** Create new CouponCode */
  @PostMapping("/private/couponCode")
  @ApiOperation(
      httpMethod = "POST",
      value = "Creates a  CouponCode",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse create(
      @ApiIgnore MerchantStore merchantStore,
      @RequestParam(defaultValue = "-1") Long unSetDefaultCouponId,
      @Valid @RequestBody PersistableCouponCode persistableCouponCode) {
    return new SuccessResponse(
        couponCodeFacade.create(persistableCouponCode, merchantStore, unSetDefaultCouponId));
  }

  @PutMapping("/private/couponCode")
  @ApiOperation(
      httpMethod = "PUT",
      value = "Updates a  CouponCode",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse update(
      @ApiIgnore MerchantStore merchantStore,
      @RequestParam(defaultValue = "-1") Long unSetDefaultCouponId,
      @Valid @RequestBody PersistableCouponCode persistableCouponCode) {
    return new SuccessResponse(
        couponCodeFacade.update(persistableCouponCode, merchantStore, unSetDefaultCouponId));
  }

  @DeleteMapping("/private/couponCode/{id}")
  @ApiOperation(
      httpMethod = "DELETE",
      value = "Deletes a CouponCode",
      notes = "Requires administration access")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse delete(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore) {
    couponCodeFacade.deleteById(id);
    return new SuccessResponse(" deleted successfully.");
  }

  @GetMapping("/private/defaultCouponCode")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get a  getDefaultCouponCode",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse get(
      @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
    return new SuccessResponse(couponCodeFacade.getDefaultCouponCode(merchantStore, language));
  }

  @GetMapping("/private/couponCodeById/{id}")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get a  CouponCode by id",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse get(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
    return new SuccessResponse(couponCodeFacade.getCouponCodeById(id, merchantStore, language));
  }

  @GetMapping("/private/couponCodeByCode/{code}")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get a  CouponCode by code",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse get(
      @PathVariable String code,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language) {
    return new SuccessResponse(couponCodeFacade.getCouponCodeByCode(code));
  }

  @GetMapping("/private/couponCodes")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get all CouponCode",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse getAll(
      @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
    return new SuccessResponse(couponCodeFacade.getAllCouponCode(merchantStore, language));
  }

  @GetMapping("/private/discountTypes")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get all allDiscountType",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse allDiscountType(
      @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
    return new SuccessResponse(couponCodeFacade.allDiscountType(merchantStore, language));
  }

  @GetMapping("/private/couponCodeTypes")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get all allCouponCodeType",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse allCouponCodeType(
      @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
    return new SuccessResponse(couponCodeFacade.allCouponCodeType(merchantStore, language));
  }
}
