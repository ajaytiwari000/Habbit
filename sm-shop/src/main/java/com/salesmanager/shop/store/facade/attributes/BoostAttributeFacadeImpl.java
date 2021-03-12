package com.salesmanager.shop.store.facade.attributes;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.attribute.BoostService;
import com.salesmanager.core.model.catalog.product.Boost;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.admin.controller.attributes.BoostAttributeFacade;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.mapper.product.BoostMapper;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableBoostList;
import com.salesmanager.shop.model.productAttribute.PersistableBoost;
import com.salesmanager.shop.store.api.exception.ResourceDuplicateException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.model.paging.PaginationData;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("boostAttributeFacade")
public class BoostAttributeFacadeImpl implements BoostAttributeFacade {
  private static final Logger LOGGER = LoggerFactory.getLogger(BoostAttributeFacadeImpl.class);
  @Inject private BoostService boostService;
  @Inject private BoostMapper boostMapper;

  @Override
  public PersistableBoost createBoost(PersistableBoost boost, MerchantStore merchantStore)
      throws ServiceException {
    String boostType = boost.getType();
    if (StringUtils.isNotEmpty(boostType)
        && (boost.getId() == null || boost.getId().longValue() == 0)) {
      Boost boostExist = null;
      try {
        boostExist = boostService.getByType(boostType);
      } catch (ServiceException e) {
        throwServiceRuntImeException(
            e,
            AttributeErrorCodes.BOOST_GET_BY_BOOST_TYPE_FAILURE.getErrorCode(),
            AttributeErrorCodes.BOOST_GET_BY_BOOST_TYPE_FAILURE.getErrorMessage() + boostType);
      }
      if (Objects.nonNull(boostExist)) {
        LOGGER.error("Boost Already exists for {}", boostType);
        throw new ResourceDuplicateException("Boost Already exists for " + boostType);
      }
    }
    try {
      boostService.create(boostMapper.toBoost(boost));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.BOOST_CREATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.BOOST_CREATE_FAILURE.getErrorMessage());
    }
    return boost;
  }

  @Override
  public PersistableBoost updateBoost(PersistableBoost boost, MerchantStore merchantStore) {
    try {
      boostService.update(boostMapper.toBoost(boost));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.BOOST_UPDATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.BOOST_UPDATE_FAILURE.getErrorMessage());
    }
    return boost;
  }

  @Override
  public void deleteBoost(Long id, MerchantStore store) throws ServiceException {
    Validate.notNull(id, "Boost id cannot be null");
    Boost boost = this.getBoost(id);
    try {
      boostService.delete(boost);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.BOOST_DELETE_FAILURE.getErrorCode(),
          AttributeErrorCodes.BOOST_DELETE_FAILURE.getErrorMessage() + id);
    }
  }

  @Override
  public PersistableBoost getBoost(Long id, MerchantStore store) throws Exception {
    Validate.notNull(id, "Boost id cannot be null");
    Boost boost = getBoost(id);
    return boostMapper.toPersistableBoost(boost);
  }

  private Boost getBoost(Long id) {
    Boost boost = null;
    try {
      boost = boostService.getById(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.BOOST_GET_BY_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.BOOST_GET_BY_ID_FAILURE.getErrorMessage() + id);
    }
    if (Objects.isNull(boost)) {
      LOGGER.error("Boost with id [" + id + " ] not found in DB.");
      throw new ResourceNotFoundException("Boost with id [" + id + " ] not found");
    }
    return boost;
  }

  @Override
  public PersistableBoostList getAllUniqueBoost(
      Integer page, Integer pageSize, MerchantStore merchantStore) {
    PersistableBoostList persistableBoostList = new PersistableBoostList();
    PaginationData paginationData = new PaginationData(pageSize, page);

    Criteria criteria = new Criteria();
    criteria.setStartIndex(paginationData.getOffset() - 1);
    criteria.setPageSize(pageSize);
    criteria.setMaxCount(pageSize);

    try {
      persistableBoostList.setBoosts(
          boostService.getBoostsByCriteria(criteria).stream()
              .map(boostMapper::toPersistableBoost)
              .collect(Collectors.toList()));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.BOOST_GET_ALL_FAILURE.getErrorCode(),
          AttributeErrorCodes.BOOST_GET_ALL_FAILURE.getErrorMessage());
    }
    return persistableBoostList;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
