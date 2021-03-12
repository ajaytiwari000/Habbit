package com.salesmanager.shop.store.unicom;

import com.salesmanager.core.model.unicommerce.UnicommerceAuthenticationToken;

public interface UnicommerceUtil {
  boolean isRefreshTokenExpired(UnicommerceAuthenticationToken token);

  boolean isAccessTokenExpired(UnicommerceAuthenticationToken token);
}
