package com.salesmanager.shop.store.facade.category;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.category.categoryReviews.CategoryReviewManager;
import com.salesmanager.core.business.modules.cms.category.categoryReviews.CategoryReviewService;
import com.salesmanager.core.model.catalog.category.CategoryReview;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.admin.controller.category.attributes.facade.CategoryReviewFacade;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.mapper.product.CategoryReviewMapper;
import com.salesmanager.shop.model.catalog.category.CategoryReviewList;
import com.salesmanager.shop.model.productAttribute.PersistableCategoryReview;
import com.salesmanager.shop.store.api.exception.ResourceDuplicateException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.model.paging.PaginationData;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

@Service("CategoryReviewFacade")
public class CategoryReviewFacadeImpl implements CategoryReviewFacade {
  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryReviewFacadeImpl.class);
  @Inject private CategoryReviewService CategoryReviewService;
  @Inject private CategoryReviewManager categoryReviewFileManager;
  @Inject private CategoryReviewMapper categoryReviewMapper;

  @Override
  public PersistableCategoryReview createCategoryReview(
      MultipartFile[] files, CategoryReview categoryReview) throws ServiceException {
    String reviewerName = categoryReview.getName();
    if (StringUtils.isNotEmpty(reviewerName)
        && (categoryReview.getId() == null || categoryReview.getId().longValue() == 0)) {
      CategoryReview review = null;
      try {
        review =
            CategoryReviewService.getByCategoryAndReviewerName(
                reviewerName, categoryReview.getCategoryName());
      } catch (Exception e) {
        throwServiceRuntImeException(
            e,
            AttributeErrorCodes.CATEGORY_REVIEW_GET_BY_CATEGORY_AND_REVIEWER_NAME_FAILURE
                .getErrorCode(),
            AttributeErrorCodes.CATEGORY_REVIEW_GET_BY_CATEGORY_AND_REVIEWER_NAME_FAILURE
                    .getErrorMessage()
                + reviewerName
                + " "
                + categoryReview.getCategoryName());
      }
      if (Objects.nonNull(review)) {
        LOGGER.error("CategoryReview Already exists for {}", reviewerName);
        throw new ResourceDuplicateException("CategoryReview Already exists for " + reviewerName);
      }
    }
    try {
      for (MultipartFile multipartFile : files) {
        if (!multipartFile.isEmpty()) {
          categoryReview.setImage(multipartFile.getInputStream());
          categoryReview.setImg(multipartFile.getOriginalFilename());
          addCategoryReview(categoryReview);
        }
      }
      categoryReview = CategoryReviewService.create(categoryReview);
    } catch (ServiceException | IOException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.CATEGORY_REVIEW_CREATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.CATEGORY_REVIEW_CREATE_FAILURE.getErrorMessage());
    }
    return categoryReviewMapper.toPersistableCategoryReview(categoryReview);
  }

  private CategoryReview addCategoryReview(CategoryReview CategoryReview) throws ServiceException {
    Assert.notNull(
        CategoryReview.getImage(), "persistableCategoryReview ImageContentFile cannot be null");

    InputStream inputStream = CategoryReview.getImage();
    ImageContentFile cmsContentImage = new ImageContentFile();
    cmsContentImage.setFileName(CategoryReview.getImg());
    cmsContentImage.setFile(inputStream);
    cmsContentImage.setFileContentType(FileContentType.PRODUCT_STICKER);
    categoryReviewFileManager.addImage(CategoryReview, cmsContentImage);
    return CategoryReview;
  }

  @Override
  public void deleteCategoryReview(Long id) {
    Validate.notNull(id, "CategoryReviewId cannot be null");
    CategoryReview CategoryReview = CategoryReviewService.getById(id);
    try {
      categoryReviewFileManager.removeImage(CategoryReview);
      CategoryReviewService.delete(CategoryReview);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_STICKER_DELETE_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_STICKER_DELETE_FAILURE.getErrorMessage() + id);
    }
  }

  @Override
  public PersistableCategoryReview getCategoryReview(Long id) {
    Validate.notNull(id, "CategoryReview id cannot be null");
    CategoryReview CategoryReview = getCategoryReviewId(id);
    return categoryReviewMapper.toPersistableCategoryReview(CategoryReview);
  }

  private CategoryReview getCategoryReviewId(Long id) {
    CategoryReview CategoryReview = null;
    try {
      CategoryReview = CategoryReviewService.getById(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.CATEGORY_REVIEW_GET_BY_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.CATEGORY_REVIEW_GET_BY_ID_FAILURE.getErrorMessage() + id);
    }
    if (Objects.isNull(CategoryReview)) {
      LOGGER.error("CategoryReview with id [" + id + " ] not found in DB.");
      throw new ResourceNotFoundException("CategoryReview with id [" + id + " ] not found");
    }
    return CategoryReview;
  }

  @Override
  public CategoryReviewList getAllCategoryReview(
      Integer page, Integer pageSize, MerchantStore merchantStore, String categoryName) {
    CategoryReviewList CategoryReviewList = new CategoryReviewList();
    PaginationData paginationData = new PaginationData(pageSize, page);

    Criteria criteria = new Criteria();
    if (!Objects.nonNull(categoryName)) {
      criteria.setStartIndex(paginationData.getOffset() - 1);
      criteria.setPageSize(pageSize);
      criteria.setMaxCount(pageSize);
    } else {
      criteria.setName(categoryName);
    }

    try {
      CategoryReviewList.setCategoryReviews(
          Optional.ofNullable(CategoryReviewService.getAllCategoryReviews(criteria))
              .map(Collection::stream)
              .orElseGet(Stream::empty)
              .map(categoryReviewMapper::toPersistableCategoryReview)
              .collect(Collectors.toList()));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.CATEGORY_REVIEW_GET_ALL_FAILURE.getErrorCode(),
          AttributeErrorCodes.CATEGORY_REVIEW_GET_ALL_FAILURE.getErrorMessage());
    }
    return CategoryReviewList;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
