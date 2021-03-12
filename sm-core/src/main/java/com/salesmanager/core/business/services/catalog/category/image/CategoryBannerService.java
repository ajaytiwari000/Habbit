package com.salesmanager.core.business.services.catalog.category.image;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategoryBanner;
import com.salesmanager.core.model.catalog.category.CategoryDetails;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.content.OutputContentFile;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryBannerService extends SalesManagerEntityService<Long, CategoryBanner> {

  /**
   * @param id @throws ServiceException
   * @param files
   * @param bannerLinkedSkuId
   */
  void addCategoryBanners(Long id, MultipartFile[] files, String bannerLinkedSkuId)
      throws ServiceException;

  /**
   * Add a CategoryBanner to the persistence and an entry to the CMS
   *
   * @param categoryDetail
   * @param categoryBanner
   * @throws ServiceException
   */
  CategoryBanner addCategoryBanner(CategoryDetails categoryDetail, CategoryBanner categoryBanner)
      throws ServiceException;

  /**
   * Add a CategoryBanner to the persistence and an entry to the CMS
   *
   * @param categoryDetail
   * @param categoryBanner
   * @param imageContentFile
   * @throws ServiceException
   */
  CategoryBanner addCategoryBanner(
      CategoryDetails categoryDetail,
      CategoryBanner categoryBanner,
      ImageContentFile imageContentFile)
      throws ServiceException;

  /**
   * @param categoryBanner
   * @param categoryId
   * @throws ServiceException
   */
  void removeCategoryBanner(CategoryBanner categoryBanner, Long categoryId) throws ServiceException;

  /**
   * @param categoryBanner
   * @throws ServiceException
   */
  CategoryBanner saveOrUpdate(CategoryBanner categoryBanner) throws ServiceException;

  /**
   * Returns all Images for a given pack
   *
   * @param category
   * @return
   * @throws ServiceException
   */
  List<OutputContentFile> getCategoryBanners(Category category) throws ServiceException;

  /**
   * Returns an image file from required identifier. This method is used by the image servlet
   *
   * @param categoryName
   * @return
   * @throws ServiceException
   */
  List<OutputContentFile> getCategoryBanners(String categoryName) throws ServiceException;

  /**
   * Get the image ByteArrayOutputStream and content description from CMS
   *
   * @param categoryDetails
   * @return
   * @throws ServiceException
   */
  List<OutputContentFile> getCategoryBanners(CategoryDetails categoryDetails)
      throws ServiceException;

  /**
   * @param categoryId
   * @return
   * @throws ServiceException
   */
  List<CategoryBanner> getCategoryBannersByCategoryId(Long categoryId) throws ServiceException;
}
