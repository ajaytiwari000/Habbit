package com.salesmanager.core.business.repositories.order.sale;

import com.salesmanager.core.model.order.SaleOrderShipperDetails;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleOrderShipperDetailsRepository
    extends JpaRepository<SaleOrderShipperDetails, Long> {

  @Query("select cso from SaleOrderShipperDetails cso ")
  Optional<SaleOrderShipperDetails> findByID(Long id);

  @Query("select cso from SaleOrderShipperDetails cso")
  Optional<SaleOrderShipperDetails> findByOrderCode(String code);
}
