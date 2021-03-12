package com.salesmanager.shop.store.controller.authentication.authenticationToken.facade;

import com.salesmanager.core.model.customer.AuthenticationToken;

public interface AuthenticationTokenFacade {

  AuthenticationToken create(AuthenticationToken authenticationToken);

  AuthenticationToken update(AuthenticationToken authenticationToken);

  AuthenticationToken getByPhoneAndCode(String phone, String code);

  void reSendOtp(String phone, String deviceId);

  void sendOtp(String phone, String deviceId);
}
