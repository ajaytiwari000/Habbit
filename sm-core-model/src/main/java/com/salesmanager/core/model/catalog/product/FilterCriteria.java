package com.salesmanager.core.model.catalog.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilterCriteria implements Serializable {

  private static final long serialVersionUID = 1L;
  List<HomeFilter> productTypeCategoryList;
  List<HomeFilter> productFlavourTypeList;
  List<HomeFilter> productFeatureTypeList;

  public List<HomeFilter> getProductTypeCategoryList() {
    return productTypeCategoryList;
  }

  public void setProductTypeCategoryList(List<HomeFilter> productTypeCategoryList) {
    this.productTypeCategoryList = productTypeCategoryList;
  }

  public List<HomeFilter> getProductFlavourTypeList() {
    return productFlavourTypeList;
  }

  public void setProductFlavourTypeList(List<HomeFilter> productFlavourTypeList) {
    this.productFlavourTypeList = productFlavourTypeList;
  }

  public List<HomeFilter> getProductFeatureTypeList() {
    return productFeatureTypeList;
  }

  public void setProductFeatureTypeList(List<HomeFilter> productFeatureTypeList) {
    this.productFeatureTypeList = productFeatureTypeList;
  }
}
