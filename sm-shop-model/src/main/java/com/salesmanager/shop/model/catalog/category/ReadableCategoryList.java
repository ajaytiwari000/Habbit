package com.salesmanager.shop.model.catalog.category;

import com.salesmanager.shop.model.entity.ReadableList;
import java.util.ArrayList;
import java.util.List;

/** Not in Habbit */
public class ReadableCategoryList extends ReadableList {

  /** */
  private static final long serialVersionUID = 1L;

  private List<ReadableCategorys> categories = new ArrayList<ReadableCategorys>();

  public List<ReadableCategorys> getCategories() {
    return categories;
  }

  public void setCategories(List<ReadableCategorys> categories) {
    this.categories = categories;
  }
}
