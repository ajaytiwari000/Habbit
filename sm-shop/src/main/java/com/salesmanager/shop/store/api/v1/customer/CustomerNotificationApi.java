package com.salesmanager.shop.store.api.v1.customer;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.customer.PersistableCustomerNotification;
import com.salesmanager.shop.store.facade.customer.notification.CustomerNotificationFacade;
import com.salesmanager.shop.utils.SuccessResponse;
import io.swagger.annotations.*;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = {"Customer notification management resource (Customer notification Management Api)"})
@SwaggerDefinition(
    tags = {
      @Tag(
          name = "Customer notification management resource",
          description = "Manage customer notification")
    })
public class CustomerNotificationApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerNotificationApi.class);

  @Inject private CustomerNotificationFacade customerNotificationFacade;

  @PutMapping("/customers/notification")
  @ApiOperation(
      httpMethod = "PUT",
      value = "update customer notification",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse updateNotification(
      @RequestBody PersistableCustomerNotification customerNotification,
      HttpServletRequest request,
      @ApiIgnore MerchantStore merchantStore) {
    return new SuccessResponse(
        customerNotificationFacade.updateCustomerNotification(
            customerNotification, request, merchantStore));
  }

  @GetMapping("/customers/notification")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get customer notification",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse getNotification(
      HttpServletRequest request, @ApiIgnore MerchantStore merchantStore) {
    return new SuccessResponse(
        customerNotificationFacade.getCustomerNotification(request, merchantStore));
  }
}
