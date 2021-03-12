package com.salesmanager.shop.store.controller.items.facade;

import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.ReadableProductsList;
import com.salesmanager.shop.model.catalog.product.group.ProductGroup;
import java.util.List;

public interface ProductItemsFacade {

  /**
   * List items attached to a Manufacturer
   *
   * @param store
   * @param language
   * @return
   */
  ReadableProductsList listItemsByManufacturer(
      MerchantStore store, Language language, Long manufacturerId, int startCount, int maxCount)
      throws Exception;

  ProductGroup createProductGroup(ProductGroup group, MerchantStore store);

  List<ProductGroup> listProductGroups(MerchantStore store, Language language);

  /**
   * Update product group visible flag
   *
   * @param code
   * @param group
   * @param store
   */
  void updateProductGroup(String code, ProductGroup group, MerchantStore store);

  /**
   * List product items by id
   *
   * @param store
   * @param language
   * @param ids
   * @param startCount
   * @param maxCount
   * @return
   * @throws Exception
   */
  ReadableProductsList listItemsByIds(
      MerchantStore store, Language language, List<Long> ids, int startCount, int maxCount)
      throws Exception;

  /**
   * List products created in a group, for instance FEATURED group
   *
   * @param group
   * @param store
   * @param language
   * @return
   * @throws Exception
   */
  ReadableProductsList listItemsByGroup(String group, MerchantStore store, Language language)
      throws Exception;

  /**
   * Add product to a group
   *
   * @param product
   * @param group
   * @param store
   * @param language
   * @return
   * @throws Exception
   */
  ReadableProductsList addItemToGroup(
      Product product, String group, MerchantStore store, Language language);

  /**
   * Removes a product from a group
   *
   * @param product
   * @param group
   * @param store
   * @param language
   * @return
   * @throws Exception
   */
  ReadableProductsList removeItemFromGroup(
      Product product, String group, MerchantStore store, Language language) throws Exception;

  void deleteGroup(String group, MerchantStore store);
}
