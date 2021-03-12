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
import com.salesmanager.core.model.catalog.product.Pack;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.ProductCriteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.cache.util.CacheUtil;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.mapper.product.*;
import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.catalog.category.ReadableCategoryProducts;
import com.salesmanager.shop.model.catalog.category.ReadableCategoryProductsList;
import com.salesmanager.shop.model.catalog.category.ReadableMerchandiseCategoryProducts;
import com.salesmanager.shop.model.catalog.product.ReadableMerchandiseProductDetails;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.catalog.product.ReadableProductDetails;
import com.salesmanager.shop.model.catalog.product.attribute.ReadablePack;
import com.salesmanager.shop.model.productAttribute.PersistableProduct;
import com.salesmanager.shop.populator.catalog.PersistableProductPopulator;
import com.salesmanager.shop.populator.catalog.ReadableCategoryPopulator;
import com.salesmanager.shop.populator.catalog.ReadableProductDetailPopulator;
import com.salesmanager.shop.populator.catalog.ReadableProductPopulator;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.category.facade.CategoryFacade;
import com.salesmanager.shop.store.controller.product.facade.MerchandiseProductFacade;
import com.salesmanager.shop.store.controller.product.facade.ProductFacade;
import com.salesmanager.shop.utils.ImageFilePath;
import java.util.*;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("merchandiseProductFacade")
// todo : need to check , will it be available for specific profile or not
// @Profile({"default", "cloud", "gcp", "aws", "mysql"})
public class MerchandiseProductFacadeImpl implements MerchandiseProductFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(MerchandiseProductFacadeImpl.class);

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
  @Inject private MerchandiseProductDetailsMapper merchandiseProductDetailsMapper;
  @Inject private ProductFacade productFacade;
  @Inject private MerchandiseProductMapper merchandiseProductMapper;
  @Inject private MerchandiseCategoryMapper merchandiseCategoryMapper;
  @Inject private CacheUtil cacheUtil;

  @Inject
  @Qualifier("img")
  private ImageFilePath imageUtils;

  @Override
  public ReadableMerchandiseProductDetails getProductDetail(
      MerchantStore store, Long id, Language language, String skuId, String productName)
      throws Exception {
    ProductCriteria criteria = new ProductCriteria();
    criteria.setCategoryName(HabbitCoreConstant.MERCHANDISE);
    if (StringUtils.isEmpty(productName)) {
      productName = cacheUtil.getObjectFromCache(skuId, String.class);
    }
    if (StringUtils.isEmpty(productName)) {
      PersistableProduct p = productFacade.getProductBySkuId(skuId, store);
      productName = p.getName();
      // persist sku to productName in jedis
      cacheUtil.setObjectInCache(p.getSku(), p.getName());
    }
    criteria.setProductName(productName);
    List<Product> products = new LinkedList<>();
    try {
      products = productService.listByMerchandiseProductName(store, language, criteria);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_GET_BY_MERCHANDISE_PRODUCT_NAME_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_GET_BY_MERCHANDISE_PRODUCT_NAME_FAILURE.getErrorMessage());
    }
    ReadableProductDetails readableProduct = new ReadableProductDetails();
    Product product = null;
    for (Product productModel : products) {
      if (productModel.getSku().equals(skuId)) {
        product = productModel;
      }
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
    // readableProduct.setAvailablePackSizeList(null);
    List<ReadablePack> availablePackSizeList = new ArrayList<>();
    for (Product productModel : products) {
      if (Objects.nonNull(productModel.getPackSize())) {
        Pack pack = productModel.getPackSize();
        ReadablePack readablePack = new ReadablePack();
        readablePack.setId(pack.getId());
        readablePack.setPackSizeValue(pack.getPackSizeValue());
        if (Objects.nonNull(pack.getPackIcon())) {
          readablePack.setIconUrl(pack.getPackIcon().getIconUrl());
        }
        availablePackSizeList.add(readablePack);
      }
    }
    Collections.sort(availablePackSizeList);
    readableProduct.setAvailablePackSizeList(availablePackSizeList);

    return merchandiseProductDetailsMapper.toReadableMerchandiseProductDetails(readableProduct);
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
  public ReadableMerchandiseProductDetails getProductDetailsByCategoryFlavorAndPack(
      MerchantStore merchantStore,
      Long categoryId,
      Long packId,
      Language language,
      MerchantStore store,
      String productName)
      throws Exception {

    ReadableProductDetails readableProduct = new ReadableProductDetails();
    Product product = null;
    try {
      product =
          productService.getMerchandiseProductDetailsByCategoryFlavorAndPack(
              merchantStore, categoryId, packId, productName);
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
          merchantStore, product.getName(), -1L, categoryId, readableProduct);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_GET_BY_CATEGORY_FLAVOR_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_GET_BY_CATEGORY_FLAVOR_FAILURE.getErrorMessage());
    }

    ProductCriteria criteria = new ProductCriteria();
    criteria.setCategoryName(HabbitCoreConstant.MERCHANDISE);
    criteria.setProductName(productName);
    List<Product> products = new LinkedList<>();
    try {
      products = productService.listByMerchandiseProductName(store, language, criteria);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PRODUCT_GET_BY_MERCHANDISE_PRODUCT_NAME_FAILURE.getErrorCode(),
          AttributeErrorCodes.PRODUCT_GET_BY_MERCHANDISE_PRODUCT_NAME_FAILURE.getErrorMessage());
    }
    List<ReadablePack> availablePackSizeList = new ArrayList<>();
    for (Product productModel : products) {
      if (Objects.nonNull(productModel.getPackSize())) {
        Pack pack = productModel.getPackSize();
        ReadablePack readablePack = new ReadablePack();
        readablePack.setId(pack.getId());
        readablePack.setPackSizeValue(pack.getPackSizeValue());
        if (Objects.nonNull(pack.getPackIcon())) {
          readablePack.setIconUrl(pack.getPackIcon().getIconUrl());
        }
        availablePackSizeList.add(readablePack);
      }
    }
    Collections.sort(availablePackSizeList);
    readableProduct.setAvailablePackSizeList(availablePackSizeList);

    return merchandiseProductDetailsMapper.toReadableMerchandiseProductDetails(readableProduct);
  }

  @Override
  public ReadableMerchandiseCategoryProducts getProductList(
      Long id, MerchantStore merchantStore, Language language)
      throws ServiceException, ConversionException {
    ProductCriteria criteria = new ProductCriteria();
    List<Long> categoryIds = new ArrayList<>();
    categoryIds.add(id);
    criteria.setProductTypeCategoryIds(categoryIds);
    List<Product> products = new LinkedList<>();
    try {
      products = productService.getProductListForHome(merchantStore, language, criteria, 0);
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
          readableCategoryPopulator.populateFromMerchandisesProduct(
              product, readableCategory, merchantStore, language);
      readableCategoryProducts.setCategory(readableCategory);
      List<ReadableProduct> readableProducts = new ArrayList<>();
      ReadableProduct readableProduct = new ReadableProduct();
      readableProducts.add(
          readableProductPopulator.populate(product, readableProduct, merchantStore, language));

      if (readableCategorysMap.containsKey(readableCategory.getId())) {
        readableCategorysMap.get(readableCategory.getId()).getProductList().add(readableProduct);
      } else {
        readableCategoryProducts.setProductList(readableProducts);
        readableCategorysMap.put(readableCategory.getId(), readableCategoryProducts);
      }
    }
    readableCategoryProductsList.addAll(readableCategorysMap.values());
    categoryProductList.setCategoryProductsList(readableCategoryProductsList);

    ReadableCategoryProducts readableCategoryProducts =
        categoryProductList.getCategoryProductsList().get(0);
    ReadableMerchandiseCategoryProducts readableMerchandiseCategoryProducts =
        new ReadableMerchandiseCategoryProducts();
    readableMerchandiseCategoryProducts.setCategory(
        merchandiseCategoryMapper.toReadableMerchandiseCategory(
            readableCategoryProducts.getCategory()));
    readableMerchandiseCategoryProducts.setProductList(
        readableCategoryProducts.getProductList().stream()
            .map(merchandiseProductMapper::toReadableMerchandiseProduct)
            .collect(Collectors.toList()));
    return readableMerchandiseCategoryProducts;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
