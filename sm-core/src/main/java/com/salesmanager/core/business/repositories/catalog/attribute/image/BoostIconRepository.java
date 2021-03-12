package com.salesmanager.core.business.repositories.catalog.attribute.image;

import com.salesmanager.core.model.catalog.product.BoostIcon;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoostIconRepository extends JpaRepository<BoostIcon, Long> {

  @Query("select b from BoostIcon b where b.id = ?1")
  Optional<BoostIcon> findById(Long boostId);

  @Query("select b from BoostIcon b where b.icon = ?1")
  Optional<BoostIcon> findByName(String boostIconImageName);
}
