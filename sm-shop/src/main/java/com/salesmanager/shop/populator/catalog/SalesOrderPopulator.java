package com.salesmanager.shop.populator.catalog;

import com.salesmanager.core.business.UnicommerceConstant;
import com.salesmanager.core.model.customer.CustomerOrderAddress;
import com.salesmanager.core.model.order.CustomerOrder;
import com.salesmanager.core.model.order.orderproduct.CustomerOrderProduct;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.shop.model.customer.PersistableCustomerSaleOrderAddress;
import com.salesmanager.shop.model.order.PersistableCustomerSaleOrderItem;
import com.salesmanager.shop.model.order.UnicommerceCreateSaleOrderRequest;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SalesOrderPopulator {

  private static final Logger LOGGER = LoggerFactory.getLogger(SalesOrderPopulator.class);

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }

  public UnicommerceCreateSaleOrderRequest populateCustomerOrderToCustomerSaleOrder(
      CustomerOrder customerOrder) {
    UnicommerceCreateSaleOrderRequest customerSaleOrder = new UnicommerceCreateSaleOrderRequest();
    customerSaleOrder.setCode(customerOrder.getOrderCode());
    customerSaleOrder.setDisplayOrderCode(String.valueOf(customerOrder.getId()));
    if (customerOrder.getStatus().getValue().equals(OrderStatus.ORDERED.getValue())) {
      customerSaleOrder.setCashOnDelivery(false);
    }
    for (CustomerOrderProduct customerOrderProduct : customerOrder.getOrderProducts()) {

      if (customerOrderProduct.getProductQuantity() == 1) {
        PersistableCustomerSaleOrderItem customerSaleOrderItem =
            new PersistableCustomerSaleOrderItem();
        customerSaleOrderItem.setItemSku(customerOrderProduct.getSku());
        customerSaleOrderItem.setChannelProductId(customerOrderProduct.getSku());
        customerSaleOrderItem.setChannelSaleOrderItemCode(
            customerOrderProduct.getOrderProductCode());
        customerSaleOrderItem.setCode(customerOrderProduct.getOrderProductCode());

        customerSaleOrderItem.setTotalPrice(customerOrderProduct.getDisplayPrice());
        customerSaleOrderItem.setShippingMethodCode(UnicommerceConstant.STD);
        customerSaleOrderItem.setGiftWrap(customerOrder.isGiftWrap());
        customerSaleOrderItem.setGiftMessage("");
        customerSaleOrderItem.setSellingPrice(customerOrderProduct.getDisplayPrice());
        customerSaleOrderItem.setDiscount(0L);
        customerSaleOrderItem.setShippingCharges(0L);
        customerSaleOrderItem.setFacilityCode(UnicommerceConstant.HABBIT);
        customerSaleOrderItem
            .getShippingAddress()
            .setReferenceId(customerOrder.getBillingAddress().getId());

        customerSaleOrder.getSaleOrderItems().add(customerSaleOrderItem);
      } else if (customerOrderProduct.getProductQuantity() > 1) {
        for (int i = 0; i < customerOrderProduct.getProductQuantity(); i++) {
          PersistableCustomerSaleOrderItem customerSaleOrderItem =
              new PersistableCustomerSaleOrderItem();
          customerSaleOrderItem.setItemSku(customerOrderProduct.getSku());
          customerSaleOrderItem.setChannelSaleOrderItemCode(
              customerOrderProduct.getOrderProductCode());
          customerSaleOrderItem.setCode(customerOrderProduct.getSku() + "-" + i);
          customerSaleOrderItem.setTotalPrice(customerOrderProduct.getDisplayPrice());
          customerSaleOrderItem.setShippingMethodCode(UnicommerceConstant.STD);
          customerSaleOrderItem.setGiftWrap(false);
          customerSaleOrderItem.setGiftMessage("");
          customerSaleOrderItem.setSellingPrice(customerOrderProduct.getDisplayPrice());
          customerSaleOrderItem.setDiscount(0L);
          customerSaleOrderItem.setChannelProductId(customerOrderProduct.getSku());
          customerSaleOrderItem.setShippingCharges(0L);
          customerSaleOrderItem.setFacilityCode(UnicommerceConstant.HABBIT);
          customerSaleOrderItem
              .getShippingAddress()
              .setReferenceId(customerOrder.getBillingAddress().getId());

          customerSaleOrder.getSaleOrderItems().add(customerSaleOrderItem);
        }
      }
    }
    // customerSaleOrder.setDisplayPrice(customerOrder.getDisplayPrice());
    // customerSaleOrder.setTotalPrice(customerOrder.getTotalPrice());
    List<PersistableCustomerSaleOrderAddress> addresses = new ArrayList<>();
    PersistableCustomerSaleOrderAddress persistableCustomerSaleOrderAddress =
        getPersistableCustomerSaleOrderAddress(customerOrder.getBillingAddress());
    addresses.add(persistableCustomerSaleOrderAddress);
    customerSaleOrder.setAddresses(addresses);

    customerSaleOrder.getBillingAddress().setReferenceId(customerOrder.getBillingAddress().getId());
    customerSaleOrder
        .getShippingAddress()
        .setReferenceId(customerOrder.getBillingAddress().getId());
    // customerSaleOrder.setGiftMessage(customerOrder.getGiftWrapMessage());
    customerSaleOrder.setTotalDiscount(customerOrder.getTotalDiscountVal());
    customerSaleOrder.setTotalShippingCharges(customerOrder.getDeliveryCharges());
    if (customerSaleOrder.isCashOnDelivery()) {
      customerSaleOrder.setTotalCashOnDeliveryCharges(
          customerOrder.getTotalPrice()
              + customerOrder.getDeliveryCharges()
              + customerOrder.getGiftWrapCharges());
    }
    customerSaleOrder.setTotalGiftWrapCharges(customerOrder.getGiftWrapCharges());
    customerSaleOrder.setTotalPrepaidAmount(customerOrder.getDisplayPrice().intValue());
    customerSaleOrder.setChannel("CUSTOM");

    return customerSaleOrder;
  }

  private PersistableCustomerSaleOrderAddress getPersistableCustomerSaleOrderAddress(
      CustomerOrderAddress source) {
    PersistableCustomerSaleOrderAddress target = new PersistableCustomerSaleOrderAddress();
    target.setId(source.getId());
    target.setAddressLine1(source.getLine1());
    target.setAddressLine2(source.getLine2());
    target.setCity(source.getCity());
    target.setCountry(source.getCountry());
    target.setPhone(source.getPhoneNumber());
    target.setPincode(source.getPinCode());
    target.setName(source.getFirstName());
    target.setState(source.getState());
    return target;
  }
}
