package com.salesmanager.core.business.repositories.payments.gateway.razorpay;

import com.salesmanager.core.model.order.CustomerRazorPayOrder;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRazorPayOrderRepository
    extends JpaRepository<CustomerRazorPayOrder, Long> {

  @Query("select distinct crpo from CustomerRazorPayOrder crpo where crpo.id= ?1")
  Optional<CustomerRazorPayOrder> findById(Long id);

  @Query("select distinct crpo from CustomerRazorPayOrder crpo where crpo.orderCode= ?1")
  Optional<CustomerRazorPayOrder> findByRazorPayOrderCode(String orderCode);

  @Query("select distinct crpo from CustomerRazorPayOrder crpo where crpo.receipt= ?1")
  Optional<CustomerRazorPayOrder> findByReceipt(String receipt);
}
