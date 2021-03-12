package com.salesmanager.shop.store.facade.authentication.unicom;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.unicom.UnicommerceAuthenticationTokenService;
import com.salesmanager.core.model.customer.AuthenticationTokenStatus;
import com.salesmanager.core.model.unicommerce.UnicommerceAuthenticationToken;
import com.salesmanager.shop.error.codes.UnicommerceErrorCodes;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.authentication.unicom.UnicommerceAuthenticationTokenFacade;
import java.util.Objects;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("unicommerceAuthenticationTokenFacade")
public class UnicommerceAuthenticationTokenFacadeImpl
    implements UnicommerceAuthenticationTokenFacade {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(UnicommerceAuthenticationTokenFacadeImpl.class);

  @Inject private UnicommerceAuthenticationTokenService unicommerceAuthenticationTokenService;

  @Override
  public UnicommerceAuthenticationToken create(
      UnicommerceAuthenticationToken unicommerceAuthenticationToken) {
    UnicommerceAuthenticationToken token = getActiveToken();
    if (Objects.nonNull(token)) {
      LOGGER.info("Active UnicommerceAuthenticationToken is found there");
      token.setStatus(AuthenticationTokenStatus.EXPIRED);
      update(token);
    }
    try {
      unicommerceAuthenticationToken =
          unicommerceAuthenticationTokenService.create(unicommerceAuthenticationToken);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          UnicommerceErrorCodes.CREATE_UNICOM_AUTH_TOKEN.getErrorCode(),
          UnicommerceErrorCodes.CREATE_UNICOM_AUTH_TOKEN.getErrorMessage());
    }
    return unicommerceAuthenticationToken;
  }

  @Override
  public UnicommerceAuthenticationToken update(
      UnicommerceAuthenticationToken unicommerceAuthenticationToken) {
    UnicommerceAuthenticationToken token = getActiveToken();
    if (Objects.isNull(token)) {
      LOGGER.info("Active Unicommerce Authentication Token is not found in db");
      throw new ResourceNotFoundException(
          "Active Unicommerce Authentication Token is not found in db");
    }
    udpateToken(token, unicommerceAuthenticationToken);
    try {
      token = unicommerceAuthenticationTokenService.update(token);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          UnicommerceErrorCodes.UPDATE_UNICOM_AUTH_TOKEN.getErrorCode(),
          UnicommerceErrorCodes.UPDATE_UNICOM_AUTH_TOKEN.getErrorMessage());
    }
    return token;
  }

  private void udpateToken(
      UnicommerceAuthenticationToken target, UnicommerceAuthenticationToken source) {
    target.setAuthToken(source.getAuthToken());
    target.setTokenType(source.getTokenType());
    target.setRefreshToken(source.getRefreshToken());
    target.setExpiresIn(source.getExpiresIn());
    target.setScope(source.getScope());
    target.setStatus(source.getStatus());
  }

  @Override
  public UnicommerceAuthenticationToken getActiveToken() {
    UnicommerceAuthenticationToken token = null;
    try {
      token = unicommerceAuthenticationTokenService.getActiveToken();
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          UnicommerceErrorCodes.GET_ACTIVE_UNICOM_AUTH_TOKEN.getErrorCode(),
          UnicommerceErrorCodes.GET_ACTIVE_UNICOM_AUTH_TOKEN.getErrorMessage());
    }
    return token;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
