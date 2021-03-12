package com.salesmanager.shop.model.catalog.category;

import com.salesmanager.shop.model.entity.Entity;

public class PersistableCategoryBanner extends Entity {
  private String bannerLinkedSkuId;
  private String bannerImage;
  private String bannerImageUrl;

  public String getBannerLinkedSkuId() {
    return bannerLinkedSkuId;
  }

  public void setBannerLinkedSkuId(String bannerLinkedSkuId) {
    this.bannerLinkedSkuId = bannerLinkedSkuId;
  }

  public String getBannerImage() {
    return bannerImage;
  }

  public void setBannerImage(String bannerImage) {
    this.bannerImage = bannerImage;
  }

  public String getBannerImageUrl() {
    return bannerImageUrl;
  }

  public void setBannerImageUrl(String bannerImageUrl) {
    this.bannerImageUrl = bannerImageUrl;
  }
}
