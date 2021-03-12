package com.salesmanager.shop.store.facade.category;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.category.image.CategoryBannerService;
import com.salesmanager.core.model.catalog.category.CategoryBanner;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.category.facade.CategoryBannerFacade;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service(value = "categoryBannerFacade")
public class CategoryBannerFacadeImpl implements CategoryBannerFacade {
  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryBannerFacadeImpl.class);
  @Inject private CategoryBannerService categoryBannerService;

  @Override
  public void addCategoryBanners(Long id, MultipartFile[] files, String bannerLinkedSkuId) {
    try {
      categoryBannerService.addCategoryBanners(id, files, bannerLinkedSkuId);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.CATEGORY_BANNER_ADD_FAILURE.getErrorCode(),
          AttributeErrorCodes.CATEGORY_BANNER_ADD_FAILURE.getErrorMessage());
    }
  }

  @Override
  public CategoryBanner getCategoryBannerById(Long categoryBannerId) {
    try {
      return categoryBannerService.getById(categoryBannerId);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.CATEGORY_BANNER_GET_BY_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.CATEGORY_BANNER_GET_BY_ID_FAILURE.getErrorMessage());
    }
    return null;
  }

  @Override
  public void removeCategoryBanner(CategoryBanner categoryBanner, Long categoryId) {
    try {
      categoryBannerService.removeCategoryBanner(categoryBanner, categoryId);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.CATEGORY_BANNER_REMOVE_FAILURE.getErrorCode(),
          AttributeErrorCodes.CATEGORY_BANNER_REMOVE_FAILURE.getErrorMessage());
    }
  }

  @Override
  public List<CategoryBanner> getCategoryBannersByCategoryId(Long categoryId) {
    try {
      return categoryBannerService.getCategoryBannersByCategoryId(categoryId);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.CATEGORY_BANNER_GET_BY_CATEGORY_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.CATEGORY_BANNER_GET_BY_CATEGORY_ID_FAILURE.getErrorMessage());
    }
    return null;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
