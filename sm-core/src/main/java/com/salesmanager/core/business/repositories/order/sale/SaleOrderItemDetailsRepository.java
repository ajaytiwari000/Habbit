package com.salesmanager.core.business.repositories.order.sale;

import com.salesmanager.core.model.order.SaleOrderItemDetails;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleOrderItemDetailsRepository extends JpaRepository<SaleOrderItemDetails, Long> {

  @Query("select cso from SaleOrderItemDetails cso ")
  Optional<SaleOrderItemDetails> findByID(Long id);

  @Query("select cso from SaleOrderItemDetails cso")
  Optional<SaleOrderItemDetails> findByOrderCode(String code);
}
