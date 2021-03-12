package com.salesmanager.shop.model.catalog.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReadableCategoryProducts {

  private static final long serialVersionUID = 1L;

  private ReadableCategory category = new ReadableCategory();
  public List<ReadableProduct> productList = new ArrayList<>();

  public ReadableCategoryProducts() {}

  public ReadableCategory getCategory() {
    return category;
  }

  public void setCategory(ReadableCategory category) {
    this.category = category;
  }

  public List<ReadableProduct> getProductList() {
    return productList;
  }

  public void setProductList(List<ReadableProduct> productList) {
    this.productList = productList;
  }
}
