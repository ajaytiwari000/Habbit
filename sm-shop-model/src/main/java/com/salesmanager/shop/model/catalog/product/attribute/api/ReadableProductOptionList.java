package com.salesmanager.shop.model.catalog.product.attribute.api;

import com.salesmanager.shop.model.entity.ReadableList;
import java.util.ArrayList;
import java.util.List;

public class ReadableProductOptionList extends ReadableList {

  /** */
  private static final long serialVersionUID = 1L;

  private List<ReadableProductOptionEntity> options = new ArrayList<ReadableProductOptionEntity>();

  public List<ReadableProductOptionEntity> getOptions() {
    return options;
  }

  public void setOptions(List<ReadableProductOptionEntity> options) {
    this.options = options;
  }
}
