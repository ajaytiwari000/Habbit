package com.salesmanager.core.business.repositories.customer;

import com.salesmanager.core.model.customer.Address;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerAddressRepository extends JpaRepository<Address, Long> {
  @Query("select distinct ad from Address ad left join fetch ad.customer adcus where adcus.id= ?1")
  Optional<List<Address>> getCustomerAllAddressByCustomerId(Long id);

  @Query("select distinct ad from Address ad where ad.id= ?1")
  Optional<Address> getById(Long id);

  @Query(
      "select distinct ad from Address ad left join fetch ad.customer adcus where adcus.id= ?1 and ad.defaultAddress=1")
  Optional<Address> getCustomerDefaultAddress(Long id);
}
