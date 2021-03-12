package com.salesmanager.shop.store.facade.membershipcolor;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.membershipcolor.MembershipColorService;
import com.salesmanager.core.model.catalog.product.MembershipColor;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.admin.controller.membershipcolor.MembershipColorFacade;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.mapper.product.MembershipColorMapper;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableMembershipColorList;
import com.salesmanager.shop.model.productAttribute.PersistableMembershipColor;
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
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("membershipColorFacade")
public class MembershipColorFacadeImpl implements MembershipColorFacade {
  private static final Logger LOGGER = LoggerFactory.getLogger(MembershipColorFacadeImpl.class);

  @Inject private MembershipColorService membershipColorService;
  @Inject private MembershipColorMapper membershipColorMapper;

  @Override
  public PersistableMembershipColor createMembershipColor(
      PersistableMembershipColor persistableMembershipColor, MerchantStore merchantStore)
      throws ServiceRuntimeException {
    TierType tierType = persistableMembershipColor.getTierType();
    if (Objects.nonNull(tierType)
        && (persistableMembershipColor.getId() == null
            || persistableMembershipColor.getId().longValue() == 0)) {
      MembershipColor MembershipColorExist = null;
      try {
        MembershipColorExist = membershipColorService.getByTierType(tierType);
      } catch (ServiceException e) {
        throwServiceRuntImeException(
            e,
            AttributeErrorCodes.MEMBERSHIP_COLOR_GET_BY_TIER_TYPE_FAILURE.getErrorCode(),
            AttributeErrorCodes.MEMBERSHIP_COLOR_GET_BY_TIER_TYPE_FAILURE.getErrorMessage()
                + tierType.name());
      }
      if (Objects.nonNull(MembershipColorExist)) {
        LOGGER.error(
            "MembershipColor Already exists for {}",
            persistableMembershipColor.getTierType().name());
        throw new ResourceDuplicateException(
            "MembershipColor Already exists for "
                + persistableMembershipColor.getTierType().name());
      }
    }
    try {
      membershipColorService.create(
          membershipColorMapper.toMembershipColor(persistableMembershipColor));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.MEMBERSHIP_COLOR_CREATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.MEMBERSHIP_COLOR_CREATE_FAILURE.getErrorMessage());
    }
    return persistableMembershipColor;
  }

  @Override
  public PersistableMembershipColor updateMembershipColor(
      PersistableMembershipColor MembershipColor, MerchantStore merchantStore)
      throws ServiceRuntimeException {
    try {
      membershipColorService.update(membershipColorMapper.toMembershipColor(MembershipColor));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.MEMBERSHIP_COLOR_UPDATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.MEMBERSHIP_COLOR_UPDATE_FAILURE.getErrorMessage());
    }
    return MembershipColor;
  }

  @Override
  public void deleteMembershipColor(Long id, MerchantStore store) {
    Validate.notNull(id, "MembershipColor id cannot be null");
    MembershipColor MembershipColor = getMembershipColor(id);
    try {
      membershipColorService.delete(MembershipColor);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.MEMBERSHIP_COLOR_DELETE_FAILURE.getErrorCode(),
          AttributeErrorCodes.MEMBERSHIP_COLOR_DELETE_FAILURE.getErrorMessage() + id);
    }
  }

  @Override
  public PersistableMembershipColor getMembershipColor(Long id, MerchantStore store) {
    Validate.notNull(id, "MembershipColor id cannot be null");
    MembershipColor MembershipColor = getMembershipColor(id);
    return membershipColorMapper.toPersistableMembershipColor(MembershipColor);
  }

  private MembershipColor getMembershipColor(Long id) {
    MembershipColor MembershipColor = null;
    try {
      MembershipColor = membershipColorService.getById(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.MEMBERSHIP_COLOR_GET_BY_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.MEMBERSHIP_COLOR_GET_BY_ID_FAILURE.getErrorMessage() + id);
    }
    if (Objects.isNull(MembershipColor)) {
      LOGGER.error("MembershipColor with id {} not found in DB.", id);
      throw new ResourceNotFoundException("MembershipColor with id [" + id + " ] not found");
    }
    return MembershipColor;
  }

  @Override
  public PersistableMembershipColorList getAllUniqueMembershipColor(
      Integer page, Integer pageSize, MerchantStore merchantStore) {
    PersistableMembershipColorList persistableMembershipColorList =
        new PersistableMembershipColorList();
    PaginationData paginationData = new PaginationData(pageSize, page);

    Criteria criteria = new Criteria();
    criteria.setStartIndex(paginationData.getOffset() - 1);
    criteria.setPageSize(pageSize);
    criteria.setMaxCount(pageSize);

    try {
      persistableMembershipColorList.setMembershipColors(
          Optional.ofNullable(membershipColorService.getAllMembershipColor())
              .map(Collection::stream)
              .orElseGet(Stream::empty)
              .map(membershipColorMapper::toPersistableMembershipColor)
              .collect(Collectors.toList()));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.MEMBERSHIP_COLOR_GET_ALL_FAILURE.getErrorCode(),
          AttributeErrorCodes.MEMBERSHIP_COLOR_GET_ALL_FAILURE.getErrorMessage());
    }
    return persistableMembershipColorList;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
