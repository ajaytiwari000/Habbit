/** */
package com.salesmanager.shop.store.facade.customer.membership;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.referralcode.ReferralCodeService;
import com.salesmanager.core.model.common.ReferralCode;
import com.salesmanager.core.model.common.enumerator.OwnerType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.customer.membership.ReferralCodeFacade;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.model.customer.PersistableOwnerTypeList;
import com.salesmanager.shop.model.customer.PersistableReferralCode;
import com.salesmanager.shop.model.customer.PersistableReferralCodeList;
import com.salesmanager.shop.model.customer.mapper.ReferralCodeMapper;
import com.salesmanager.shop.store.api.exception.ResourceDuplicateException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.facade.authentication.util.AuthenticationTokenUtil;
import com.salesmanager.shop.store.facade.authentication.util.CustomerUtil;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("referralCodeFacade")
public class ReferralCodeFacadeImpl implements ReferralCodeFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReferralCodeFacadeImpl.class);
  @Inject private ReferralCodeService referralCodeService;
  @Inject private ReferralCodeMapper referralCodeMapper;
  @Inject private AuthenticationTokenUtil authenticationTokenUtil;
  @Inject private CustomerUtil customerUtil;

  @Override
  public PersistableReferralCode create(
      PersistableReferralCode persistableReferralCode, MerchantStore merchantStore) {
    ReferralCode referralCode = null;
    //    if (!OwnerType.ADMIN.name().equals(persistableReferralCode.getOwnerType().name())) {
    //      LOGGER.error("OwnerType should be ADMIN while referral deletion");
    //      throw new IllegalArgumentException("OwnerType should be ADMIN while referral deletion");
    //    }
    try {
      if (StringUtils.isNotEmpty(persistableReferralCode.getCodeName())) {
        referralCode =
            referralCodeService.getReferralCodeByCode(persistableReferralCode.getCodeName());
      }
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REFERRAL_CODE_GET_BY_CODE_FAILURE.getErrorCode(),
          AttributeErrorCodes.REFERRAL_CODE_GET_BY_CODE_FAILURE.getErrorMessage());
    }
    if (Objects.nonNull(referralCode)) {
      LOGGER.error("Referral code Already exists for" + persistableReferralCode.getCodeName());
      throw new ResourceDuplicateException(
          "Referral code Already exists for " + persistableReferralCode.getCodeName());
    }
    referralCode = referralCodeMapper.toReferralCode(persistableReferralCode);
    referralCode.setCodeName(customerUtil.getReferralCode());
    referralCode.setEndDate(setDate());
    try {
      referralCode = referralCodeService.create(referralCode);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REFERRAL_CODE_CREATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.REFERRAL_CODE_CREATE_FAILURE.getErrorMessage());
    }
    persistableReferralCode.setId(referralCode.getId());
    persistableReferralCode.setCodeName(referralCode.getCodeName());
    return persistableReferralCode;
  }

  // TODO : Move to Util
  private Long setDate() {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, 79);
    return cal.getTimeInMillis();
  }

  @Override
  public PersistableReferralCode update(
      PersistableReferralCode persistableReferralCode, MerchantStore merchantStore) {
    ReferralCode referralCode = referralCodeMapper.toReferralCode((persistableReferralCode));
    try {
      referralCode = referralCodeService.update(referralCode);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REFERRAL_CODE_UPDATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.REFERRAL_CODE_UPDATE_FAILURE.getErrorMessage());
    }
    return referralCodeMapper.toPersistableReferralCode(referralCode);
  }

  @Override
  public void deleteById(Long id) {
    Validate.notNull(id, "ReferralCode id cannot be null");
    ReferralCode referralCode = null;
    try {
      referralCode = getById(id);
      if (!OwnerType.ADMIN.name().equals(referralCode.getOwnerType().name())) {
        LOGGER.error("OwnerType should be ADMIN while referral deletion");
        throw new IllegalArgumentException("OwnerType should be ADMIN while referral deletion");
      }
      referralCodeService.delete(referralCode);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REFERRAL_CODE_DELETE_FAILURE.getErrorCode(),
          AttributeErrorCodes.REFERRAL_CODE_DELETE_FAILURE.getErrorMessage());
    }
  }

  @Override
  public PersistableReferralCode getReferralCodeById(
      Long id, MerchantStore merchantStore, Language language) {
    ReferralCode referralCode = getById(id);
    return referralCodeMapper.toPersistableReferralCode(referralCode);
  }

  @Override
  public PersistableReferralCode getReferralCodeByCode(String codeName) {
    ReferralCode referralCode = getByReferralCode(codeName);
    return referralCodeMapper.toPersistableReferralCode(referralCode);
  }

  @Override
  public PersistableReferralCode getReferralCodeByPhone(String phoneNumber) {
    ReferralCode referralCode = getByPhoneNumber(phoneNumber);
    return referralCodeMapper.toPersistableReferralCode(referralCode);
  }

  private ReferralCode getByPhoneNumber(String phoneNumber) {
    ReferralCode referralCode = null;
    try {
      referralCode = referralCodeService.getReferralCodeByPhone(phoneNumber);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REFERRAL_CODE_GET_BY_PHONE_FAILURE.getErrorCode(),
          AttributeErrorCodes.REFERRAL_CODE_GET_BY_PHONE_FAILURE.getErrorMessage() + phoneNumber);
    }
    if (Objects.isNull(referralCode)) {
      LOGGER.error("referral code with phone [" + phoneNumber + " ] not found in DB.");
      throw new ResourceNotFoundException(
          "referral code with phone [" + phoneNumber + " ] not found");
    }
    return referralCode;
  }

  @Override
  public PersistableReferralCodeList getAllReferralCode(
      MerchantStore merchantStore, Language language) {
    PersistableReferralCodeList persistableReferralCodeList = new PersistableReferralCodeList();
    try {
      persistableReferralCodeList.setPersistableReferralCodes(
          Optional.ofNullable(referralCodeService.getAllReferralCodeList())
              .map(Collection::stream)
              .orElse(Stream.empty())
              .map(referralCodeMapper::toPersistableReferralCode)
              .collect(Collectors.toList()));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REFERRAL_CODE_GET_ALL_FAILURE.getErrorCode(),
          AttributeErrorCodes.REFERRAL_CODE_GET_ALL_FAILURE.getErrorMessage());
    }
    return persistableReferralCodeList;
  }

  @Override
  public PersistableOwnerTypeList allOwnerType(MerchantStore merchantStore, Language language) {
    PersistableOwnerTypeList persistableOwnerTypeList = new PersistableOwnerTypeList();
    try {
      persistableOwnerTypeList.setOwnerTypes(Arrays.asList(OwnerType.values()));
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REFERRAL_CODE_GET_ALL_OWNER_TYPE_FAILURE.getErrorCode(),
          AttributeErrorCodes.REFERRAL_CODE_GET_ALL_OWNER_TYPE_FAILURE.getErrorMessage());
    }
    return persistableOwnerTypeList;
  }

  private ReferralCode getById(Long id) {
    ReferralCode referralCode = null;
    try {
      referralCode = referralCodeService.getById(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REFERRAL_CODE_GET_BY_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.REFERRAL_CODE_GET_BY_ID_FAILURE.getErrorMessage() + id);
    }
    if (Objects.isNull(referralCode)) {
      LOGGER.error("referral code with id [" + id + " ] not found in DB.");
      throw new ResourceNotFoundException("referral code with id [" + id + " ] not found");
    }
    return referralCode;
  }

  private ReferralCode getByReferralCode(String codeName) {
    ReferralCode referralCode = null;
    try {
      referralCode = referralCodeService.getReferralCodeByCode(codeName);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.REFERRAL_CODE_GET_BY_CODE_FAILURE.getErrorCode(),
          AttributeErrorCodes.REFERRAL_CODE_GET_BY_CODE_FAILURE.getErrorMessage() + codeName);
    }
    //    if (Objects.isNull(referralCode)) {
    //      LOGGER.error("referral code with code [" + codeName + " ] not found in DB.");
    //      throw new ResourceNotFoundException("referral code with code [" + codeName + " ] not
    // found");
    //    }
    return referralCode;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
