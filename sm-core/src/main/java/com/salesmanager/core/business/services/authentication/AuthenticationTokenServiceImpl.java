package com.salesmanager.core.business.services.authentication;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.authentication.AuthenticationTokenRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.customer.AuthenticationToken;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("authenticationTokenService")
public class AuthenticationTokenServiceImpl
    extends SalesManagerEntityServiceImpl<Long, AuthenticationToken>
    implements AuthenticationTokenService {
  AuthenticationTokenRepository authenticationTokenRepository;

  @Inject
  public AuthenticationTokenServiceImpl(
      AuthenticationTokenRepository authenticationTokenRepository) {
    super(authenticationTokenRepository);
    this.authenticationTokenRepository = authenticationTokenRepository;
  }

  @Override
  public AuthenticationToken getByPhoneAndCode(String phone, String code) throws ServiceException {
    return authenticationTokenRepository.findByPhoneAndOtp(phone, code).orElse(null);
  }

  @Override
  public AuthenticationToken getByPhoneDevice(String phoneDevice) throws ServiceException {
    return authenticationTokenRepository.findByPhoneDevice(phoneDevice).orElse(null);
  }
}
