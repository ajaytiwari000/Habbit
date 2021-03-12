package com.salesmanager.shop.store.api.v1.customer;

import com.salesmanager.core.model.customer.CustomerCriteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.PersistableCustomer;
import com.salesmanager.shop.model.customer.ReadableCustomer;
import com.salesmanager.shop.populator.customer.ReadableCustomerList;
import com.salesmanager.shop.store.facade.customer.CustomerFacade;
import com.salesmanager.shop.store.facade.customer.CustomerJoinWaitListFacade;
import com.salesmanager.shop.utils.SuccessResponse;
import io.swagger.annotations.*;
import java.security.Principal;
import java.util.Optional;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = {"Customer management resource (Customer Management Api)"})
@SwaggerDefinition(
    tags = {@Tag(name = "Customer management resource", description = "Manage customer addresses")})
public class CustomerApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerApi.class);

  @Inject private CustomerFacade customerFacade;

  @Inject private CustomerJoinWaitListFacade customerJoinWaitListFacade;

  /** Create new customer for a given MerchantStore */
  @PostMapping("/auth/customer")
  @ApiOperation(
      httpMethod = "POST",
      value = "Creates a customer",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse create(
      @ApiIgnore MerchantStore merchantStore, @Valid @RequestBody PersistableCustomer customer) {
    return new SuccessResponse(customerFacade.create(customer, merchantStore));
  }

  /*  */
  /**
   * Update authenticated customer adresses
   *
   * @param
   * @param merchantStore
   * @param customer
   * @return
   */
  /*
  @PutMapping("/auth/customer/{id}")
  @ApiOperation(
      httpMethod = "PUT",
      value = "Updates a customer",
      produces = "application/json",
      response = PersistableCustomer.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public PersistableCustomer update(
      @PathVariable String userName,
      @ApiIgnore MerchantStore merchantStore,
      @Valid @RequestBody PersistableCustomer customer) {
      // TODO customer.setUserName
      // TODO more validation
      return customerFacade.update(customer, merchantStore);
  }*/

  // TODO - write an API which fetch details using help of Session Token
  @PutMapping("/auth/customer/editProfile")
  @ApiOperation(
      httpMethod = "PUT",
      value = "Updates a customer",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public SuccessResponse update(
      @ApiIgnore MerchantStore merchantStore, @Valid @RequestBody PersistableCustomer customer) {
    return new SuccessResponse(customerFacade.update(customer, merchantStore));
  }

  /**
   * @param id
   * @param merchantStore
   */
  // TODO - write an API which fetch details using help of Session Token
  @DeleteMapping("/auth/customer/{id}")
  @ApiOperation(httpMethod = "DELETE", value = "Deletes a customer")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse delete(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore) {
    customerFacade.deleteById(id);
    return new SuccessResponse("Customer deleted successfully.");
  }

  /**
   * @param id
   * @param merchantStore
   * @param language
   * @return
   */
  // TODO - write an API which fetch details using help of Session Token
  // TODO - check if we can use getAuthUser bel
  @Deprecated
  @GetMapping("/auth/customer/{id}")
  @ApiOperation(httpMethod = "GET", value = "Get customer by customer id")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse get(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
    return new SuccessResponse(customerFacade.getCustomerById(id, merchantStore));
  }

  /**
   * @param userName
   * @param merchantStore
   * @param language
   * @return
   */
  // TODO - write an API which fetch details using help of Session Token, remove this and above
  // method
  // TODO - check if we can use getAuthUser below
  @Deprecated
  @GetMapping("/auth/customer/userName/{userName}")
  @ApiOperation(httpMethod = "GET", value = "Get by customer username")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse getByUserName(
      @PathVariable String userName,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language)
      throws Exception {
    return new SuccessResponse(customerFacade.getCustomerByUserName(userName));
  }

  /**
   * Get all customers
   *
   * @param start
   * @param count
   * @param
   * @return
   * @throws Exception
   */
  @GetMapping("/private/customers")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  @Deprecated
  public ReadableCustomerList getFilteredCustomers(
      @RequestParam(value = "start", required = false) Integer start,
      @RequestParam(value = "count", required = false) Integer count,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language) {
    CustomerCriteria customerCriteria = createCustomerCriteria(start, count);
    return customerFacade.getListByStore(merchantStore, customerCriteria, language);
  }

  @Deprecated
  private CustomerCriteria createCustomerCriteria(Integer start, Integer count) {
    CustomerCriteria customerCriteria = new CustomerCriteria();
    Optional.ofNullable(start).ifPresent(customerCriteria::setStartIndex);
    Optional.ofNullable(count).ifPresent(customerCriteria::setMaxCount);
    return customerCriteria;
  }

  /**
   * Get logged in customer profile
   *
   * @param merchantStore
   * @param language
   * @param request
   * @return
   */
  @GetMapping("/private/customer/profile")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public ReadableCustomer getAuthUser(
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request) {
    Principal principal = request.getUserPrincipal();
    String userName = principal.getName();
    return customerFacade.getCustomerByNick(userName, merchantStore, language);
  }

  //  @Deprecated
  //  @PutMapping("/auth/customer/{id}")
  //  @ApiOperation(
  //      httpMethod = "PUT",
  //      value = "Updates a loged in customer profile",
  //      notes = "Requires authentication",
  //      produces = "application/json",
  //      response = PersistableCustomer.class)
  //  @ApiImplicitParams({
  //    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  //  })
  //  public PersistableCustomer update(
  //      @ApiIgnore MerchantStore merchantStore,
  //      @Valid @RequestBody PersistableCustomer customer,
  //      HttpServletRequest request) {
  //
  //    Principal principal = request.getUserPrincipal();
  //    String userName = principal.getName();
  //
  //    return customerFacade.update(userName, customer, merchantStore);
  //  }

  @PostMapping("/auth/customer/image/{id}")
  @ApiOperation(
      httpMethod = "POST",
      value = "upload a customer image",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse create(
      @PathVariable Long id,
      @RequestParam(value = "file[]", required = true) MultipartFile[] files,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language) {
    return new SuccessResponse(customerFacade.uploadImage(files, merchantStore, id));
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping(value = {"/auth/customer/image/{id}"})
  @ApiOperation(
      httpMethod = "DELETE",
      value = "Remove customer image",
      notes = "",
      produces = "application/json")
  public void deleteImage(
      @PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
    try {
      customerFacade.removeCustomerImage(id);
    } catch (Exception e) {
      LOGGER.error("Error while deleting Customer Image", e);
      try {
        response.sendError(503, "Error while deleting CustomerImage " + e.getMessage());
      } catch (Exception ignore) {
      }
    }
  }

  @GetMapping("/validate/referralCode")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get validate referral code",
      notes = "",
      produces = "application/json")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public SuccessResponse getValidateReferralCode(
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      @RequestParam String referralCode,
      HttpServletRequest request) {
    return new SuccessResponse(customerFacade.getValidateReferralCode(referralCode, request));
  }

  @PutMapping("/auth/customer/editEmail")
  @ApiOperation(
      httpMethod = "PUT",
      value = "Updates a customer email",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public SuccessResponse update(
      @ApiIgnore MerchantStore merchantStore, @RequestParam String email, @RequestParam Long id) {
    return new SuccessResponse(customerFacade.updateEmail(id, email, merchantStore));
  }

  @GetMapping("/newUser")
  @ApiOperation(
      httpMethod = "GET",
      value = "new user or not",
      notes = "",
      produces = "application/json")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public SuccessResponse newUser(
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      @RequestParam String phone,
      HttpServletRequest request) {
    return new SuccessResponse(customerFacade.newUser(phone));
  }

  @PostMapping("/joinWaitList")
  @ApiOperation(
      httpMethod = "POST",
      value = "upload customer to join wait list",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse joinWaitList(
      @RequestParam String phone,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language) {
    return new SuccessResponse(customerJoinWaitListFacade.joinWaitList(phone));
  }

  @GetMapping("/emailVerification/{phone}")
  @ApiOperation(
      httpMethod = "GET",
      value = "get customer email verification",
      notes = "",
      produces = "application/json")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public SuccessResponse emailVerification(
      @PathVariable String phone, @RequestParam String secret, HttpServletRequest request) {
    return new SuccessResponse(customerFacade.emailVerification(phone, secret));
  }
}
