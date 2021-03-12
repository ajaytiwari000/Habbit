package com.salesmanager.core.business.repositories.membershipcolor;

import com.salesmanager.core.model.catalog.product.MembershipColor;
import com.salesmanager.core.model.common.enumerator.TierType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MembershipColorRepository extends JpaRepository<MembershipColor, Long> {

  @Query("select mc from MembershipColor mc where mc.id = ?1")
  Optional<MembershipColor> findById(Long MembershipColorId);

  @Query("select mc from MembershipColor mc ")
  List<MembershipColor> findAll();

  @Query("select mc from MembershipColor mc where mc.tierType = ?1")
  Optional<MembershipColor> getByTierType(TierType tierTypeName);
}
