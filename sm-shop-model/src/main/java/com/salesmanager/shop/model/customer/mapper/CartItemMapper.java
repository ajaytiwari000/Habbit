package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.shoppingcart.CartItem;
import com.salesmanager.shop.mapper.product.BoostMapper;
import com.salesmanager.shop.mapper.product.FlavourMapper;
import com.salesmanager.shop.mapper.product.PackMapper;
import com.salesmanager.shop.model.shoppingcart.PersistableCartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
    componentModel = "spring",
    uses = {FlavourMapper.class, PackMapper.class, BoostMapper.class})
public interface CartItemMapper {
  @Mapping(source = "cartItemId", target = "id", qualifiedByName = "cartItemIdToId")
  CartItem toCartItem(PersistableCartItem persistableCartItem);

  @Named("cartItemIdToId")
  static Long cartItemIdToId(Long cartItemId) {
    return cartItemId;
  }

  @Mapping(source = "id", target = "cartItemId", qualifiedByName = "idToCartItem")
  PersistableCartItem toPersistableCartItem(CartItem cartItem);

  @Named("idToCartItem")
  static Long idToCartItem(Long id) {
    return id;
  }
}
