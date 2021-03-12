package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.shoppingcart.Cart;
import com.salesmanager.shop.model.shoppingcart.PersistableCart;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {CartItemMapper.class})
public interface CartMapper {
  Cart toCart(PersistableCart persistableCart);

  PersistableCart toPersistableCart(Cart cart);
}
