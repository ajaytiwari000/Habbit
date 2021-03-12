package com.salesmanager.core.business.repositories.shoppingcart;

import com.salesmanager.core.model.shoppingcart.ShoppingCartAttributeItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartAttributeRepository
    extends JpaRepository<ShoppingCartAttributeItem, Long> {}
