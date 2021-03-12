/** */
package com.salesmanager.shop.admin.controller.customer.membership;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.CustomerOrder;
import com.salesmanager.core.model.order.CustomerSaleOrder;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.shop.model.customer.PersistableCheckoutCart;
import com.salesmanager.shop.model.customer.PersistableCustomerOrder;

public interface CustomerOrderFacade {

  PersistableCustomerOrder create(
      PersistableCustomerOrder persistableCustomerOrder, MerchantStore merchantStore);

  PersistableCustomerOrder checkoutCart(
      PersistableCheckoutCart persistableCheckoutCart, MerchantStore merchantStore, String phone)
      throws Exception;

  CustomerOrder getByOrderCode(String receipt);

  CustomerOrder update(CustomerOrder customerOrder);

  PersistableCustomerOrder getOrderDetails(String orderCode, MerchantStore merchantStore);

  CustomerOrder updateOrderCustomerAfterPayment(String receipt, boolean successful)
      throws Exception;

  void orderCancel(String orderCode, MerchantStore merchantStore, OrderStatus orderStatus);

  PersistableCustomerOrder getByOrderId(Long id, MerchantStore merchantStore);

  String getTrackingUrl(String orderCode);

  void sendOrderShippedMail(CustomerSaleOrder customerSaleOrder);

  void sendOrderDeliveredMail(CustomerSaleOrder customerSaleOrder);

  void sendOrderConfirmationSms(CustomerOrder customerOrder);

  void sendOrderShippedSms(CustomerSaleOrder customerSaleOrder);

  void sendOrderConfirmationMail(CustomerOrder customerOrder);
}
