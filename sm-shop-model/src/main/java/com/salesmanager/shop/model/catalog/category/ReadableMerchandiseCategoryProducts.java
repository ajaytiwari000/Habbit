package com.salesmanager.shop.model.catalog.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.catalog.product.ReadableMerchandiseProduct;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReadableMerchandiseCategoryProducts {

  private static final long serialVersionUID = 1L;

  private ReadableMerchandiseCategory category = new ReadableMerchandiseCategory();
  public List<ReadableMerchandiseProduct> productList = new ArrayList<>();

  public ReadableMerchandiseCategoryProducts() {}

  public ReadableMerchandiseCategory getCategory() {
    return category;
  }

  public void setCategory(ReadableMerchandiseCategory category) {
    this.category = category;
  }

  public List<ReadableMerchandiseProduct> getProductList() {
    return productList;
  }

  public void setProductList(List<ReadableMerchandiseProduct> productList) {
    this.productList = productList;
  }
}
