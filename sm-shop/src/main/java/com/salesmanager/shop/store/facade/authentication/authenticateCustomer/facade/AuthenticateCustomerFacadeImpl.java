package com.salesmanager.shop.store.facade.authentication.authenticateCustomer.facade;

import com.salesmanager.core.business.HabbitCoreConstant;
import com.salesmanager.core.model.customer.AuthenticationToken;
import com.salesmanager.core.model.customer.AuthenticationTokenStatus;
import com.salesmanager.shop.admin.security.SecurityDataAccessException;
import com.salesmanager.shop.cache.util.CacheUtil;
import com.salesmanager.shop.error.codes.CustomerErrorCodes;
import com.salesmanager.shop.error.codes.OtpErrorCodes;
import com.salesmanager.shop.model.customer.PersistableCustomer;
import com.salesmanager.shop.model.security.AuthenticationRequest;
import com.salesmanager.shop.model.security.AuthenticationResponse;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.authentication.authenticatecustomer.facade.AuthenticateCustomerFacade;
import com.salesmanager.shop.store.controller.authentication.authenticationToken.facade.AuthenticationTokenFacade;
import com.salesmanager.shop.store.facade.customer.CustomerFacade;
import com.salesmanager.shop.store.security.JWTTokenUtil;
import com.salesmanager.shop.store.security.common.CustomAuthenticationException;
import com.salesmanager.shop.store.security.user.JWTUser;
import java.util.Objects;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.auth.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("authenticateCustomerFacade")
public class AuthenticateCustomerFacadeImpl implements AuthenticateCustomerFacade {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(AuthenticateCustomerFacadeImpl.class);

  @Inject private JWTTokenUtil jwtTokenUtil;
  @Inject private CustomerFacade customerFacade;
  @Inject private CacheUtil cacheUtil;
  @Inject private UserDetailsService jwtCustomerDetailsService;
  @Inject private PasswordEncoder deviceEncoder;
  @Autowired private AuthenticationTokenFacade authenticationTokenFacade;

  /**
   * Please check {@link com.salesmanager.shop.store.security.AbstractCustomerServices} for
   * loadUserByUsername API [JWTCustomerServicesImpl{jwtCustomerDetailsService} extends
   * AbstractCustomerServices]
   *
   * @param authenticationRequest
   * @param requestSource
   * @param deviceId
   * @return
   * @throws AuthenticationException
   */
  @Override
  public AuthenticationResponse authenticate(
      AuthenticationRequest authenticationRequest, String requestSource, String deviceId) {
    AuthenticationToken token =
        cacheUtil.getObjectFromCache(
            authenticationRequest.getUsername() + HabbitCoreConstant.DELIMITER + deviceId,
            AuthenticationToken.class);
    if (token == null) {
      LOGGER.info("token", token);
    } else {
      LOGGER.info("token code {}", token.getCode());
    }
    if (Objects.isNull(token) || !token.getCode().equals(authenticationRequest.getPassword())) {
      throw new CustomAuthenticationException(
          OtpErrorCodes.OTP_INVALID.getErrorCode(),
          OtpErrorCodes.OTP_INVALID.getErrorMessage() + authenticationRequest.getPassword());
    }
    if (AuthenticationTokenStatus.USED.equals(token.getStatus())) {
      throw new CustomAuthenticationException(
          OtpErrorCodes.OTP_ALREADY_USED.getErrorCode(),
          OtpErrorCodes.OTP_ALREADY_USED.getErrorMessage() + authenticationRequest.getPassword());
    }
    BCryptPasswordEncoder deviceCode = (BCryptPasswordEncoder) deviceEncoder;
    if (!deviceCode.matches(deviceId, token.getDeviceId())) {
      throw new CustomAuthenticationException(
          OtpErrorCodes.OTP_DEVICE_ID_MISMATCH.getErrorCode(),
          OtpErrorCodes.OTP_DEVICE_ID_MISMATCH.getErrorMessage() + deviceId);
    }
    token.setStatus(AuthenticationTokenStatus.USED);
    try {
      cacheUtil.setObjectInCache(
          authenticationRequest.getUsername() + HabbitCoreConstant.DELIMITER + deviceId,
          authenticationTokenFacade.update(token));
    } catch (ServiceRuntimeException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_AUTHENTICATION_UPDATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_AUTHENTICATION_UPDATE_FAILURE.getErrorMessage()
              + authenticationRequest.getUsername());
    }
    JWTUser userDetails = null;
    try {
      userDetails =
          (JWTUser)
              jwtCustomerDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    } catch (SecurityDataAccessException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_CREATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_CREATE_FAILURE.getErrorMessage()
              + authenticationRequest.getUsername());
    }
    final String jwtToken = jwtTokenUtil.generateToken(userDetails, requestSource);
    AuthenticationResponse authenticationResponse = new AuthenticationResponse(null, jwtToken);
    PersistableCustomer customer =
        customerFacade.getCustomerByUserName(authenticationRequest.getUsername());
    authenticationResponse.setCustomer(customer);
    boolean isNewUser = StringUtils.isEmpty((customer.getFirstName())) ? true : false;
    authenticationResponse.setNewUser(isNewUser);
    return authenticationResponse;
  }

  @Override
  public void sendOtp(String phone, boolean reSendFlag, String deviceId) {
    if (reSendFlag) {
      authenticationTokenFacade.reSendOtp(phone, deviceId);
    } else {
      authenticationTokenFacade.sendOtp(phone, deviceId);
    }
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
