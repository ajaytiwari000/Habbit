package com.salesmanager.shop.store.facade.unicomMetaData;

import com.salesmanager.core.business.services.unicomMetaData.SaleOrderItemsMetaDataService;
import com.salesmanager.core.business.services.unicomMetaData.SaleOrderMetaDataService;
import com.salesmanager.core.business.services.unicomMetaData.SaleOrderShippingPackageMetaDataService;
import com.salesmanager.core.model.order.CustomerSaleOrder;
import com.salesmanager.core.model.order.SaleOrderItemsMetaData;
import com.salesmanager.core.model.order.SaleOrderMetaData;
import com.salesmanager.core.model.order.SaleOrderShippingPackageMetaData;
import com.salesmanager.shop.admin.controller.unicomMetaData.SaleOrderMetaDataFacade;
import com.salesmanager.shop.error.codes.OrderErrorCodes;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("saleOrderMetaDataFacade")
public class SaleOrderMetaDataFacadeImpl implements SaleOrderMetaDataFacade {
  private static final Logger LOGGER = LoggerFactory.getLogger(SaleOrderMetaDataFacadeImpl.class);

  @Inject private SaleOrderMetaDataService saleOrderMetaDataService;
  @Inject private SaleOrderShippingPackageMetaDataService saleOrderShippingPackageMetaDataService;
  @Inject private SaleOrderItemsMetaDataService saleOrderItemsMetaDataService;

  @Override
  public void createOrUpdate(
      SaleOrderMetaData saleOrderMetaData, CustomerSaleOrder customerSaleOrderModel) {
    // todo : need to update subentity of SaleOrderShippingPackageMetaData named
    // SaleOrderShippingPackageItemCodeMetaData
    SaleOrderMetaData saleOrderMetaDataModel = null;
    if (Objects.isNull(customerSaleOrderModel.getSaleOrderMetaData())) {
      List<SaleOrderShippingPackageMetaData> metaDataShipperList =
          saleOrderMetaData.getShipperList();
      List<SaleOrderItemsMetaData> itemsMetaDataList = saleOrderMetaData.getOrderItemDetails();
      try {
        saleOrderMetaDataModel = saleOrderMetaDataService.create(saleOrderMetaData);
      } catch (Exception e) {
        throwServiceRuntImeException(
            e,
            OrderErrorCodes.CREATE_SALE_ORDER_META_DATA.getErrorCode(),
            OrderErrorCodes.CREATE_SALE_ORDER_META_DATA.getErrorMessage());
      }
      createSOMetaDateWithShippingPackage(saleOrderMetaDataModel, metaDataShipperList);
      createSOMetaDataWithItemDetails(saleOrderMetaDataModel, itemsMetaDataList);
      customerSaleOrderModel.setSaleOrderMetaData(saleOrderMetaDataModel);
    } else {
      saleOrderMetaDataModel = customerSaleOrderModel.getSaleOrderMetaData();
      List<SaleOrderShippingPackageMetaData> metaDataShipperList =
          saleOrderMetaData.getShipperList();
      List<SaleOrderItemsMetaData> itemsMetaDataList = saleOrderMetaData.getOrderItemDetails();

      if (saleOrderMetaDataModel.getShipperList().size() > 0) {
        updateSOMetaDateWithShippingPackage(saleOrderMetaDataModel, metaDataShipperList);
      } else {
        createSOMetaDateWithShippingPackage(saleOrderMetaDataModel, metaDataShipperList);
      }

      updateSOMetaDataWithItemDetails(saleOrderMetaDataModel, itemsMetaDataList);
      populateSaleOrderMetaData(saleOrderMetaData, saleOrderMetaDataModel);
      try {
        saleOrderMetaDataService.update(saleOrderMetaDataModel);
      } catch (Exception e) {
        throwServiceRuntImeException(
            e,
            OrderErrorCodes.UPDATE_SALE_ORDER_META_DATA.getErrorCode(),
            OrderErrorCodes.UPDATE_SALE_ORDER_META_DATA.getErrorMessage());
      }
    }
  }

  private void populateSaleOrderMetaData(SaleOrderMetaData source, SaleOrderMetaData target) {}

  private void updateSOMetaDataWithItemDetails(
      SaleOrderMetaData saleOrderMetaDataModel, List<SaleOrderItemsMetaData> itemsMetaDataList) {
    for (SaleOrderItemsMetaData saleOrderItemsMetaData :
        itemsMetaDataList) { // update item detail to customer sale order
      SaleOrderItemsMetaData itemsMetaData =
          saleOrderMetaDataModel.getOrderItemDetails().stream()
              .filter(obj -> obj.getItemSku().equals(saleOrderItemsMetaData.getItemSku()))
              .collect(Collectors.toList())
              .get(0);

      saleOrderItemsMetaData.setStatusCode(itemsMetaData.getStatusCode());
      saleOrderItemsMetaData.setShippingPackageStatus(itemsMetaData.getShippingPackageStatus());
      // saleOrderItemDetailModel.getChannelSaleOrderItemCode(saleOrderItemDetails.getChannelSaleOrderItemCode());
      try {
        saleOrderItemsMetaDataService.update(saleOrderItemsMetaData);
      } catch (Exception e) {
        throwServiceRuntImeException(
            e,
            OrderErrorCodes.UPDATE_SALE_ORDER_ITEM_DETAIL_META_DATA.getErrorCode(),
            OrderErrorCodes.UPDATE_SALE_ORDER_ITEM_DETAIL_META_DATA.getErrorMessage());
      }
    }
  }

