package com.salesmanager.core.business.repositories.catalog.attribute;

import com.salesmanager.core.model.catalog.product.Pack;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PackRepository extends JpaRepository<Pack, Long>, PackRepositoryCustom {

  @Query("select p from Pack p where p.id = ?1")
  Optional<Pack> findById(Long packId);

  @Query("select p from Pack p where p.packSizeValue = ?1")
  Optional<Pack> findByPackSize(String packSizeValue);
}
