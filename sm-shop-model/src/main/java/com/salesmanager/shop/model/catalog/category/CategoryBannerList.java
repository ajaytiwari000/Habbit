package com.salesmanager.shop.model.catalog.category;

import java.util.ArrayList;
import java.util.List;

public class CategoryBannerList {
  private static final long serialVersionUID = 1L;

  private List<PersistableCategoryBanner> categoryBanners =
      new ArrayList<PersistableCategoryBanner>();

  public List<PersistableCategoryBanner> getCategoryBanners() {
    return categoryBanners;
  }

  public void setCategoryBanners(List<PersistableCategoryBanner> categoryBanners) {
    this.categoryBanners = categoryBanners;
  }
}
