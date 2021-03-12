package com.salesmanager.core.business.repositories.payments.paytm;

import com.salesmanager.core.model.order.PaytmOrder;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaytmOrderRepository extends JpaRepository<PaytmOrder, Long> {

  @Query("select distinct po from PaytmOrder po where po.id= ?1")
  Optional<PaytmOrder> findById(Long id);

  @Query("select distinct po from PaytmOrder po where po.orderCode= ?1")
  Optional<PaytmOrder> findByPaytmOrderCode(String orderCode);
}
