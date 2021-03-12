package com.salesmanager.core.business.services.unicom;

import com.salesmanager.core.business.repositories.unicom.UnicommerceAuthenticationTokenRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.unicommerce.UnicommerceAuthenticationToken;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("unicommerceAuthenticationTokenService")
public class UnicommerceAuthenticationTokenServiceImpl
    extends SalesManagerEntityServiceImpl<Long, UnicommerceAuthenticationToken>
    implements UnicommerceAuthenticationTokenService {
  UnicommerceAuthenticationTokenRepository repository;

  @Inject
  public UnicommerceAuthenticationTokenServiceImpl(
      UnicommerceAuthenticationTokenRepository tokenRepository) {
    super(tokenRepository);
    this.repository = tokenRepository;
  }

  @Override
  public UnicommerceAuthenticationToken getActiveToken() {
    return repository.getActiveToken().orElse(null);
  }
}
