package com.salesmanager.shop.store.facade.order;

import com.salesmanager.core.business.services.order.CustomerSaleOrderService;
import com.salesmanager.core.business.services.order.sale.SaleOrderItemDetailsService;
import com.salesmanager.core.business.services.order.sale.SaleOrderShipperDetailsService;
import com.salesmanager.core.model.order.CustomerSaleOrder;
import com.salesmanager.core.model.order.SaleOrderItemDetails;
import com.salesmanager.core.model.order.SaleOrderMetaData;
import com.salesmanager.core.model.order.SaleOrderShipperDetails;
import com.salesmanager.shop.admin.controller.saleOrder.CustomerSaleOrderFacade;
import com.salesmanager.shop.admin.controller.unicomMetaData.SaleOrderMetaDataFacade;
import com.salesmanager.shop.error.codes.OrderErrorCodes;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerSaleOrderFacade")
public class CustomerSaleOrderFacadeImpl implements CustomerSaleOrderFacade {
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerSaleOrderFacadeImpl.class);
  @Inject private CustomerSaleOrderService customerSaleOrderService;
  @Inject private SaleOrderShipperDetailsService saleOrderShipperDetailsService;
  @Inject private SaleOrderItemDetailsService saleOrderItemDetailsService;
  @Inject private SaleOrderMetaDataFacade saleOrderMetaDataFacade;

  @Override
  public CustomerSaleOrder createCustomerSaleOrder(CustomerSaleOrder saleOrder) {
    CustomerSaleOrder customerSaleOrder = null;
    try {
      customerSaleOrder = customerSaleOrderService.create(saleOrder);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          OrderErrorCodes.CREATE_SALE_ORDER.getErrorCode(),
          OrderErrorCodes.CREATE_SALE_ORDER.getErrorMessage());
    }
    return customerSaleOrder;
  }

  @Override
  public void updateSaleOrderAndMetaData(
      CustomerSaleOrder customerSaleOrder, SaleOrderMetaData saleOrderMetaData) {
    CustomerSaleOrder customerSaleOrderModel = getByCode(customerSaleOrder.getCode());
    if (Objects.isNull(customerSaleOrderModel)) {
      throw new ResourceNotFoundException(
          "customer sale order not exist for {} code", customerSaleOrder.getCode());
    }

    saleOrderMetaDataFacade.createOrUpdate(saleOrderMetaData, customerSaleOrderModel);

    createOrUpdateCustomerSaleOrderShipperDetails(customerSaleOrder, customerSaleOrderModel);
    createOrUpdateCustomerSaleOrderItemDetails(customerSaleOrder, customerSaleOrderModel);
    populateToCustomerSaleOderModel(customerSaleOrder, customerSaleOrderModel);
    try {
      customerSaleOrderModel = customerSaleOrderService.update(customerSaleOrderModel);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          OrderErrorCodes.UPDATE_SALE_ORDER.getErrorCode(),
          OrderErrorCodes.UPDATE_SALE_ORDER.getErrorMessage());
    }
  }

  private void populateToCustomerSaleOderModel(CustomerSaleOrder source, CustomerSaleOrder target) {
    target.setStatus(source.getStatus());
  }

  private void createOrUpdateCustomerSaleOrderItemDetails(
      CustomerSaleOrder customerSaleOrder, CustomerSaleOrder customerSaleOrderModel) {

    if (customerSaleOrderModel.getOrderItemDetails().size() == 0) {
      for (SaleOrderItemDetails saleOrderItemDetail :
          customerSaleOrder
              .getOrderItemDetails()) { // create and update item detail to customer sale order
        try {
          saleOrderItemDetail = saleOrderItemDetailsService.create(saleOrderItemDetail);
        } catch (Exception e) {
          throwServiceRuntImeException(
              e,
              OrderErrorCodes.CREATE_SALE_ORDER_ITEM_DETAIL.getErrorCode(),
              OrderErrorCodes.CREATE_SALE_ORDER_ITEM_DETAIL.getErrorMessage());
        }
        saleOrderItemDetail.setCustomerSaleOrder(customerSaleOrderModel);
        try {
          saleOrderItemDetailsService.update(saleOrderItemDetail);
        } catch (Exception e) {
          throwServiceRuntImeException(
              e,
              OrderErrorCodes.UPDATE_SALE_ORDER_ITEM_DETAIL.getErrorCode(),
              OrderErrorCodes.UPDATE_SALE_ORDER_ITEM_DETAIL.getErrorMessage());
        }
      }
    } else {

      for (SaleOrderItemDetails saleOrderItemDetailModel :
          customerSaleOrderModel
              .getOrderItemDetails()) { // update shipper detail to customer sale order
        SaleOrderItemDetails saleOrderItemDetails =
            customerSaleOrder.getOrderItemDetails().stream()
                .filter(obj -> obj.getItemSku().equals(saleOrderItemDetailModel.getItemSku()))
                .collect(Collectors.toList())
                .get(0);

        saleOrderItemDetailModel.setStatusCode(saleOrderItemDetails.getStatusCode());
        saleOrderItemDetailModel.setCode(saleOrderItemDetails.getCode());
        saleOrderItemDetailModel.setSellerSkuCode(saleOrderItemDetails.getSellerSkuCode());
        // saleOrderItemDetailModel.getChannelSaleOrderItemCode(saleOrderItemDetails.getChannelSaleOrderItemCode());
        saleOrderItemDetailModel.setProductName(saleOrderItemDetails.getProductName());
        saleOrderItemDetailModel.setDisplayPrice(saleOrderItemDetails.getDisplayPrice());
        try {
          saleOrderItemDetailsService.update(saleOrderItemDetailModel);
        } catch (Exception e) {
          throwServiceRuntImeException(
              e,
              OrderErrorCodes.UPDATE_SALE_ORDER_ITEM_DETAIL.getErrorCode(),
              OrderErrorCodes.UPDATE_SALE_ORDER_ITEM_DETAIL.getErrorMessage());
        }
      }
    }
  }

  private void createOrUpdateCustomerSaleOrderShipperDetails(
      CustomerSaleOrder customerSaleOrder, CustomerSaleOrder customerSaleOrderModel) {
    if (customerSaleOrderModel.getShipperList().size() == 0) {
      for (SaleOrderShipperDetails shipperDetails :
          customerSaleOrder
              .getShipperList()) { // create and update shipper detail to customer sale order
        try {
          shipperDetails = saleOrderShipperDetailsService.create(shipperDetails);
        } catch (Exception e) {
          throwServiceRuntImeException(
              e,
              OrderErrorCodes.CREATE_SALE_ORDER_SHIPPING_DETAIL.getErrorCode(),
              OrderErrorCodes.CREATE_SALE_ORDER_SHIPPING_DETAIL.getErrorMessage());
        }
        shipperDetails.setCustomerSaleOrder(customerSaleOrderModel);
        try {
          saleOrderShipperDetailsService.update(shipperDetails);
        } catch (Exception e) {
          throwServiceRuntImeException(
              e,
              OrderErrorCodes.UPDATE_SALE_ORDER_SHIPPING_DETAIL.getErrorCode(),
              OrderErrorCodes.UPDATE_SALE_ORDER_SHIPPING_DETAIL.getErrorMessage());
        }
      }
    } else {

      for (SaleOrderShipperDetails shipperDetailsModel :
          customerSaleOrderModel
              .getShipperList()) { // update existing shipper detail to customer sale order
        SaleOrderShipperDetails shipperDetails =
            customerSaleOrder.getShipperList().stream()
                .filter(obj -> obj.getCode().equals(shipperDetailsModel.getCode()))
                .collect(Collectors.toList())
                .get(0);
        shipperDetailsModel.setUnicommerceShippingStatus(
            shipperDetails.getUnicommerceShippingStatus());
        shipperDetailsModel.setTrackingStatus(shipperDetails.getTrackingStatus());
        shipperDetailsModel.setShippingProvider(shipperDetails.getShippingProvider());
        shipperDetailsModel.setTrackingNumber(shipperDetails.getTrackingNumber());
        shipperDetailsModel.setCourierStatus(shipperDetails.getCourierStatus());
        try {
          saleOrderShipperDetailsService.update(shipperDetailsModel);
        } catch (Exception e) {
          throwServiceRuntImeException(
              e,
              OrderErrorCodes.UPDATE_SALE_ORDER_SHIPPING_DETAIL.getErrorCode(),
              OrderErrorCodes.UPDATE_SALE_ORDER_SHIPPING_DETAIL.getErrorMessage());
        }
      }
    }
  }

  @Override
  public CustomerSaleOrder getByCode(String code) {
    CustomerSaleOrder customerSaleOrder = null;
    try {
      customerSaleOrder = customerSaleOrderService.getByCode(code);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          OrderErrorCodes.GET_CODE_SALE_ORDER.getErrorCode(),
          OrderErrorCodes.GET_CODE_SALE_ORDER.getErrorMessage() + code);
    }
    return customerSaleOrder;
  }

  @Override
  public CustomerSaleOrder updateOrder(CustomerSaleOrder customerSaleOrder) {
    try {
      customerSaleOrder = customerSaleOrderService.update(customerSaleOrder);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          OrderErrorCodes.UPDATE_SALE_ORDER.getErrorCode(),
          OrderErrorCodes.UPDATE_SALE_ORDER.getErrorMessage() + customerSaleOrder.getCode());
    }
    return customerSaleOrder;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
