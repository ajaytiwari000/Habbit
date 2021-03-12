package com.salesmanager.shop.admin.controller.attributes;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableFlavourList;
import com.salesmanager.shop.model.productAttribute.PersistableFlavour;

public interface FlavourAttributeFacade {

  /**
   * Delete Flavour
   *
   * @param id
   * @param store
   * @throws Exception
   */
  void deleteFlavour(Long id, MerchantStore store) throws Exception;

  /**
   * Get Flavour
   *
   * @param id
   * @param store
   * @return
   */
  PersistableFlavour getFlavour(Long id, MerchantStore store) throws Exception;

  /**
   * @param flavour
   * @param merchantStore
   */
  PersistableFlavour createFlavour(PersistableFlavour flavour, MerchantStore merchantStore)
      throws Exception;

  /**
   * @param flavour
   * @param merchantStore
   * @return
   * @throws Exception
   */
  PersistableFlavour updateFlavour(PersistableFlavour flavour, MerchantStore merchantStore)
      throws Exception;;

  /**
   * @param page
   * @param pageSize
   * @param merchantStore
   * @return
   * @throws Exception
   */
  PersistableFlavourList getAllUniqueFlavour(
      Integer page, Integer pageSize, MerchantStore merchantStore) throws Exception;;
}
