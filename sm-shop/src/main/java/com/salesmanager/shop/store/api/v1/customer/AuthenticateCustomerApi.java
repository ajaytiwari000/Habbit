package com.salesmanager.shop.store.api.v1.customer;

import com.salesmanager.core.business.HabbitCoreConstant;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.authentication.AuthenticationTokenService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.unicom.UnicommerceService;
import com.salesmanager.shop.model.customer.PersistableCustomer;
import com.salesmanager.shop.model.security.AuthenticationRequest;
import com.salesmanager.shop.model.security.AuthenticationResponse;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.controller.authentication.authenticatecustomer.facade.AuthenticateCustomerFacade;
import com.salesmanager.shop.store.controller.store.facade.StoreFacade;
import com.salesmanager.shop.store.facade.customer.CustomerFacade;
import com.salesmanager.shop.store.security.JWTTokenUtil;
import com.salesmanager.shop.store.security.PasswordRequest;
import com.salesmanager.shop.store.security.user.JWTUser;
import com.salesmanager.shop.utils.LanguageUtils;
import com.salesmanager.shop.utils.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import java.time.LocalTime;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.lang.Validate;
import org.apache.http.auth.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Customer authentication resource (Customer Authentication Api)"})
@SwaggerDefinition(
    tags = {
      @Tag(
          name = "Customer authentication resource",
          description = "Authenticates customer, register customer and reset customer password")
    })
