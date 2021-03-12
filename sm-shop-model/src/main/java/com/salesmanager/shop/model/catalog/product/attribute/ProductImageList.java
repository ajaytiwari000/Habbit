package com.salesmanager.shop.model.catalog.product.attribute;

import java.util.ArrayList;
import java.util.List;

public class ProductImageList {
  private static final long serialVersionUID = 1L;

  private List<PersistableProductImage> productImageList = new ArrayList<>();

  public List<PersistableProductImage> getProductImageList() {
    return productImageList;
  }

  public void setProductImageList(List<PersistableProductImage> productImageList) {
    this.productImageList = productImageList;
  }
}
