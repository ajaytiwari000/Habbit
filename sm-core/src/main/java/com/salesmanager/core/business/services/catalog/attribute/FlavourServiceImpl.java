package com.salesmanager.core.business.services.catalog.attribute;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.attribute.FlavourRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.Flavour;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("flavourService")
public class FlavourServiceImpl extends SalesManagerEntityServiceImpl<Long, Flavour>
    implements FlavourService {

  FlavourRepository flavourRepository;

  @Inject
  public FlavourServiceImpl(FlavourRepository flavourRepository) {
    super(flavourRepository);
    this.flavourRepository = flavourRepository;
  }

  @Override
  public Flavour getById(Long id) {
    return flavourRepository.findById(id).orElse(null);
  }

  @Override
  public Flavour getByName(String flavourName) throws ServiceException {
    try {
      return flavourRepository.findByName(flavourName).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public List<Flavour> getFlavoursByCriteria(Criteria criteria) throws ServiceException {
    try {
      return flavourRepository.getFlavours(criteria).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
