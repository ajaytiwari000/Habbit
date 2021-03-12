package com.salesmanager.shop.model.productAttribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.catalog.product.attribute.PersistablePack;
import com.salesmanager.shop.model.entity.Entity;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCategoryDetails extends Entity {
  private String name;
  private String description;
  private String detailDescription;
  private String bannerLinkedSkuId;
  // private String categoryBannerImage;
  private PersistablePack defaultPackSize;
  private PersistableNutritionalInfo nutritionalInfo;
  private List<PersistableCategoryReview> categoryReviews;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getBannerLinkedSkuId() {
    return bannerLinkedSkuId;
  }

  public void setBannerLinkedSkuId(String bannerLinkedSkuId) {
    this.bannerLinkedSkuId = bannerLinkedSkuId;
  }

  public PersistablePack getDefaultPackSize() {
    return defaultPackSize;
  }

  public void setDefaultPackSize(PersistablePack defaultPackSize) {
    this.defaultPackSize = defaultPackSize;
  }

  public PersistableNutritionalInfo getNutritionalInfo() {
    return nutritionalInfo;
  }

  public void setNutritionalInfo(PersistableNutritionalInfo nutritionalInfo) {
    this.nutritionalInfo = nutritionalInfo;
  }

  public List<PersistableCategoryReview> getCategoryReviews() {
    return categoryReviews;
  }

  public void setCategoryReviews(List<PersistableCategoryReview> categoryReviews) {
    this.categoryReviews = categoryReviews;
  }
}
