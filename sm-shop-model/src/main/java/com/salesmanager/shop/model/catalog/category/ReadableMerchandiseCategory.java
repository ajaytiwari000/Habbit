package com.salesmanager.shop.model.catalog.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReadableMerchandiseCategory extends Entity implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  private Long Id;
  private String name;
  private String description;
  private String detailDescription;
  private List<ReadableCategoryBanner> categoryBannerList;
  private String defaultPackSize;
  //  private ReadableNutritionalInfo readableNutritionalInfoList;
  //  private List<ReadableCategoryReview> categoryReviews;

  public ReadableMerchandiseCategory() {}

  @Override
  public Long getId() {
    return Id;
  }

  @Override
  public void setId(Long id) {
    Id = id;
  }

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

  public String getDefaultPackSize() {
    return defaultPackSize;
  }

  public void setDefaultPackSize(String defaultPackSize) {
    this.defaultPackSize = defaultPackSize;
  }

  //  public ReadableNutritionalInfo getReadableNutritionalInfoList() {
  //    return readableNutritionalInfoList;
  //  }
  //
  //  public void setReadableNutritionalInfoList(ReadableNutritionalInfo
  // readableNutritionalInfoList) {
  //    this.readableNutritionalInfoList = readableNutritionalInfoList;
  //  }
  //
  //  public List<ReadableCategoryReview> getCategoryReviews() {
  //    return categoryReviews;
  //  }
  //
  //  public void setCategoryReviews(List<ReadableCategoryReview> categoryReviews) {
  //    this.categoryReviews = categoryReviews;
  //  }

  public List<ReadableCategoryBanner> getCategoryBannerList() {
    return categoryBannerList;
  }

  public void setCategoryBannerList(List<ReadableCategoryBanner> categoryBannerList) {
    this.categoryBannerList = categoryBannerList;
  }
}
