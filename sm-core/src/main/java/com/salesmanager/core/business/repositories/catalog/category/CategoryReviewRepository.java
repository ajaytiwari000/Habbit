package com.salesmanager.core.business.repositories.catalog.category;

import com.salesmanager.core.model.catalog.category.CategoryReview;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryReviewRepository
    extends JpaRepository<CategoryReview, Long>, CategoryReviewRepositoryCustom {
  @Query("select distinct cr from CategoryReview cr where cr.name= ?1 And cr.categoryName = ?2")
  Optional<CategoryReview> getByCategoryAndReviewerName(String reviewerName, String categoryName);
}
