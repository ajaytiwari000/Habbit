package com.salesmanager.shop.store.api.v1.order;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.admin.controller.customer.order.CustomerOrderStatusHistoryFacade;
import com.salesmanager.shop.utils.SessionUtil;
import com.salesmanager.shop.utils.SuccessResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
public class CustomerOrderStatusHistoryApi {

  @Inject private CustomerOrderStatusHistoryFacade customerOrderStatusHistoryFacade;
  @Inject private SessionUtil sessionUtil;

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerOrderStatusHistoryApi.class);

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/customer/orderHistory")
  @ApiOperation(
      httpMethod = "GET",
      value = "GET customer order status History",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse getOrderStatusHistory(
      HttpServletRequest request, @ApiIgnore MerchantStore merchantStore) {
    String phone = sessionUtil.getPhoneByAuthToken(request);
    return new SuccessResponse(
        customerOrderStatusHistoryFacade.getOrderStatusHistoryList(merchantStore, phone));
  }
}
