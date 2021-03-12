package com.salesmanager.core.business.repositories.referralcode;

import com.salesmanager.core.model.common.ReferralCode;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReferralCodeRepository extends JpaRepository<ReferralCode, Long> {
  @Query("select distinct rc from ReferralCode rc where rc.id= ?1")
  Optional<ReferralCode> getById(Long id);

  @Query("select distinct rc from ReferralCode rc where rc.codeName= ?1")
  Optional<ReferralCode> getReferralCodeByCode(String referralCode);

  @Query("select distinct rc from ReferralCode rc")
  Optional<List<ReferralCode>> getAllReferralCode();

  @Query("select distinct rc from ReferralCode rc where rc.phoneNumber= ?1")
  Optional<ReferralCode> getReferralCodeByPhone(String phoneNumber);
}
