package com.salesmanager.shop.store.api.v1.membership;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.customer.membership.MembershipFacade;
import com.salesmanager.shop.utils.SessionUtil;
import com.salesmanager.shop.utils.SuccessResponse;
import io.swagger.annotations.*;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api/v1")
@Api(tags = {"Membership resources (Membership  management Api)"})
@SwaggerDefinition(tags = {@Tag(name = "Membership  resources", description = "get Membership ")})
public class MembershipApi {
  private static final Logger LOGGER = LoggerFactory.getLogger(MembershipApi.class);
  @Inject private MembershipFacade membershipFacade;
  @Inject private SessionUtil sessionUtil;

  @ApiOperation(
      httpMethod = "GET",
      value = "Get Membership Detail",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/membership"},
      method = RequestMethod.GET)
  public @ResponseBody SuccessResponse get(
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    String phone = sessionUtil.getPhoneByAuthToken(request);
    return new SuccessResponse(membershipFacade.loyaltyDetail(phone));
  }

  @ApiOperation(
      httpMethod = "GET",
      value = "Get Invite Detail",
      notes = "",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/invite"},
      method = RequestMethod.GET)
  public @ResponseBody SuccessResponse getInvite(HttpServletRequest request) {
    String phone = sessionUtil.getPhoneByAuthToken(request);
    return new SuccessResponse(membershipFacade.getInviteDetail(phone));
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = {"/customerPoints"})
  public @ResponseBody SuccessResponse getCustomerPoints(@RequestParam String phone) {
    return new SuccessResponse(membershipFacade.getInviteDetail(phone));
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping(value = {"/membership/editPoints"})
  public @ResponseBody SuccessResponse editCustomerPoints(
      @RequestParam int rewardPoints, @RequestParam String phone) {
    return new SuccessResponse(membershipFacade.editRewardPoints(rewardPoints, phone), "");
  }
}
