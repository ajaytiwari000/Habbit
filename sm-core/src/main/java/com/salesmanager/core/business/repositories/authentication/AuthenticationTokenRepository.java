package com.salesmanager.core.business.repositories.authentication;

import com.salesmanager.core.model.customer.AuthenticationToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationToken, Long> {
  @Query("select auth from AuthenticationToken auth where auth.phone =?1 and auth.code=?2")
  Optional<AuthenticationToken> findByPhoneAndOtp(String phone, String code);

  @Query("select auth from AuthenticationToken auth where auth.phoneDevice =?1")
  Optional<AuthenticationToken> findByPhoneDevice(String phoneDevice);
}
