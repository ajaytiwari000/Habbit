package com.salesmanager.core.business.repositories.unicomMetaData;

import com.salesmanager.core.model.order.SaleOrderShippingPackageMetaData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleOrderShippingPackageMetaDataRepository
    extends JpaRepository<SaleOrderShippingPackageMetaData, Long> {

  @Query("select b from SaleOrderShippingPackageMetaData b")
  Optional<SaleOrderShippingPackageMetaData> findById(Long boostId);

  @Query("select b from SaleOrderShippingPackageMetaData b ")
  Optional<SaleOrderShippingPackageMetaData> findByType(String boostType);
}