public class AuthenticateCustomerApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateCustomerApi.class);

  @Value("${authToken.header}")
  private String tokenHeader;

  @Inject private AuthenticationManager jwtCustomerAuthenticationManager;

  @Inject private JWTTokenUtil jwtTokenUtil;

  @Inject private UserDetailsService jwtCustomerDetailsService;

  @Inject private CustomerFacade customerFacade;

  @Inject private StoreFacade storeFacade;

  @Inject private LanguageUtils languageUtils;

  @Autowired private AuthenticateCustomerFacade authenticateCustomerFacade;

  @Inject private AuthenticationTokenService authenticationTokenService;

  @Inject private UnicommerceService unicommerceService;

  /** Create new customer for a given MerchantStore, then authenticate that customer */
  // TODO : need to clean up
  @RequestMapping(
      value = {"/customer/register"},
      method = RequestMethod.POST,
      produces = {"application/json"})
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(
      httpMethod = "POST",
      value = "Registers a customer to the application",
      notes = "Used as self-served operation",
      response = AuthenticationResponse.class)
  @ResponseBody
  public ResponseEntity<?> register(
      @Valid @RequestBody PersistableCustomer customer,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    // try {

    MerchantStore merchantStore = storeFacade.getByCode(request);
    Language language = languageUtils.getRESTLanguage(request, merchantStore);

    // Transition
    // customer.setUserName(customer.getEmailAddress());

    Validate.notNull(customer.getUserName(), "Username cannot be null");

    customerFacade.registerCustomer(customer, merchantStore, language);
    return ResponseEntity.ok(new AuthenticationResponse(customer.getId(), null));
  }

  /**
   * Authenticate a customer using username & password
   *
   * @param authenticationRequest
   * @param
   * @return
   * @throws AuthenticationException
   */
  @RequestMapping(
      value = "/customer/login",
      method = {RequestMethod.POST},
      produces = {"application/json"})
  //  @ApiOperation(
  //      httpMethod = "POST",
  //      value = "Authenticates a customer to the application",
  //      notes =
  //          "Customer can authenticate after registration, request is
  // {\"username\":\"admin\",\"password\":\"password\"}",
  //      response = SuccessResponse.class)
  public @ResponseBody SuccessResponse authenticate(
      @RequestBody AuthenticationRequest authenticationRequest,
      HttpServletRequest request,
      @RequestParam(name = "requestSource") String requestSource,
      @RequestParam(name = "deviceId") String deviceId)
      throws AuthenticationException, ServiceException {

    // String requestSource = request.getHeader("requestSource");
    AuthenticationResponse authenticationResponse =
        authenticateCustomerFacade.authenticate(
            authenticationRequest, requestSource, deviceId); // request.getHeader("deviceId")
    return new SuccessResponse(ResponseEntity.ok(authenticationResponse));
  }

  @RequestMapping(
      value = "/customer/sendOtp",
      method = {RequestMethod.POST},
      produces = {"application/json"})
  @ApiOperation(
      httpMethod = "POST",
      value = "send otp to customer",
      response = ResponseEntity.class)
  public @ResponseBody SuccessResponse sendOtp(
      @RequestParam(name = "reSendFlag", defaultValue = "false") boolean reSendFlag,
      @RequestParam(name = "phone") String phone,
      HttpServletRequest request,
      @RequestParam(name = "deviceId") String deviceId)
      throws AuthenticationException, ServiceException {
    System.out.println("service start " + LocalTime.now());
    authenticateCustomerFacade.sendOtp(
        phone, reSendFlag, deviceId); // request.getHeader("deviceId")
    System.out.println("service end " + LocalTime.now());
    return new SuccessResponse(ResponseEntity.ok("OTP successfully send."));
  }

  @RequestMapping(
      value = "/customer/getOtp",
      method = RequestMethod.GET,
      produces = {"application/json"})
  @ApiOperation(httpMethod = "GET", value = "get otp to customer", response = ResponseEntity.class)
  public @ResponseBody SuccessResponse getOtp(
      @RequestParam String phone,
      HttpServletRequest request,
      @RequestParam(name = "deviceId") String deviceId)
      throws ServiceException {
    // String deviceId = request.getHeader("deviceId");
    return new SuccessResponse(
        authenticationTokenService
            .getByPhoneDevice(phone + HabbitCoreConstant.DELIMITER + deviceId)
            .getCode());
  }

  @RequestMapping(
      value = "/auth/customer/refresh",
      method = RequestMethod.GET,
      produces = {"application/json"})
  public ResponseEntity<?> refreshToken(HttpServletRequest request) {
    String token = request.getHeader(tokenHeader);
    String username = jwtTokenUtil.getUsernameFromToken(token);
    JWTUser user = (JWTUser) jwtCustomerDetailsService.loadUserByUsername(username);

    if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
      String refreshedToken = jwtTokenUtil.refreshToken(token);
      return ResponseEntity.ok(new AuthenticationResponse(user.getId(), refreshedToken));
    } else {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @RequestMapping(
      value = "/customer/password/reset",
      method = RequestMethod.PUT,
      produces = {"application/json"})
  @ApiOperation(
      httpMethod = "POST",
      value = "Change customer password",
      notes = "Change password request object is {\"username\":\"test@email.com\"}",
      response = ResponseEntity.class)
  public ResponseEntity<?> resetPassword(
      @RequestBody @Valid AuthenticationRequest authenticationRequest, HttpServletRequest request) {

    try {

      MerchantStore merchantStore = storeFacade.getByCode(request);
      Language language = languageUtils.getRESTLanguage(request, merchantStore);

      Customer customer =
          customerFacade.getCustomerByUserName(authenticationRequest.getUsername(), merchantStore);

      if (customer == null) {
        return ResponseEntity.notFound().build();
      }

      customerFacade.resetPassword(customer, merchantStore, language);
      return ResponseEntity.ok(Void.class);

    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Exception when reseting password " + e.getMessage());
    }
  }

  @RequestMapping(
      value = "/customer/password",
      method = RequestMethod.POST,
      produces = {"application/json"})
  @ApiOperation(
      httpMethod = "PUT",
      value = "Sends a request to reset password",
      notes = "Password reset request is {\"username\":\"test@email.com\"}",
      response = ResponseEntity.class)
  public ResponseEntity<?> changePassword(
      @RequestBody @Valid PasswordRequest passwordRequest, HttpServletRequest request) {

    try {

      MerchantStore merchantStore = storeFacade.getByCode(request);

      Customer customer =
          customerFacade.getCustomerByUserName(passwordRequest.getUsername(), merchantStore);

      if (customer == null) {
        return ResponseEntity.notFound().build();
      }

      // need to validate if password matches
      if (!customerFacade.passwordMatch(passwordRequest.getCurrent(), customer)) {
        throw new ResourceNotFoundException("Username or password does not match");
      }

      if (!passwordRequest.getPassword().equals(passwordRequest.getRepeatPassword())) {
        throw new ResourceNotFoundException("Both passwords do not match");
      }

      customerFacade.changePassword(customer, passwordRequest.getPassword());
      return ResponseEntity.ok(Void.class);

    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Exception when reseting password " + e.getMessage());
    }
  }
}
