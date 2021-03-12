package com.salesmanager.shop.store.facade.authentication.authenticationToken.facade;

import com.salesmanager.core.business.HabbitCoreConstant;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.sms.constants.SMSType;
import com.salesmanager.core.business.modules.sms.model.Sms;
import com.salesmanager.core.business.services.authentication.AuthenticationTokenService;
import com.salesmanager.core.business.services.system.SmsService;
import com.salesmanager.core.model.customer.AuthenticationToken;
import com.salesmanager.core.model.customer.AuthenticationTokenStatus;
import com.salesmanager.shop.cache.util.CacheUtil;
import com.salesmanager.shop.error.codes.CustomerErrorCodes;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.authentication.authenticationToken.facade.AuthenticationTokenFacade;
import com.salesmanager.shop.store.facade.authentication.util.AuthenticationTokenUtil;
import com.salesmanager.shop.utils.DateUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("authenticationTokenFacade")
public class AuthenticationTokenFacadeImpl implements AuthenticationTokenFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenFacadeImpl.class);

  @Inject private AuthenticationTokenService authenticationTokenService;
  @Inject private SmsService smsService;
  @Inject private AuthenticationTokenUtil authenticationTokenUtil;
  @Inject private CacheUtil cacheUtil;
  @Autowired private AuthenticationTokenFacade authenticationTokenFacade;
  @Inject private PasswordEncoder deviceEncoder;

  @Override
  public AuthenticationToken create(AuthenticationToken authenticationToken) {
    try {
      AuthenticationToken authenticationTokenExist =
          authenticationTokenService.getByPhoneDevice(authenticationToken.getPhoneDevice());
      if (Objects.nonNull(authenticationTokenExist)) {
        LOGGER.info(
            "AuthenticationToken found for customer {}", authenticationTokenExist.getPhone());
        authenticationTokenExist.setCode(authenticationToken.getCode());
        authenticationTokenExist.setStatus(authenticationToken.getStatus());
        authenticationTokenExist.setExpirationTime(authenticationToken.getExpirationTime());
        return update(authenticationTokenExist);
      }
      authenticationToken = authenticationTokenService.create(authenticationToken);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_AUTHENTICATION_CREATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_AUTHENTICATION_CREATE_FAILURE.getErrorMessage()
              + authenticationToken.getPhone());
    }
    return authenticationToken;
  }

  @Override
  public AuthenticationToken update(AuthenticationToken authenticationToken) {
    try {
      authenticationToken = authenticationTokenService.update(authenticationToken);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_AUTHENTICATION_UPDATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_AUTHENTICATION_UPDATE_FAILURE.getErrorMessage()
              + authenticationToken.getPhone());
    }
    return authenticationToken;
  }

  @Override
  public AuthenticationToken getByPhoneAndCode(String phone, String code) {
    AuthenticationToken authenticationToken = null;
    try {
      authenticationToken = authenticationTokenService.getByPhoneAndCode(phone, code);
      if (authenticationToken == null) {
        LOGGER.info("AuthenticationToken not found for customer {}", phone);
      }
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_AUTHENTICATION_GET_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_AUTHENTICATION_GET_FAILURE.getErrorMessage() + phone);
    }
    return authenticationToken;
  }

  private void sendOtpUsingThirdParty(String phone, String code) {
    Map<String, Object> model = new HashMap();
    model.put("otp", code);
    try {
      smsService.sendSms(new Sms(phone, SMSType.SEND_OTP, model));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.SEND_OTP_SMS_FAILURE_THROUGH_KARIX.getErrorCode(),
          CustomerErrorCodes.SEND_OTP_SMS_FAILURE_THROUGH_KARIX.getErrorMessage() + phone);
    }
  }

  @Override
  public void reSendOtp(String phone, String deviceId) {
    try {
      AuthenticationToken authenticationTokenExist =
          cacheUtil.getObjectFromCache(
              phone + HabbitCoreConstant.DELIMITER + deviceId, AuthenticationToken.class);
      LOGGER.info("redis authentication object from redis is {}", authenticationTokenExist);
      if (Objects.nonNull(authenticationTokenExist)
          && authenticationTokenUtil.isNotExpired(authenticationTokenExist.getExpirationTime())
          && authenticationTokenUtil.isActive(authenticationTokenExist.getStatus())) {
        LOGGER.info("redis authentication object {}", authenticationTokenExist);
        sendOtpUsingThirdParty(phone, authenticationTokenExist.getCode());
      } else {
        LOGGER.info("redis authentication object is null");
        authenticationTokenExist = new AuthenticationToken();
        authenticationTokenExist.setPhone(phone);
        authenticationTokenExist.setExpirationTime(
            authenticationTokenUtil.calculateExpiryTime(DateUtil.getDate()));
        authenticationTokenExist.setCode(authenticationTokenUtil.generateCode());
        authenticationTokenExist.setStatus(AuthenticationTokenStatus.ACTIVE);
        authenticationTokenExist.setDeviceId(deviceEncoder.encode(deviceId));
        authenticationTokenExist.setPhoneDevice(phone + HabbitCoreConstant.DELIMITER + deviceId);
        authenticationTokenExist = authenticationTokenFacade.create(authenticationTokenExist);
        sendOtpUsingThirdParty(phone, authenticationTokenExist.getCode());
        cacheUtil.setObjectInCache(
            phone + HabbitCoreConstant.DELIMITER + deviceId,
            authenticationTokenExist,
            authenticationTokenUtil.calculateExpiryTime(DateUtil.getDate()));
      }
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_AUTHENTICATION_RESEND_OTP_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_AUTHENTICATION_RESEND_OTP_FAILURE.getErrorMessage() + phone);
    }
  }

  @Override
  public void sendOtp(String phone, String deviceId) {
    AuthenticationToken authenticationToken =
        new AuthenticationToken(
            authenticationTokenUtil.calculateExpiryTime(DateUtil.getDate()),
            phone,
            authenticationTokenUtil.generateCode(),
            AuthenticationTokenStatus.ACTIVE,
            deviceEncoder.encode(deviceId),
            phone + HabbitCoreConstant.DELIMITER + deviceId);
    try {
      AuthenticationToken authenticationTokenExist =
          authenticationTokenService.getByPhoneDevice(
              phone + HabbitCoreConstant.DELIMITER + deviceId);
      if (Objects.nonNull(authenticationTokenExist)) {
        authenticationTokenExist.setStatus(authenticationToken.getStatus());
        authenticationTokenExist.setCode(authenticationToken.getCode());
        authenticationTokenExist.setExpirationTime(authenticationToken.getExpirationTime());
        authenticationToken = authenticationTokenService.update(authenticationTokenExist);
      } else {
        authenticationToken = authenticationTokenService.create(authenticationToken);
      }

      cacheUtil.setObjectInCache(
          authenticationToken.getPhone() + HabbitCoreConstant.DELIMITER + deviceId,
          authenticationToken,
          authenticationTokenUtil.calculateExpiryTime(DateUtil.getDate()));
      sendOtpUsingThirdParty(phone, authenticationToken.getCode());
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_AUTHENTICATION_SEND_OTP_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_AUTHENTICATION_SEND_OTP_FAILURE.getErrorMessage()
              + authenticationToken.getPhone());
    }
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
