package com.salesmanager.core.business.repositories.unicomMetaData;

import com.salesmanager.core.model.order.SaleOrderShippingPackageItemCodeMetaData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleOrderShippingPackageItemCodeMetaDataRepository
    extends JpaRepository<SaleOrderShippingPackageItemCodeMetaData, Long> {

  @Query("select b from SaleOrderShippingPackageItemCodeMetaData b")
  Optional<SaleOrderShippingPackageItemCodeMetaData> findById(Long boostId);

  @Query("select b from SaleOrderShippingPackageItemCodeMetaData b ")
  Optional<SaleOrderShippingPackageItemCodeMetaData> findByType(String boostType);
}
