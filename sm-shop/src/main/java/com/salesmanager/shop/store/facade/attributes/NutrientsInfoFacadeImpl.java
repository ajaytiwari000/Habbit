package com.salesmanager.shop.store.facade.attributes;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.attributes.NutrientsInfo.NutrientsInfoManager;
import com.salesmanager.core.business.services.catalog.category.NutrientsInfoService;
import com.salesmanager.core.model.catalog.product.NutrientsInfo;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.admin.controller.attributes.NutrientsInfoFacade;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.mapper.product.NutrientsInfoMapper;
import com.salesmanager.shop.model.catalog.category.NutrientsInfoList;
import com.salesmanager.shop.model.productAttribute.PersistableNutrientsInfo;
import com.salesmanager.shop.store.api.exception.ResourceDuplicateException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.model.paging.PaginationData;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

@Service(value = "nutrientsInfoFacade")
public class NutrientsInfoFacadeImpl implements NutrientsInfoFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(NutrientsInfoFacadeImpl.class);
  @Inject private NutrientsInfoService nutrientsInfoService;
  @Inject private NutrientsInfoManager nutrientsInfoManager;
  @Inject private NutrientsInfoMapper nutrientsInfoMapper;

  @Override
  public PersistableNutrientsInfo createNutrientsInfo(
      MultipartFile[] files, NutrientsInfo nutrientsInfo) throws ServiceException {
    String description = nutrientsInfo.getDescription();
    if (StringUtils.isNotEmpty(description)
        && (nutrientsInfo.getId() == null || nutrientsInfo.getId().longValue() == 0)) {
      NutrientsInfo nutrientsInfoExit = null;
      try {
        nutrientsInfoExit = nutrientsInfoService.getByDescription(description);
      } catch (ServiceException e) {
        throwServiceRuntImeException(
            e,
            AttributeErrorCodes.NUTRIENTS_INFO_GET_BY_DESCRIPTION_FAILURE.getErrorCode(),
            AttributeErrorCodes.NUTRIENTS_INFO_GET_BY_DESCRIPTION_FAILURE.getErrorMessage()
                + description);
      }
      if (Objects.nonNull(nutrientsInfoExit)) {
        LOGGER.error("NutrientsInfo Already exists for {}", description);
        throw new ResourceDuplicateException("NutrientsInfo Already exists for " + description);
      }
    }
    try {
      for (MultipartFile multipartFile : files) {
        if (!multipartFile.isEmpty()) {
          nutrientsInfo.setImage(multipartFile.getInputStream());
          nutrientsInfo.setImg(multipartFile.getOriginalFilename());
          addProductNutrientsInfoImage(nutrientsInfo);
        }
      }
      nutrientsInfoService.create(nutrientsInfo);
    } catch (ServiceException | IOException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.NUTRIENTS_INFO_CREATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.NUTRIENTS_INFO_CREATE_FAILURE.getErrorMessage());
    }
    return nutrientsInfoMapper.toPersistableNutrientsInfo(nutrientsInfo);
  }

  private NutrientsInfo addProductNutrientsInfoImage(NutrientsInfo nutrientsInfo)
      throws ServiceException {
    Assert.notNull(nutrientsInfo.getImage(), "NutrientsInfo ImageContentFile cannot be null");

    InputStream inputStream = nutrientsInfo.getImage();
    ImageContentFile cmsContentImage = new ImageContentFile();
    cmsContentImage.setFileName(nutrientsInfo.getImg());
    cmsContentImage.setFile(inputStream);
    cmsContentImage.setFileContentType(FileContentType.NUTRIENTS_INFO);
    nutrientsInfoManager.addImage(nutrientsInfo, cmsContentImage);
    return nutrientsInfo;
  }

  @Override
  public void deleteNutrientsInfo(Long id) {
    Validate.notNull(id, "productNutrientsInfoId cannot be null");
    NutrientsInfo NutrientsInfo = nutrientsInfoService.getById(id);
    try {
      nutrientsInfoManager.removeImage(NutrientsInfo);
      nutrientsInfoService.delete(NutrientsInfo);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.NUTRIENTS_INFO_DELETE_FAILURE.getErrorCode(),
          AttributeErrorCodes.NUTRIENTS_INFO_DELETE_FAILURE.getErrorMessage() + id);
    }
  }

  @Override
  public PersistableNutrientsInfo getNutrientsInfo(Long id) {
    Validate.notNull(id, "ProductNutrientsInfo id cannot be null");
    PersistableNutrientsInfo nutrientsInfo = getNutrientsInfos(id);
    return nutrientsInfo;
  }

  private PersistableNutrientsInfo getNutrientsInfos(Long id) {
    NutrientsInfo nutrientsInfo = null;
    try {
      nutrientsInfo = nutrientsInfoService.getById(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.NUTRIENTS_INFO_GET_BY_ID_FAILURE.getErrorCode(),
          AttributeErrorCodes.NUTRIENTS_INFO_GET_BY_ID_FAILURE.getErrorMessage() + id);
    }
    if (Objects.isNull(nutrientsInfo)) {
      LOGGER.error("NutrientsInfo with id [" + id + " ] not found in DB.");
      throw new ResourceNotFoundException("NutrientsInfo with id [" + id + " ] not found");
    }
    return nutrientsInfoMapper.toPersistableNutrientsInfo(nutrientsInfo);
  }

  @Override
  public NutrientsInfoList getAllNutrientsInfos(
      Integer page, Integer pageSize, MerchantStore merchantStore) {
    NutrientsInfoList nutrientsInfoList = new NutrientsInfoList();
    PaginationData paginationData = new PaginationData(pageSize, page);

    Criteria criteria = new Criteria();
    criteria.setStartIndex(paginationData.getOffset() - 1);
    criteria.setPageSize(pageSize);
    criteria.setMaxCount(pageSize);

    try {
      nutrientsInfoList.setNutrientsInfos(
          Optional.ofNullable(nutrientsInfoService.getAllNutrientsInfo(criteria))
              .map(Collection::stream)
              .orElseGet(Stream::empty)
              .map(nutrientsInfoMapper::toPersistableNutrientsInfo)
              .collect(Collectors.toList()));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.NUTRIENTS_INFO_GET_ALL_FAILURE.getErrorCode(),
          AttributeErrorCodes.NUTRIENTS_INFO_GET_ALL_FAILURE.getErrorMessage());
    }
    return nutrientsInfoList;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
