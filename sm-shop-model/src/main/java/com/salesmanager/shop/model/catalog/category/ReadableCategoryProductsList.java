package com.salesmanager.shop.model.catalog.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.ReadCategory;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReadableCategoryProductsList extends ReadCategory {

  private static final long serialVersionUID = 1L;

  public List<ReadableCategoryProducts> categoryProductsList = new ArrayList<>();
  private long recordsTotal; // total number of records in Object

  public ReadableCategoryProductsList() {}

  public List<ReadableCategoryProducts> getCategoryProductsList() {
    return categoryProductsList;
  }

  public void setCategoryProductsList(List<ReadableCategoryProducts> categoryProductsList) {
    this.categoryProductsList = categoryProductsList;
  }

  @Override
  public long getRecordsTotal() {
    return recordsTotal;
  }

  @Override
  public void setRecordsTotal(long recordsTotal) {
    this.recordsTotal = recordsTotal;
  }
}
