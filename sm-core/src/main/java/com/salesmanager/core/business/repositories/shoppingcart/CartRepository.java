package com.salesmanager.core.business.repositories.shoppingcart;

import com.salesmanager.core.model.shoppingcart.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {
  @Query("select c from Cart c left join fetch c.cartItems ci where c.customerPhone = ?1 ")
  Optional<Cart> getByCustomerPhone(String customerPhone);

  @Query("select c from Cart c left join fetch c.cartItems ci where c.id = ?1 ")
  Optional<Cart> findById(Long carId);
}
