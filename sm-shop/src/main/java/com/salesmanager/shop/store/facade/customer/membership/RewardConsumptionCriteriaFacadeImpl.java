/** */
package com.salesmanager.shop.store.facade.customer.membership;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.rewardconsumptioncreteria.RewardConsumptionCriteriaService;
import com.salesmanager.core.model.common.RewardConsumptionCriteria;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.customer.membership.RewardConsumptionCriteriaFacade;
import com.salesmanager.shop.cache.util.CacheUtil;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.model.customer.PersistableRewardConsumptionCriteria;
import com.salesmanager.shop.model.customer.PersistableRewardConsumptionCriteriaList;
import com.salesmanager.shop.model.customer.PersistableTierNameTypeList;
import com.salesmanager.shop.model.customer.mapper.RewardConsumptionCriteriaMapper;
import com.salesmanager.shop.store.api.exception.ResourceDuplicateException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("rewardConsumptionCriteriaFacade")
public class RewardConsumptionCriteriaFacadeImpl implements RewardConsumptionCriteriaFacade {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(RewardConsumptionCriteriaFacadeImpl.class);
  @Inject private RewardConsumptionCriteriaService rewardConsumptionCriteriaService;
  @Inject private RewardConsumptionCriteriaMapper rewardConsumptionCriteriaMapper;
  @Inject private CacheUtil cacheUtil;

