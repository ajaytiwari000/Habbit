package com.salesmanager.core.business.repositories.catalog.attribute.image;

import com.salesmanager.core.model.catalog.product.PackIcon;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PackIconRepository extends JpaRepository<PackIcon, Long> {

  @Query("select p from PackIcon p where p.id = ?1")
  Optional<PackIcon> findById(Long packIconId);

  @Query("select p from PackIcon p where p.icon = ?1")
  Optional<PackIcon> findByName(String PackIconImageName);
}
