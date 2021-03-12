package com.salesmanager.shop.model.catalog.product.attribute.api;

import com.salesmanager.shop.model.catalog.product.attribute.ProductOptionValueDescription;
import java.util.ArrayList;
import java.util.List;

public class ReadableProductOptionValueFull extends ReadableProductOptionValueEntity {

  /** */
  private static final long serialVersionUID = 1L;

  private List<ProductOptionValueDescription> descriptions =
      new ArrayList<ProductOptionValueDescription>();

  public List<ProductOptionValueDescription> getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(List<ProductOptionValueDescription> descriptions) {
    this.descriptions = descriptions;
  }
}
