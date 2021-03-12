package com.salesmanager.shop.store.api.v1.product;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.checkout.PinCodeFacade;
import com.salesmanager.shop.model.customer.PersistablePinCode;
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
@Api(tags = {" management resource ( Management Api)"})
@SwaggerDefinition(tags = {@Tag(name = " management resource", description = "Manage  pincode")})
public class AdminPinCodeApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(AdminPinCodeApi.class);

  @Inject private PinCodeFacade pinCodeFacade;

  /** Create new PinCode */
  @PostMapping("/private/pinCode")
  @ApiOperation(
      httpMethod = "POST",
      value = "Creates a  PinCode",
      notes = "Requires administration access",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse create(
      @ApiIgnore MerchantStore merchantStore,
      @Valid @RequestBody PersistablePinCode persistablePinCode) {
    return new SuccessResponse(pinCodeFacade.create(persistablePinCode, merchantStore));
  }

  @PutMapping("/private/pinCode")
  @ApiOperation(
      httpMethod = "PUT",
      value = "Updates a  PinCode",
      notes = "Requires administration access",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse update(
      @ApiIgnore MerchantStore merchantStore,
      @Valid @RequestBody PersistablePinCode persistablePinCode) {
    return new SuccessResponse(pinCodeFacade.update(persistablePinCode, merchantStore));
  }

  @DeleteMapping("/private/pinCode/{id}")
  @ApiOperation(
      httpMethod = "DELETE",
      value = "Deletes a PinCode",
      notes = "Requires administration access")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse delete(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore) {
    pinCodeFacade.deleteById(id);
    return new SuccessResponse(" deleted successfully.");
  }

  @GetMapping("/private/pinCode/{id}")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get a  PinCode",
      notes = "Requires administration access",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse get(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
    return new SuccessResponse(pinCodeFacade.getPinCodeById(id, merchantStore, language));
  }

  @GetMapping("/private/pinCodes")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get all PinCode",
      notes = "Requires administration access",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse getAll(
      @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
    return new SuccessResponse(pinCodeFacade.getAllPinCode(merchantStore, language));
  }
}
