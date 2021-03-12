/** */
package com.salesmanager.shop.store.facade.customer.metaData;

import com.salesmanager.core.business.services.customer.metaData.CustomerMetaDataService;
import com.salesmanager.core.model.customer.CustomerMetaData;
import com.salesmanager.shop.error.codes.CustomerMetaDataErrorCodes;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.facade.customer.CustomerFacade;
import com.salesmanager.shop.utils.SessionUtil;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerMetaDataFacade")
public class CustomerMetaDataFacadeImpl implements CustomerMetaDataFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerMetaDataFacadeImpl.class);
  @Inject private CustomerFacade customerFacade;
  @Inject private SessionUtil sessionUtil;
  @Inject private CustomerMetaDataService customerMetaDataService;

  @Override
  public CustomerMetaData createCustomerMetaData(CustomerMetaData customerMetaData) {
    CustomerMetaData customerMetaDataModel = getCustomerMetaData(customerMetaData.getPhone());
    if (Objects.nonNull(customerMetaDataModel)) {
      return customerMetaDataModel;
    }
    try {
      customerMetaData = customerMetaDataService.create(customerMetaData);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerMetaDataErrorCodes.CUSTOMER_META_DATA_CREATE_FAILURE.getErrorCode(),
          CustomerMetaDataErrorCodes.CUSTOMER_META_DATA_CREATE_FAILURE.getErrorMessage());
    }
    return customerMetaData;
  }

  @Override
  public CustomerMetaData getCustomerMetaData(String phone) {
    CustomerMetaData customerMetaData = null;
    try {
      customerMetaData = customerMetaDataService.getByPhone(phone);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerMetaDataErrorCodes.CUSTOMER_META_DATA_GET_FAILURE.getErrorCode(),
          CustomerMetaDataErrorCodes.CUSTOMER_META_DATA_GET_FAILURE.getErrorMessage());
    }
    return customerMetaData;
  }

  @Override
  public CustomerMetaData updateCustomerMetaData(CustomerMetaData customerMetaData) {
    CustomerMetaData customerMetaDataModel = getCustomerMetaData(customerMetaData.getPhone());
    updatePopulator(customerMetaData, customerMetaDataModel);
    try {
      customerMetaDataModel = customerMetaDataService.update(customerMetaDataModel);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerMetaDataErrorCodes.CUSTOMER_META_DATA_UPDATE_FAILURE.getErrorCode(),
          CustomerMetaDataErrorCodes.CUSTOMER_META_DATA_UPDATE_FAILURE.getErrorMessage());
    }
    return customerMetaDataModel;
  }

  @Override
  public List<CustomerMetaData> getMetaDataList(Date from, Date to) {
    List<CustomerMetaData> customerMetaDataList = null;
    try {
      customerMetaDataList = customerMetaDataService.getMetaDataList(from, to);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerMetaDataErrorCodes.CUSTOMER_META_DATA_GET_LIST_FAILURE.getErrorCode(),
          CustomerMetaDataErrorCodes.CUSTOMER_META_DATA_GET_LIST_FAILURE.getErrorMessage());
    }
    return customerMetaDataList;
  }

  private void updatePopulator(CustomerMetaData source, CustomerMetaData target) {
    target.setMailSent(source.isMailSent());
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
