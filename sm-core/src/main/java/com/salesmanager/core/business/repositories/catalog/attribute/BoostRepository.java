package com.salesmanager.core.business.repositories.catalog.attribute;

import com.salesmanager.core.model.catalog.product.Boost;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoostRepository extends JpaRepository<Boost, Long>, BoostRepositoryCustom {

  @Query("select b from Boost b where b.id = ?1")
  Optional<Boost> findById(Long boostId);

  @Query("select b from Boost b where b.type = ?1")
  Optional<Boost> findByType(String boostType);
}
