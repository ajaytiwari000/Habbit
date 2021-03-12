package com.salesmanager.core.business.services.catalog.attribute;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.attribute.BoostRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.Boost;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("boostService")
public class BoostServiceImpl extends SalesManagerEntityServiceImpl<Long, Boost>
    implements BoostService {

  BoostRepository boostRepository;

  @Inject
  public BoostServiceImpl(BoostRepository boostRepository) {
    super(boostRepository);
    this.boostRepository = boostRepository;
  }

  @Override
  public Boost getById(Long id) {
    return boostRepository.findById(id).orElse(null);
  }

  @Override
  public Boost getByType(String boostType) throws ServiceException {
    try {
      return boostRepository.findByType(boostType).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public List<Boost> getBoostsByCriteria(Criteria criteria) throws ServiceException {
    try {
      return boostRepository.getBoosts(criteria).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
