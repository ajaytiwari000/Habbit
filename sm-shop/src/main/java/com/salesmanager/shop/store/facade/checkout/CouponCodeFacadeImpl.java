/** */
package com.salesmanager.shop.store.facade.checkout;

import com.salesmanager.core.business.HabbitCoreConstant;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.coupon.CouponCodeService;
import com.salesmanager.core.model.common.Coupon;
import com.salesmanager.core.model.common.enumerator.CouponCodeType;
import com.salesmanager.core.model.common.enumerator.CouponDiscountType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.checkout.CouponCodeFacade;
import com.salesmanager.shop.cache.util.CacheUtil;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.model.customer.PersistableCouponCode;
import com.salesmanager.shop.model.customer.PersistableCouponCodeList;
import com.salesmanager.shop.model.customer.PersistableCouponCodeTypeList;
import com.salesmanager.shop.model.customer.PersistableDiscountTypeList;
import com.salesmanager.shop.model.customer.mapper.CouponCodeMapper;
import com.salesmanager.shop.store.api.exception.ResourceDuplicateException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.facade.authentication.util.AuthenticationTokenUtil;
import com.salesmanager.shop.store.facade.authentication.util.CustomerUtil;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("couponCodeFacade")
public class CouponCodeFacadeImpl implements CouponCodeFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(CouponCodeFacadeImpl.class);
  @Inject private CouponCodeService couponCodeService;
  @Inject private CouponCodeMapper couponCodeMapper;
  @Inject private AuthenticationTokenUtil authenticationTokenUtil;
  @Inject private CacheUtil cacheUtil;
  @Inject private CustomerUtil customerUtil;

  @Override
  public PersistableCouponCode create(
      PersistableCouponCode persistableCouponCode,
      MerchantStore merchantStore,
      Long unSetDefaultCouponId) {
    Coupon coupon = null;
    try {
      if (StringUtils.isNotEmpty(persistableCouponCode.getCodeName())) {
        coupon = couponCodeService.getCouponCodeByCode(persistableCouponCode.getCodeName());
      }
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.COUPON_CODE_GET_BY_CODE_FAILURE.getErrorCode(),
          AttributeErrorCodes.COUPON_CODE_GET_BY_CODE_FAILURE.getErrorMessage());
    }
    if (Objects.nonNull(coupon)) {
      LOGGER.error("Coupon code Already exists for {}", persistableCouponCode.getCodeName());
      throw new ResourceDuplicateException(
          "Coupon code Already exists for " + persistableCouponCode.getCodeName());
    }
    coupon = couponCodeMapper.toCoupon(persistableCouponCode);
    if (coupon.isPreApplied() && unSetDefaultCouponId > 0) {
      unSetDefaultCoupon(unSetDefaultCouponId);
    }
    coupon.setCodeName(customerUtil.getReferralCode());

    try {
      coupon = couponCodeService.create(coupon);
      if (coupon.isPreApplied()) {
        cacheUtil.setObjectInCache(HabbitCoreConstant.DEFAULT_COUPON_CODE, coupon);
      }
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.COUPON_CODE_CREATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.COUPON_CODE_CREATE_FAILURE.getErrorMessage());
    }
    persistableCouponCode.setId(coupon.getId());
    persistableCouponCode.setCodeName(coupon.getCodeName());
    return persistableCouponCode;
  }

  @Override
  public PersistableCouponCode update(
      PersistableCouponCode persistableCouponCode,
      MerchantStore merchantStore,
      Long unSetDefaultCouponId) {
    Coupon coupon = couponCodeMapper.toCoupon((persistableCouponCode));
    if (coupon.isPreApplied() && unSetDefaultCouponId > 0) {
      unSetDefaultCoupon(unSetDefaultCouponId);
    }
    try {
      coupon = couponCodeService.update(coupon);
      if (coupon.isPreApplied()) {
        cacheUtil.setObjectInCache(HabbitCoreConstant.DEFAULT_COUPON_CODE, coupon);
      }
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.COUPON_CODE_UPDATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.COUPON_CODE_UPDATE_FAILURE.getErrorMessage());
    }
    return persistableCouponCode;
  }

  @Override
  public void deleteById(Long id) {
    Validate.notNull(id, "CouponCode id cannot be null");
    Coupon coupon = null;
    try {
      coupon = getById(id);
      if (coupon.isPreApplied()) {
        cacheUtil.deleteObjectFromCache(HabbitCoreConstant.DEFAULT_COUPON_CODE);
      }
      couponCodeService.delete(coupon);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.COUPON_CODE_DELETE_FAILURE.getErrorCode(),
          AttributeErrorCodes.COUPON_CODE_DELETE_FAILURE.getErrorMessage());
    }
  }

  @Override
  public PersistableCouponCode getCouponCodeById(
      Long id, MerchantStore merchantStore, Language language) {
    Coupon coupon = getById(id);
    return couponCodeMapper.toPersistableCoupon(coupon);
  }

  @Override
  public PersistableCouponCode getCouponCodeByCode(String codeName) {
    Coupon coupon = getByCouponCode(codeName);
    return couponCodeMapper.toPersistableCoupon(coupon);
  }

  @Override
  public PersistableCouponCodeList getAllCouponCode(
      MerchantStore merchantStore, Language language) {
    PersistableCouponCodeList persistableCouponCodeList = new PersistableCouponCodeList();
    try {
      persistableCouponCodeList.setPersistableCouponCodes(
          Optional.ofNullable(couponCodeService.getAllCouponCodeList())
              .map(Collection::stream)
              .orElse(Stream.empty())
              .map(couponCodeMapper::toPersistableCoupon)
              .collect(Collectors.toList()));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.COUPON_CODE_GET_ALL_FAILURE.getErrorCode(),
          AttributeErrorCodes.COUPON_CODE_GET_ALL_FAILURE.getErrorMessage());
    }
    return persistableCouponCodeList;
  }

  @Override
  public PersistableDiscountTypeList allDiscountType(
      MerchantStore merchantStore, Language language) {
    PersistableDiscountTypeList discountTypeList = new PersistableDiscountTypeList();
    try {
      discountTypeList.setCouponDiscountTypes(Arrays.asList(CouponDiscountType.values()));
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.COUPON_CODE_GET_ALL_DISCOUNT_TYPE_FAILURE.getErrorCode(),
          AttributeErrorCodes.COUPON_CODE_GET_ALL_DISCOUNT_TYPE_FAILURE.getErrorMessage());
    }
    return discountTypeList;
  }

  @Override
  public PersistableCouponCodeTypeList allCouponCodeType(
      MerchantStore merchantStore, Language language) {
    PersistableCouponCodeTypeList couponCodeTypeList = new PersistableCouponCodeTypeList();
    try {
      couponCodeTypeList.setCouponCodeTypes(Arrays.asList(CouponCodeType.values()));
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.COUPON_CODE_GET_ALL_COUPON_CODE_TYPE_FAILURE.getErrorCode(),
          AttributeErrorCodes.COUPON_CODE_GET_ALL_COUPON_CODE_TYPE_FAILURE.getErrorMessage());
    }
    return couponCodeTypeList;
  }

  @Override
  public PersistableCouponCode getDefaultCouponCode(
      MerchantStore merchantStore, Language language) {
    Coupon coupon =
        cacheUtil.getObjectFromCache(HabbitCoreConstant.DEFAULT_COUPON_CODE, Coupon.class);
    return couponCodeMapper.toPersistableCoupon(coupon);
  }

  private Coupon getById(Long id) {
    Coupon coupon = null;
    try {
      coupon = couponCodeService.getById(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.COUPON_CODE_GET_BY_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.COUPON_CODE_GET_BY_ID_FAILURE.getErrorMessage() + id);
    }
    if (Objects.isNull(coupon)) {
      LOGGER.error("Coupon code with id {} not found in DB.", id);
      throw new ResourceNotFoundException("Coupon code with id [" + id + " ] not found");
    }
    return coupon;
  }

  private Coupon getByCouponCode(String codeName) {
    Coupon coupon = null;
    try {
      coupon = couponCodeService.getCouponCodeByCode(codeName);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.COUPON_CODE_GET_BY_CODE_FAILURE.getErrorCode(),
          AttributeErrorCodes.COUPON_CODE_GET_BY_CODE_FAILURE.getErrorMessage() + codeName);
    }
    //    if (Objects.isNull(coupon)) {
    //      LOGGER.error("Coupon code with code {} not found in DB.", codeName);
    //      throw new ResourceNotFoundException("Coupon code with code [" + codeName + " ] not
    // found");
    //    }
    return coupon;
  }

  private void unSetDefaultCoupon(Long id) {
    try {
      Coupon coupon = couponCodeService.getById(id);
      if (Objects.nonNull(coupon)) {
        coupon.setPreApplied(false);
        couponCodeService.update(coupon);
      }
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.COUPON_CODE_UPDATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.COUPON_CODE_UPDATE_FAILURE.getErrorMessage());
    }
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
