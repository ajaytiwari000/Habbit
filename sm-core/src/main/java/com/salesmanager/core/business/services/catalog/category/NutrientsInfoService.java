package com.salesmanager.core.business.services.catalog.category;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.NutrientsInfo;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;

public interface NutrientsInfoService extends SalesManagerEntityService<Long, NutrientsInfo> {

  /**
   * @param description
   * @return
   */
  NutrientsInfo getByDescription(String description) throws ServiceException;

  /**
   * @param criteria
   * @return
   */
  List<NutrientsInfo> getAllNutrientsInfo(Criteria criteria) throws ServiceException;
}
