package com.salesmanager.core.business.repositories.order.orderproduct;

import com.salesmanager.core.model.order.orderproduct.CustomerSaleOrderItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerSaleOrderItemRepository
    extends JpaRepository<CustomerSaleOrderItem, Long> {

  @Query("select csoi from CustomerSaleOrderItem csoi where csoi.id = ?1")
  Optional<CustomerSaleOrderItem> findByID(Long id);

  @Query("select csoi from CustomerSaleOrderItem csoi where csoi.channelSaleOrderItemCode = ?1")
  Optional<CustomerSaleOrderItem> findByOrderTemCode(String channelSaleOrderItemCode);
}
