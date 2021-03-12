package com.salesmanager.core.business.repositories.unicomMetaData;

import com.salesmanager.core.model.order.SaleOrderItemsMetaData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleOrderItemsMetaDataRepository
    extends JpaRepository<SaleOrderItemsMetaData, Long> {

  @Query("select b from SaleOrderItemsMetaData b")
  Optional<SaleOrderItemsMetaData> findById(Long boostId);

  @Query("select b from SaleOrderItemsMetaData b ")
  Optional<SaleOrderItemsMetaData> findByType(String boostType);
}
