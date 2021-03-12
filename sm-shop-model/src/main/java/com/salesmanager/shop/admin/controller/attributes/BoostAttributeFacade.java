package com.salesmanager.shop.admin.controller.attributes;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableBoostList;
import com.salesmanager.shop.model.productAttribute.PersistableBoost;

public interface BoostAttributeFacade {

  /**
   * @param persistableBoost
   * @param merchantStore
   * @return
   * @throws Exception
   */
  PersistableBoost createBoost(PersistableBoost persistableBoost, MerchantStore merchantStore)
      throws Exception;

  /**
   * @param persistableBoost
   * @throws ServiceException
   */
  PersistableBoost updateBoost(PersistableBoost persistableBoost, MerchantStore merchantStore)
      throws Exception;
  /**
   * Delete Boost
   *
   * @param id
   * @param store
   * @throws Exception
   */
  void deleteBoost(Long id, MerchantStore store) throws Exception;

  /**
   * Get Boost
   *
   * @param id
   * @param store
   * @return
   */
  PersistableBoost getBoost(Long id, MerchantStore store) throws Exception;

  /**
   * @param page
   * @param pageSize
   * @param merchantStore
   * @return
   * @throws Exception
   */
  PersistableBoostList getAllUniqueBoost(
      Integer page, Integer pageSize, MerchantStore merchantStore) throws Exception;;
}
