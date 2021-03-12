package com.salesmanager.core.business.repositories.order;

import com.salesmanager.core.model.order.CustomerSaleOrder;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerSaleOrderRepository extends JpaRepository<CustomerSaleOrder, Long> {

  @Query("select cso from CustomerSaleOrder cso where cso.id = ?1")
  Optional<CustomerSaleOrder> findByID(Long id);

  @Query("select cso from CustomerSaleOrder cso where cso.code = ?1")
  Optional<CustomerSaleOrder> getByCode(String code);
}
