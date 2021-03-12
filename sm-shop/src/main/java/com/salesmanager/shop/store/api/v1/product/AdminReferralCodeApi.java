package com.salesmanager.shop.store.api.v1.product;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.customer.membership.ReferralCodeFacade;
import com.salesmanager.shop.model.customer.PersistableReferralCode;
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
@Api(tags = {" Referral code management resource ( Referral code  Management Api)"})
@SwaggerDefinition(
    tags = {
      @Tag(name = " Referral code  management resource", description = "Manage  ReferralCode")
    })
public class AdminReferralCodeApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(AdminReferralCodeApi.class);

  @Inject private ReferralCodeFacade referralCodeFacade;

  /** Create new ReferralCode */
  @PostMapping("/private/referralCode")
  @ApiOperation(
      httpMethod = "POST",
      value = "Creates a  ReferralCode",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse create(
      @ApiIgnore MerchantStore merchantStore,
      @Valid @RequestBody PersistableReferralCode persistableReferralCode) {
    return new SuccessResponse(referralCodeFacade.create(persistableReferralCode, merchantStore));
  }

  @PutMapping("/private/referralCode")
  @ApiOperation(
      httpMethod = "PUT",
      value = "Updates a  ReferralCode",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse update(
      @ApiIgnore MerchantStore merchantStore,
      @Valid @RequestBody PersistableReferralCode persistableReferralCode) {
    return new SuccessResponse(referralCodeFacade.update(persistableReferralCode, merchantStore));
  }

  @DeleteMapping("/private/referralCode/{id}")
  @ApiOperation(
      httpMethod = "DELETE",
      value = "Deletes a ReferralCode",
      notes = "Requires administration access")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse delete(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore) {
    referralCodeFacade.deleteById(id);
    return new SuccessResponse(" deleted successfully.");
  }

  @GetMapping("/private/referralCodeById/{id}")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get a  ReferralCode by id",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse get(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
    return new SuccessResponse(referralCodeFacade.getReferralCodeById(id, merchantStore, language));
  }

  @GetMapping("/private/referralCodeByCode/{code}")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get a  ReferralCode by code",
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
    return new SuccessResponse(referralCodeFacade.getReferralCodeByCode(code));
  }

  @GetMapping("/private/referralCodes")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get all ReferralCode",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse getAll(
      @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
    return new SuccessResponse(referralCodeFacade.getAllReferralCode(merchantStore, language));
  }

  @GetMapping("/private/ownerTypes")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get all allOwnerType",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse allOwnerType(
      @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
    return new SuccessResponse(referralCodeFacade.allOwnerType(merchantStore, language));
  }
}
