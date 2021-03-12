package com.salesmanager.shop.store.controller.category.facade;

import com.salesmanager.core.model.catalog.category.CategoryBanner;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryBannerFacade {
  void addCategoryBanners(Long id, MultipartFile[] files, String bannerLinkedSkuId);

  CategoryBanner getCategoryBannerById(Long categoryBannerId);

  void removeCategoryBanner(CategoryBanner categoryBanner, Long categoryId);

  List<CategoryBanner> getCategoryBannersByCategoryId(Long categoryId);
}
