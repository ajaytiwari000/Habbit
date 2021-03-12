package com.salesmanager.shop.store.controller.category.facade;

import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.category.PersistablesCategory;
import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.catalog.category.ReadableCategoryList;
import com.salesmanager.shop.model.catalog.category.ReadableCategorys;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableCategoryList;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductVariant;
import com.salesmanager.shop.model.entity.ListCriteria;
import java.util.List;

public interface CategoryFacade {

  /**
   * Returns a list of ReadableCategory ordered and built according to a given depth
   *
   * @param store
   * @param depth
   * @param language
   * @param filter
   * @param page
   * @param count
   * @return ReadableCategoryList
   */
  ReadableCategoryList getCategoryHierarchy(
      MerchantStore store,
      ListCriteria criteria,
      int depth,
      Language language,
      List<String> filter,
      int page,
      int count);

  /**
   * @param store
   * @param category
   * @return PersistableCategory
   */
  PersistablesCategory saveCategory(MerchantStore store, PersistablesCategory category);

  /**
   * @param store
   * @param id
   * @param language
   * @return ReadableCategorys
   */
  ReadableCategorys getById(MerchantStore store, Long id, Language language);

  /**
   * @param store
   * @param categoryId
   * @param language
   * @return ReadableCategory
   */
  ReadableCategory getByCategoryId(MerchantStore store, Long categoryId, Language language);

  /**
   * @param store
   * @param code
   * @param language
   * @return ReadableCategory
   * @throws Exception
   */
  ReadableCategorys getByCode(MerchantStore store, String code, Language language) throws Exception;

  Category getByCode(String code, MerchantStore store);

  void deleteCategory(Long categoryId, MerchantStore store);

  void deleteCategory(Category category);

  /**
   * List product options variations for a given category
   *
   * @param categoryId
   * @param store
   * @param language
   * @return
   */
  List<ReadableProductVariant> categoryProductVariants(
      Long categoryId, MerchantStore store, Language language);

  /**
   * Check if category code already exist
   *
   * @param store
   * @param code
   * @return
   * @throws Exception
   */
  boolean existByCode(MerchantStore store, String code);

  /**
   * Move a Category from a node to another node
   *
   * @param child
   * @param parent
   * @param store
   */
  void move(Long child, Long parent, MerchantStore store);

  /**
   * Set category visible or not
   *
   * @param category
   * @param store
   */
  void setVisible(PersistablesCategory category, MerchantStore store);

  /**
   * Crud operation for Attribute for admin panel
   *
   * @param category
   * @param merchantStore
   */
  com.salesmanager.shop.model.productAttribute.PersistableCategory createCategory(
      com.salesmanager.shop.model.productAttribute.PersistableCategory category,
      MerchantStore merchantStore);
  /**
   * Crud operation for Attribute at admin panel
   *
   * @param id
   * @param merchantStore
   * @param language
   * @return
   */
  com.salesmanager.shop.model.productAttribute.PersistableCategory getCategory(
      Long id, MerchantStore merchantStore, Language language);

  /**
   * Crud operation for Attribute at admin panel
   *
   * @param category
   * @param merchantStore
   */
  com.salesmanager.shop.model.productAttribute.PersistableCategory updateCategory(
      com.salesmanager.shop.model.productAttribute.PersistableCategory category,
      MerchantStore merchantStore);

  /**
   * Crud operation for Attribute at admin panel
   *
   * @param id
   */
  void deleteCategory(Long id);

  /**
   * Crud operation for Attribute at admin panel
   *
   * @param page
   * @param pageSize
   * @param merchantStore
   * @return
   */
  PersistableCategoryList getAllUniqueCategory(
      Integer page, Integer pageSize, MerchantStore merchantStore) throws Exception;

  void setParentDetailsAtChildLevel(Category category, MerchantStore merchantStore);
}
