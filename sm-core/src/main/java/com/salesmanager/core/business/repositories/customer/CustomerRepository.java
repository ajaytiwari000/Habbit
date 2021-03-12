package com.salesmanager.core.business.repositories.customer;

import com.salesmanager.core.model.customer.Customer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository
    extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {

  // TODO : will clean up this
  @Query(
      "select c from Customer c join fetch c.merchantStore cm left join fetch c.defaultLanguage cl left join fetch c.attributes ca left join fetch ca.customerOption cao left join fetch ca.customerOptionValue cav left join fetch cao.descriptions caod left join fetch cav.descriptions left join fetch c.groups where c.id = ?1")
  Customer findOne(Long id);

  // TODO : will clean up this
  @Query(
      "select distinct c from Customer c join fetch c.merchantStore cm left join fetch c.defaultLanguage cl left join fetch c.attributes ca left join fetch ca.customerOption cao left join fetch ca.customerOptionValue cav left join fetch cao.descriptions caod left join fetch cav.descriptions left join fetch c.groups ") // where c.billing.firstName = ?1
  List<Customer> findByName(String name);

  @Query("select c from Customer c left join fetch c.groups  where c.userName = ?1")
  Optional<Customer> getByUserName(String userName);

  @Query("select distinct c from Customer c where c.id = ?1")
  Optional<Customer> findById(Long id);

  // TODO : will clean up this
  @Query(
      "select c from Customer c join fetch c.merchantStore cm left join fetch c.defaultLanguage cl left join fetch c.attributes ca left join fetch ca.customerOption cao left join fetch ca.customerOptionValue cav left join fetch cao.descriptions caod left join fetch cav.descriptions  left join fetch c.groups  where c.userName = ?1 and cm.id = ?2")
  Customer findByNick(String nick, int storeId);

  // TODO : will clean up this
  @Query(
      "select distinct c from Customer c join fetch c.merchantStore cm left join fetch c.defaultLanguage cl left join fetch c.attributes ca left join fetch ca.customerOption cao left join fetch ca.customerOptionValue cav left join fetch cao.descriptions caod left join fetch cav.descriptions left join fetch c.groups  where cm.id = ?1")
  List<Customer> findByStore(int storeId);
}
