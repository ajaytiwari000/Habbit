/** */
package com.salesmanager.shop.store.facade.cart;

import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shoppingcart.Cart;
import com.salesmanager.core.model.shoppingcart.CartItem;
import com.salesmanager.shop.model.shoppingcart.PersistableCart;
import com.salesmanager.shop.model.shoppingcart.PersistableCartItem;
import com.salesmanager.shop.store.facade.cart.enums.ValidateCartEvent;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;

public interface CartFacade {

  PersistableCart getCustomerCart(HttpServletRequest request);

  Cart getCartByPhone(String phone);

  PersistableCart createCustomerCartPostSignup(String phone);

  PersistableCart updateCartPostCouponValidation(PersistableCart cart, HttpServletRequest request)
      throws ValidationException;

  PersistableCart updateCart(PersistableCart cart, HttpServletRequest request)
      throws ValidationException;

  Cart update(Cart cartModel);

  int getEarnPoint(TierType tierType, Long amount);

  boolean validationCartItem(
      PersistableCartItem persistableCartItem,
      HttpServletRequest request,
      MerchantStore merchantStore,
      String phone)
      throws Exception;

  PersistableCart validateCart(
      ValidateCartEvent validateCartEvent,
      PersistableCart persistableCart,
      HttpServletRequest request,
      MerchantStore merchantStore)
      throws Exception;

  PersistableCart validationCart(
      PersistableCart persistableCart, HttpServletRequest request, MerchantStore merchantStore)
      throws Exception;

  void deleteCartItem(CartItem cartItem);

  void deleteAllCartItem(CartItem cartItem);

  String freeCart(HttpServletRequest request, MerchantStore merchantStore);

  void freeCartByPhone(String phone);
}
