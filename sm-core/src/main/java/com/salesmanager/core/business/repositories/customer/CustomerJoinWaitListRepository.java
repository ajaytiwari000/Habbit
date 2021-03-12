package com.salesmanager.core.business.repositories.customer;

import com.salesmanager.core.model.customer.CustomerJoinWaitList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerJoinWaitListRepository extends JpaRepository<CustomerJoinWaitList, Long> {

  @Query("select distinct cjwl from CustomerJoinWaitList cjwl where cjwl.phone = ?1")
  Optional<CustomerJoinWaitList> findByPhone(String phone);
}
