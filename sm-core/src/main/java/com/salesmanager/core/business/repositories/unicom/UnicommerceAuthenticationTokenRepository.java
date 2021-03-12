package com.salesmanager.core.business.repositories.unicom;

import com.salesmanager.core.model.unicommerce.UnicommerceAuthenticationToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UnicommerceAuthenticationTokenRepository
    extends JpaRepository<UnicommerceAuthenticationToken, Long> {
  @Query("select auth from UnicommerceAuthenticationToken auth where auth.status = 0") // 0- active
  Optional<UnicommerceAuthenticationToken> getActiveToken();
}
