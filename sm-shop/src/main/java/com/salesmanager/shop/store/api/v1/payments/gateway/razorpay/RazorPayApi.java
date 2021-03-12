package com.salesmanager.shop.store.api.v1.payments.gateway.razorpay;

import com.razorpay.Utils;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.customer.PersistableCustomerRazorPayOrder;
import com.salesmanager.shop.store.facade.payments.gateway.razorpay.CustomerRazorPayFacade;
import com.salesmanager.shop.utils.SuccessResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Date;
import java.util.concurrent.TimeUnit;
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
public class RazorPayApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(RazorPayApi.class);

  @Inject private CustomerRazorPayFacade customerRazorPayFacade;

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/payment/razorPay", method = RequestMethod.GET)
  @ApiOperation(
      httpMethod = "GET",
      value = "get getRazorPayDetail",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse getRazorPayDetail(
      @RequestParam String orderCode, HttpServletRequest request) throws Exception {

    return new SuccessResponse(customerRazorPayFacade.getRazorPayDetail(orderCode));
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/payment/razorPay", method = RequestMethod.PUT)
  @ApiOperation(
      httpMethod = "PUT",
      value = "get getRazorPayDetail",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse updateRazorPayDetail(
      @RequestBody PersistableCustomerRazorPayOrder persistableCustomerRazorPayOrder,
      HttpServletRequest request,
      @ApiIgnore MerchantStore merchantStore)
      throws Exception {
    Long startTime = new Date().getTime();
    LOGGER.info("UPDATE RAZORPAY API START TIME : {}", startTime);
    PersistableCustomerRazorPayOrder customerRazorPayOrder =
        customerRazorPayFacade.updateRazorPayDetail(
            persistableCustomerRazorPayOrder, merchantStore, false);
    Long endTime = new Date().getTime();
    LOGGER.info("UPDATE RAZORPAY API END TIME : {}", endTime);
    LOGGER.info("UPDATE RAZORPAY API LATENCY : {}", endTime - startTime);

    return new SuccessResponse(customerRazorPayOrder);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = "/payment/razorPay/webhooks")
  @ApiOperation(
      httpMethod = "POST",
      value = "post RazorPayDetail",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse updateRazorPayDetailByWebhooks(
      @RequestBody String webhookBody,
      HttpServletRequest request,
      @ApiIgnore MerchantStore merchantStore)
      throws Exception {

    TimeUnit.SECONDS.sleep(30);
    String webhookSignature = request.getHeader("X-Razorpay-Signature");
    LOGGER.info("webhookSignature :{}", webhookSignature);
    boolean verify = Utils.verifyWebhookSignature(webhookBody, webhookSignature, "habbithook");
    LOGGER.info("verify :{}", verify);

    Long startTime = new Date().getTime();
    LOGGER.info("UPDATE WEBHOOKS RAZORPAY API START TIME : {}", startTime);
    LOGGER.info("webhookBody :{}", webhookBody);
    if (verify) {
      customerRazorPayFacade.updateRazorPayDetailByWebhooks(webhookBody, merchantStore);
    }
    Long endTime = new Date().getTime();
    LOGGER.info("UPDATE WEBHOOKS RAZORPAY API END TIME : {}", endTime);
    LOGGER.info("UPDATE WEBHOOKS RAZORPAY API LATENCY : {}", endTime - startTime);

    return new SuccessResponse("update webhooks ");
  }
}
