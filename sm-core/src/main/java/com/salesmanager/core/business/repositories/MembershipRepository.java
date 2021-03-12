package com.salesmanager.core.business.repositories;

import com.salesmanager.core.model.common.Membership;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
  @Query("select distinct m from Membership m where m.id= ?1")
  Optional<Membership> getById(Long id);

  @Query("select distinct m from Membership m where m.phoneNumber= ?1")
  Optional<Membership> getMembershipByphone(String phone);
}
