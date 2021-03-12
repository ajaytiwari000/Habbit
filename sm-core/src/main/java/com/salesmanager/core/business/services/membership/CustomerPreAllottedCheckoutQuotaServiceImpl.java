package com.salesmanager.core.business.services.membership;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.CustomerPreAllottedCheckoutQuotaRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.shoppingcart.CustomerPreAllottedCheckoutQuota;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerPreAllottedCheckoutQuotaService")
public class CustomerPreAllottedCheckoutQuotaServiceImpl
    extends SalesManagerEntityServiceImpl<Long, CustomerPreAllottedCheckoutQuota>
    implements CustomerPreAllottedCheckoutQuotaService {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomerPreAllottedCheckoutQuotaServiceImpl.class);
  private CustomerPreAllottedCheckoutQuotaRepository customerPreAllottedCheckoutQuotaRepository;

  @Inject
  public CustomerPreAllottedCheckoutQuotaServiceImpl(
      CustomerPreAllottedCheckoutQuotaRepository customerPreAllottedCheckoutQuotaRepository) {
    super(customerPreAllottedCheckoutQuotaRepository);
    this.customerPreAllottedCheckoutQuotaRepository = customerPreAllottedCheckoutQuotaRepository;
  }

  @Override
  public CustomerPreAllottedCheckoutQuota getById(Long id) {
    return customerPreAllottedCheckoutQuotaRepository.getById(id).orElse(null);
  }

  @Override
  public CustomerPreAllottedCheckoutQuota getCustomerProductConsumptionPendingByCartItemCode(
      String cartItemCode, String phone) throws ServiceException {
    try {
      return customerPreAllottedCheckoutQuotaRepository
          .getCustomerPreAllottedCheckoutQuotaByCartItemCode(cartItemCode, phone)
          .orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
