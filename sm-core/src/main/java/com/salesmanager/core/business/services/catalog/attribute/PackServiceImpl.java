package com.salesmanager.core.business.services.catalog.attribute;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.attribute.PackRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.Pack;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("packService")
public class PackServiceImpl extends SalesManagerEntityServiceImpl<Long, Pack>
    implements PackService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PackServiceImpl.class);

  PackRepository packRepository;

  @Inject
  public PackServiceImpl(PackRepository packRepository) {
    super(packRepository);
    this.packRepository = packRepository;
  }

  @Override
  public Pack getById(Long id) {
    return packRepository.findById(id).orElse(null);
  }

  @Override
  public Pack getByPackSize(String packSizeValue) throws ServiceException {
    try {
      return packRepository.findByPackSize(packSizeValue).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public List<Pack> getPacksByCriteria(Criteria criteria) throws ServiceException {
    try {
      return packRepository.getPacks(criteria).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
