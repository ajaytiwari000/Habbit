/** */
package com.salesmanager.shop.store.facade.payments.gateway.razorpay;

import com.razorpay.Customer;
import com.razorpay.Order;
import com.razorpay.Refund;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.CustomerRazorPayOrder;
import com.salesmanager.shop.model.customer.PersistableCustomerOrder;
import com.salesmanager.shop.model.customer.PersistableCustomerRazorPayOrder;
import com.salesmanager.shop.model.customer.PersistableRazorPayDetail;

public interface CustomerRazorPayFacade {

  PersistableRazorPayDetail getRazorPayDetail(String ordercode);

  PersistableCustomerRazorPayOrder updateRazorPayDetail(
      PersistableCustomerRazorPayOrder persistableCustomerRazorPayOrder,
      MerchantStore merchantStore,
      boolean isWebhooks)
      throws Exception;

  Order createRazorPayOrder(int intValue, String orderCode);

  Customer createRazorPayCustomer(String customerName, String customerEmail, String customerPhone);

  PersistableCustomerRazorPayOrder createRazorPayOrderByRazorPayOrder(
      Order razorPayOrder, PersistableCustomerOrder persistableCustomerOrder);

  Refund refundAmountToCustomer(String orderCode);

  CustomerRazorPayOrder getByReceipt(String orderCode);

  CustomerRazorPayOrder getByRazorPayOrder(String orderCode);

  void updateRazorPayDetailByWebhooks(String payload, MerchantStore merchantStore) throws Exception;
}
