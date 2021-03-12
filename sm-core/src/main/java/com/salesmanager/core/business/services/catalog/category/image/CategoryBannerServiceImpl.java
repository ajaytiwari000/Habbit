package com.salesmanager.core.business.services.catalog.category.image;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.category.categoryBanner.CategoryBannerManager;
import com.salesmanager.core.business.repositories.catalog.category.image.CategoryBannerRepository;
import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategoryBanner;
import com.salesmanager.core.model.catalog.category.CategoryDetails;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.content.OutputContentFile;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

@Service("categoryBanner")
public class CategoryBannerServiceImpl extends SalesManagerEntityServiceImpl<Long, CategoryBanner>
    implements CategoryBannerService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryBannerServiceImpl.class);
  @Inject private CategoryService categoryService;
  @Inject private CategoryBannerManager categoryBannerManager;
  @Inject private CategoryBannerService categoryBannerService;
  private CategoryBannerRepository categoryBannerRepository;

  @Inject
  public CategoryBannerServiceImpl(CategoryBannerRepository categoryBannerRepository) {
    super(categoryBannerRepository);
    this.categoryBannerRepository = categoryBannerRepository;
  }

  @Override
  public CategoryBanner getById(Long id) {
    return categoryBannerRepository.findById(id).orElse(null);
  }

  @Override
  public void addCategoryBanners(Long id, MultipartFile[] files, String bannerLinkedSkuId)
      throws ServiceException {
    try {
      Category category = categoryService.getById(id);
      List<CategoryBanner> categoryBanners = new ArrayList<>();
      for (MultipartFile multipartFile : files) {
        if (!multipartFile.isEmpty()) {
          CategoryBanner categoryBanner = new CategoryBanner();
          categoryBanner.setBannerLinkedSkuId(bannerLinkedSkuId);
          categoryBanner.setImage(multipartFile.getInputStream());
          categoryBanner.setBannerImage(multipartFile.getOriginalFilename());
          categoryBanner.setCategoryName(category.getCategoryDetails().getName());
          categoryBanners.add(categoryBanner);
        }
      }
      List<CategoryBanner> categoryBannerDbList = new ArrayList();
      for (CategoryBanner categoryBanner : categoryBanners) {
        categoryBannerDbList.add(addCategoryBanner(category.getCategoryDetails(), categoryBanner));
      }
      category.getCategoryDetails().setCategoryBanners(categoryBannerDbList);
      categoryService.update(category);

    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public CategoryBanner addCategoryBanner(
      CategoryDetails categoryDetail, CategoryBanner categoryBanner) throws ServiceException {
    Assert.notNull(categoryBanner.getImage(), "Category Banner ImageContentFile cannot be null");

    InputStream inputStream = categoryBanner.getImage();
    ImageContentFile cmsContentImage = new ImageContentFile();
    cmsContentImage.setFileName(categoryBanner.getBannerImage());
    cmsContentImage.setFile(inputStream);
    cmsContentImage.setFileContentType(FileContentType.CATEGORY);

    return addCategoryBanner(categoryDetail, categoryBanner, cmsContentImage);
  }

  @Override
  public CategoryBanner addCategoryBanner(
      CategoryDetails categoryDetails, CategoryBanner categoryBanner, ImageContentFile inputImage)
      throws ServiceException {
    categoryBanner.setCategoryDetails(categoryDetails);

    try {
      Assert.notNull(inputImage.getFile(), "ImageContentFile.file cannot be null");
      categoryBannerManager.addImage(categoryBanner, inputImage);
      // insert CategoryImage
      return this.saveOrUpdate(categoryBanner);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(e);
    } finally {
      try {
        if (inputImage.getFile() != null) {
          inputImage.getFile().close();
        }
      } catch (Exception ignore) {
      }
    }
  }

  @Override
  public void removeCategoryBanner(CategoryBanner categoryBanner, Long categoryId)
      throws ServiceException {
    try {
      Category category = categoryService.getById(categoryId);
      if (category != null) {
        categoryBanner.setCategoryName(category.getCategoryDetails().getName());
        categoryBannerManager.removeImage(categoryBanner);
        categoryBannerService.delete(categoryBanner);
      }
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      LOGGER.info("Error while removing the Category Banner image", e);
      throw new ServiceException(e);
    }
  }

  @Override
  public CategoryBanner saveOrUpdate(CategoryBanner categoryBanner) throws ServiceException {
    return super.create(categoryBanner);
  }

  @Override
  public List<OutputContentFile> getCategoryBanners(Category category) throws ServiceException {
    return null;
  }

  @Override
  public List<OutputContentFile> getCategoryBanners(String categoryName) throws ServiceException {
    return null;
  }

  @Override
  public List<OutputContentFile> getCategoryBanners(CategoryDetails categoryDetails)
      throws ServiceException {
    return null;
  }

  @Override
  public List<CategoryBanner> getCategoryBannersByCategoryId(Long categoryId)
      throws ServiceException {
    try {
      return categoryService.getById(categoryId).getCategoryDetails().getCategoryBanners();
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
