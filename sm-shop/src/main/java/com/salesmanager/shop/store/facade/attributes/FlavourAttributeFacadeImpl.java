package com.salesmanager.shop.store.facade.attributes;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.attribute.FlavourService;
import com.salesmanager.core.model.catalog.product.Flavour;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.admin.controller.attributes.FlavourAttributeFacade;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.mapper.product.FlavourMapper;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableFlavourList;
import com.salesmanager.shop.model.productAttribute.PersistableFlavour;
import com.salesmanager.shop.store.api.exception.ResourceDuplicateException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.model.paging.PaginationData;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("flavourAttributeFacade")
public class FlavourAttributeFacadeImpl implements FlavourAttributeFacade {
  private static final Logger LOGGER = LoggerFactory.getLogger(FlavourAttributeFacadeImpl.class);

  @Inject private FlavourService flavourService;
  @Inject private FlavourMapper flavourMapper;

  @Override
  public PersistableFlavour createFlavour(PersistableFlavour flavour, MerchantStore merchantStore)
      throws ServiceRuntimeException {
    String flavourName = flavour.getName();
    if (StringUtils.isNotEmpty(flavourName)
        && (flavour.getId() == null || flavour.getId().longValue() == 0)) {
      Flavour flavourExist = null;
      try {
        flavourExist = flavourService.getByName(flavourName);
      } catch (ServiceException e) {
        throwServiceRuntImeException(
            e,
            AttributeErrorCodes.FLAVOUR_GET_BY_NAME_FAILURE.getErrorCode(),
            AttributeErrorCodes.FLAVOUR_GET_BY_NAME_FAILURE.getErrorMessage() + flavourName);
      }
      if (Objects.nonNull(flavourExist)) {
        LOGGER.error("Flavour Already exists for {}", flavour.getName());
        throw new ResourceDuplicateException("Flavour Already exists for " + flavour.getName());
      }
    }
    try {
      flavourService.create(flavourMapper.toFlavour(flavour));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.FLAVOUR_CREATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.FLAVOUR_CREATE_FAILURE.getErrorMessage());
    }
    return flavour;
  }

  @Override
  public PersistableFlavour updateFlavour(PersistableFlavour flavour, MerchantStore merchantStore)
      throws ServiceRuntimeException {
    try {
      flavourService.update(flavourMapper.toFlavour(flavour));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.FLAVOUR_UPDATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.FLAVOUR_UPDATE_FAILURE.getErrorMessage());
    }
    return flavour;
  }

  @Override
  public void deleteFlavour(Long id, MerchantStore store) throws ServiceException {
    Validate.notNull(id, "Flavour id cannot be null");
    Flavour flavour = this.getFlavour(id);
    try {
      flavourService.delete(flavour);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.FLAVOUR_DELETE_FAILURE.getErrorCode(),
          AttributeErrorCodes.FLAVOUR_DELETE_FAILURE.getErrorMessage() + id);
    }
  }

  @Override
  public PersistableFlavour getFlavour(Long id, MerchantStore store) throws Exception {
    Validate.notNull(id, "Flavour id cannot be null");
    Flavour flavour = getFlavour(id);
    return flavourMapper.toPersistableFlavour(flavour);
  }

  private Flavour getFlavour(Long id) {
    Flavour flavour = null;
    try {
      flavour = flavourService.getById(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.FLAVOUR_GET_BY_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.FLAVOUR_GET_BY_ID_FAILURE.getErrorMessage() + id);
    }
    if (Objects.isNull(flavour)) {
      LOGGER.error("Flavour with id [" + id + " ] not found in DB.");
      throw new ResourceNotFoundException("Flavour with id [" + id + " ] not found");
    }
    return flavour;
  }

  @Override
  public PersistableFlavourList getAllUniqueFlavour(
      Integer page, Integer pageSize, MerchantStore merchantStore) {
    PersistableFlavourList persistableFlavourList = new PersistableFlavourList();
    PaginationData paginationData = new PaginationData(pageSize, page);

    Criteria criteria = new Criteria();
    criteria.setStartIndex(paginationData.getOffset() - 1);
    criteria.setPageSize(pageSize);
    criteria.setMaxCount(pageSize);

    try {
      persistableFlavourList.setFlavours(
          Optional.ofNullable(flavourService.getFlavoursByCriteria(criteria))
              .map(Collection::stream)
              .orElseGet(Stream::empty)
              .map(flavourMapper::toPersistableFlavour)
              .collect(Collectors.toList()));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.FLAVOUR_GET_ALL_FAILURE.getErrorCode(),
          AttributeErrorCodes.FLAVOUR_GET_ALL_FAILURE.getErrorMessage());
    }
    return persistableFlavourList;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
