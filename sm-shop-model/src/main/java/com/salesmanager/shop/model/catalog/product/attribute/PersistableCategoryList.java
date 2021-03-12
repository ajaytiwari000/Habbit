package com.salesmanager.shop.model.catalog.product.attribute;

import com.salesmanager.shop.model.productAttribute.PersistableCategory;
import java.util.ArrayList;
import java.util.List;

public class PersistableCategoryList {
  private List<PersistableCategory> categorys = new ArrayList<>();

  public List<PersistableCategory> getCategorys() {
    return categorys;
  }

  public void setCategorys(List<PersistableCategory> categorys) {
    this.categorys = categorys;
  }
}
