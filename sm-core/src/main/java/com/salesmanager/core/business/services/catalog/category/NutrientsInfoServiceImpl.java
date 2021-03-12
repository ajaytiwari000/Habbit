package com.salesmanager.core.business.services.catalog.category;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.category.NutrientsInfoRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.NutrientsInfo;
import com.salesmanager.core.model.common.Criteria;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("nutrientsInfoService")
public class NutrientsInfoServiceImpl extends SalesManagerEntityServiceImpl<Long, NutrientsInfo>
    implements NutrientsInfoService {

  private static final Logger LOGGER = LoggerFactory.getLogger(NutrientsInfoServiceImpl.class);

  NutrientsInfoRepository nutrientsInfoRepository;

  @Inject
  public NutrientsInfoServiceImpl(NutrientsInfoRepository nutrientsInfoRepository) {
    super(nutrientsInfoRepository);
    this.nutrientsInfoRepository = nutrientsInfoRepository;
  }

  @Override
  public NutrientsInfo getByDescription(String description) throws ServiceException {
    try {
      return nutrientsInfoRepository.getByDescription(description).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public List<NutrientsInfo> getAllNutrientsInfo(Criteria criteria) throws ServiceException {
    try {
      return nutrientsInfoRepository.getNutrientsInfos(criteria).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public NutrientsInfo getById(Long id) {
    return nutrientsInfoRepository.findById(id).orElse(null);
  }
}
