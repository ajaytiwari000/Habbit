package com.salesmanager.shop.model.catalog.catalog;

import com.salesmanager.shop.model.catalog.category.ReadableCategorys;
import com.salesmanager.shop.model.catalog.product.ReadableProducts;

/** Not in Habbit */
public class ReadableCatalogEntry extends CetalogEntryEntity {

  /** */
  private static final long serialVersionUID = 1L;

  private String creationDate;
  private ReadableProducts product;
  private ReadableCategorys category;

  public String getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(String creationDate) {
    this.creationDate = creationDate;
  }

  public ReadableProducts getProduct() {
    return product;
  }

  public void setProduct(ReadableProducts product) {
    this.product = product;
  }

  public ReadableCategorys getCategory() {
    return category;
  }

  public void setCategory(ReadableCategorys category) {
    this.category = category;
  }
}
