package com.salesmanager.shop.model.order;

import com.salesmanager.shop.model.catalog.product.ReadableProducts;
import java.io.Serializable;

public class OrderProductEntity extends OrderProduct implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  private int orderedQuantity;
  private ReadableProducts product;

  public void setOrderedQuantity(int orderedQuantity) {
    this.orderedQuantity = orderedQuantity;
  }

  public int getOrderedQuantity() {
    return orderedQuantity;
  }

  public ReadableProducts getProduct() {
    return product;
  }

  public void setProduct(ReadableProducts product) {
    this.product = product;
  }
}