  private void updateSOMetaDateWithShippingPackage(
      SaleOrderMetaData saleOrderMetaDataModel,
      List<SaleOrderShippingPackageMetaData> metaDataShipperList) {

    for (SaleOrderShippingPackageMetaData saleOrderShippingPackageMetaData :
        metaDataShipperList) { // update shipper detail to sale order meta data
      SaleOrderShippingPackageMetaData packageMetaData =
          saleOrderMetaDataModel.getShipperList().stream()
              .filter(
                  obj ->
                      obj.getSaleOrderCode()
                          .equals(saleOrderShippingPackageMetaData.getSaleOrderCode()))
              .collect(Collectors.toList())
              .get(0);

      saleOrderShippingPackageMetaData.setStatus(packageMetaData.getStatus());
      saleOrderShippingPackageMetaData.setTrackingStatus(packageMetaData.getTrackingStatus());
      saleOrderShippingPackageMetaData.setCourierStatus(packageMetaData.getCourierStatus());
      try {
        saleOrderShippingPackageMetaDataService.update(saleOrderShippingPackageMetaData);
      } catch (Exception e) {
        throwServiceRuntImeException(
            e,
            OrderErrorCodes.UPDATE_SALE_ORDER_SHIPPING_DETAIL_META_DATA.getErrorCode(),
            OrderErrorCodes.UPDATE_SALE_ORDER_SHIPPING_DETAIL_META_DATA.getErrorMessage());
      }
    }
  }

  private void createSOMetaDataWithItemDetails(
      SaleOrderMetaData saleOrderMetaDataModel, List<SaleOrderItemsMetaData> itemsMetaDataList) {
    for (SaleOrderItemsMetaData saleOrderItemsMetaData :
        itemsMetaDataList) { // create and update shipping detail to sale order meta data
      try {
        saleOrderItemsMetaData = saleOrderItemsMetaDataService.create(saleOrderItemsMetaData);
      } catch (Exception e) {
        throwServiceRuntImeException(
            e,
            OrderErrorCodes.CREATE_SALE_ORDER_ITEM_DETAIL_META_DATA.getErrorCode(),
            OrderErrorCodes.CREATE_SALE_ORDER_ITEM_DETAIL_META_DATA.getErrorMessage());
      }
      saleOrderItemsMetaData.setSaleOrderMetaData(saleOrderMetaDataModel);
      try {
        saleOrderItemsMetaDataService.update(saleOrderItemsMetaData);
      } catch (Exception e) {
        throwServiceRuntImeException(
            e,
            OrderErrorCodes.CREATE_SALE_ORDER_ITEM_DETAIL_META_DATA.getErrorCode(),
            OrderErrorCodes.CREATE_SALE_ORDER_ITEM_DETAIL_META_DATA.getErrorMessage());
      }
    }
  }

  private void createSOMetaDateWithShippingPackage(
      SaleOrderMetaData saleOrderMetaDataModel,
      List<SaleOrderShippingPackageMetaData> metaDataShipperList) {
    for (SaleOrderShippingPackageMetaData saleOrderShippingPackageMetaData :
        metaDataShipperList) { // create and update shipping detail to  sale order meta data
      try {
        saleOrderShippingPackageMetaData =
            saleOrderShippingPackageMetaDataService.create(saleOrderShippingPackageMetaData);
      } catch (Exception e) {
        throwServiceRuntImeException(
            e,
            OrderErrorCodes.CREATE_SALE_ORDER_SHIPPING_DETAIL_META_DATA.getErrorCode(),
            OrderErrorCodes.CREATE_SALE_ORDER_SHIPPING_DETAIL_META_DATA.getErrorMessage());
      }
      saleOrderShippingPackageMetaData.setSaleOrderMetaData(saleOrderMetaDataModel);
      try {
        saleOrderShippingPackageMetaDataService.update(saleOrderShippingPackageMetaData);
      } catch (Exception e) {
        throwServiceRuntImeException(
            e,
            OrderErrorCodes.UPDATE_SALE_ORDER_SHIPPING_DETAIL_META_DATA.getErrorCode(),
            OrderErrorCodes.UPDATE_SALE_ORDER_SHIPPING_DETAIL_META_DATA.getErrorMessage());
      }
    }
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
