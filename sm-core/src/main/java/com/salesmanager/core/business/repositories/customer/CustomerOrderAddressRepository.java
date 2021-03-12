package com.salesmanager.core.business.repositories.customer;

import com.salesmanager.core.model.customer.CustomerOrderAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderAddressRepository extends JpaRepository<CustomerOrderAddress, Long> {}
