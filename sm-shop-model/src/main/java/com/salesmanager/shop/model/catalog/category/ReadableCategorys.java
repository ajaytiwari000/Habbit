package com.salesmanager.shop.model.catalog.category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Not in Habbitgit diff */
public class ReadableCategorys extends CategoryEntity implements Serializable {
  /** */
  private static final long serialVersionUID = 1L;

  private CategoryDescription description; // one category based on language
  private int productCount;
  private String store;
  private List<ReadableCategorys> children = new ArrayList<ReadableCategorys>();

  public ReadableCategorys() {}

  public CategoryDescription getDescription() {
    return description;
  }

  public void setDescription(CategoryDescription description) {
    this.description = description;
  }

  public int getProductCount() {
    return productCount;
  }

  public void setProductCount(int productCount) {
    this.productCount = productCount;
  }

  public String getStore() {
    return store;
  }

  public void setStore(String store) {
    this.store = store;
  }

  public List<ReadableCategorys> getChildren() {
    return children;
  }

  public void setChildren(List<ReadableCategorys> children) {
    this.children = children;
  }
}
