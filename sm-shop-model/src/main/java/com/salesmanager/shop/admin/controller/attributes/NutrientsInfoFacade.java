package com.salesmanager.shop.admin.controller.attributes;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.catalog.product.NutrientsInfo;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.catalog.category.NutrientsInfoList;
import com.salesmanager.shop.model.productAttribute.PersistableNutrientsInfo;
import org.springframework.web.multipart.MultipartFile;

public interface NutrientsInfoFacade {

  /**
   * @param files
   * @param categoryNutrientsInfo
   * @return
   */
  PersistableNutrientsInfo createNutrientsInfo(
      MultipartFile[] files, NutrientsInfo categoryNutrientsInfo) throws ServiceException;

  /** @param productNutrientsInfoId */
  void deleteNutrientsInfo(Long productNutrientsInfoId);

  /**
   * @param id
   * @return
   */
  PersistableNutrientsInfo getNutrientsInfo(Long id);

  /**
   * @param page
   * @param pageSize
   * @param merchantStore
   * @return
   */
  NutrientsInfoList getAllNutrientsInfos(
      Integer page, Integer pageSize, MerchantStore merchantStore);
}
