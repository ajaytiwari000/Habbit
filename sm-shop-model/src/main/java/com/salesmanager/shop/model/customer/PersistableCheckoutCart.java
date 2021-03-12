package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;
import com.salesmanager.shop.model.shoppingcart.PersistableCart;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCheckoutCart extends Entity {
  private PersistableCart cart;
  private PersistableAddress shippingAddress;

  public PersistableCart getCart() {
    return cart;
  }

  public void setCart(PersistableCart cart) {
    this.cart = cart;
  }

  public PersistableAddress getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(PersistableAddress shippingAddress) {
    this.shippingAddress = shippingAddress;
  }
}
