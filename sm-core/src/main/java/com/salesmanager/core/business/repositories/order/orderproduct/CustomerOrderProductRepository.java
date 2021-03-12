package com.salesmanager.core.business.repositories.order.orderproduct;

import com.salesmanager.core.model.order.orderproduct.CustomerOrderProduct;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerOrderProductRepository extends JpaRepository<CustomerOrderProduct, Long> {

  @Query("select cop from CustomerOrderProduct cop where cop.id = ?1")
  Optional<CustomerOrderProduct> findByID(Long id);

  @Query("select cop from CustomerOrderProduct cop where cop.orderProductCode = ?1")
  Optional<CustomerOrderProduct> findByOrderProductCode(String orderProductCode);
}
