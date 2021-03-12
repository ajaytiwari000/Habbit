package com.salesmanager.shop.store.controller.authentication.authenticatecustomer.facade;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.shop.model.security.AuthenticationRequest;
import com.salesmanager.shop.model.security.AuthenticationResponse;
import org.apache.http.auth.AuthenticationException;

public interface AuthenticateCustomerFacade {

  AuthenticationResponse authenticate(
      AuthenticationRequest request, String requestSource, String deviceId)
      throws AuthenticationException, ServiceException;

  void sendOtp(String phone, boolean reSendFlag, String deviceId) throws ServiceException;
}
