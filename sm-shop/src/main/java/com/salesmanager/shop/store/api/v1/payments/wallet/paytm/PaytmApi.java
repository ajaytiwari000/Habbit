package com.salesmanager.shop.store.api.v1.payments.wallet.paytm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.salesmanager.shop.model.customer.PersistablePaytmTransaction;
import com.salesmanager.shop.store.facade.payments.wallet.paytm.PaytmFacade;
import com.salesmanager.shop.utils.SuccessResponse;
import io.swagger.annotations.*;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = {" management resource ( Management Api)"})
@SwaggerDefinition(
    tags = {@Tag(name = " paytm management resource", description = "Manage  paytm")})
public class PaytmApi {
  @Inject private PaytmFacade paytmFacade;

  //  @RequestMapping(
  //      value = {"/getTnxToken"},
  //      method = RequestMethod.POST)
  //  @ApiOperation(
  //      httpMethod = "POST",
  //      value = "upload paytm to show payment page",
  //      notes = "",
  //      produces = "application/json",
  //      response = void.class)
  //  @ApiImplicitParams({
  //    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
  //    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  //  })
  //  public @ResponseBody
  //  SuccessResponse getTrxToken(
  //      @RequestParam(defaultValue = "order_12") String orderCode,
  //      @RequestParam(defaultValue = "cus_01") String customerPhone,
  //      @RequestParam(defaultValue = "1") String amountValue)
  //      throws Exception {
  //    return new SuccessResponse(paytmFacadeImpl.getTrxToken(orderCode, amountValue,
  // customerPhone));
  //  }
  //
  //  @ResponseStatus(HttpStatus.CREATED)
  //  @RequestMapping(
  //      value = {"/paytmCallbackResponse"},
  //      method = RequestMethod.POST)
  //  @ApiOperation(
  //      httpMethod = "POST",
  //      value = "upload paytm callBack response",
  //      notes = "",
  //      produces = "application/json",
  //      response = SuccessResponse.class)
  //  @ApiImplicitParams({
  //    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
  //    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  //  })
  //  public @ResponseBody SuccessResponse getResponseRedirect(
  //          HttpServletRequest request, Model model) {
  //    return new SuccessResponse(paytmFacadeImpl.getResponseRedirect(request, model));
  //  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(
      value = {"/payment/paytm"},
      method = RequestMethod.GET)
  @ApiOperation(
      httpMethod = "GET",
      value = "get Paytm Init Transaction Request With CheckSum",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse getPaytmInitTransactionRequestWithCheckSum(
      HttpServletRequest request,
      @RequestParam String orderCode,
      @RequestParam String totalOrderValue,
      @RequestParam String channelId,
      @RequestParam String website) {
    return new SuccessResponse(
        paytmFacade.getPaytmInitTransactionRequestWithCheckSum(
            orderCode, totalOrderValue, request, channelId, website));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(
      value = {"/payment/paytm/validateChecksum"},
      method = RequestMethod.PUT)
  @ApiOperation(
      httpMethod = "PUT",
      value = "Paytm validateChecksum",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse validateChecksum(
      @RequestParam String paytmCheckSum,
      @RequestBody Map<String, Object> paytmInitTransactionRequest,
      HttpServletRequest request) {
    return new SuccessResponse(
        paytmFacade.validateCheckSumHash(
            paytmCheckSum, new JSONObject(paytmInitTransactionRequest), request));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(
      value = {"/payment/paytm/verifyCheckSUmAndUpdateTxnStatus"},
      method = RequestMethod.POST)
  @ApiOperation(
      httpMethod = "POST",
      value = "update transaction status",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse verifyCheckSumAndUpdateTxnStatus(
      @RequestBody PersistablePaytmTransaction persistablePaytmTransaction,
      HttpServletRequest request)
      throws JsonProcessingException {
    return new SuccessResponse(
        paytmFacade.verifyCheckSumAndUpdateTxnStatus(persistablePaytmTransaction, request));
  }
}
