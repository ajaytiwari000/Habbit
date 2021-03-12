package com.salesmanager.core.business.repositories.shoppingcart;

import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {

  @Query("select i from ShoppingCartItem i left join fetch i.attributes ia where i.id = ?1")
  ShoppingCartItem findOne(Long id);

  @Modifying
  @Query("delete from ShoppingCartItem i where i.id = ?1")
  void deleteById(Long id);
}