  @Override
  public PersistableRewardConsumptionCriteria create(
      PersistableRewardConsumptionCriteria persistableRewardConsumptionCriteria,
      MerchantStore merchantStore) {
    RewardConsumptionCriteria rewardConsumptionCriteria = null;
    try {
      rewardConsumptionCriteria =
          rewardConsumptionCriteriaService.getRewardConsumptionCriteriaByType(
              persistableRewardConsumptionCriteria.getTierType());
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REWARD_CONSUMPTION_CRITERIA_GET_BY_TYPE_FAILURE.getErrorCode(),
          AttributeErrorCodes.REWARD_CONSUMPTION_CRITERIA_GET_BY_TYPE_FAILURE.getErrorMessage());
    }
    if (Objects.nonNull(rewardConsumptionCriteria)) {
      LOGGER.error(
          "Referral code Already exists for {}",
          persistableRewardConsumptionCriteria.getTierType());
      throw new ResourceDuplicateException(
          "Referral code Already exists for " + persistableRewardConsumptionCriteria.getTierType());
    }
    rewardConsumptionCriteria =
        rewardConsumptionCriteriaMapper.toRewardConsumptionCriteria(
            persistableRewardConsumptionCriteria);
    try {
      rewardConsumptionCriteria =
          rewardConsumptionCriteriaService.create(rewardConsumptionCriteria);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REWARD_CONSUMPTION_CRITERIA_CREATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.REWARD_CONSUMPTION_CRITERIA_CREATE_FAILURE.getErrorMessage());
    }
    persistableRewardConsumptionCriteria.setId(rewardConsumptionCriteria.getId());
    cacheUtil.setObjectInCache(
        rewardConsumptionCriteria.getTierType().name(), rewardConsumptionCriteria);
    return persistableRewardConsumptionCriteria;
  }

  @Override
  public PersistableRewardConsumptionCriteria update(
      PersistableRewardConsumptionCriteria persistableRewardConsumptionCriteria,
      MerchantStore merchantStore) {
    RewardConsumptionCriteria rewardConsumptionCriteria =
        rewardConsumptionCriteriaMapper.toRewardConsumptionCriteria(
            (persistableRewardConsumptionCriteria));
    try {
      rewardConsumptionCriteria =
          rewardConsumptionCriteriaService.update(rewardConsumptionCriteria);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REWARD_CONSUMPTION_CRITERIA_UPDATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.REWARD_CONSUMPTION_CRITERIA_UPDATE_FAILURE.getErrorMessage());
    }
    cacheUtil.setObjectInCache(
        rewardConsumptionCriteria.getTierType().name(), rewardConsumptionCriteria);
    return rewardConsumptionCriteriaMapper.toPersistableRewardConsumptionCriteria(
        rewardConsumptionCriteria);
  }

  @Override
  public void deleteById(Long id) {
    Validate.notNull(id, "RewardConsumptionCriteria id cannot be null");
    RewardConsumptionCriteria rewardConsumptionCriteria = null;
    try {
      rewardConsumptionCriteria = getById(id);
      rewardConsumptionCriteriaService.delete(rewardConsumptionCriteria);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REWARD_CONSUMPTION_CRITERIA_DELETE_FAILURE.getErrorCode(),
          AttributeErrorCodes.REWARD_CONSUMPTION_CRITERIA_DELETE_FAILURE.getErrorMessage());
    }
  }

  @Override
  public PersistableRewardConsumptionCriteria getRewardConsumptionCriteriaById(
      Long id, MerchantStore merchantStore, Language language) {
    RewardConsumptionCriteria rewardConsumptionCriteria = getById(id);
    return rewardConsumptionCriteriaMapper.toPersistableRewardConsumptionCriteria(
        rewardConsumptionCriteria);
  }

  @Override
  public PersistableRewardConsumptionCriteria getRewardConsumptionCriteriaByType(
      TierType type, MerchantStore merchantStore, Language language) {
    RewardConsumptionCriteria rewardConsumptionCriteria = getByType(type);
    return rewardConsumptionCriteriaMapper.toPersistableRewardConsumptionCriteria(
        rewardConsumptionCriteria);
  }

  private RewardConsumptionCriteria getById(Long id) {
    RewardConsumptionCriteria rewardConsumptionCriteria = null;
    try {
      rewardConsumptionCriteria = rewardConsumptionCriteriaService.getById(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REWARD_CONSUMPTION_CRITERIA_GET_BY_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.REWARD_CONSUMPTION_CRITERIA_GET_BY_ID_FAILURE.getErrorMessage() + id);
    }
    if (Objects.isNull(rewardConsumptionCriteria)) {
      LOGGER.error("referral code with id {} not found in DB.", id);
      throw new ResourceNotFoundException("referral code with id [" + id + " ] not found");
    }
    return rewardConsumptionCriteria;
  }

  private RewardConsumptionCriteria getByType(TierType type) {
    RewardConsumptionCriteria rewardConsumptionCriteria = null;
    try {
      rewardConsumptionCriteria =
          rewardConsumptionCriteriaService.getRewardConsumptionCriteriaByType(type);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REWARD_CONSUMPTION_CRITERIA_GET_BY_TYPE_FAILURE.getErrorCode(),
          AttributeErrorCodes.REWARD_CONSUMPTION_CRITERIA_GET_BY_TYPE_FAILURE.getErrorMessage()
              + type);
    }
    if (Objects.isNull(rewardConsumptionCriteria)) {
      LOGGER.error(
          "Error while getting reward consumption criteria for type {} not found in DB.", type);
      throw new ResourceNotFoundException(
          "Error while getting reward consumption criteria for type [" + type + " ] not found");
    }
    return rewardConsumptionCriteria;
  }

  @Override
  public PersistableRewardConsumptionCriteriaList getAllRewardConsumptionCriteriaFacade(
      MerchantStore merchantStore, Language language) {
    PersistableRewardConsumptionCriteriaList persistableRewardConsumptionCriteriaList =
        new PersistableRewardConsumptionCriteriaList();
    try {
      persistableRewardConsumptionCriteriaList.setPersistableRewardConsumptionCriterias(
          Optional.ofNullable(rewardConsumptionCriteriaService.getAllRewardConsumptionCriteria())
              .map(Collection::stream)
              .orElse(Stream.empty())
              .map(rewardConsumptionCriteriaMapper::toPersistableRewardConsumptionCriteria)
              .collect(Collectors.toList()));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REWARD_CONSUMPTION_CRITERIA_GET_ALL_FAILURE.getErrorCode(),
          AttributeErrorCodes.REWARD_CONSUMPTION_CRITERIA_GET_ALL_FAILURE.getErrorMessage());
    }
    return persistableRewardConsumptionCriteriaList;
  }

  @Override
  public PersistableTierNameTypeList getAllTierNameType(
      MerchantStore merchantStore, Language language) {
    PersistableTierNameTypeList persistableTierNameTypeList = new PersistableTierNameTypeList();
    try {
      persistableTierNameTypeList.setPersistableRewardConsumptionCriterias(
          Arrays.asList(TierType.values()));
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REWARD_CONSUMPTION_CRITERIA_GET_ALL_TIER_NAME_TYPE_FAILURE
              .getErrorCode(),
          AttributeErrorCodes.REWARD_CONSUMPTION_CRITERIA_GET_ALL_TIER_NAME_TYPE_FAILURE
              .getErrorMessage());
    }
    return persistableTierNameTypeList;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
