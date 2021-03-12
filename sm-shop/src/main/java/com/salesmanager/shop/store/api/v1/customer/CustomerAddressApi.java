package com.salesmanager.shop.store.api.v1.customer;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.PersistableAddress;
import com.salesmanager.shop.store.facade.customer.address.CustomerAddressFacade;
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
@Api(tags = {"Customer management resource (Customer Management Api)"})
@SwaggerDefinition(
    tags = {@Tag(name = "Customer management resource", description = "Manage customer addresses")})
public class CustomerAddressApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAddressApi.class);

  @Inject private CustomerAddressFacade customerAddressFacade;

  /** Create new customer address */
  @PostMapping("/auth/customer/address")
  @ApiOperation(
      httpMethod = "POST",
      value = "Creates a customer address",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse create(
      @ApiIgnore MerchantStore merchantStore,
      @RequestParam(defaultValue = "false") boolean unSetDefaultAddressFlag,
      @Valid @RequestBody PersistableAddress persistableAddress) {
    return new SuccessResponse(
        customerAddressFacade.create(persistableAddress, merchantStore, unSetDefaultAddressFlag));
  }

  @PutMapping("/auth/customer/address")
  @ApiOperation(
      httpMethod = "PUT",
      value = "Updates a customer address",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse update(
      @ApiIgnore MerchantStore merchantStore,
      @RequestParam(defaultValue = "false") boolean unSetDefaultAddressFlag,
      @Valid @RequestBody PersistableAddress persistableAddress) {
    return new SuccessResponse(
        customerAddressFacade.update(persistableAddress, merchantStore, unSetDefaultAddressFlag));
  }

  @DeleteMapping("/auth/customer/address/{id}")
  @ApiOperation(httpMethod = "DELETE", value = "Deletes a customer address")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse delete(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore) {
    customerAddressFacade.deleteById(id);
    return new SuccessResponse("Customer deleted successfully.");
  }

  @GetMapping("/auth/customer/address/{id}")
  @ApiOperation(httpMethod = "GET", value = "Get customer address by address id")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse get(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
    return new SuccessResponse(
        customerAddressFacade.getCustomerAddressById(id, merchantStore, language));
  }

  @GetMapping("/auth/customer/{id}/address")
  @ApiOperation(httpMethod = "GET", value = "Get all customer address by customer id")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse getAll(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
    return new SuccessResponse(
        customerAddressFacade.getCustomerAllAddressByCustomerId(id, merchantStore, language));
  }

  @GetMapping("/auth/customer/isServiceablePinCode")
  @ApiOperation(httpMethod = "GET", value = "Get serviceable pincode")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse checkIfServiceablePinCode(
      @RequestParam String pinCode,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language) {
    return new SuccessResponse(
        customerAddressFacade.isServiceablePinCode(pinCode, merchantStore, language));
  }
}
