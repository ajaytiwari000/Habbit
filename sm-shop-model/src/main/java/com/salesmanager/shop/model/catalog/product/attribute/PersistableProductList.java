package com.salesmanager.shop.model.catalog.product.attribute;

import com.salesmanager.shop.model.productAttribute.PersistableProduct;
import java.util.ArrayList;
import java.util.List;

public class PersistableProductList {
  private List<PersistableProduct> products = new ArrayList<>();

  public List<PersistableProduct> getProducts() {
    return products;
  }

  public void setProducts(List<PersistableProduct> products) {
    this.products = products;
  }
}
