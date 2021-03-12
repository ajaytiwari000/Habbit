package com.salesmanager.core.business.services.catalog.attribute;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.Flavour;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;

public interface FlavourService extends SalesManagerEntityService<Long, Flavour> {

  Flavour getByName(String flavourName) throws ServiceException;

  List<Flavour> getFlavoursByCriteria(Criteria criteria) throws ServiceException;
}
