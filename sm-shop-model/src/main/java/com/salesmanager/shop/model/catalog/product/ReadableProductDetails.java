package com.salesmanager.shop.model.catalog.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableBoost;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableCategoryReview;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableFlavour;
import com.salesmanager.shop.model.catalog.product.attribute.ReadablePack;
import com.salesmanager.shop.model.entity.Entity;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
// Need to merge this with com.salesmanager.shop.model.catalog.product
public class ReadableProductDetails extends Entity {

  private String skuId;
  private String name;
  private String primaryImgUrl;
  private List<String> secondaryImgUrlList;
  private String categoryName;
  private String categoryId;
  private ReadablePack packSize;
  private String description;
  private String detailDescription;

  private ReadableFlavour readableFlavour;

  private Long price;
  private String earningPoints;

  private ReadableNutritionalInfo readableNutritionalInfo;
  private List<ReadableFlavour> availableFlavourList = new ArrayList<>();
  private List<ReadablePack> availablePackSizeList = new ArrayList<>();
  private List<ReadableBoost> availableBoostList = new ArrayList<>();
  private List<ReadableCategoryReview> reviewsList = new ArrayList<>();
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

  public ReadableProductDetails() {}

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

  public ReadableFlavour getReadableFlavour() {
    return readableFlavour;
  }

  public void setReadableFlavour(ReadableFlavour readableFlavour) {
    this.readableFlavour = readableFlavour;
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

  public List<ReadableFlavour> getAvailableFlavourList() {
    return availableFlavourList;
  }

  public void setAvailableFlavourList(List<ReadableFlavour> availableFlavourList) {
    this.availableFlavourList = availableFlavourList;
  }

  public List<ReadableBoost> getAvailableBoostList() {
    return availableBoostList;
  }

  public void setAvailableBoostList(List<ReadableBoost> availableBoostList) {
    this.availableBoostList = availableBoostList;
  }

  public List<ReadableCategoryReview> getReviewsList() {
    return reviewsList;
  }

  public void setReviewsList(List<ReadableCategoryReview> reviewsList) {
    this.reviewsList = reviewsList;
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
