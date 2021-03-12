package com.salesmanager.shop.store.api.v1.shoppingCart;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.shoppingcart.PersistableCart;
import com.salesmanager.shop.store.facade.cart.CartFacade;
import com.salesmanager.shop.store.facade.cart.enums.ValidateCartEvent;
import com.salesmanager.shop.utils.SuccessResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api/v1")
public class CartApi {

  @Inject private CartFacade cartFacade;

  @Inject private CustomerService customerService;

  private static final Logger LOGGER = LoggerFactory.getLogger(CartApi.class);

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/customers/cart", method = RequestMethod.PUT)
  @ApiOperation(
      httpMethod = "PUT",
      value = "Add product to a specific customer cart",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse updateCart(
      @RequestBody PersistableCart cart, HttpServletRequest request, HttpServletResponse response)
      throws ValidationException {
    return new SuccessResponse(cartFacade.updateCart(cart, request));
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/customers/cart", method = RequestMethod.GET)
  @ApiOperation(
      httpMethod = "GET",
      value = "Get a cart by phone",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse getCustomerCart(HttpServletRequest request) {

    return new SuccessResponse(cartFacade.getCustomerCart(request));
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/customers/cart/validateCoupon", method = RequestMethod.PUT)
  @ApiOperation(
      httpMethod = "PUT",
      value = "PUT  a ValidateCoupon",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse updateCartPostCouponValidation(
      @RequestBody PersistableCart persistableCart, HttpServletRequest request)
      throws ValidationException {
    return new SuccessResponse(cartFacade.updateCartPostCouponValidation(persistableCart, request));
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/customers/cart/getEarnPoint", method = RequestMethod.GET)
  @ApiOperation(
      httpMethod = "GET",
      value = "Get a getEarnPoint",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse getEarnPoint(
      @RequestParam TierType tierType, @RequestParam Long amount) {
    return new SuccessResponse(cartFacade.getEarnPoint(tierType, amount));
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/customers/cart/validate", method = RequestMethod.PUT)
  @ApiOperation(
      httpMethod = "PUT",
      value = "PUT cart after validating",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse validateCart(
      @RequestParam ValidateCartEvent validateCartEvent,
      @RequestBody PersistableCart persistableCart,
      HttpServletRequest request,
      @ApiIgnore MerchantStore merchantStore)
      throws Exception {
    // return new SuccessResponse(cartFacade.validationCart(persistableCart, request,
    // merchantStore));
    return new SuccessResponse(
        cartFacade.validateCart(validateCartEvent, persistableCart, request, merchantStore));
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/customers/cart/free", method = RequestMethod.PUT)
  @ApiOperation(
      httpMethod = "PUT",
      value = "PUT cart free",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse freeCart(
      HttpServletRequest request, @ApiIgnore MerchantStore merchantStore) throws Exception {
    return new SuccessResponse(cartFacade.freeCart(request, merchantStore));
  }
}
