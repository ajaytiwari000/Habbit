package com.salesmanager.core.business.services.customer.checkoutAudit;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.customer.checkoutAudit.CheckoutAuditFlowRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.order.CheckoutAuditFlow;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("checkoutAuditFlowService")
public class CheckoutAuditFlowServiceImpl
    extends SalesManagerEntityServiceImpl<Long, CheckoutAuditFlow>
    implements CheckoutAuditFlowService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutAuditFlowServiceImpl.class);

  private CheckoutAuditFlowRepository checkoutAuditFlowRepository;

  @Inject
  public CheckoutAuditFlowServiceImpl(CheckoutAuditFlowRepository checkoutAuditFlowRepository) {
    super(checkoutAuditFlowRepository);
    this.checkoutAuditFlowRepository = checkoutAuditFlowRepository;
  }

  @Override
  public CheckoutAuditFlow getByOrderCode(String orderCode) throws ServiceException {
    try {
      return checkoutAuditFlowRepository.getByOrderCode(orderCode).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
