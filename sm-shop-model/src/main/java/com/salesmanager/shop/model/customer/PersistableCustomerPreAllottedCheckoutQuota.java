package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCustomerPreAllottedCheckoutQuota extends Entity {
  private String customerPhone;
  private String productSku;
  private int count;
  private String cartItemCode;

  public String getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
  }

  public String getProductSku() {
    return productSku;
  }

  public void setProductSku(String productSku) {
    this.productSku = productSku;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getCartItemCode() {
    return cartItemCode;
  }

  public void setCartItemCode(String cartItemCode) {
    this.cartItemCode = cartItemCode;
  }
}
