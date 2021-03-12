package com.salesmanager.shop.model.catalog.category;

import com.salesmanager.shop.model.productAttribute.PersistableCategoryReview;
import java.util.ArrayList;
import java.util.List;

public class CategoryReviewList {
  private static final long serialVersionUID = 1L;

  private List<PersistableCategoryReview> categoryReviews =
      new ArrayList<PersistableCategoryReview>();

  public List<PersistableCategoryReview> getCategoryReviews() {
    return categoryReviews;
  }

  public void setCategoryReviews(List<PersistableCategoryReview> categoryReviews) {
    this.categoryReviews = categoryReviews;
  }
}
