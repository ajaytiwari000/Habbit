package com.salesmanager.shop.store.controller.authentication.unicom;

import com.salesmanager.core.model.unicommerce.UnicommerceAuthenticationToken;

public interface UnicommerceAuthenticationTokenFacade {
  UnicommerceAuthenticationToken create(
      UnicommerceAuthenticationToken unicommerceAuthenticationToken);

  UnicommerceAuthenticationToken update(
      UnicommerceAuthenticationToken unicommerceAuthenticationToken);

  UnicommerceAuthenticationToken getActiveToken();
}
