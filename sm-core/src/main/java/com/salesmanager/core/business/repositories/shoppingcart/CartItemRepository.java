package com.salesmanager.core.business.repositories.shoppingcart;

import com.salesmanager.core.model.shoppingcart.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

  @Query("select i from CartItem i where i.id = ?1")
  Optional<CartItem> findById(Long id);

  @Modifying
  @Query("delete from CartItem i where i.id = ?1")
  void deleteById(Long id);

  // need to change the qurey
  @Modifying
  @Query("delete from CartItem i where i.id= ?1")
  void deleteAllByCartId(Long id);
}
