package com.salesmanager.core.business.repositories.customer.checkoutAudit;

import com.salesmanager.core.model.order.CheckoutAuditFlow;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CheckoutAuditFlowRepository extends JpaRepository<CheckoutAuditFlow, Long> {
  @Query("select caf from CheckoutAuditFlow caf where caf.orderCode= ?1")
  Optional<CheckoutAuditFlow> getByOrderCode(String orderCode);
}
