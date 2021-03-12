package com.salesmanager.core.business.services.catalog.attribute;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.Pack;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;

public interface PackService extends SalesManagerEntityService<Long, Pack> {

  Pack getByPackSize(String packSizeValue) throws ServiceException;

  List<Pack> getPacksByCriteria(final Criteria criteria) throws ServiceException;
}
