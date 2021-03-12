package com.salesmanager.shop.store.api.v1.product;

import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.customer.membership.RewardConsumptionCriteriaFacade;
import com.salesmanager.shop.model.customer.PersistableRewardConsumptionCriteria;
import com.salesmanager.shop.utils.SuccessResponse;
import io.swagger.annotations.*;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1")
@Api(
    tags = {
      " RewardConsumptionCriteria management resource ( RewardConsumptionCriteria Management Api)"
    })
@SwaggerDefinition(
    tags = {
      @Tag(
          name = " RewardConsumptionCriteria  management resource",
          description = "Manage  RewardConsumptionCriteria")
    })
public class AdminRewardConsumptionCriteriaApi {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(AdminRewardConsumptionCriteriaApi.class);

  @Inject private RewardConsumptionCriteriaFacade rewardConsumptionCriteriaFacade;

  /** Create new RewardConsumptionCriteria */
  @PostMapping("/private/rewardConsumptionCriteria")
  @ApiOperation(
      httpMethod = "POST",
      value = "Creates a  RewardConsumptionCriteria",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse create(
      @ApiIgnore MerchantStore merchantStore,
      @Valid @RequestBody
          PersistableRewardConsumptionCriteria persistableRewardConsumptionCriteria) {
    return new SuccessResponse(
        rewardConsumptionCriteriaFacade.create(
            persistableRewardConsumptionCriteria, merchantStore));
  }

  @PutMapping("/private/rewardConsumptionCriteria")
  @ApiOperation(
      httpMethod = "PUT",
      value = "Updates a  RewardConsumptionCriteria",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse update(
      @ApiIgnore MerchantStore merchantStore,
      @Valid @RequestBody
          PersistableRewardConsumptionCriteria persistableRewardConsumptionCriteria) {
    return new SuccessResponse(
        rewardConsumptionCriteriaFacade.update(
            persistableRewardConsumptionCriteria, merchantStore));
  }

  @DeleteMapping("/private/rewardConsumptionCriteria/{id}")
  @ApiOperation(
      httpMethod = "DELETE",
      value = "Deletes a RewardConsumptionCriteria",
      notes = "Requires administration access")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
  })
  public @ResponseBody SuccessResponse delete(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore) {
    rewardConsumptionCriteriaFacade.deleteById(id);
    return new SuccessResponse(" deleted successfully.");
  }

  @GetMapping("/private/rewardConsumptionCriteriaById/{id}")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get a  RewardConsumptionCriteria by id",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse get(
      @PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
    return new SuccessResponse(
        rewardConsumptionCriteriaFacade.getRewardConsumptionCriteriaById(
            id, merchantStore, language));
  }

  @GetMapping("/private/rewardConsumptionCriteriaByType/{type}")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get a  RewardConsumptionCriteria by type",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse get(
      @PathVariable TierType type,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language) {
    return new SuccessResponse(
        rewardConsumptionCriteriaFacade.getRewardConsumptionCriteriaByType(
            type, merchantStore, language));
  }

  @GetMapping("/private/rewardConsumptionCriterias")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get all RewardConsumptionCriteria",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse getAll(
      @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
    return new SuccessResponse(
        rewardConsumptionCriteriaFacade.getAllRewardConsumptionCriteriaFacade(
            merchantStore, language));
  }

  @GetMapping("/private/tierNameTypes")
  @ApiOperation(
      httpMethod = "GET",
      value = "Get all TierNameType",
      produces = "application/json",
      response = SuccessResponse.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en")
  })
  public @ResponseBody SuccessResponse getAllTierNameType(
      @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
    return new SuccessResponse(
        rewardConsumptionCriteriaFacade.getAllTierNameType(merchantStore, language));
  }
}
