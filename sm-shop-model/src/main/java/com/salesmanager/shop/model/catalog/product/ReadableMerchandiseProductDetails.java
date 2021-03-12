package com.salesmanager.shop.model.catalog.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.catalog.product.attribute.ReadablePack;
import com.salesmanager.shop.model.entity.Entity;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
// Need to merge this with com.salesmanager.shop.model.catalog.product
public class ReadableMerchandiseProductDetails extends Entity {

  private String skuId;
  private String name;
  private String primaryImgUrl;
  private List<String> secondaryImgUrlList;
  private String categoryName;
  private String categoryId;
  private ReadablePack packSize;
  private String description;
  private String detailDescription;
  private Long price;
  private String earningPoints;
  private ReadableNutritionalInfo readableNutritionalInfo;
  private List<ReadablePack> availablePackSizeList = new ArrayList<>();
  private String defaultPackSize;
  private List<ReadableProduct> recommendedProductList;

  public ReadablePack getPackSize() {
    return packSize;
  }

  public void setPackSize(ReadablePack packSize) {
    this.packSize = packSize;
  }

  public List<ReadablePack> getAvailablePackSizeList() {
    return availablePackSizeList;
  }

  public void setAvailablePackSizeList(List<ReadablePack> availablePackSizeList) {
    this.availablePackSizeList = availablePackSizeList;
  }

  public ReadableMerchandiseProductDetails() {}

  public String getSkuId() {
    return skuId;
  }

  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPrimaryImgUrl() {
    return primaryImgUrl;
  }

  public void setPrimaryImgUrl(String primaryImgUrl) {
    this.primaryImgUrl = primaryImgUrl;
  }

  public List<String> getSecondaryImgUrlList() {
    return secondaryImgUrlList;
  }

  public void setSecondaryImgUrlList(List<String> secondaryImgUrlList) {
    this.secondaryImgUrlList = secondaryImgUrlList;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDetailDescription() {
    return detailDescription;
  }

  public void setDetailDescription(String detailDescription) {
    this.detailDescription = detailDescription;
  }

  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public String getEarningPoints() {
    return earningPoints;
  }

  public void setEarningPoints(String earningPoints) {
    this.earningPoints = earningPoints;
  }

  public ReadableNutritionalInfo getReadableNutritionalInfo() {
    return readableNutritionalInfo;
  }

  public void setReadableNutritionalInfo(ReadableNutritionalInfo readableNutritionalInfo) {
    this.readableNutritionalInfo = readableNutritionalInfo;
  }

  public String getDefaultPackSize() {
    return defaultPackSize;
  }

  public void setDefaultPackSize(String defaultPackSize) {
    this.defaultPackSize = defaultPackSize;
  }

  public List<ReadableProduct> getRecommendedProductList() {
    return recommendedProductList;
  }

  public void setRecommendedProductList(List<ReadableProduct> recommendedProductList) {
    this.recommendedProductList = recommendedProductList;
  }
}
