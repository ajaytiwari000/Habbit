package com.salesmanager.core.business.repositories.catalog.category;

import com.salesmanager.core.model.catalog.product.NutrientsInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NutrientsInfoRepository
    extends JpaRepository<NutrientsInfo, Long>, NutrientsInfoRepositoryCustom {
  @Query("select distinct n from NutrientsInfo n where n.description= ?1")
  Optional<NutrientsInfo> getByDescription(String description);
}
