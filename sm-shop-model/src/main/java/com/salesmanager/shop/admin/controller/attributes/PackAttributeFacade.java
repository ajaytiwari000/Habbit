package com.salesmanager.shop.admin.controller.attributes;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.catalog.product.attribute.PersistablePack;
import com.salesmanager.shop.model.catalog.product.attribute.PersistablePackList;

public interface PackAttributeFacade {

  /**
   * @param pack
   * @param store
   * @return
   * @throws Exception
   */
  PersistablePack createPack(PersistablePack pack, MerchantStore store) throws Exception;

  /**
   * @param pack
   * @param store
   * @return
   * @throws Exception
   */
  PersistablePack updatePack(PersistablePack pack, MerchantStore store) throws Exception;

  /**
   * @param id
   * @param store
   * @return
   * @throws Exception
   */
  PersistablePack getPack(Long id, MerchantStore store) throws Exception;

  /**
   * Get All Unique Packs
   *
   * @param store
   * @return
   */
  PersistablePackList getAllUniquePack(Integer page, Integer pageSize, MerchantStore store)
      throws Exception;

  /**
   * @param id
   * @param store
   * @throws Exception
   */
  void deletePack(Long id, MerchantStore store) throws Exception;
}
