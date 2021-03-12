package com.salesmanager.core.business.services.catalog.attribute;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.Boost;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;

public interface BoostService extends SalesManagerEntityService<Long, Boost> {

  /**
   * @param boostType
   * @return
   * @throws ServiceException
   */
  Boost getByType(String boostType) throws ServiceException;

  List<Boost> getBoostsByCriteria(final Criteria criteria) throws ServiceException;
}
