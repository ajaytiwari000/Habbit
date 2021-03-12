/** */
package com.salesmanager.shop.store.facade.customer.membership;

import com.salesmanager.core.business.HabbitCoreConstant;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.membership.MembershipService;
import com.salesmanager.core.business.services.membershipcolor.MembershipColorService;
import com.salesmanager.core.model.catalog.product.MembershipColor;
import com.salesmanager.core.model.common.Membership;
import com.salesmanager.core.model.common.RewardConsumptionCriteria;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.core.model.common.enumerator.TierTypeEarnPoint;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shoppingcart.Cart;
import com.salesmanager.shop.admin.controller.customer.membership.MembershipFacade;
import com.salesmanager.shop.admin.controller.customer.membership.ReferralCodeFacade;
import com.salesmanager.shop.admin.controller.customer.membership.RewardConsumptionCriteriaFacade;
import com.salesmanager.shop.cache.util.CacheUtil;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.model.customer.LoyaltyPageDetail;
import com.salesmanager.shop.model.customer.PersistableMembership;
import com.salesmanager.shop.model.customer.PersistableReferralCode;
import com.salesmanager.shop.model.customer.PersistableRewardConsumptionCriteria;
import com.salesmanager.shop.model.customer.mapper.MembershipMapper;
import com.salesmanager.shop.model.customer.mapper.RewardConsumptionCriteriaMapper;
import com.salesmanager.shop.store.api.exception.ResourceDuplicateException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.facade.authentication.util.AuthenticationTokenUtil;
import com.salesmanager.shop.store.facade.cart.CartFacade;
import java.util.Calendar;
import java.util.Objects;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("membershipFacade")
public class MembershipFacadeImpl implements MembershipFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(MembershipFacadeImpl.class);
  @Inject private MembershipService membershipService;
  @Inject private MembershipMapper membershipMapper;
  @Inject private AuthenticationTokenUtil authenticationTokenUtil;
  @Inject private MembershipColorService membershipColorService;
  @Inject private ReferralCodeFacade referralCodeFacade;
  @Inject private CacheUtil cacheUtil;
  @Inject private RewardConsumptionCriteriaFacade rewardConsumptionCriteriaFacade;
  @Inject private RewardConsumptionCriteriaMapper rewardConsumptionCriteriaMapper;
  @Inject private CartFacade cartFacade;

  @Override
  public PersistableMembership create(
      PersistableMembership persistableMembership, MerchantStore merchantStore) {
    Membership membership = null;
    if (StringUtils.isNotEmpty(persistableMembership.getPhoneNumber())) {
      membership = getByMembership(persistableMembership.getPhoneNumber());
    }
    if (Objects.nonNull(membership)) {
      LOGGER.error(
          "Membership Already exists for phone {}", persistableMembership.getPhoneNumber());
      throw new ResourceDuplicateException(
          "Membership Already exists for phone " + persistableMembership.getPhoneNumber());
    }
    membership = membershipMapper.toMembership(persistableMembership);
    try {
      membership = membershipService.create(membership);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.MEMBERSHIP_CREATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.MEMBERSHIP_CREATE_FAILURE.getErrorMessage());
    }
    persistableMembership.setId(membership.getId());
    return persistableMembership;
  }

  @Override
  public PersistableMembership update(
      PersistableMembership persistableMembership, MerchantStore merchantStore) {
    Membership membership = membershipMapper.toMembership((persistableMembership));
    try {
      membership = membershipService.update(membership);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.MEMBERSHIP_UPDATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.MEMBERSHIP_UPDATE_FAILURE.getErrorMessage());
    }
    return membershipMapper.toPersistableMembership(membership);
  }

  @Override
  public void deleteById(Long id) {
    Validate.notNull(id, "Membership id cannot be null");
    Membership membership = null;
    try {
      membership = getById(id);
      membershipService.delete(membership);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.MEMBERSHIP_DELETE_FAILURE.getErrorCode(),
          AttributeErrorCodes.MEMBERSHIP_DELETE_FAILURE.getErrorMessage());
    }
  }

  @Override
  public PersistableMembership getMembershipById(
      Long id, MerchantStore merchantStore, Language language) {
    Membership membership = getById(id);
    return membershipMapper.toPersistableMembership(membership);
  }

  @Override
  public PersistableMembership getMembershipByPhone(String phone) {
    Membership Membership = getByMembership(phone);
    return membershipMapper.toPersistableMembership(Membership);
  }

  @Override
  public void addRewardPoints(int rewardPoint, String phone, boolean friend) {
    Membership membership = getByMembership(phone);
    membership.setRewardPoint(membership.getRewardPoint() + rewardPoint);
    membership.setLiftTimePoint(membership.getLiftTimePoint() + rewardPoint);
    membership.setEarnedPointCurTier(membership.getEarnedPointCurTier() + rewardPoint);
    if (friend) {
      membership.setEarnedThroughReferrals(membership.getEarnedThroughReferrals() + rewardPoint);
    }
    updateTier(membership);
    try {
      membershipService.update(membership);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.MEMBERSHIP_UPDATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.MEMBERSHIP_UPDATE_FAILURE.getErrorMessage());
    }
  }

  @Override
  public void consumeRewardPoints(int rewardPoint, String phone, boolean friend) {
    Membership membership = getByMembership(phone);
    membership.setRewardPoint(membership.getRewardPoint() - rewardPoint);
    membership.setLiftTimePoint(membership.getLiftTimePoint() - rewardPoint);
    membership.setEarnedPointCurTier(membership.getEarnedPointCurTier() - rewardPoint);
    if (friend) {
      membership.setEarnedThroughReferrals(membership.getEarnedThroughReferrals() - rewardPoint);
    }
    updateTier(membership);
    try {
      membershipService.update(membership);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.MEMBERSHIP_UPDATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.MEMBERSHIP_UPDATE_FAILURE.getErrorMessage());
    }
  }

  @Override
  public int calculateRewardPoint(TierType tierType, long cashAmount) {
    return (int)
        Math.ceil(
            (double) cashAmount / (TierTypeEarnPoint.valueOf(tierType.name()).getSpendPerPoint()));
  }

  @Override
  public LoyaltyPageDetail loyaltyDetail(String phone) {
    Membership membership = null;
    LoyaltyPageDetail loyaltyPageDetail = null;
    try {
      membership = membershipService.getMembershipByPhone(phone);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.MEMBERSHIP_GET_BY_PHONE_FAILURE.getErrorCode(),
          AttributeErrorCodes.MEMBERSHIP_GET_BY_PHONE_FAILURE.getErrorMessage());
    }
    MembershipColor membershipColor = null;
    try {
      membershipColor = membershipColorService.getByTierType(membership.getTierType());
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.MEMBERSHIP_COLOR_GET_BY_TIER_TYPE_FAILURE.getErrorCode(),
          AttributeErrorCodes.MEMBERSHIP_COLOR_GET_BY_TIER_TYPE_FAILURE.getErrorMessage()
              + membership.getTierType().name());
    }
    MembershipColor nextTierMembershipColor = null;
    // todo : need to handle the last tier
    TierType nextTierName = membership.getTierType().next();
    try {
      nextTierMembershipColor = membershipColorService.getByTierType(nextTierName);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.MEMBERSHIP_COLOR_GET_BY_TIER_TYPE_FAILURE.getErrorCode(),
          AttributeErrorCodes.MEMBERSHIP_COLOR_GET_BY_TIER_TYPE_FAILURE.getErrorMessage()
              + membership.getTierType().name());
    }
    loyaltyPageDetail =
        populateLoyalityDetail(membership, membershipColor, nextTierMembershipColor);
    return loyaltyPageDetail;
  }

  @Override
  public PersistableMembership getInviteDetail(String phone) {
    Membership membership = null;
    PersistableMembership persistableMembership = null;
    try {
      membership = membershipService.getMembershipByPhone(phone);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.MEMBERSHIP_GET_BY_PHONE_FAILURE.getErrorCode(),
          AttributeErrorCodes.MEMBERSHIP_GET_BY_PHONE_FAILURE.getErrorMessage());
    }
    persistableMembership = membershipMapper.toPersistableMembership(membership);
    PersistableReferralCode persistableReferralCode =
        referralCodeFacade.getReferralCodeByPhone(phone);
    persistableMembership.setReferrerPoints(persistableReferralCode.getReferrerPoints());
    persistableMembership.setRefereePoints(persistableReferralCode.getRefereePoints());
    persistableMembership.setReferralLink(
        HabbitCoreConstant.DOMAIN_REFERRAL + persistableReferralCode.getCodeName());
    return persistableMembership;
  }

  @Override
  public String editRewardPoints(int rewardPoints, String phone) {
    if (rewardPoints > 0) {
      addRewardPoints(rewardPoints, phone, false);
    } else {
      consumeRewardPoints(rewardPoints * (-1), phone, false);
    }
    return "Points updated successfully..!";
  }

  private LoyaltyPageDetail populateLoyalityDetail(
      Membership membership,
      MembershipColor membershipColor,
      MembershipColor nextTierMembershipColor) {
    LoyaltyPageDetail loyaltyPageDetail = new LoyaltyPageDetail();
    loyaltyPageDetail.setAvailableRewardPoint(membership.getRewardPoint());
    loyaltyPageDetail.setEarnedLifeTimePoint(membership.getLiftTimePoint());
    loyaltyPageDetail.setEarnedPointCurTier(membership.getEarnedPointCurTier());
    int pointToNextTier = membership.getMinUpgradePoint() - membership.getEarnedPointCurTier();
    loyaltyPageDetail.setNeedPointToNextTier(pointToNextTier);
    loyaltyPageDetail.setPhoneNumber(membership.getPhoneNumber());
    loyaltyPageDetail.setTierType(membership.getTierType());
    loyaltyPageDetail.setPrimaryColor(membershipColor.getPrimaryColor());
    loyaltyPageDetail.setSecondaryColor(membershipColor.getSecondaryColor());
    // next tier information
    loyaltyPageDetail.setNextTierType(nextTierMembershipColor.getTierType());
    loyaltyPageDetail.setNextTierPrimaryColor(nextTierMembershipColor.getPrimaryColor());
    loyaltyPageDetail.setNextTierSecondaryColor(nextTierMembershipColor.getSecondaryColor());

    RewardConsumptionCriteria rewardConsumptionCriteria =
        cacheUtil.getObjectFromCache(TierType.ALL.name(), RewardConsumptionCriteria.class);
    if (Objects.isNull(rewardConsumptionCriteria)) {
      PersistableRewardConsumptionCriteria persistableRewardConsumptionCriteria =
          rewardConsumptionCriteriaFacade.getRewardConsumptionCriteriaByType(
              TierType.ALL, null, null);
      loyaltyPageDetail.setPersistableRewardConsumptionCriteria(
          persistableRewardConsumptionCriteria);
      cacheUtil.setObjectInCache(
          TierType.ALL.name(),
          rewardConsumptionCriteriaMapper.toRewardConsumptionCriteria(
              persistableRewardConsumptionCriteria));
    } else {
      loyaltyPageDetail.setPersistableRewardConsumptionCriteria(
          rewardConsumptionCriteriaMapper.toPersistableRewardConsumptionCriteria(
              rewardConsumptionCriteria));
    }

    return loyaltyPageDetail;
  }

  @Override
  public void updateTier(Membership membership) {
    Calendar cal = Calendar.getInstance();
    if (membership.getEarnedPointCurTier() < membership.getMinUpgradePoint()
        && cal.getTimeInMillis() > membership.getEndDateOfCurTier()) {
      if (membership.getEarnedPointCurTier() >= membership.getMinDowngradePoint()) {
        resetCurrentTier(membership);
        return;
      }
      downgradeTier(membership);
      updateCustomerCartTier(membership);
    } else if (membership.getEarnedPointCurTier() >= membership.getMinUpgradePoint()
        && cal.getTimeInMillis() <= membership.getEndDateOfCurTier()) {
      upgradeTier(membership);
      updateCustomerCartTier(membership);
    }
  }

  private void updateCustomerCartTier(Membership membership) {
    Cart cart = cartFacade.getCartByPhone(membership.getPhoneNumber());
    cart.setTierType(membership.getTierType());
    cartFacade.update(cart);
  }

  private void downgradeTier(Membership membership) {
    updateNewTierTime(membership);
    membership.setEarnedPointCurTier(0);
    downgradeTierType(membership);
  }

  private void downgradeTierType(Membership membership) {
    switch (membership.getTierType().ordinal()) {
        // Habbit blue :- it wont occur
      case 0:
        {
          break;
        }
        // Habbit gold
      case 1:
        {
          membership.setTierType(TierType.HABBIT_BLUE);
          membership.setMinDowngradePoint(TierType.HABBIT_BLUE.getLowerLimit());
          membership.setMinUpgradePoint(TierType.HABBIT_GOLD.getLowerLimit());
          break;
        }
        // Habbit platinum
      case 2:
        {
          membership.setTierType(TierType.HABBIT_GOLD);
          membership.setMinDowngradePoint(TierType.HABBIT_GOLD.getLowerLimit());
          membership.setMinUpgradePoint(TierType.HABBIT_PLATINUM.getLowerLimit());
          break;
        }
        // Habbit diamond
      case 3:
        {
          membership.setTierType(TierType.HABBIT_PLATINUM);
          membership.setMinDowngradePoint(TierType.HABBIT_PLATINUM.getLowerLimit());
          membership.setMinUpgradePoint(TierType.HABBIT_DIAMOND.getLowerLimit());
          break;
        }
        // Habbit black
      case 4:
        {
          membership.setTierType(TierType.HABBIT_DIAMOND);
          membership.setMinDowngradePoint(TierType.HABBIT_DIAMOND.getLowerLimit());
          membership.setMinUpgradePoint(TierType.HABBIT_BLACK.getLowerLimit());
          break;
        }
    }
  }

  private void updateNewTierTime(Membership membership) {
    Calendar cal = Calendar.getInstance();
    membership.setStartDateOfCurTier(cal.getTimeInMillis());
    cal.add(Calendar.YEAR, 1);
    membership.setEndDateOfCurTier(cal.getTimeInMillis());
  }

  private void upgradeTier(Membership membership) {
    updateNewTierTime(membership);
    membership.setEarnedPointCurTier(
        membership.getEarnedPointCurTier() - membership.getMinUpgradePoint());
    upgradeTierType(membership);
  }

  private void upgradeTierType(Membership membership) {
    switch (membership.getTierType().ordinal()) {
        // Habbit blue
      case 0:
        {
          membership.setTierType(TierType.HABBIT_GOLD);
          membership.setMinDowngradePoint(TierType.HABBIT_GOLD.getLowerLimit());
          membership.setMinUpgradePoint(TierType.HABBIT_PLATINUM.getLowerLimit());
          break;
        }
        // Habbit gold
      case 1:
        {
          membership.setTierType(TierType.HABBIT_PLATINUM);
          membership.setMinDowngradePoint(TierType.HABBIT_PLATINUM.getLowerLimit());
          membership.setMinUpgradePoint(TierType.HABBIT_DIAMOND.getLowerLimit());
          break;
        }
        // Habbit platinum
      case 2:
        {
          membership.setTierType(TierType.HABBIT_DIAMOND);
          membership.setMinDowngradePoint(TierType.HABBIT_DIAMOND.getLowerLimit());
          membership.setMinUpgradePoint(TierType.HABBIT_BLACK.getLowerLimit());
          break;
        }
        // Habbit diamond
      case 3:
        {
          membership.setTierType(TierType.HABBIT_BLACK);
          membership.setMinDowngradePoint(TierType.HABBIT_BLACK.getLowerLimit());
          membership.setMinUpgradePoint(TierType.HABBIT_BLACK.getUpperLimit());
          break;
        }
        // Habbit black
      case 4:
        {
          break;
        }
    }
  }

  private void resetCurrentTier(Membership membership) {
    Calendar cal = Calendar.getInstance();
    membership.setEarnedPointCurTier(0);
    membership.setStartDateOfCurTier(cal.getTimeInMillis());
    cal.add(Calendar.YEAR, 1);
    membership.setEndDateOfCurTier(cal.getTimeInMillis());
  }

  private Membership getById(Long id) {
    Membership membership = null;
    try {
      membership = membershipService.getById(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.MEMBERSHIP_GET_BY_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.MEMBERSHIP_GET_BY_ID_FAILURE.getErrorMessage() + id);
    }
    if (Objects.isNull(membership)) {
      LOGGER.error("Membership with id {} not found in DB.", id);
      throw new ResourceNotFoundException("Membership with id [" + id + " ] not found");
    }
    return membership;
  }

  private Membership getByMembership(String phone) {
    Membership membership = null;
    try {
      membership = membershipService.getMembershipByPhone(phone);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.MEMBERSHIP_GET_BY_PHONE_FAILURE.getErrorCode(),
          AttributeErrorCodes.MEMBERSHIP_GET_BY_PHONE_FAILURE.getErrorMessage() + phone);
    }
    return membership;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
