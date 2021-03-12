package com.salesmanager.shop.store.facade.product;

import com.salesmanager.core.business.HabbitCoreConstant;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.attribute.FlavourService;
import com.salesmanager.core.business.services.catalog.category.CategoryFlavourCountService;
import com.salesmanager.core.business.services.catalog.category.CategoryPackCountService;
import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.business.services.catalog.product.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.relationship.ProductRelationshipService;
import com.salesmanager.core.business.services.catalog.product.review.ProductReviewService;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategoryFlavourCount;
import com.salesmanager.core.model.catalog.category.CategoryPackCount;
import com.salesmanager.core.model.catalog.product.*;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer;
import com.salesmanager.core.model.catalog.product.price.ProductPrice;
import com.salesmanager.core.model.catalog.product.relationship.ProductRelationship;
import com.salesmanager.core.model.catalog.product.relationship.ProductRelationshipType;
import com.salesmanager.core.model.catalog.product.review.ProductReview;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.cache.util.CacheUtil;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.mapper.product.CategoryMapper;
import com.salesmanager.shop.mapper.product.ProductMapper;
import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.catalog.category.ReadableCategoryProducts;
import com.salesmanager.shop.model.catalog.category.ReadableCategoryProductsList;
import com.salesmanager.shop.model.catalog.product.*;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductList;
import com.salesmanager.shop.model.productAttribute.PersistableNutrientsInfo;
import com.salesmanager.shop.model.productAttribute.PersistableProduct;
import com.salesmanager.shop.model.productAttribute.PersistableProductNutrientsFact;
import com.salesmanager.shop.populator.catalog.*;
import com.salesmanager.shop.store.api.exception.OperationNotAllowedException;
import com.salesmanager.shop.store.api.exception.ResourceDuplicateException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.category.facade.CategoryFacade;
import com.salesmanager.shop.store.controller.product.facade.ProductFacade;
import com.salesmanager.shop.store.model.paging.PaginationData;
import com.salesmanager.shop.utils.DateUtil;
import com.salesmanager.shop.utils.ImageFilePath;
import java.util.*;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("productFacade")
// todo : need to check , will it be available for specific profile or not
// @Profile({"default", "cloud", "gcp", "aws", "mysql","prod","dev0","local","qa0"})
public class ProductFacadeImpl implements ProductFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductFacadeImpl.class);

  @Inject private CategoryService categoryService;

  @Inject private FlavourService flavourService;

  @Inject private CategoryFlavourCountService categoryFlavourCountService;

  @Inject private CategoryPackCountService categoryPackCountService;

  @Inject private CategoryFacade categoryFacade;

  @Inject private LanguageService languageService;

  @Inject private ProductService productService;

  @Inject private PricingService pricingService;

  @Inject private CustomerService customerService;

  @Inject private ProductReviewService productReviewService;

  @Inject private ProductRelationshipService productRelationshipService;

  @Inject private PersistableProductPopulator persistableProductPopulator;

  @Inject private ReadableProductPopulator readableProductPopulator;

  @Inject private ReadableProductDetailPopulator readableProductDetailPopulator;

  @Inject private ReadableCategoryPopulator readableCategoryPopulator;

  @Inject private ProductMapper productMapper;

  @Inject private CategoryMapper categoryMapper;
  @Inject private CacheUtil cacheUtil;

  @Inject
  @Qualifier("img")
  private ImageFilePath imageUtils;

  @Override
  public PersistablesProduct saveProduct(
      MerchantStore store, PersistablesProduct product, Language language) {

    String manufacturer = Manufacturer.DEFAULT_MANUFACTURER;
    if (product.getProductSpecifications() != null) {
      manufacturer = product.getProductSpecifications().getManufacturer();
    } else {
      ProductSpecification specifications = new ProductSpecification();
      specifications.setManufacturer(manufacturer);
    }

    Product target = null;
    if (product.getId() != null && product.getId().longValue() > 0) {
      target = productService.getById(product.getId());
    } else {
      target = new Product();
    }

    try {
      persistableProductPopulator.populate(product, target, store, language);
      if (target.getId() != null && target.getId() > 0) {
        productService.update(target);
      } else {
        productService.create(target);
        product.setId(target.getId());
      }

      return product;
    } catch (Exception e) {
      throw new ServiceRuntimeException(e);
    }
  }

  public void updateProduct(MerchantStore store, PersistablesProduct product, Language language) {

    Validate.notNull(product, "Product must not be null");
    Validate.notNull(product.getId(), "Product id must not be null");

    // get original product
    Product productModel = productService.getById(product.getId());

    // merge original product with persistable product

    /*
     * String manufacturer = Manufacturer.DEFAULT_MANUFACTURER; if
     * (product.getProductSpecifications() != null) { manufacturer =
     * product.getProductSpecifications().getManufacturer(); } else {
     * ProductSpecification specifications = new ProductSpecification();
     * specifications.setManufacturer(manufacturer); }
     *
     * Product target = null; if (product.getId() != null &&
     * product.getId().longValue() > 0) { target =
     * productService.getById(product.getId()); } else { target = new
     * Product(); }
     *
     *
     * try { persistableProductPopulator.populate(product, target, store,
     * language); productService.create(target);
     * product.setId(target.getId()); return product; } catch (Exception e)
     * { throw new ServiceRuntimeException(e); }
     */

  }

  @Override
  public ReadableProducts getProduct(MerchantStore store, Long id, Language language)
      throws Exception {

    Product product = productService.getById(id);

    if (product == null) {
      throw new ResourceNotFoundException("Product [" + id + "] not found");
    }

    if (product.getMerchantStore().getId() != store.getId()) {
      throw new ResourceNotFoundException(
          "Product [" + id + "] not found for store [" + store.getId() + "]");
    }

    ReadableProducts readableProducts = new ReadableProducts();

    ReadableProductsPopulator populator = new ReadableProductsPopulator();

    populator.setPricingService(pricingService);
    populator.setimageUtils(imageUtils);
    readableProducts = populator.populate(product, readableProducts, store, language);

    return readableProducts;
  }

  @Override
  public ReadableProducts getProduct(MerchantStore store, String sku, Language language)
      throws Exception {

    Product product = productService.getByCode(sku, language);

    if (product == null) {
      return null;
    }

    ReadableProducts readableProducts = new ReadableProducts();

    ReadableProductsPopulator populator = new ReadableProductsPopulator();

    populator.setPricingService(pricingService);
    populator.setimageUtils(imageUtils);
    populator.populate(product, readableProducts, store, language);

    return readableProducts;
  }

  @Override
  public ReadableProducts updateProductPrice(
      ReadableProducts product, ProductPriceEntity price, Language language) throws Exception {

    Product persistable = productService.getById(product.getId());

    if (persistable == null) {
      throw new Exception("product is null for id " + product.getId());
    }

    java.util.Set<ProductAvailability> availabilities = persistable.getAvailabilities();
    for (ProductAvailability availability : availabilities) {
      ProductPrice productPrice = availability.defaultPrice();
      productPrice.setProductPriceAmount(price.getOriginalPrice());
      if (price.isDiscounted()) {
        productPrice.setProductPriceSpecialAmount(price.getDiscountedPrice());
        if (!StringUtils.isBlank(price.getDiscountStartDate())) {
          Date startDate = DateUtil.getDate(price.getDiscountStartDate());
          productPrice.setProductPriceSpecialStartDate(startDate);
        }
        if (!StringUtils.isBlank(price.getDiscountEndDate())) {
          Date endDate = DateUtil.getDate(price.getDiscountEndDate());
          productPrice.setProductPriceSpecialEndDate(endDate);
        }
      }
    }

    productService.update(persistable);

    ReadableProducts readableProducts = new ReadableProducts();

    ReadableProductsPopulator populator = new ReadableProductsPopulator();

    populator.setPricingService(pricingService);
    populator.setimageUtils(imageUtils);
    populator.populate(persistable, readableProducts, persistable.getMerchantStore(), language);

    return readableProducts;
  }

  @Override
  public ReadableProducts updateProductQuantity(
      ReadableProducts product, int quantity, Language language) throws Exception {
    Product persistable = productService.getById(product.getId());

    if (persistable == null) {
      throw new Exception("product is null for id " + product.getId());
    }

    java.util.Set<ProductAvailability> availabilities = persistable.getAvailabilities();
    for (ProductAvailability availability : availabilities) {
      availability.setProductQuantity(quantity);
    }

    productService.update(persistable);

    ReadableProducts readableProducts = new ReadableProducts();

    ReadableProductsPopulator populator = new ReadableProductsPopulator();

    populator.setPricingService(pricingService);
    populator.setimageUtils(imageUtils);
    populator.populate(persistable, readableProducts, persistable.getMerchantStore(), language);

    return readableProducts;
  }

  @Override
  public void deleteProduct(Product product) throws Exception {
    productService.delete(product);
  }

  @Override
  public ReadableProductsList getProductListsByCriterias(
      MerchantStore store, Language language, ProductCriteria criterias) throws Exception {

    Validate.notNull(criterias, "ProductCriteria must be set for this product");

    /** This is for category * */
    if (CollectionUtils.isNotEmpty(criterias.getCategoryIds())) {

      if (criterias.getCategoryIds().size() == 1) {

        com.salesmanager.core.model.catalog.category.Category category =
            categoryService.getById(criterias.getCategoryIds().get(0));

        if (category != null) {
          String lineage =
              new StringBuilder().append(category.getLineage()).append(Constants.SLASH).toString();

          List<com.salesmanager.core.model.catalog.category.Category> categories =
              categoryService.getListByLineage(store, lineage);

          List<Long> ids = new ArrayList<Long>();
          if (categories != null && categories.size() > 0) {
            for (com.salesmanager.core.model.catalog.category.Category c : categories) {
              ids.add(c.getId());
            }
          }
          ids.add(category.getId());
          criterias.setCategoryIds(ids);
        }
      }
    }

    com.salesmanager.core.model.catalog.product.ProductList products =
        productService.listByStore(store, language, criterias);

    ReadableProductsPopulator populator = new ReadableProductsPopulator();
    populator.setPricingService(pricingService);
    populator.setimageUtils(imageUtils);

    ReadableProductsList productList = new ReadableProductsList();
    for (Product product : products.getProducts()) {

      // create new proxy product
      ReadableProducts readProduct =
          populator.populate(product, new ReadableProducts(), store, language);
      productList.getProducts().add(readProduct);
    }

    // productList.setTotalPages(products.getTotalCount());
    productList.setRecordsTotal(products.getTotalCount());
    productList.setNumber(
        products.getTotalCount() >= criterias.getMaxCount()
            ? products.getTotalCount()
            : criterias.getMaxCount());

    int lastPageNumber = (int) (Math.ceil(products.getTotalCount() / criterias.getPageSize()));
    productList.setTotalPages(lastPageNumber);

    return productList;
  }

  @Override
  public ReadableProducts addProductToCategory(
      Category category, Product product, Language language) throws Exception {

    Validate.notNull(category, "Category cannot be null");
    Validate.notNull(product, "Product cannot be null");

    // not alloweed if category already attached
    List<Category> assigned =
        product.getCategories().stream()
            .filter(cat -> cat.getId().longValue() == category.getId().longValue())
            .collect(Collectors.toList());

    if (assigned.size() > 0) {
      throw new OperationNotAllowedException(
          "Category with id ["
              + category.getId()
              + "] already attached to product ["
              + product.getId()
              + "]");
    }

    product.getCategories().add(category);

    productService.update(product);

    ReadableProducts readableProducts = new ReadableProducts();

    ReadableProductsPopulator populator = new ReadableProductsPopulator();

    populator.setPricingService(pricingService);
    populator.setimageUtils(imageUtils);
    populator.populate(product, readableProducts, product.getMerchantStore(), language);

    return readableProducts;
  }

  @Override
  public ReadableProducts removeProductFromCategory(
      Category category, Product product, Language language) throws Exception {

    Validate.notNull(category, "Category cannot be null");
    Validate.notNull(product, "Product cannot be null");

    product.getCategories().remove(category);
    productService.update(product);

    ReadableProducts readableProducts = new ReadableProducts();

    ReadableProductsPopulator populator = new ReadableProductsPopulator();

    populator.setPricingService(pricingService);
    populator.setimageUtils(imageUtils);
    populator.populate(product, readableProducts, product.getMerchantStore(), language);

    return readableProducts;
  }

  @Override
  public ReadableProducts getProductByCode(
      MerchantStore store, String uniqueCode, Language language) throws Exception {

    Product product = productService.getByCode(uniqueCode, language);

    ReadableProducts readableProducts = new ReadableProducts();

    ReadableProductsPopulator populator = new ReadableProductsPopulator();

    populator.setPricingService(pricingService);
    populator.setimageUtils(imageUtils);
    populator.populate(product, readableProducts, product.getMerchantStore(), language);

    return readableProducts;
  }

  @Override
  public void saveOrUpdateReview(
      PersistableProductReview review, MerchantStore store, Language language) throws Exception {
    PersistableProductReviewPopulator populator = new PersistableProductReviewPopulator();
    populator.setLanguageService(languageService);
    populator.setCustomerService(customerService);
    populator.setProductService(productService);

    com.salesmanager.core.model.catalog.product.review.ProductReview rev =
        new com.salesmanager.core.model.catalog.product.review.ProductReview();
    populator.populate(review, rev, store, language);

    if (review.getId() == null) {
      productReviewService.create(rev);
    } else {
      productReviewService.update(rev);
    }

    review.setId(rev.getId());
  }

  @Override
  public void deleteReview(ProductReview review, MerchantStore store, Language language)
      throws Exception {
    productReviewService.delete(review);
  }

  @Override
  public List<ReadableProductReview> getProductReviews(
      Product product, MerchantStore store, Language language) throws Exception {

    List<ProductReview> reviews = productReviewService.getByProduct(product);

    ReadableProductReviewPopulator populator = new ReadableProductReviewPopulator();

    List<ReadableProductReview> productReviews = new ArrayList<ReadableProductReview>();

    for (ProductReview review : reviews) {
      ReadableProductReview readableReview = new ReadableProductReview();
      populator.populate(review, readableReview, store, language);
      productReviews.add(readableReview);
    }

    return productReviews;
  }

  @Override
  public List<ReadableProducts> relatedItems(
      MerchantStore store, Product product, Language language) throws Exception {
    ReadableProductsPopulator populator = new ReadableProductsPopulator();
    populator.setPricingService(pricingService);
    populator.setimageUtils(imageUtils);

    List<ProductRelationship> relatedItems =
        productRelationshipService.getByType(store, product, ProductRelationshipType.RELATED_ITEM);
    if (relatedItems != null && relatedItems.size() > 0) {
      List<ReadableProducts> items = new ArrayList<ReadableProducts>();
      for (ProductRelationship relationship : relatedItems) {
        Product relatedProduct = relationship.getRelatedProduct();
        ReadableProducts proxyProduct =
            populator.populate(relatedProduct, new ReadableProducts(), store, language);
        items.add(proxyProduct);
      }
      return items;
    }
    return null;
  }

  @Override
  public void update(
      Long productId, LightPersistableProduct product, MerchantStore merchant, Language language) {
    // Get product
    Product modified = productService.findOne(productId, merchant);

    // Update product with minimal set
    modified.setAvailable(product.isAvailable());

    for (ProductAvailability availability : modified.getAvailabilities()) {
      availability.setProductQuantity(product.getQuantity());
      if (!StringUtils.isBlank(product.getPrice())) {
        // set default price
        for (ProductPrice price : availability.getPrices()) {
          if (price.isDefaultPrice()) {
            try {
              price.setProductPriceAmount(pricingService.getAmount(product.getPrice()));
            } catch (ServiceException e) {
              throw new ServiceRuntimeException("Invalid product price format");
            }
          }
        }
      }
    }

    try {
      productService.save(modified);
    } catch (ServiceException e) {
      throw new ServiceRuntimeException("Cannot update product ", e);
    }
  }

  @Override
  public boolean exists(String sku, MerchantStore store) {
    boolean exists = false;
    Product product = productService.getByCode(sku, store.getDefaultLanguage());
    if (product != null
        && product.getMerchantStore().getId().intValue() == store.getId().intValue()) {
      exists = true;
    }
    return exists;
  }

  @Override
  public Product getProduct(String sku, MerchantStore store) {
    return productService.getByCode(sku, store.getDefaultLanguage());
  }

  @Override
  public void deleteProduct(Long id, MerchantStore store) {

    Validate.notNull(id, "Product id cannot be null");
    Product product = getProduct(id);
    try {
      removeFlavourAndPackCount(product, store);
      productService.delete(product);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_DELETE_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_DELETE_FAILURE.getErrorMessage() + id);
    }
  }

  private void removeFlavourAndPackCount(Product product, MerchantStore store)
      throws ServiceException {
    Category category = product.getCategories().iterator().next();
    Flavour flavour = product.getFlavour();
    Iterator<CategoryFlavourCount> categoryFlavourCountIterator =
        category.getCategoryFlavourCounts().iterator();
    while (Objects.nonNull(flavour) && categoryFlavourCountIterator.hasNext()) {
      CategoryFlavourCount categoryFlavourCount = categoryFlavourCountIterator.next();
      if (categoryFlavourCount.getFlavourId() == flavour.getId()) {
        categoryFlavourCount.setCount(categoryFlavourCount.getCount() - 1);
      }
      if (categoryFlavourCount.getCount() == 0) {
        category.getFlavours().remove(flavour);
        categoryFlavourCountService.delete(categoryFlavourCount);
      }
    }

    Pack pack = product.getPackSize();
    Iterator<CategoryPackCount> categoryPackCountIterator =
        category.getCategoryPackCounts().iterator();
    while (Objects.nonNull(pack) && categoryPackCountIterator.hasNext()) {
      CategoryPackCount categoryPackCount = categoryPackCountIterator.next();
      if (categoryPackCount.getPackId() == pack.getId()) {
        categoryPackCount.setCount(categoryPackCount.getCount() - 1);
      }
      if (categoryPackCount.getCount() == 0) {
        category.getPackSize().remove(pack);
        categoryPackCountService.delete(categoryPackCount);
      }
    }
    categoryFacade.setParentDetailsAtChildLevel(category, store);
    categoryService.createOrUpdate(category);
  }

  @Override
  public FilterCriteria getHomeFilters() throws ServiceException {
    // Todo Features filter need to implement
    FilterCriteria filterCriteria = new FilterCriteria();
    // categoryFilter
    List<Category> categoryList = categoryService.getCategoryIds();
    List<HomeFilter> categoryListFilter = new ArrayList<>();
    for (Category category : categoryList) {
      HomeFilter homeFilter = new HomeFilter();
      if (!category.getCategoryDetails().getName().equals(HabbitCoreConstant.MERCHANDISE)) {
        homeFilter.setId(category.getId());
        homeFilter.setName(category.getCategoryDetails().getName());
        categoryListFilter.add(homeFilter);
      }
    }
    filterCriteria.setProductTypeCategoryList(categoryListFilter);
    // flavourFilter
    List<Flavour> flavourList = flavourService.getFlavoursByCriteria(new Criteria());
    List<HomeFilter> flavourListFilter = new ArrayList<>();
    for (Flavour flavour : flavourList) {
      HomeFilter homeFilter = new HomeFilter();
      homeFilter.setId(flavour.getId());
      homeFilter.setName(flavour.getName());
      flavourListFilter.add(homeFilter);
    }
    filterCriteria.setProductFlavourTypeList(flavourListFilter);
    return filterCriteria;
  }

  @Override
  public ReadableCategoryProductsList getAllCategoryProductLists(
      MerchantStore store, Language language, int count) throws Exception {

    ProductCriteria criteria = new ProductCriteria();
    List<Long> categoryIds = new ArrayList<>();
    List<Category> categoryList = new LinkedList<>();
    try {
      categoryList = categoryService.getCategoryIds();
      if (Objects.isNull(categoryList)) {
        return new ReadableCategoryProductsList();
      }
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.CATEGORY_GET_ALL_UNIQUE_CATEGORY_IDS_FAILURE.getErrorCode(),
          AttributeErrorCodes.CATEGORY_GET_ALL_UNIQUE_CATEGORY_IDS_FAILURE.getErrorMessage());
    }
    for (Category category : categoryList) {
      if (!category.getCategoryDetails().getName().equals(HabbitCoreConstant.MERCHANDISE)) {
        categoryIds.add(category.getId());
      }
    }
    criteria.setProductTypeCategoryIds(categoryIds);
    List<Product> products = new LinkedList<>();
    try {
      products = productService.getProductListForHome(store, language, criteria, count);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_GET_FOR_HOME_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_GET_FOR_HOME_FAILURE.getErrorMessage());
    }
    readableCategoryPopulator.setPricingService(pricingService);
    readableCategoryPopulator.setImageUtils(imageUtils);
    readableProductPopulator.setPricingService(pricingService);
    readableProductPopulator.setImageUtils(imageUtils);
    ReadableCategoryProductsList categoryProductList = new ReadableCategoryProductsList();
    Map<Long, ReadableCategoryProducts> readableCategorysMap = new HashMap<>();
    List<ReadableCategoryProducts> readableCategoryProductsList = new ArrayList<>();
    // TODO persist featuredProduct as 1st Product in ProductList
    for (Product product : products) {
      ReadableCategory readableCategory = new ReadableCategory();
      ReadableCategoryProducts readableCategoryProducts = new ReadableCategoryProducts();
      // create new proxy product
      readableCategory =
          readableCategoryPopulator.populateFromProduct(product, readableCategory, store, language);
      readableCategoryProducts.setCategory(readableCategory);
      List<ReadableProduct> readableProducts = new ArrayList<>();
      ReadableProduct readableProduct = new ReadableProduct();
      readableProducts.add(
          readableProductPopulator.populate(product, readableProduct, store, language));

      if (readableCategorysMap.containsKey(readableCategory.getId())) {
        readableCategorysMap.get(readableCategory.getId()).getProductList().add(readableProduct);
      } else {
        readableCategoryProducts.setProductList(readableProducts);
        readableCategorysMap.put(readableCategory.getId(), readableCategoryProducts);
      }
    }
    readableCategoryProductsList.addAll(readableCategorysMap.values());
    categoryProductList.setCategoryProductsList(readableCategoryProductsList);

    return categoryProductList;
  }

  @Override
  public ReadableProductDetails getProductDetail(
      MerchantStore store, Long id, Language language, String skuId) throws Exception {

    ReadableProductDetails readableProduct = new ReadableProductDetails();
    Product product = null;
    try {
      if (id != -1) {
        product = productService.getById(id);
      } else if (skuId != null) {
        product = productService.getBySkuId(skuId);
      }

    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_GET_BY_PRODUCT_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_GET_BY_PRODUCT_ID_FAILURE.getErrorMessage() + id);
    }
    Long flavourId = null;
    if (Objects.nonNull(product.getFlavour())) {
      flavourId = product.getFlavour().getId();
    }
    Long categoryId = 0L;
    if (Objects.nonNull(product.getCategories()))
      categoryId = product.getCategories().iterator().next().getId();

    try {
      getRecommendedProductListForFlavorIdAndCategoryIdAndProductId(
          store, product.getName(), flavourId, categoryId, readableProduct);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_GET_BY_CATEGORY_FLAVOR_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_GET_BY_CATEGORY_FLAVOR_FAILURE.getErrorMessage());
    }

    if (product == null) {
      throw new ResourceNotFoundException("Product [" + id + "] not found");
    }

    readableProductDetailPopulator.setPricingService(pricingService);
    readableProductDetailPopulator.setImageUtils(imageUtils);
    readableProduct =
        readableProductDetailPopulator.populate(product, readableProduct, store, language);

    return readableProduct;
  }

  private ReadableProductDetails getRecommendedProductListForFlavorIdAndCategoryIdAndProductId(
      MerchantStore store,
      String productName,
      Long flavourId,
      Long categoryId,
      ReadableProductDetails readableProduct)
      throws ServiceException {
    List<Product> recommendedProducts =
        productService.getProductDetailsByFlavorIdAndCategoryId(
            store, flavourId, categoryId, productName);
    return readableProductDetailPopulator.populateRecommendedProductIList(
        readableProduct, recommendedProducts);
  }

  @Override
  public ReadableProductDetails getProductDetailsByCategoryFlavorAndPack(
      Boolean productFlag,
      MerchantStore merchantStore,
      Long categoryId,
      Long flavourId,
      Long packId,
      Language language)
      throws Exception {
    ReadableProductDetails readableProduct = new ReadableProductDetails();
    if (packId == null) {
      // default packsize
      Category category = null;
      try {
        category = categoryService.getById(categoryId);
      } catch (Exception e) {
        throwServiceRuntImeException(
            e,
            AttributeErrorCodes.CATEGORY_GET_BY_ID_FAILURE.getErrorCode(),
            AttributeErrorCodes.CATEGORY_GET_BY_ID_FAILURE.getErrorMessage() + categoryId);
      }
      packId = category.getCategoryDetails().getDefaultPackSize().getId();
    }

    Product product = null;
    try {
      product =
          productService.getProductDetailsByCategoryFlavorAndPack(
              productFlag, merchantStore, categoryId, flavourId, packId);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_DETAILS_BY_CATEGORY_FLAVOR_PACK_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_DETAILS_BY_CATEGORY_FLAVOR_PACK_FAILURE.getErrorMessage());
    }
    readableProductDetailPopulator.setPricingService(pricingService);
    readableProductDetailPopulator.setImageUtils(imageUtils);
    readableProduct =
        readableProductDetailPopulator.populate(product, readableProduct, merchantStore, language);
    try {
      getRecommendedProductListForFlavorIdAndCategoryIdAndProductId(
          merchantStore, product.getName(), flavourId, categoryId, readableProduct);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_GET_BY_CATEGORY_FLAVOR_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_GET_BY_CATEGORY_FLAVOR_FAILURE.getErrorMessage());
    }
    return readableProduct;
  }

  @Override
  public ReadableProductList getFilteredProductLists(
      MerchantStore store, Language language, ProductCriteria criterias)
      throws ConversionException, ServiceException {

    List<Product> products = new LinkedList<>();
    try {
      products = productService.listByFilter(store, language, criterias);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_GET_BY_FILTER_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_GET_BY_FILTER_FAILURE.getErrorMessage());
    }
    readableProductPopulator.setPricingService(pricingService);
    readableProductPopulator.setImageUtils(imageUtils);
    List<ReadableProduct> readableProducts = new ArrayList<>();
    for (Product product : products) {
      ReadableProduct readableProduct = new ReadableProduct();
      readableProducts.add(
          readableProductPopulator.populateFilteredProduct(
              product, readableProduct, store, language));
    }
    ReadableProductList readableProductList = new ReadableProductList();
    readableProductList.setProducts(readableProducts);
    return readableProductList;
  }

  @Override
  public ReadableCategoryProducts getCategoryProductLists(
      Long categoryId, MerchantStore store, Language language)
      throws ConversionException, ServiceException {
    Validate.notNull(categoryId, "ProductCriteria must be set for this category");

    ProductCriteria criteria = new ProductCriteria();
    List<Long> categoryIds = new ArrayList<>();
    categoryIds.add(categoryId);
    criteria.setProductTypeCategoryIds(categoryIds);
    List<Product> products = new LinkedList<>();
    try {
      products = productService.listByFilter(store, language, criteria);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_GET_BY_FILTER_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_GET_BY_FILTER_FAILURE.getErrorMessage());
    }

    readableCategoryPopulator.setPricingService(pricingService);
    readableCategoryPopulator.setImageUtils(imageUtils);
    readableProductPopulator.setImageUtils(imageUtils);
    readableProductPopulator.setPricingService(pricingService);
    ReadableCategoryProducts readableCategoryProducts = new ReadableCategoryProducts();
    ReadableCategory readableCategory = new ReadableCategory();

    Category category =
        Optional.ofNullable(categoryService.getOneByLanguage(categoryId, language))
            .orElseThrow(
                () -> new ResourceNotFoundException("Category not found for id " + categoryId));

    readableCategoryProducts.setCategory(
        readableCategoryPopulator.populateCategorySelected(
            category, readableCategory, store, language));
    List<ReadableProduct> readableProducts = new ArrayList<>();
    for (Product product : products) {
      ReadableProduct readableProduct = new ReadableProduct();
      readableProducts.add(
          readableProductPopulator.populateFilteredProduct(
              product, readableProduct, store, language));
    }
    readableCategoryProducts.setProductList(readableProducts);
    return readableCategoryProducts;
  }

  /** Admin Portal CRUD Operation API's */

  /**
   * @param persistableProduct
   * @param merchantStore
   */
  @Override
  public com.salesmanager.shop.model.productAttribute.PersistableProduct createProduct(
      com.salesmanager.shop.model.productAttribute.PersistableProduct persistableProduct,
      MerchantStore merchantStore) {
    String sku = persistableProduct.getSku();
    if (org.apache.commons.lang.StringUtils.isNotEmpty(sku)
        && (persistableProduct.getId() == null || persistableProduct.getId().longValue() == 0)) {
      Product productExist = null;
      productExist = getProductBySkuId(sku);
      if (Objects.nonNull(productExist)) {
        LOGGER.error("Product Already exists for sku {}", sku);
        throw new ResourceDuplicateException("Product Already exists for " + sku);
      }
    }
    Product product = null;
    try {
      product = productMapper.toProduct(persistableProduct);
      setParentDetailsAtChildLevel(product, persistableProduct, merchantStore);
      product = productService.create(product);
      addFlavourAndPackCount(product, merchantStore);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_CREATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_CREATE_FAILURE.getErrorMessage());
    }
    // persist sku to productName in jedis
    cacheUtil.setObjectInCache(product.getSku(), product.getName());
    return productMapper.toPersistableProduct(product);
  }

  private void addFlavourAndPackCount(Product product, MerchantStore merchantStore)
      throws ServiceException {
    Category category = product.getCategories().iterator().next();
    Flavour flavour = product.getFlavour();
    Iterator<CategoryFlavourCount> categoryFlavourCountiterator =
        category.getCategoryFlavourCounts().iterator();
    boolean flavourExistFlag = false;
    while (Objects.nonNull(flavour) && categoryFlavourCountiterator.hasNext()) {
      CategoryFlavourCount categoryFlavourCount = categoryFlavourCountiterator.next();
      if (categoryFlavourCount.getFlavourId() == flavour.getId()) {
        categoryFlavourCount.setCount(categoryFlavourCount.getCount() + 1);
        category.getFlavours().add(flavour);
        flavourExistFlag = true;
      }
    }
    if (!flavourExistFlag && Objects.nonNull(flavour)) {
      category.getFlavours().add(flavour);
      CategoryFlavourCount categoryFlavourCount = new CategoryFlavourCount();
      categoryFlavourCount.setFlavourId(flavour.getId());
      categoryFlavourCount.setCount(1);
      categoryFlavourCount.setCategory(category);
      categoryFlavourCount = categoryFlavourCountService.create(categoryFlavourCount);
      category.getCategoryFlavourCounts().add(categoryFlavourCount);
    }

    Pack pack = product.getPackSize();
    Iterator<CategoryPackCount> categoryPackCountIterator =
        category.getCategoryPackCounts().iterator();
    boolean packExistFlag = false;
    while (Objects.nonNull(pack) && categoryPackCountIterator.hasNext()) {
      CategoryPackCount categoryPackCount = categoryPackCountIterator.next();
      if (categoryPackCount.getPackId() == pack.getId()) {
        categoryPackCount.setCount(categoryPackCount.getCount() + 1);
        category.getPackSize().add(pack);
        packExistFlag = true;
      }
    }
    if (!packExistFlag && Objects.nonNull(pack)) {
      category.getPackSize().add(pack);
      CategoryPackCount categoryPackCount = new CategoryPackCount();
      categoryPackCount.setPackId(pack.getId());
      categoryPackCount.setCount(1);
      categoryPackCount.setCategory(category);
      categoryPackCount = categoryPackCountService.create(categoryPackCount);
      category.getCategoryPackCounts().add(categoryPackCount);
    }
    categoryFacade.setParentDetailsAtChildLevel(category, merchantStore);
    categoryService.createOrUpdate(category);
  }

  private void setParentDetailsAtChildLevel(
      Product p,
      com.salesmanager.shop.model.productAttribute.PersistableProduct persistableProduct,
      MerchantStore store) {
    p.setMerchantStore(store);
    ProductNutritionalInfo productNutritionalInfo = p.getProductNutritionalInfo();
    if (Objects.nonNull(productNutritionalInfo)) {
      if (Objects.nonNull(productNutritionalInfo.getProductNutrientsFacts())) {
        p.getProductNutritionalInfo().getProductNutrientsFacts().stream()
            .forEach(
                productNutrientsFact ->
                    productNutrientsFact.setProductNutritionalInfo(productNutritionalInfo));
      }
    }
    Set<ProductAvailability> productAvailabilities = p.getAvailabilities();
    if (Objects.nonNull(productAvailabilities)) {
      p.getAvailabilities().stream()
          .forEach(productAvailability -> productAvailability.setProduct(p));
    }
  }

  private Product getProductBySkuId(String sku) {
    Product product = null;
    try {
      product = productService.getBySkuId(sku);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_GET_BY_PRODUCT_SKU_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_GET_BY_PRODUCT_SKU_ID_FAILURE.getErrorMessage() + sku);
    }
    return product;
  }

  /**
   * @param persistableProduct
   * @param merchantStore
   */
  @Override
  public com.salesmanager.shop.model.productAttribute.PersistableProduct updateProduct(
      com.salesmanager.shop.model.productAttribute.PersistableProduct persistableProduct,
      MerchantStore merchantStore) {
    Product product = null;
    try {
      product = productMapper.toProduct(persistableProduct);
      setParentDetailsAtChildLevel(product, persistableProduct, merchantStore);
      product = productService.update(product);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_UPDATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_UPDATE_FAILURE.getErrorMessage());
    }
    return productMapper.toPersistableProduct(product);
  }

  /**
   * @param skuId
   * @param merchantStore
   * @return
   */
  @Override
  public com.salesmanager.shop.model.productAttribute.PersistableProduct getProductBySkuId(
      String skuId, MerchantStore merchantStore) {
    Validate.notNull(skuId, "Product id cannot be null");
    Product product = getProductBySkuId(skuId);
    return productMapper.toPersistableProduct(product);
  }

  /**
   * @param id
   * @param merchantStore
   * @return
   */
  @Override
  public com.salesmanager.shop.model.productAttribute.PersistableProduct getProduct(
      Long id, MerchantStore merchantStore) {
    Validate.notNull(id, "Product id cannot be null");
    Product product = getProduct(id);
    PersistableProduct persistableProduct = productMapper.toPersistableProduct(product);
    LinkedHashSet<PersistableProductNutrientsFact> productNutrientsFacts =
        persistableProduct.getProductNutritionalInfo().getProductNutrientsFacts();
    LinkedHashSet<PersistableNutrientsInfo> productNutrientsInfo =
        persistableProduct.getProductNutritionalInfo().getProductNutrientsInfo();

    List<PersistableNutrientsInfo> listNutrientInfo = new ArrayList<>(productNutrientsInfo);
    List<PersistableProductNutrientsFact> listFact = new ArrayList<>(productNutrientsFacts);

    Collections.sort(listNutrientInfo);
    Collections.sort(listFact);

    persistableProduct
        .getProductNutritionalInfo()
        .setProductNutrientsInfo(new LinkedHashSet<>(listNutrientInfo));
    persistableProduct
        .getProductNutritionalInfo()
        .setProductNutrientsFacts(new LinkedHashSet<>(listFact));

    return persistableProduct;
  }

  private Product getProduct(Long id) {
    Product product = null;
    try {
      product = productService.getById(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_GET_BY_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_GET_BY_ID_FAILURE.getErrorMessage() + id);
    }
    if (Objects.isNull(product)) {
      LOGGER.error("Product with id [" + id + " ] not found in DB.");
      throw new ResourceNotFoundException("Product not found");
    }
    return product;
  }

  /**
   * @param page
   * @param pageSize
   * @param merchantStore
   * @return
   */
  @Override
  public PersistableProductList getAllUniqueProduct(
      Integer page, Integer pageSize, MerchantStore merchantStore) {
    PersistableProductList persistableProductList = new PersistableProductList();
    PaginationData paginationData = new PaginationData(pageSize, page);

    Criteria criteria = new Criteria();
    criteria.setStartIndex(paginationData.getOffset() - 1);
    criteria.setPageSize(pageSize);
    criteria.setMaxCount(pageSize);

    try {
      persistableProductList.setProducts(
          productService.getProductsByCriteria(criteria).stream()
              .map(productMapper::toPersistableProduct)
              .collect(Collectors.toList()));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_GET_ALL_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_GET_ALL_FAILURE.getErrorMessage());
    }
    return persistableProductList;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
