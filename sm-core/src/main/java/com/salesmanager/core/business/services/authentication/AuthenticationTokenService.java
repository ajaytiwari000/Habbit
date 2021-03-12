package com.salesmanager.core.business.services.authentication;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.customer.AuthenticationToken;

public interface AuthenticationTokenService
    extends SalesManagerEntityService<Long, AuthenticationToken> {

  AuthenticationToken getByPhoneAndCode(String phone, String code) throws ServiceException;

  AuthenticationToken getByPhoneDevice(String phoneDevice) throws ServiceException;
}
