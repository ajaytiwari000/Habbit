package com.salesmanager.shop.populator.catalog;

import com.razorpay.Order;
import com.salesmanager.core.model.order.CustomerRazorPayOrder;
import com.salesmanager.core.model.order.enums.RazorPayOrderStatus;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.shop.model.customer.PersistableCustomerOrder;
import com.salesmanager.shop.model.customer.PersistableCustomerRazorPayOrder;
import com.salesmanager.shop.model.customer.mapper.CustomerRazorPayOrderMapper;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import java.util.Calendar;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PersistableRazorPayOrderPopulator {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(PersistableRazorPayOrderPopulator.class);
  @Inject private CustomerRazorPayOrderMapper customerRazorPayOrderMapper;

  public CustomerRazorPayOrder populateCustomerRazorPayOrder(
      Order razorPayOrder, PersistableCustomerOrder persistableCustomerOrder) {
    PersistableCustomerRazorPayOrder persistableCustomerRazorPayOrder =
        new PersistableCustomerRazorPayOrder();
    persistableCustomerRazorPayOrder.setOrderCode(razorPayOrder.get("id").toString());
    persistableCustomerRazorPayOrder.setStatus(OrderStatus.PENDING);
    persistableCustomerRazorPayOrder.setEntity("order");
    persistableCustomerRazorPayOrder.setAmount(
        persistableCustomerOrder.getDisplayPrice().intValue());
    persistableCustomerRazorPayOrder.setAmountDue(persistableCustomerOrder.getDisplayPrice());
    persistableCustomerRazorPayOrder.setCurrency("INR");
    persistableCustomerRazorPayOrder.setReceipt(persistableCustomerOrder.getOrderCode());
    persistableCustomerRazorPayOrder.setRazorPayOrderStatus(RazorPayOrderStatus.CREATED);
    persistableCustomerRazorPayOrder.setCreatedAt(Calendar.getInstance().getTimeInMillis());

    return customerRazorPayOrderMapper.toCustomerRazorPayOrder(persistableCustomerRazorPayOrder);
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
