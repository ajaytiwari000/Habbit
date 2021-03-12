package com.salesmanager.shop.model.catalog.product;

import com.salesmanager.shop.model.entity.ReadableList;
import java.util.ArrayList;
import java.util.List;

/** This file is not used in HABBIT */
public class ReadableProductsList extends ReadableList {

  /** */
  private static final long serialVersionUID = 1L;

  private List<ReadableProducts> products = new ArrayList<ReadableProducts>();

  public void setProducts(List<ReadableProducts> products) {
    this.products = products;
  }

  public List<ReadableProducts> getProducts() {
    return products;
  }
}
