package com.salesmanager.core.business.services.catalog.product;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.products.productsticker.ProductStickerImageService;
import com.salesmanager.core.business.repositories.catalog.product.ProductRepository;
import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductAttributeService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionValueService;
import com.salesmanager.core.business.services.catalog.product.availability.ProductAvailabilityService;
import com.salesmanager.core.business.services.catalog.product.image.ProductImageService;
import com.salesmanager.core.business.services.catalog.product.price.ProductPriceService;
import com.salesmanager.core.business.services.catalog.product.relationship.ProductRelationshipService;
import com.salesmanager.core.business.services.catalog.product.review.ProductReviewService;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.services.search.SearchService;
import com.salesmanager.core.business.utils.CatalogServiceHelper;
import com.salesmanager.core.business.utils.CoreConfiguration;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.FilterCriteria;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.ProductCriteria;
import com.salesmanager.core.model.catalog.product.ProductList;
import com.salesmanager.core.model.catalog.product.description.ProductDescription;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.catalog.product.relationship.ProductRelationship;
import com.salesmanager.core.model.catalog.product.review.ProductReview;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.tax.taxclass.TaxClass;
import java.io.InputStream;
import java.util.*;
import javax.inject.Inject;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("productService")
public class ProductServiceImpl extends SalesManagerEntityServiceImpl<Long, Product>
    implements ProductService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

  ProductRepository productRepository;

  @Inject CategoryService categoryService;

  @Inject ProductAvailabilityService productAvailabilityService;

  @Inject ProductPriceService productPriceService;

  @Inject ProductOptionService productOptionService;

  @Inject ProductOptionValueService productOptionValueService;

  @Inject ProductAttributeService productAttributeService;

  @Inject ProductRelationshipService productRelationshipService;

  @Inject SearchService searchService;

  @Inject ProductImageService productImageService;

  @Inject ProductStickerImageService productStickerImageService;

  @Inject CoreConfiguration configuration;

  @Inject ProductReviewService productReviewService;

  @Inject
  public ProductServiceImpl(ProductRepository productRepository) {
    super(productRepository);
    this.productRepository = productRepository;
  }

  @Override
  public void addProductDescription(Product product, ProductDescription description)
      throws ServiceException {

    if (product.getDescriptions() == null) {
      product.setDescriptions(new HashSet<ProductDescription>());
    }

    product.getDescriptions().add(description);
    description.setProduct(product);
    update(product);
    searchService.index(product.getMerchantStore(), product);
  }

  @Override
  public List<Product> getProducts(List<Long> categoryIds) throws ServiceException {

    @SuppressWarnings({"unchecked", "rawtypes"})
    Set ids = new HashSet(categoryIds);
    return productRepository.getProductsListByCategories(ids);
  }

  public Product getById(Long productId) {
    return productRepository.getById(productId);
  }

  @Override
  public Product getProductWithOnlyMerchantStoreById(Long productId) {
    return productRepository.getProductWithOnlyMerchantStoreById(productId);
  }

  @Override
  public List<Product> getProducts(List<Long> categoryIds, Language language)
      throws ServiceException {

    @SuppressWarnings({"unchecked", "rawtypes"})
    Set<Long> ids = new HashSet(categoryIds);
    return productRepository.getProductsListByCategories(ids, language);
  }

  @Override
  public ProductDescription getProductDescription(Product product, Language language) {
    for (ProductDescription description : product.getDescriptions()) {
      if (description.getLanguage().equals(language)) {
        return description;
      }
    }
    return null;
  }

  @Override
  public Product getBySeUrl(MerchantStore store, String seUrl, Locale locale) {
    return productRepository.getByFriendlyUrl(store, seUrl, locale);
  }

  @Override
  public Product getProductForLocale(long productId, Language language, Locale locale)
      throws ServiceException {
    Product product = productRepository.getProductForLocale(productId, language, locale);
    if (product == null) {
      return null;
    }

    CatalogServiceHelper.setToAvailability(product, locale);
    CatalogServiceHelper.setToLanguage(product, language.getId());
    return product;
  }

  @Override
  public List<Product> getProductsForLocale(Category category, Language language, Locale locale)
      throws ServiceException {

    if (category == null) {
      throw new ServiceException("The category is null");
    }

    // Get the category list
    StringBuilder lineage =
        new StringBuilder().append(category.getLineage()).append(category.getId()).append("/");
    List<Category> categories =
        categoryService.getListByLineage(category.getMerchantStore(), lineage.toString());
    Set<Long> categoryIds = new HashSet<Long>();
    for (Category c : categories) {

      categoryIds.add(c.getId());
    }

    categoryIds.add(category.getId());

    // Get products
    List<Product> products =
        productRepository.getProductsForLocale(
            category.getMerchantStore(), categoryIds, language, locale);

    // Filter availability

    return products;
  }

  @Override
  public ProductList listByStore(MerchantStore store, Language language, ProductCriteria criteria) {

    return productRepository.listByStore(store, language, criteria);
  }

  @Override
  public List<Product> listByStore(MerchantStore store) {

    return productRepository.listByStore(store);
  }

  @Override
  public List<Product> listByTaxClass(TaxClass taxClass) {
    return productRepository.listByTaxClass(taxClass);
  }

  @Override
  public Product getByCode(String productCode, Language language) {
    return productRepository.getByCode(productCode, language);
  }

  @Override
  public void delete(Product product) throws ServiceException {
    LOGGER.debug("Deleting product");
    Validate.notNull(product, "Product cannot be null");
    Validate.notNull(product.getMerchantStore(), "MerchantStore cannot be null in product");
    product = this.getById(product.getId()); // Prevents detached entity error
    product.setCategories(null);
    Set<ProductImage> images = product.getImages();

    for (ProductImage image : images) {
      productImageService.removeProductImage(image);
    }

    product.setImages(null);

    // delete reviews
    List<ProductReview> reviews = productReviewService.getByProductNoCustomers(product);
    for (ProductReview review : reviews) {
      productReviewService.delete(review);
    }

    // related - featured
    List<ProductRelationship> relationships = productRelationshipService.listByProduct(product);
    for (ProductRelationship relationship : relationships) {
      productRelationshipService.deleteRelationship(relationship);
    }

    super.delete(product);
    searchService.deleteIndex(product.getMerchantStore(), product);
  }

  @Override
  public Product create(Product product) throws ServiceException {
    product = this.saveOrUpdate(product);
    searchService.index(product.getMerchantStore(), product);
    return product;
  }

  @Override
  public Product update(Product product) throws ServiceException {
    product = this.saveOrUpdate(product);
    searchService.index(product.getMerchantStore(), product);
    return product;
  }

  private Product saveOrUpdate(Product product) throws ServiceException {
    LOGGER.debug("Save or update product ");
    Validate.notNull(product, "product cannot be null");
    Validate.notNull(product.getAvailabilities(), "product must have at least one availability");
    Validate.notEmpty(product.getAvailabilities(), "product must have at least one availability");

    // List of original images
    /*		Set<ProductImage> originalProductImages = null;

    if(product.getId()!=null && product.getId()>0) {
    	originalProductImages = product.getImages();
    }*/

    // take care of product images separately
    Set<ProductImage> originalProductImages = new HashSet<ProductImage>(product.getImages());

    /** save product first * */
    if (product.getId() != null && product.getId() > 0) {
      product = super.update(product);
    } else {
      product = super.create(product);
    }

    /** Image creation needs extra service to save the file in the CMS */
    List<Long> newImageIds = new ArrayList<Long>();
    Set<ProductImage> images = product.getImages();

    try {

      if (images != null && images.size() > 0) {
        for (ProductImage image : images) {
          if (image.getImage() != null && (image.getId() == null || image.getId() == 0L)) {
            image.setProduct(product);

            InputStream inputStream = image.getImage();
            ImageContentFile cmsContentImage = new ImageContentFile();
            cmsContentImage.setFileName(image.getProductImage());
            cmsContentImage.setFile(inputStream);
            cmsContentImage.setFileContentType(FileContentType.PRODUCT);

            productImageService.addProductImage(product, image, cmsContentImage);
            newImageIds.add(image.getId());
          } else {
            if (image.getId() != null) {
              productImageService.save(image);
              newImageIds.add(image.getId());
            }
          }
        }
      }

      // cleanup old and new images
      if (originalProductImages != null) {
        for (ProductImage image : originalProductImages) {

          if (image.getImage() != null && image.getId() == null) {
            image.setProduct(product);

            InputStream inputStream = image.getImage();
            ImageContentFile cmsContentImage = new ImageContentFile();
            cmsContentImage.setFileName(image.getProductImage());
            cmsContentImage.setFile(inputStream);
            cmsContentImage.setFileContentType(FileContentType.PRODUCT);

            productImageService.addProductImage(product, image, cmsContentImage);
            newImageIds.add(image.getId());
          } else {
            if (!newImageIds.contains(image.getId())) {
              productImageService.delete(image);
            }
          }
        }
      }

    } catch (Exception e) {
      LOGGER.error("Cannot save images " + e.getMessage());
    }
    return product;
  }

  @Override
  public Product findOne(Long id, MerchantStore merchant) {
    Validate.notNull(merchant, "MerchantStore must not be null");
    Validate.notNull(id, "id must not be null");
    return productRepository.getById(id, merchant);
  }

  @Override
  public FilterCriteria getHomeFilters() {
    return productRepository.getHomeFilters();
  }

  @Override
  public List<Product> listByFilter(
      MerchantStore store, Language language, ProductCriteria criteria) throws ServiceException {
    try {
      return productRepository.listByFilter(store, language, criteria).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public List<Product> listByMerchandiseProductName(
      MerchantStore store, Language language, ProductCriteria criteria) throws ServiceException {
    try {
      return productRepository.listByMerchandiseProductName(store, language, criteria).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public Product getMerchandiseProductDetailsByCategoryFlavorAndPack(
      MerchantStore merchantStore, Long categoryId, Long packId, String productName)
      throws ServiceException {
    try {
      return productRepository
          .getMerchandiseProductDetailsByCategoryFlavorAndPack(
              merchantStore, categoryId, packId, productName)
          .orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  /**
   * @param store
   * @param flavourId
   * @param categoryId
   * @param excludeProductName
   * @return
   */
  @Override
  public List<Product> getProductDetailsByFlavorIdAndCategoryId(
      MerchantStore store, Long flavourId, Long categoryId, String excludeProductName)
      throws ServiceException {

    try {
      return productRepository
          .getProductDetailsByFlavorIdAndCategoryId(
              store, flavourId, categoryId, excludeProductName)
          .orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  /**
   * @param productFlag
   * @param merchantStore
   * @param categoryId
   * @param flavourId
   * @param packId
   * @return
   */
  @Override
  public Product getProductDetailsByCategoryFlavorAndPack(
      Boolean productFlag,
      MerchantStore merchantStore,
      Long categoryId,
      Long flavourId,
      Long packId)
      throws ServiceException {
    try {
      return productRepository
          .getProductDetailsByCategoryFlavorAndPack(
              productFlag, merchantStore, categoryId, flavourId, packId)
          .orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  /**
   * @param store
   * @param language
   * @param criteria
   * @param count
   * @return
   */
  @Override
  public List<Product> getProductListForHome(
      MerchantStore store, Language language, ProductCriteria criteria, int count)
      throws ServiceException {
    try {
      List<Product> productList =
          productRepository.getProductListForHome(store, language, criteria, count).orElse(null);
      return productList;
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  /**
   * @param sku
   * @return
   */
  @Override
  public Product getBySkuId(String sku) throws ServiceException {
    try {
      return productRepository.getBySkuId(sku).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public Product getByProductId(Long id) throws ServiceException {
    try {
      return productRepository.getByProductId(id).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  /**
   * @param criteria
   * @return
   * @throws ServiceException
   */
  @Override
  public Collection<Product> getProductsByCriteria(Criteria criteria) throws ServiceException {
    try {
      return productRepository.getProducts(criteria).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
