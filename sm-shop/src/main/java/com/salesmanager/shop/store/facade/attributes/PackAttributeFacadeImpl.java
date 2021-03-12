package com.salesmanager.shop.store.facade.attributes;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.attribute.PackService;
import com.salesmanager.core.model.catalog.product.Pack;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.admin.controller.attributes.PackAttributeFacade;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.mapper.product.PackMapper;
import com.salesmanager.shop.model.catalog.product.attribute.PersistablePack;
import com.salesmanager.shop.model.catalog.product.attribute.PersistablePackList;
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

@Service("packAttributeFacade")
public class PackAttributeFacadeImpl implements PackAttributeFacade {
  private static final Logger LOGGER = LoggerFactory.getLogger(FlavourAttributeFacadeImpl.class);

  @Inject private PackService packService;

  @Inject private PackMapper packMapper;

  @Override
  public PersistablePack createPack(PersistablePack pack, MerchantStore store)
      throws ServiceRuntimeException {

    String packSizeValue = pack.getPackSizeValue();
    if (StringUtils.isNotEmpty(packSizeValue)
        && (pack.getId() == null || pack.getId().longValue() == 0)) {
      Pack packExist = null;
      try {
        packExist = packService.getByPackSize(packSizeValue);
      } catch (Exception e) {
        throwServiceRuntImeException(
            e,
            AttributeErrorCodes.PACK_GET_BY_PACK_SIZE_FAILURE.getErrorCode(),
            AttributeErrorCodes.PACK_GET_BY_PACK_SIZE_FAILURE.getErrorMessage() + packSizeValue);
      }
      if (Objects.nonNull(packExist)) {
        LOGGER.error("pack Already exists for" + packSizeValue);
        throw new ResourceDuplicateException("pack Already exists for " + pack.getPackSizeValue());
      }
    }
    try {
      packService.create(packMapper.toPack(pack));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PACK_CREATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.PACK_CREATE_FAILURE.getErrorMessage());
    }
    return pack;
  }

  @Override
  public PersistablePack updatePack(PersistablePack pack, MerchantStore store)
      throws ServiceRuntimeException {
    try {
      packService.update(packMapper.toPack(pack));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PACK_UPDATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.PACK_UPDATE_FAILURE.getErrorMessage());
    }
    return pack;
  }

  @Override
  public void deletePack(Long id, MerchantStore store) throws ServiceRuntimeException {

    Validate.notNull(id, "pack id cannot be null");
    Pack p = this.getPack(id);
    try {
      packService.delete(p);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PACK_DELETE_FAILURE.getErrorCode(),
          AttributeErrorCodes.PACK_DELETE_FAILURE.getErrorMessage() + id);
    }
  }

  @Override
  public PersistablePack getPack(Long id, MerchantStore store) throws ServiceRuntimeException {
    Validate.notNull(id, "pack id cannot be null");
    Pack pack = getPack(id);
    return packMapper.toPersistablePack(pack);
  }

  private Pack getPack(Long id) throws ServiceRuntimeException {
    Pack pack = null;
    try {
      pack = packService.getById(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PACK_GET_BY_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.PACK_GET_BY_ID_FAILURE.getErrorMessage() + id);
    }
    if (Objects.isNull(pack)) {
      LOGGER.error("pack with id [" + id + " ] not found in DB.");
      throw new ResourceNotFoundException("pack with id [" + id + " ] not found");
    }
    return pack;
  }

  @Override
  public PersistablePackList getAllUniquePack(Integer page, Integer pageSize, MerchantStore store)
      throws ServiceRuntimeException {

    PersistablePackList persistablePackList = new PersistablePackList();
    PaginationData paginationData = new PaginationData(pageSize, page);

    Criteria criteria = new Criteria();
    criteria.setStartIndex(paginationData.getOffset() - 1);
    criteria.setPageSize(pageSize);
    criteria.setMaxCount(pageSize);

    try {
      persistablePackList.setPacks(
          Optional.ofNullable(packService.getPacksByCriteria(criteria))
              .map(Collection::stream)
              .orElseGet(Stream::empty)
              .map(packMapper::toPersistablePack)
              .collect(Collectors.toList()));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.PACK_GET_ALL_FAILURE.getErrorCode(),
          AttributeErrorCodes.PACK_GET_ALL_FAILURE.getErrorMessage());
    }
    return persistablePackList;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
