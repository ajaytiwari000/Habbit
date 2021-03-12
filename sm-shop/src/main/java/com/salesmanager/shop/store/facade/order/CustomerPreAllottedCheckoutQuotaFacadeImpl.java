/** */
package com.salesmanager.shop.store.facade.order;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.membership.CustomerPreAllottedCheckoutQuotaService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shoppingcart.CustomerPreAllottedCheckoutQuota;
import com.salesmanager.shop.admin.controller.customer.membership.CustomerPreAllottedCheckoutQuotaFacade;
import com.salesmanager.shop.error.codes.CustomerErrorCodes;
import com.salesmanager.shop.model.customer.PersistableCustomerPreAllottedCheckoutQuota;
import com.salesmanager.shop.model.customer.mapper.CustomerPreAllottedCheckoutQuotaMapper;
import com.salesmanager.shop.store.api.exception.ResourceDuplicateException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.facade.authentication.util.AuthenticationTokenUtil;
import java.util.Objects;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerPreAllottedCheckoutQuotaFacade")
public class CustomerPreAllottedCheckoutQuotaFacadeImpl
    implements CustomerPreAllottedCheckoutQuotaFacade {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomerPreAllottedCheckoutQuotaFacadeImpl.class);
  @Inject private CustomerPreAllottedCheckoutQuotaService customerPreAllottedCheckoutQuotaService;
  @Inject private CustomerPreAllottedCheckoutQuotaMapper customerPreAllottedCheckoutQuotaMapper;
  @Inject private AuthenticationTokenUtil authenticationTokenUtil;

  @Override
  public PersistableCustomerPreAllottedCheckoutQuota create(
      PersistableCustomerPreAllottedCheckoutQuota persistableCustomerPreAllottedCheckoutQuota,
      MerchantStore merchantStore) {
    CustomerPreAllottedCheckoutQuota customerPreAllottedCheckoutQuota = null;
    if (StringUtils.isNotEmpty(persistableCustomerPreAllottedCheckoutQuota.getCartItemCode())) {
      customerPreAllottedCheckoutQuota =
          getByCustomerProductConsumptionPending(
              persistableCustomerPreAllottedCheckoutQuota.getCartItemCode(),
              persistableCustomerPreAllottedCheckoutQuota.getCustomerPhone());
    }
    if (Objects.nonNull(customerPreAllottedCheckoutQuota)) {
      LOGGER.error(
          "customerPreAllottedCheckoutQuota Already exists for cartItem {}",
          persistableCustomerPreAllottedCheckoutQuota.getCartItemCode());
      throw new ResourceDuplicateException(
          "customerPreAllottedCheckoutQuota Already exists for cartItem "
              + persistableCustomerPreAllottedCheckoutQuota.getCartItemCode());
    }
    customerPreAllottedCheckoutQuota =
        customerPreAllottedCheckoutQuotaMapper.toCustomerPreAllottedCheckoutQuota(
            persistableCustomerPreAllottedCheckoutQuota);
    try {
      customerPreAllottedCheckoutQuota =
          customerPreAllottedCheckoutQuotaService.create(customerPreAllottedCheckoutQuota);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_PRODUCT_CONSUMPTION_PENDING_CREATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_PRODUCT_CONSUMPTION_PENDING_CREATE_FAILURE.getErrorMessage());
    }
    persistableCustomerPreAllottedCheckoutQuota.setId(customerPreAllottedCheckoutQuota.getId());
    return persistableCustomerPreAllottedCheckoutQuota;
  }

  @Override
  public PersistableCustomerPreAllottedCheckoutQuota update(
      PersistableCustomerPreAllottedCheckoutQuota persistableCustomerPreAllottedCheckoutQuota,
      MerchantStore merchantStore) {
    CustomerPreAllottedCheckoutQuota customerPreAllottedCheckoutQuota =
        customerPreAllottedCheckoutQuotaMapper.toCustomerPreAllottedCheckoutQuota(
            (persistableCustomerPreAllottedCheckoutQuota));
    try {
      customerPreAllottedCheckoutQuota =
          customerPreAllottedCheckoutQuotaService.update(customerPreAllottedCheckoutQuota);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_PRODUCT_CONSUMPTION_PENDING_UPDATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_PRODUCT_CONSUMPTION_PENDING_UPDATE_FAILURE.getErrorMessage());
    }
    return customerPreAllottedCheckoutQuotaMapper.toPersistableCustomerPreAllottedCheckoutQuota(
        customerPreAllottedCheckoutQuota);
  }

  @Override
  public void deleteById(Long id) {
    Validate.notNull(id, "CustomerPreAllottedCheckoutQuota id cannot be null");
    CustomerPreAllottedCheckoutQuota CustomerPreAllottedCheckoutQuota = null;
    try {
      CustomerPreAllottedCheckoutQuota = getById(id);
      customerPreAllottedCheckoutQuotaService.delete(CustomerPreAllottedCheckoutQuota);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_PRODUCT_CONSUMPTION_PENDING_DELETE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_PRODUCT_CONSUMPTION_PENDING_DELETE_FAILURE.getErrorMessage());
    }
  }

  private CustomerPreAllottedCheckoutQuota getById(Long id) {
    CustomerPreAllottedCheckoutQuota customerPreAllottedCheckoutQuota = null;
    try {
      customerPreAllottedCheckoutQuota = customerPreAllottedCheckoutQuotaService.getById(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_PRODUCT_CONSUMPTION_PENDING_GET_BY_ID_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_PRODUCT_CONSUMPTION_PENDING_GET_BY_ID_FAILURE
                  .getErrorMessage()
              + id);
    }
    if (Objects.isNull(customerPreAllottedCheckoutQuota)) {
      LOGGER.error("customerPreAllottedCheckoutQuota with id {} not found in DB.", id);
      throw new ResourceNotFoundException(
          "customerPreAllottedCheckoutQuota with id [" + id + " ] not found");
    }
    return customerPreAllottedCheckoutQuota;
  }

  @Override
  public PersistableCustomerPreAllottedCheckoutQuota
      getCustomerProductConsumptionPendingByCartItemCode(String cartItemCode, String phone) {
    CustomerPreAllottedCheckoutQuota customerPreAllottedCheckoutQuota =
        getByCustomerProductConsumptionPending(cartItemCode, phone);
    return customerPreAllottedCheckoutQuotaMapper.toPersistableCustomerPreAllottedCheckoutQuota(
        customerPreAllottedCheckoutQuota);
  }

  private CustomerPreAllottedCheckoutQuota getByCustomerProductConsumptionPending(
      String cartItemCode, String phone) {
    CustomerPreAllottedCheckoutQuota customerPreAllottedCheckoutQuota = null;
    try {
      customerPreAllottedCheckoutQuota =
          customerPreAllottedCheckoutQuotaService
              .getCustomerProductConsumptionPendingByCartItemCode(cartItemCode, phone);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_PRODUCT_CONSUMPTION_PENDING_CREATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_PRODUCT_CONSUMPTION_PENDING_CREATE_FAILURE.getErrorMessage()
              + cartItemCode);
    }
    return customerPreAllottedCheckoutQuota;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
