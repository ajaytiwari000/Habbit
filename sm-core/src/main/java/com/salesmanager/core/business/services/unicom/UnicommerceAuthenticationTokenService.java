package com.salesmanager.core.business.services.unicom;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.unicommerce.UnicommerceAuthenticationToken;

public interface UnicommerceAuthenticationTokenService
    extends SalesManagerEntityService<Long, UnicommerceAuthenticationToken> {
  UnicommerceAuthenticationToken getActiveToken();
}
