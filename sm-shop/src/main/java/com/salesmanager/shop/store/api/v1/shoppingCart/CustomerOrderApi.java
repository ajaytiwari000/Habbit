package com.salesmanager.shop.store.api.v1.shoppingCart;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.shop.admin.controller.customer.membership.CustomerOrderFacade;
import com.salesmanager.shop.model.customer.PersistableCheckoutCart;
import com.salesmanager.shop.model.customer.PersistableCustomerOrder;
import com.salesmanager.shop.utils.SessionUtil;
import com.salesmanager.shop.utils.SuccessResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Date;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api/v1")
public class CustomerOrderApi {

  @Inject private CustomerOrderFacade customerOrderFacade;
  @Inject private SessionUtil sessionUtil;

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerOrderApi.class);

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/customers/cart/checkout", method = RequestMethod.POST)
  @ApiOperation(
      httpMethod = "POST",
      value = "POST checkoutCart",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse checkoutCart(
      @RequestBody PersistableCheckoutCart persistableCheckoutCart,
      HttpServletRequest request,
      @ApiIgnore MerchantStore merchantStore)
      throws Exception {
    String phone = sessionUtil.getPhoneByAuthToken(request);
    Long startTime = new Date().getTime();
    LOGGER.info("CHECKOUT API START TIME : {}", startTime);
    PersistableCustomerOrder persistableCustomerOrder =
        customerOrderFacade.checkoutCart(persistableCheckoutCart, merchantStore, phone);
    Long endTime = new Date().getTime();
    LOGGER.info("CHECKOUT API END TIME : {}", endTime);
    LOGGER.info("CHECKOUT API LATENCY : {}", endTime - startTime);
    return new SuccessResponse(persistableCustomerOrder, "Checkout Successful.. !");
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/customers/order/{orderCode}", method = RequestMethod.GET)
  @ApiOperation(
      httpMethod = "GET",
      value = "GET customer order status thank you page",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse getOrderDetails(
      @PathVariable String orderCode, @ApiIgnore MerchantStore merchantStore) {
    return new SuccessResponse(customerOrderFacade.getOrderDetails(orderCode, merchantStore));
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping(value = "/customers/order/{orderCode}/status/{orderStatus}")
  @ApiOperation(
      httpMethod = "PUT",
      value = "update customer order status",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse orderCancel(
      @PathVariable String orderCode,
      @PathVariable OrderStatus orderStatus,
      @ApiIgnore MerchantStore merchantStore) {
    customerOrderFacade.orderCancel(orderCode, merchantStore, orderStatus);
    return new SuccessResponse("Order cancelled successfully...!!");
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/customers/order")
  @ApiOperation(
      httpMethod = "GET",
      value = "get customer order",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse getOrder(
      @RequestParam Long id, @ApiIgnore MerchantStore merchantStore) {
    return new SuccessResponse(customerOrderFacade.getByOrderId(id, merchantStore));
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/customers/trackingUrl")
  @ApiOperation(
      httpMethod = "GET",
      value = "GET tracking url",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse trackingUrl(
      @RequestParam String orderCode,
      HttpServletRequest request,
      @ApiIgnore MerchantStore merchantStore) {
    return new SuccessResponse(customerOrderFacade.getTrackingUrl(orderCode));
  }
}
