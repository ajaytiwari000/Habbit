package com.salesmanager.core.business.repositories.unicomMetaData;

import com.salesmanager.core.model.order.SaleOrderMetaData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleOrderMetaDataRepository extends JpaRepository<SaleOrderMetaData, Long> {

  @Query("select b from SaleOrderMetaData b")
  Optional<SaleOrderMetaData> findById(Long boostId);

  @Query("select b from SaleOrderMetaData b ")
  Optional<SaleOrderMetaData> findByType(String boostType);
}
