package com.salesmanager.core.business.repositories.catalog.attribute;

import com.salesmanager.core.model.catalog.product.Flavour;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FlavourRepository extends JpaRepository<Flavour, Long>, FlavourRepositoryCustom {

  @Query("select f from Flavour f where f.id = ?1")
  Optional<Flavour> findById(Long flavourId);

  @Query("select f from Flavour f where f.name = ?1")
  Optional<Flavour> findByName(String flavourName);
}
