package com.salesmanager.core.business.repositories.order;

import com.salesmanager.core.model.order.orderstatus.CustomerOrderStatusHistory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerOrderStatusHistoryRepository
    extends JpaRepository<CustomerOrderStatusHistory, Long> {

  @Query("select cosh from CustomerOrderStatusHistory cosh where cosh.id = ?1")
  Optional<CustomerOrderStatusHistory> findById(Long id);

  @Query("select cosh from CustomerOrderStatusHistory cosh where cosh.orderCode = ?1")
  Optional<CustomerOrderStatusHistory> findByOrderCode(String orderCode);

  @Query(
      "select cosh from CustomerOrderStatusHistory cosh left join fetch cosh.customer coshc where coshc.id = ?1 and cosh.status<>'CREATED'")
  Optional<List<CustomerOrderStatusHistory>> findAllOrderByCustomerId(Long id);
}
