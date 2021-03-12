package com.salesmanager.shop.store.controller.product.facade;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.FilterCriteria;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.ProductCriteria;
import com.salesmanager.core.model.catalog.product.review.ProductReview;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.category.ReadableCategoryProducts;
import com.salesmanager.shop.model.catalog.category.ReadableCategoryProductsList;
import com.salesmanager.shop.model.catalog.product.*;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductList;
import java.util.List;

public interface ProductFacade {

  /**
   * Create / Update product
   *
   * @param store
   * @param product
   * @param language
   * @return
   */
  PersistablesProduct saveProduct(
      MerchantStore store, PersistablesProduct product, Language language);

  /**
   * Update minimal product details
   *
   * @param product
   * @param merchant
   * @param language
   */
  void update(
      Long productId, LightPersistableProduct product, MerchantStore merchant, Language language);

  /**
   * Get a Product by id and store
   *
   * @param store
   * @param id
   * @param language
   * @return
   * @throws Exception
   */
  ReadableProducts getProduct(MerchantStore store, Long id, Language language) throws Exception;

  /**
   * @param sku
   * @param store
   * @return
   */
  Product getProduct(String sku, MerchantStore store);

  /**
   * Reads a product by code
   *
   * @param store
   * @param uniqueCode
   * @param language
   * @return
   * @throws Exception
   */
  ReadableProducts getProductByCode(MerchantStore store, String uniqueCode, Language language)
      throws Exception;

  /**
   * Get a product by sku and store
   *
   * @param store
   * @param sku
   * @param language
   * @return
   * @throws Exception
   */
  ReadableProducts getProduct(MerchantStore store, String sku, Language language) throws Exception;

  /**
   * Sets a new price to an existing product
   *
   * @param product
   * @param price
   * @param language
   * @return
   * @throws Exception
   */
  ReadableProducts updateProductPrice(
      ReadableProducts product, ProductPriceEntity price, Language language) throws Exception;

  /**
   * Sets a new price to an existing product
   *
   * @param product
   * @param quantity
   * @param language
   * @return
   * @throws Exception
   */
  ReadableProducts updateProductQuantity(ReadableProducts product, int quantity, Language language)
      throws Exception;

  /**
   * Deletes a product for a given product id
   *
   * @param product
   * @throws Exception
   */
  void deleteProduct(Product product) throws Exception;

  /**
   * Delete product
   *
   * @param id
   * @param store
   * @throws Exception
   */
  void deleteProduct(Long id, MerchantStore store);

  /**
   * Filters a list of product based on criteria
   *
   * @param store
   * @param language
   * @param criterias
   * @return
   * @throws Exception
   */
  ReadableProductsList getProductListsByCriterias(
      MerchantStore store, Language language, ProductCriteria criterias) throws Exception;

  /**
   * Adds a product to a category
   *
   * @param category
   * @param product
   * @return
   * @throws Exception
   */
  ReadableProducts addProductToCategory(Category category, Product product, Language language)
      throws Exception;

  /**
   * Removes item from a category
   *
   * @param category
   * @param product
   * @param language
   * @return
   * @throws Exception
   */
  ReadableProducts removeProductFromCategory(Category category, Product product, Language language)
      throws Exception;

  /**
   * Saves or updates a Product review
   *
   * @param review
   * @param language
   * @throws Exception
   */
  void saveOrUpdateReview(PersistableProductReview review, MerchantStore store, Language language)
      throws Exception;

  /**
   * Deletes a product review
   *
   * @param review
   * @param store
   * @param language
   * @throws Exception
   */
  void deleteReview(ProductReview review, MerchantStore store, Language language) throws Exception;

  /**
   * Get reviews for a given product
   *
   * @param product
   * @param store
   * @param language
   * @return
   * @throws Exception
   */
  List<ReadableProductReview> getProductReviews(
      Product product, MerchantStore store, Language language) throws Exception;

  /**
   * validates if product exists
   *
   * @param sku
   * @param store
   * @return
   */
  public boolean exists(String sku, MerchantStore store);

  /**
   * Get related items
   *
   * @param store
   * @param product
   * @param language
   * @return
   * @throws Exception
   */
  List<ReadableProducts> relatedItems(MerchantStore store, Product product, Language language)
      throws Exception;

  /**
   * home filter
   *
   * @param
   * @throws Exception
   */
  FilterCriteria getHomeFilters() throws Exception;

  ReadableCategoryProductsList getAllCategoryProductLists(
      MerchantStore store, Language language, int count) throws Exception;

  /**
   * Get a Product by id and store
   *
   * @return
   * @throws Exception
   */
  ReadableProductDetails getProductDetail(
      MerchantStore merchantStore, Long id, Language language, String skuId) throws Exception;

  ReadableProductDetails getProductDetailsByCategoryFlavorAndPack(
      Boolean productFlag,
      MerchantStore merchantStore,
      Long categoryId,
      Long flavourId,
      Long packId,
      Language language)
      throws Exception;

  ReadableProductList getFilteredProductLists(
      MerchantStore merchantStore, Language language, ProductCriteria filterCriteria)
      throws ConversionException, ServiceException;

  ReadableCategoryProducts getCategoryProductLists(
      Long categoryId, MerchantStore merchantStore, Language language)
      throws ConversionException, ServiceException;

  /**
   * @param product
   * @param merchantStore
   */
  com.salesmanager.shop.model.productAttribute.PersistableProduct createProduct(
      com.salesmanager.shop.model.productAttribute.PersistableProduct product,
      MerchantStore merchantStore);

  /**
   * @param product
   * @param merchantStore
   */
  com.salesmanager.shop.model.productAttribute.PersistableProduct updateProduct(
      com.salesmanager.shop.model.productAttribute.PersistableProduct product,
      MerchantStore merchantStore);

  /**
   * @param id
   * @param store
   * @return
   * @throws Exception
   */
  com.salesmanager.shop.model.productAttribute.PersistableProduct getProduct(
      Long id, MerchantStore store) throws Exception;

  /**
   * @param skuId
   * @param merchantStore
   * @return
   */
  com.salesmanager.shop.model.productAttribute.PersistableProduct getProductBySkuId(
      String skuId, MerchantStore merchantStore);

  /**
   * @param page
   * @param pageSize
   * @param merchantStore
   * @return
   */
  PersistableProductList getAllUniqueProduct(
      Integer page, Integer pageSize, MerchantStore merchantStore) throws Exception;
}
