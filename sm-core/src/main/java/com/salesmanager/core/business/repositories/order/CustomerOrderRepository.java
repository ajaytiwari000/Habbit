package com.salesmanager.core.business.repositories.order;

import com.salesmanager.core.model.order.CustomerOrder;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

  @Query("select co from CustomerOrder co where co.id = ?1")
  Optional<CustomerOrder> findByID(Long id);

  @Query("select co from CustomerOrder co where co.orderCode = ?1")
  Optional<CustomerOrder> findByOrderCode(String orderCode);
}
