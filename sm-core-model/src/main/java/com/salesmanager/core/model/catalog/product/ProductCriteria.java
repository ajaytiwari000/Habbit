package com.salesmanager.core.model.catalog.product;

import com.salesmanager.core.model.catalog.product.attribute.AttributeCriteria;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;

public class ProductCriteria extends Criteria {

  // check for input criteria for getproductlist id or string
  private String productName;
  private String categoryName;
  private Boolean available = null;
  private String status;
  private Long manufacturerId = null;
  private Long ownerId = null;
  private List<AttributeCriteria> attributeCriteria;
  private List<String> availabilities;
  private List<Long> productIds;
  private List<Long> productFlavourTypeIds;
  private List<String> productFeatureTypes;
  List<Long> productTypeCategoryIds;
  List<Long> categoryIds;
  List<String> productTypeCategoryName;
  List<String> productFlavourType;
  List<String> productFeatureType;
  String productSortType;

  public ProductCriteria() {}

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Boolean getAvailable() {
    return available;
  }

  public void setAvailable(Boolean available) {
    this.available = available;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Long getManufacturerId() {
    return manufacturerId;
  }

  public void setManufacturerId(Long manufacturerId) {
    this.manufacturerId = manufacturerId;
  }

  public Long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
  }

  public List<AttributeCriteria> getAttributeCriteria() {
    return attributeCriteria;
  }

  public void setAttributeCriteria(List<AttributeCriteria> attributeCriteria) {
    this.attributeCriteria = attributeCriteria;
  }

  public List<String> getAvailabilities() {
    return availabilities;
  }

  public void setAvailabilities(List<String> availabilities) {
    this.availabilities = availabilities;
  }

  public List<Long> getProductIds() {
    return productIds;
  }

  public void setProductIds(List<Long> productIds) {
    this.productIds = productIds;
  }

  public List<String> getProductTypeCategoryName() {
    return productTypeCategoryName;
  }

  public void setProductTypeCategoryName(List<String> productTypeCategoryName) {
    this.productTypeCategoryName = productTypeCategoryName;
  }

  public List<String> getProductFlavourType() {
    return productFlavourType;
  }

  public void setProductFlavourType(List<String> productFlavourType) {
    this.productFlavourType = productFlavourType;
  }

  public List<String> getProductFeatureType() {
    return productFeatureType;
  }

  public void setProductFeatureType(List<String> productFeatureType) {
    this.productFeatureType = productFeatureType;
  }

  public String getProductSortType() {
    return productSortType;
  }

  public void setProductSortType(String productSortType) {
    this.productSortType = productSortType;
  }

  public void setCategoryIds(List<Long> categoryIds) {}

  public List<Long> getProductFlavourTypeIds() {
    return productFlavourTypeIds;
  }

  public void setProductFlavourTypeIds(List<Long> productFlavourTypeIds) {
    this.productFlavourTypeIds = productFlavourTypeIds;
  }

  public List<String> getProductFeatureTypes() {
    return productFeatureTypes;
  }

  public void setProductFeatureTypes(List<String> productFeatureTypes) {
    this.productFeatureTypes = productFeatureTypes;
  }

  public List<Long> getProductTypeCategoryIds() {
    return productTypeCategoryIds;
  }

  public void setProductTypeCategoryIds(List<Long> productTypeCategoryIds) {
    this.productTypeCategoryIds = productTypeCategoryIds;
  }

  public List<Long> getCategoryIds() {
    return categoryIds;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }
}
