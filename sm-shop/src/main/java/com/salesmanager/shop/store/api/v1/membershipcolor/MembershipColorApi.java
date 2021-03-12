package com.salesmanager.shop.store.api.v1.membershipcolor;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.membershipcolor.MembershipColorFacade;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableMembershipColorList;
import com.salesmanager.shop.model.productAttribute.PersistableMembershipColor;
import com.salesmanager.shop.utils.SuccessResponse;
import io.swagger.annotations.*;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api/v1")
@Api(tags = {"Membership color resources (Membership color management Api)"})
@SwaggerDefinition(
    tags = {
      @Tag(
          name = "Membership color resources",
          description = "Add , edit  and delete Membership color")
    })
public class MembershipColorApi {
  private static final Logger LOGGER = LoggerFactory.getLogger(MembershipColorApi.class);
  @Inject private MembershipColorFacade membershipColorFacade;

  @ApiOperation(
      httpMethod = "POST",
      value = "Create MembershipColor",
      notes = "",
      produces = "application/json",
      response = PersistableMembershipColor.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/private/membershipColor", method = RequestMethod.POST)
  public @ResponseBody PersistableMembershipColor create(
      @Valid @RequestBody PersistableMembershipColor membershipColor,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response) {
    membershipColorFacade.createMembershipColor(membershipColor, merchantStore);
    return membershipColor;
  }

  @ApiOperation(
      httpMethod = "PUT",
      value = "Update MembershipColor",
      notes = "",
      produces = "application/json",
      response = PersistableMembershipColor.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/membershipColor"},
      method = RequestMethod.PUT)
  public @ResponseBody PersistableMembershipColor update(
      @Valid @RequestBody PersistableMembershipColor MembershipColor,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response) {
    membershipColorFacade.updateMembershipColor(MembershipColor, merchantStore);
    return MembershipColor;
  }

  @ApiOperation(
      httpMethod = "GET",
      value = "Get MembershipColor",
      notes = "",
      produces = "application/json",
      response = PersistableMembershipColor.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/membershipColor/{id}}"},
      method = RequestMethod.GET)
  public @ResponseBody PersistableMembershipColor get(
      @PathVariable Long id,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    return membershipColorFacade.getMembershipColor(id, merchantStore);
  }

  @ApiOperation(
      httpMethod = "DELETE",
      value = "Delete MembershipColor",
      notes = "",
      response = Void.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/membershipColor/delete/{id}"},
      method = RequestMethod.DELETE)
  public @ResponseBody SuccessResponse delete(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language)
      throws Exception {
    membershipColorFacade.deleteMembershipColor(id, merchantStore);
    return new SuccessResponse("deleted Successfully..!");
  }

  @ApiOperation(
      httpMethod = "GET",
      value = "Get All MembershipColor based on page size",
      notes = "",
      produces = "application/json",
      response = PersistableMembershipColorList.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/membershipColors"},
      method = RequestMethod.GET)
  public @ResponseBody PersistableMembershipColorList getAllMembershipColor(
      @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    return membershipColorFacade.getAllUniqueMembershipColor(page, pageSize, merchantStore);
  }
}
