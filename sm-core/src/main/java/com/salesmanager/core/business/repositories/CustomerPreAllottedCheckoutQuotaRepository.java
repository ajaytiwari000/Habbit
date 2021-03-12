package com.salesmanager.core.business.repositories;

import com.salesmanager.core.model.shoppingcart.CustomerPreAllottedCheckoutQuota;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerPreAllottedCheckoutQuotaRepository
    extends JpaRepository<CustomerPreAllottedCheckoutQuota, Long> {
  @Query("select distinct cpacq from CustomerPreAllottedCheckoutQuota cpacq where cpacq.id= ?1")
  Optional<CustomerPreAllottedCheckoutQuota> getById(Long id);

  @Query(
      "select distinct cpacq from CustomerPreAllottedCheckoutQuota cpacq where cpacq.cartItemCode= ?1 and cpacq.customerPhone= ?2")
  Optional<CustomerPreAllottedCheckoutQuota> getCustomerPreAllottedCheckoutQuotaByCartItemCode(
      String cartItemCode, String phone);
}
