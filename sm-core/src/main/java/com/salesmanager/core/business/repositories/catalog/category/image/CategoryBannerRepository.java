package com.salesmanager.core.business.repositories.catalog.category.image;

import com.salesmanager.core.model.catalog.category.CategoryBanner;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryBannerRepository extends JpaRepository<CategoryBanner, Long> {

  @Query("select c from CategoryBanner c where c.id = ?1")
  Optional<CategoryBanner> findById(Long categoryBannerId);

  @Query("select c from CategoryBanner c where c.bannerImage = ?1")
  Optional<CategoryBanner> findByName(String categoryBannerImageName);
}
