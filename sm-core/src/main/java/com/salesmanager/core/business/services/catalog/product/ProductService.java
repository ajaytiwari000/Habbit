package com.salesmanager.core.business.services.catalog.product;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.FilterCriteria;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.ProductCriteria;
import com.salesmanager.core.model.catalog.product.ProductList;
import com.salesmanager.core.model.catalog.product.description.ProductDescription;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.tax.taxclass.TaxClass;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public interface ProductService extends SalesManagerEntityService<Long, Product> {

  void addProductDescription(Product product, ProductDescription description)
      throws ServiceException;

  ProductDescription getProductDescription(Product product, Language language);

  Product getProductForLocale(long productId, Language language, Locale locale)
      throws ServiceException;

  List<Product> getProductsForLocale(Category category, Language language, Locale locale)
      throws ServiceException;

  List<Product> getProducts(List<Long> categoryIds) throws ServiceException;

  /**
   * Get a product with only MerchantStore object
   *
   * @param productId
   * @return
   */
  Product getProductWithOnlyMerchantStoreById(Long productId);

  ProductList listByStore(MerchantStore store, Language language, ProductCriteria criteria);

  List<Product> listByStore(MerchantStore store);

  List<Product> listByTaxClass(TaxClass taxClass);

  List<Product> getProducts(List<Long> categoryIds, Language language) throws ServiceException;

  Product getBySeUrl(MerchantStore store, String seUrl, Locale locale);

  /**
   * Get a product by sku (code) field and the language
   *
   * @param productCode
   * @param language
   * @return
   */
  Product getByCode(String productCode, Language language);

  /**
   * Find a product for a specific merchant
   *
   * @param id
   * @param merchant
   * @return
   */
  Product findOne(Long id, MerchantStore merchant);

  /**
   * home filter
   *
   * @throws Exception
   */
  FilterCriteria getHomeFilters();

  List<Product> listByFilter(MerchantStore store, Language language, ProductCriteria criteria)
      throws ServiceException;

  List<Product> getProductDetailsByFlavorIdAndCategoryId(
      MerchantStore store, Long flavourId, Long categoryId, String excludeProductName)
      throws ServiceException;

  Product getProductDetailsByCategoryFlavorAndPack(
      Boolean productFlag,
      MerchantStore merchantStore,
      Long categoryId,
      Long flavourId,
      Long packId)
      throws ServiceException;

  List<Product> getProductListForHome(
      MerchantStore store, Language language, ProductCriteria criteria, int count)
      throws ServiceException;

  /**
   * @param sku
   * @return
   */
  Product getBySkuId(String sku) throws ServiceException;

  Product getByProductId(Long id) throws ServiceException;

  /**
   * @param criteria
   * @return
   * @throws ServiceException
   */
  Collection<Product> getProductsByCriteria(Criteria criteria) throws ServiceException;

  List<Product> listByMerchandiseProductName(
      MerchantStore store, Language language, ProductCriteria criteria) throws ServiceException;

  Product getMerchandiseProductDetailsByCategoryFlavorAndPack(
      MerchantStore merchantStore, Long categoryId, Long packId, String productName)
      throws ServiceException;
}
