package com.salesmanager.shop.store.unicom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.salesmanager.core.business.UnicommerceConstant;
import com.salesmanager.core.model.customer.AuthenticationTokenStatus;
import com.salesmanager.core.model.order.*;
import com.salesmanager.core.model.order.orderstatus.UnicommerceOrderItemStatus;
import com.salesmanager.core.model.order.orderstatus.UnicommerceShippingStatus;
import com.salesmanager.core.model.unicommerce.UnicommerceAuthenticationToken;
import com.salesmanager.shop.admin.controller.customer.membership.CustomerOrderFacade;
import com.salesmanager.shop.admin.controller.saleOrder.CustomerSaleOrderFacade;
import com.salesmanager.shop.admin.controller.unicom.UnicommerceService;
import com.salesmanager.shop.cache.util.CacheUtil;
import com.salesmanager.shop.error.codes.UnicommerceErrorCodes;
import com.salesmanager.shop.model.order.*;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.authentication.unicom.UnicommerceAuthenticationTokenFacade;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("unicommerceServiceProvider")
public class UnicommerceServiceProvider implements UnicommerceService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UnicommerceServiceProvider.class);

  @Value("${unicom.username}")
  private String username;

  @Value("${unicom.pass}")
  private String password;

  private static final Object lock = new Object();
  @Inject private CacheUtil cacheUtil;
  @Inject private CustomerSaleOrderFacade customerSaleOrderFacade;
  @Inject private UnicommerceAuthenticationTokenFacade unicommerceAuthenticationTokenFacade;
  @Inject private UnicommerceUtil unicommerceUtil;
  @Inject private CustomerOrderFacade customerOrderFacade;

  @Override
  public UnicommerceAuthenticationToken createToken() throws Exception {
    String url =
        "https://habbit.unicommerce.com/oauth/token?"
            + "grant_type=password&"
            + "client_id=my-trusted-client&"
            + "username="
            + UnicommerceConstant.USERNAME
            + "&password="
            + UnicommerceConstant.PASSWORD;
    LOGGER.info("url {} ", url);
    RestTemplate restTemplate = new RestTemplate();
    try {
      String response = restTemplate.getForObject(url, String.class);
      UnicommerceAuthenticationToken authToken = getToken(response, false);
      return authToken;
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  @Override
  public UnicommerceAuthenticationToken refreshAccessToken(String refreshToken) throws Exception {
    String url =
        "https://habbit.unicommerce.com/oauth/token?"
            + "grant_type=refresh_token&"
            + "client_id=my-trusted-client&"
            + "refresh_token="
            + refreshToken;
    LOGGER.info("url {} ", url);
    RestTemplate restTemplate = new RestTemplate();
    try {
      String response = restTemplate.getForObject(url, String.class);
      UnicommerceAuthenticationToken authToken = getToken(response, true);
      return authToken;
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  @Override
  public CustomerSaleOrder createUnicommerceSaleOrder(
      UnicommerceCreateSaleOrderRequest requestPayload, UnicommerceAuthenticationToken token)
      throws Exception {
    token = validate(token);
    LOGGER.info("token {} ", token.getAuthToken());
    UnicommerceCreateSaleOrderRequestBody req = new UnicommerceCreateSaleOrderRequestBody();
    req.setSaleOrder(requestPayload);
    String url = "https://habbit.unicommerce.com/services/rest/v1/oms/saleOrder/create";
    LOGGER.info("url {} ", url);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + token.getAuthToken());

    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<String> entity =
        new HttpEntity<>(new ObjectMapper().writeValueAsString(req), headers);
    try {
      String answer = restTemplate.postForObject(url, entity, String.class);
      CustomerSaleOrder customerSaleOrder = getCustomerSaleOrder(answer);
      return customerSaleOrder;
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  private UnicommerceAuthenticationToken validate(UnicommerceAuthenticationToken token) {
    if (Objects.isNull(token)) {
      try {
        token = createToken();
      } catch (Exception e) {
        throwServiceRuntImeException(
            e,
            UnicommerceErrorCodes.UNICOM_ACCESS_TOKEN.getErrorCode(),
            UnicommerceErrorCodes.UNICOM_ACCESS_TOKEN.getErrorMessage());
      }
    }
    //  boolean refreshTokenExpired = unicommerceUtil.isRefreshTokenExpired(token);
    boolean accessTokenExpired = unicommerceUtil.isAccessTokenExpired(token);
    // accessTokenExpired= true;

    //    if (refreshTokenExpired) {
    //      try {
    //        token = refreshAccessToken(token.getRefreshToken());
    //        ; // createToken();
    //      } catch (Exception e) {
    //        throwServiceRuntImeException(
    //            e,
    //            UnicommerceErrorCodes.UNICOM_ACCESS_TOKEN.getErrorCode(),
    //            UnicommerceErrorCodes.UNICOM_ACCESS_TOKEN.getErrorMessage());
    //      }
    //    } else
    if (accessTokenExpired) {
      try {
        token = refreshAccessToken(token.getRefreshToken());
      } catch (Exception e) {
        throwServiceRuntImeException(
            e,
            UnicommerceErrorCodes.UNICOM_REFRESH_TOKEN.getErrorCode(),
            UnicommerceErrorCodes.UNICOM_REFRESH_TOKEN.getErrorMessage());
      }
    }
    return token;
  }

  private CustomerSaleOrder getCustomerSaleOrder(String response) {
    CustomerSaleOrder customerSaleOrder = new CustomerSaleOrder();
    LOGGER.info("Create unicom sale order response {}", response);
    JsonObject jsonObject =
        new JsonParser()
            .parse(response)
            .getAsJsonObject()
            .get("saleOrderDetailDTO")
            .getAsJsonObject();
    if (!jsonObject.get("code").isJsonNull()) {
      customerSaleOrder.setCode(jsonObject.get("code").getAsString());
    }
    if (!jsonObject.get("displayOrderCode").isJsonNull()) {
      customerSaleOrder.setDisplayOrderCode(jsonObject.get("displayOrderCode").getAsString());
    }
    if (!jsonObject.get("channel").isJsonNull()) {
      customerSaleOrder.setChannel(jsonObject.get("channel").getAsString());
    }
    if (!jsonObject.get("status").isJsonNull()) {
      customerSaleOrder.setStatus(jsonObject.get("status").getAsString());
    }
    if (!jsonObject.get("notificationEmail").isJsonNull()) {
      customerSaleOrder.setCustomerEmailAddress(jsonObject.get("notificationEmail").getAsString());
    }
    if (!jsonObject.get("notificationMobile").isJsonNull()) {
      customerSaleOrder.setCustomerPhone(jsonObject.get("notificationMobile").getAsString());
    }
    if (!jsonObject.get("priority").isJsonNull()) {
      customerSaleOrder.setPriority(jsonObject.get("priority").getAsInt());
    }
    if (!jsonObject.get("currencyCode").isJsonNull()) {
      customerSaleOrder.setCustomerCode(jsonObject.get("currencyCode").getAsString());
    }
    // customerSaleOrder.setCustomerCode(jsonObject.get("customerCode").getAsString());

    return customerSaleOrder;
  }

  @Override
  public UnicommerceSearchSaleOrderResponse searchSaleOrder(
      UnicommerceSearchSaleOrderRequest requestPayload, UnicommerceAuthenticationToken token)
      throws Exception {
    String url = "https://habbit.unicommerce.com/services/rest/v1/oms/saleOrder/search";
    LOGGER.info("url {} ", url);
    token = validate(token);
    LOGGER.info("token {} ", token.getAuthToken());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + token.getAuthToken());

    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<String> entity =
        new HttpEntity<>(new ObjectMapper().writeValueAsString(requestPayload), headers);
    try {
      String answer = restTemplate.postForObject(url, entity, String.class);
      UnicommerceSearchSaleOrderResponse resObj = getSerchResponseObj(answer);
      return resObj;
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  private UnicommerceSearchSaleOrderResponse getSerchResponseObj(String response) {

    UnicommerceSearchSaleOrderResponse searchSaleOrderResponse =
        new UnicommerceSearchSaleOrderResponse();
    JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
    if (!jsonObject.get("totalRecords").isJsonNull()) {
      searchSaleOrderResponse.setTotalRecords(jsonObject.get("totalRecords").getAsInt());
    }
    JsonArray jsonElements = jsonObject.getAsJsonArray("elements");
    for (int i = 0; i < jsonElements.size(); i++) {
      JsonObject obj = jsonElements.get(i).getAsJsonObject();
      UnicommerceSearchSaleOrderElementResponse element =
          new UnicommerceSearchSaleOrderElementResponse();
      if (!obj.get("displayOrderCode").isJsonNull()) {
        element.setDisplayOrderCode(obj.get("displayOrderCode").getAsString());
      }
      if (!obj.get("channel").isJsonNull()) {
        element.setChannel(obj.get("channel").getAsString());
      }
      if (!obj.get("status").isJsonNull()) {
        element.setStatus(obj.get("status").getAsString());
      }
      if (!obj.get("code").isJsonNull()) {
        element.setCode(obj.get("code").getAsString());
      }
      if (!obj.get("displayOrderDateTime").isJsonNull()) {
        element.setCreated(obj.get("displayOrderDateTime").getAsLong());
      }
      if (!obj.get("notificationEmail").isJsonNull()) {
        element.setNotificationEmail(obj.get("notificationEmail").getAsString());
      }
      if (!obj.get("notificationMobile").isJsonNull()) {
        element.setNotificationMobile(obj.get("notificationMobile").getAsString());
      }
      if (!obj.get("created").isJsonNull()) {
        element.setCreated(obj.get("created").getAsLong());
      }
      if (!obj.get("updated").isJsonNull()) {
        element.setUpdated(obj.get("updated").getAsLong());
      }

      searchSaleOrderResponse.getElementResponseList().add(element);
    }

    return searchSaleOrderResponse;
  }

  @Override
  public void getSaleOrder(
      UnicommerceGetSaleOrderRequest requestPayload, UnicommerceAuthenticationToken token)
      throws Exception {
    String url = "https://habbit.unicommerce.com/services/rest/v1/oms/saleorder/get";
    token = validate(token);
    LOGGER.info("url {} ", url);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + token.getAuthToken());

    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<String> entity =
        new HttpEntity<>(new ObjectMapper().writeValueAsString(requestPayload), headers);
    try {
      String answer = restTemplate.postForObject(url, entity, String.class);
      LOGGER.info("Response get sale order {}", answer);
      updateCustomerSaleOrder(answer);
      return;
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  private void updateCustomerSaleOrder(String answer) {
    CustomerSaleOrder customerSaleOrder = new CustomerSaleOrder();
    List<SaleOrderShipperDetails> saleOrderShipperDetails;
    List<SaleOrderItemDetails> saleOrderItemDetails;

    SaleOrderMetaData saleOrderMetaData;
    List<SaleOrderShippingPackageMetaData> saleOrderShippingPackageMetaData;
    List<SaleOrderShippingPackageItemCodeMetaData> saleOrderShippingPackageItemCodeMetaData =
        new ArrayList<>();
    List<SaleOrderItemsMetaData> saleOrderItemsMetaData;

    saleOrderMetaData = populateSaleOrderMetaData(answer);
    // udpate customerSale Order from saleOrder meta data
    customerSaleOrder.setCode(saleOrderMetaData.getCode());

    saleOrderShippingPackageMetaData = populateSaleOrderShippingPackageMetaData(answer);
    //    saleOrderShippingPackageItemCodeMetaData =
    //        populateSaleOrderShippingPackageItemCodeMetaData(answer);
    saleOrderItemsMetaData = populateSaleOrderItemsMetaData(answer);

    saleOrderShipperDetails = populateSaleOrderShipperDetails(saleOrderShippingPackageMetaData);
    saleOrderItemDetails = populateSaleOrderItemDetails(saleOrderItemsMetaData);
    // set order item status at order level
    UnicommerceOrderItemStatus statusCode = saleOrderItemDetails.get(0).getStatusCode();
    customerSaleOrder.setStatus(statusCode.getValue());

    // customer sale order
    customerSaleOrder.setOrderItemDetails(saleOrderItemDetails);
    customerSaleOrder.setShipperList(saleOrderShipperDetails);

    // sale order meta data
    saleOrderMetaData.setOrderItemDetails(saleOrderItemsMetaData);
    saleOrderMetaData.setShipperList(saleOrderShippingPackageMetaData);

    customerSaleOrderFacade.updateSaleOrderAndMetaData(customerSaleOrder, saleOrderMetaData);
    // update customer order by trackingUrl and status
    customerOrderFacade.getTrackingUrl(customerSaleOrder.getCode());

    // send mail for shipped or delivered
    sendSmsAndMail(customerSaleOrder, statusCode);
  }

  private void sendSmsAndMail(
      CustomerSaleOrder customerSaleOrder, UnicommerceOrderItemStatus statusCode) {
    switch (statusCode) {
      case DISPATCHED:
        {
          customerOrderFacade.sendOrderShippedMail(customerSaleOrder);
          customerOrderFacade.sendOrderShippedSms(customerSaleOrder);
          break;
        }
      case DELIVERED:
        {
          customerOrderFacade.sendOrderDeliveredMail(customerSaleOrder);
          break;
        }
    }
  }

  private List<SaleOrderShipperDetails> populateSaleOrderShipperDetails(
      List<SaleOrderShippingPackageMetaData> saleOrderShippingPackageMetaData) {
    List<SaleOrderShipperDetails> saleOrderShipperDetailsList = new ArrayList<>();
    for (SaleOrderShippingPackageMetaData packageMetaData : saleOrderShippingPackageMetaData) {
      SaleOrderShipperDetails saleOrderShipperDetails = new SaleOrderShipperDetails();

      // saleOrderShipperDetails.setName(packageMetaData.get());
      saleOrderShipperDetails.setUnicommerceShippingStatus(
          UnicommerceShippingStatus.valueOf(packageMetaData.getStatus()));
      saleOrderShipperDetails.setTrackingNumber(packageMetaData.getTrackingNumber());
      saleOrderShipperDetails.setShippingProvider(packageMetaData.getShippingProvider());
      saleOrderShipperDetails.setTrackingStatus(packageMetaData.getTrackingStatus());
      saleOrderShipperDetails.setCourierStatus(packageMetaData.getCourierStatus());
      saleOrderShipperDetails.setCode(packageMetaData.getCode());
      saleOrderShipperDetailsList.add(saleOrderShipperDetails);
    }
    return saleOrderShipperDetailsList;
  }

  private List<SaleOrderItemDetails> populateSaleOrderItemDetails(
      List<SaleOrderItemsMetaData> saleOrderItemsMetaData) {

    List<SaleOrderItemDetails> saleOrderItemDetailsList = new ArrayList<>();
    for (SaleOrderItemsMetaData itemsMetaData : saleOrderItemsMetaData) {
      SaleOrderItemDetails saleOrderItemDetails = new SaleOrderItemDetails();
      saleOrderItemDetails.setStatusCode(
          UnicommerceOrderItemStatus.valueOf(itemsMetaData.getStatusCode()));
      saleOrderItemDetails.setCode(itemsMetaData.getCode());
      saleOrderItemDetails.setSellerSkuCode(itemsMetaData.getSellerSkuCode());
      // saleOrderItemDetails.setChannelSaleOrderItemCode();
      saleOrderItemDetails.setItemSku(itemsMetaData.getItemSku());
      saleOrderItemDetails.setProductName(itemsMetaData.getItemName()); // todo : check once
      saleOrderItemDetails.setDisplayPrice(new Long(itemsMetaData.getSellingPrice()));

      saleOrderItemDetailsList.add(saleOrderItemDetails);
    }
    return saleOrderItemDetailsList;
  }

  private SaleOrderMetaData populateSaleOrderMetaData(String response) {
    SaleOrderMetaData saleOrderMetaData = new SaleOrderMetaData();
    JsonObject jsonObject =
        new JsonParser().parse(response).getAsJsonObject().get("saleOrderDTO").getAsJsonObject();

    if (!jsonObject.get("code").isJsonNull()) {
      saleOrderMetaData.setCode(jsonObject.get("code").getAsString());
    }
    if (!jsonObject.get("status").isJsonNull()) {
      saleOrderMetaData.setStatus(jsonObject.get("status").getAsString());
    }
    if (!jsonObject.get("cancellable").isJsonNull()) {
      saleOrderMetaData.setCancellable(jsonObject.get("cancellable").getAsBoolean());
    }
    if (!jsonObject.get("reversePickable").isJsonNull()) {
      saleOrderMetaData.setReversePickable(jsonObject.get("reversePickable").getAsBoolean());
    }
    if (!jsonObject.get("packetConfigurable").isJsonNull()) {
      saleOrderMetaData.setPacketConfigurable(jsonObject.get("packetConfigurable").getAsBoolean());
    }
    if (!jsonObject.get("cFormProvided").isJsonNull()) {
      saleOrderMetaData.setcFormProvided(jsonObject.get("cFormProvided").getAsBoolean());
    }
    // saleOrderMetaData.setTotalDiscount(jsonObject.get("totalDiscount").getAsInt());
    //
    // saleOrderMetaData.setTotalShippingCharges(jsonObject.get("totalShippingCharges").getAsInt());
    //    saleOrderMetaData.setAdditionalInfo(jsonObject.get("additionalInfo").getAsString());

    return saleOrderMetaData;
  }

  private List<SaleOrderShippingPackageMetaData> populateSaleOrderShippingPackageMetaData(
      String response) {

    List<SaleOrderShippingPackageMetaData> saleOrderShippingPackageMetaDataList = new ArrayList<>();

    JsonObject jsonObject =
        new JsonParser().parse(response).getAsJsonObject().get("saleOrderDTO").getAsJsonObject();
    JsonArray jsonElements = jsonObject.getAsJsonArray("shippingPackages");
    for (int i = 0; i < jsonElements.size(); i++) {
      JsonObject obj = jsonElements.get(i).getAsJsonObject();
      SaleOrderShippingPackageMetaData element = new SaleOrderShippingPackageMetaData();
      if (!obj.get("code").isJsonNull()) {
        element.setCode(obj.get("code").getAsString());
      }
      if (!obj.get("saleOrderCode").isJsonNull()) {
        element.setSaleOrderCode(obj.get("saleOrderCode").getAsString());
      }
      if (!obj.get("channel").isJsonNull()) {
        element.setChannel(obj.get("channel").getAsString());
      }
      if (!obj.get("status").isJsonNull()) {
        element.setStatus(obj.get("status").getAsString());
      }
      if (!obj.get("shippingPackageType").isJsonNull()) {
        element.setShippingPackageType(obj.get("shippingPackageType").getAsString());
      }
      if (!obj.get("shippingProvider").isJsonNull()) {
        element.setShippingProvider(obj.get("shippingProvider").getAsString());
      }
      if (!obj.get("shippingMethod").isJsonNull()) {
        element.setShippingMethod(obj.get("shippingMethod").getAsString());
      }
      if (!obj.get("trackingNumber").isJsonNull()) {
        element.setTrackingNumber(obj.get("trackingNumber").getAsString());
      }
      if (!obj.get("trackingStatus").isJsonNull()) {
        element.setTrackingStatus(obj.get("trackingStatus").getAsString());
      }
      if (!obj.get("courierStatus").isJsonNull()) {
        element.setCourierStatus(obj.get("courierStatus").getAsString());
      }
      if (!obj.get("estimatedWeight").isJsonNull()) {
        element.setEstimatedWeight(obj.get("estimatedWeight").getAsInt());
      }
      if (!obj.get("actualWeight").isJsonNull()) {
        element.setActualWeight(obj.get("actualWeight").getAsInt());
      }
      if (!obj.get("customer").isJsonNull()) {
        element.setCustomer(obj.get("customer").getAsString());
      }
      if (!obj.get("created").isJsonNull()) {
        element.setCreated(obj.get("created").getAsLong());
      }
      if (!obj.get("updated").isJsonNull()) {
        element.setUpdated(obj.get("updated").getAsLong());
      }
      if (!obj.get("dispatched").isJsonNull()) {
        element.setDispatched(obj.get("dispatched").getAsLong());
      }
      if (!obj.get("delivered").isJsonNull()) {
        element.setDelivered(obj.get("delivered").getAsLong());
      }
      if (!obj.get("invoiceCode").isJsonNull()) {
        element.setInvoiceCode(obj.get("invoiceCode").getAsString());
      }
      if (!obj.get("invoiceDisplayCode").isJsonNull()) {
        element.setInvoiceDisplayCode(obj.get("invoiceDisplayCode").getAsString());
      }
      if (!obj.get("noOfItems").isJsonNull()) {
        element.setNoOfItems(obj.get("noOfItems").getAsInt());
      }
      if (!obj.get("city").isJsonNull()) {
        element.setCity(obj.get("city").getAsString());
      }
      if (!obj.get("collectableAmount").isJsonNull()) {
        element.setCollectableAmount(obj.get("collectableAmount").getAsInt());
      }
      if (!obj.get("collectedAmount").isJsonNull()) {
        element.setCollectedAmount(obj.get("collectedAmount").getAsInt());
      }
      if (!obj.get("paymentReconciled").isJsonNull()) {
        element.setPaymentReconciled(obj.get("paymentReconciled").getAsBoolean());
      }
      if (!obj.get("podCode").isJsonNull()) {
        element.setPodCode(obj.get("podCode").getAsString());
      }
      if (!obj.get("shippingManifestCode").isJsonNull()) {
        element.setShippingManifestCode(obj.get("shippingManifestCode").getAsString());
      }
      if (!obj.get("shippingLabelLink").isJsonNull()) {
        element.setShippingLabelLink(obj.get("shippingLabelLink").getAsString());
      }

      saleOrderShippingPackageMetaDataList.add(element);
    }

    return saleOrderShippingPackageMetaDataList;
  }

  private List<SaleOrderShippingPackageItemCodeMetaData>
      populateSaleOrderShippingPackageItemCodeMetaData(String response) {
    List<SaleOrderShippingPackageItemCodeMetaData> shippingPackageItemCodeMetaDataArrayList =
        new ArrayList<>();

    new JsonParser()
        .parse(response)
        .getAsJsonObject()
        .get("saleOrderDTO")
        .getAsJsonObject()
        .get("saleOrderItems")
        .getAsJsonArray();
    JsonArray jsonElements = new JsonObject().getAsJsonArray("itemCode");
    for (int i = 0; i < jsonElements.size(); i++) {
      JsonObject obj = jsonElements.get(i).getAsJsonObject();
      SaleOrderShippingPackageItemCodeMetaData element =
          new SaleOrderShippingPackageItemCodeMetaData();
      if (!obj.get("itemSku").isJsonNull()) {
        element.setItemSku(obj.get("itemSku").getAsString());
      }
      if (!obj.get("itemName").isJsonNull()) {
        element.setItemName(obj.get("itemName").getAsString());
      }
      if (!obj.get("itemTypeImageUrl").isJsonNull()) {
        element.setItemTypeImageUrl(obj.get("itemTypeImageUrl").getAsString());
      }
      if (!obj.get("itemTypePageUrl").isJsonNull()) {
        element.setItemTypePageUrl(obj.get("itemTypePageUrl").getAsString());
      }
      if (!obj.get("quantity").isJsonNull()) {
        element.setQuantity(obj.get("quantity").getAsInt());
      }

      shippingPackageItemCodeMetaDataArrayList.add(element);
    }

    return shippingPackageItemCodeMetaDataArrayList;
  }

  private List<SaleOrderItemsMetaData> populateSaleOrderItemsMetaData(String response) {

    List<SaleOrderItemsMetaData> saleOrderItemsMetaDataArrayList = new ArrayList<>();

    JsonArray jsonElements =
        new JsonParser()
            .parse(response)
            .getAsJsonObject()
            .get("saleOrderDTO")
            .getAsJsonObject()
            .get("saleOrderItems")
            .getAsJsonArray();
    for (int i = 0; i < jsonElements.size(); i++) {
      JsonObject obj = jsonElements.get(i).getAsJsonObject();
      SaleOrderItemsMetaData element = new SaleOrderItemsMetaData();
      //      element.setShippingPackageCode(obj.get("shippingPackageCode").getAsString());
      //      element.setShippingPackageStatus(obj.get("shippingPackageStatus").getAsString());
      if (!obj.get("facilityCode").isJsonNull()) {
        element.setFacilityCode(obj.get("facilityCode").getAsString());
      }
      if (!obj.get("facilityName").isJsonNull()) {
        element.setFacilityName(obj.get("facilityName").getAsString());
      }

      //      element.setAlternateFacilityCode(obj.get("alternateFacilityCode").getAsString());
      //      element.setReversePickupCode(obj.get("reversePickupCode").getAsString());
      if (!obj.get("shippingAddressId").isJsonNull()) {
        element.setShippingAddressId(obj.get("shippingAddressId").getAsString());
      }
      if (!obj.get("packetNumber").isJsonNull()) {
        element.setPacketNumber(obj.get("packetNumber").getAsInt());
      }
      //      element.setCombinationIdentifier(obj.get("combinationIdentifier").getAsString());
      //      element.setCombinationDescription(obj.get("combinationDescription").getAsString());
      if (!obj.get("type").isJsonNull()) {
        element.setType(obj.get("type").getAsString());
      }
      if (!obj.get("shippingMethodCode").isJsonNull()) {
        element.setShippingMethodCode(obj.get("shippingMethodCode").getAsString());
      }
      if (!obj.get("itemName").isJsonNull()) {
        element.setItemName(obj.get("itemName").getAsString());
      }
      if (!obj.get("itemSku").isJsonNull()) {
        element.setItemSku(obj.get("itemSku").getAsString());
      }
      if (!obj.get("sellerSkuCode").isJsonNull()) {
        element.setSellerSkuCode(obj.get("sellerSkuCode").getAsString());
      }
      if (!obj.get("channelProductId").isJsonNull()) {
        element.setChannelProductId(obj.get("channelProductId").getAsString());
      }
      // element.setImageUrl(obj.get("imageUrl").getAsString());
      if (!obj.get("statusCode").isJsonNull()) {
        element.setStatusCode(obj.get("statusCode").getAsString());
      }
      if (!obj.get("code").isJsonNull()) {
        element.setCode(obj.get("code").getAsString());
      }
      //      element.setShelfCode(obj.get("shelfCode").getAsString());
      //      element.setTotalPrice(obj.get("totalPrice").getAsInt());
      if (!obj.get("sellingPrice").isJsonNull()) {
        element.setSellingPrice(obj.get("sellingPrice").getAsInt());
      }
      if (!obj.get("shippingCharges").isJsonNull()) {
        element.setShippingCharges(obj.get("shippingCharges").getAsInt());
      }
      if (!obj.get("shippingMethodCharges").isJsonNull()) {
        element.setShippingMethodCharges(obj.get("shippingMethodCharges").getAsInt());
      }
      if (!obj.get("cashOnDeliveryCharges").isJsonNull()) {
        element.setCashOnDeliveryCharges(obj.get("cashOnDeliveryCharges").getAsInt());
      }
      if (!obj.get("prepaidAmount").isJsonNull()) {
        element.setPrepaidAmount(obj.get("prepaidAmount").getAsInt());
      }
      //      element.setVoucherCode(obj.get("voucherCode").getAsString());
      if (!obj.get("voucherValue").isJsonNull()) {
        element.setVoucherValue(obj.get("voucherValue").getAsInt());
      }
      if (!obj.get("storeCredit").isJsonNull()) {
        element.setStoreCredit(obj.get("storeCredit").getAsInt());
      }
      if (!obj.get("discount").isJsonNull()) {
        element.setDiscount(obj.get("discount").getAsInt());
      }
      //      element.setGiftWrap(obj.get("giftWrap").getAsBoolean());
      if (!obj.get("giftWrapCharges").isJsonNull()) {
        element.setGiftWrapCharges(obj.get("giftWrapCharges").getAsInt());
      }
      //      element.setTaxPercentage(obj.get("taxPercentage").getAsInt());
      if (!obj.get("giftMessage").isJsonNull()) {
        element.setGiftMessage(obj.get("giftMessage").getAsString());
      }
      if (!obj.get("cancellable").isJsonNull()) {
        element.setCancellable(obj.get("cancellable").getAsBoolean());
      }
      if (!obj.get("editAddress").isJsonNull()) {
        element.setEditAddress(obj.get("editAddress").getAsBoolean());
      }
      if (!obj.get("reversePickable").isJsonNull()) {
        element.setReversePickable(obj.get("reversePickable").getAsBoolean());
      }
      if (!obj.get("packetConfigurable").isJsonNull()) {
        element.setPacketConfigurable(obj.get("packetConfigurable").getAsBoolean());
      }
      if (!obj.get("created").isJsonNull()) {
        element.setCreated(obj.get("created").getAsLong());
      }
      if (!obj.get("updated").isJsonNull()) {
        element.setUpdated(obj.get("updated").getAsLong());
      }
      if (!obj.get("onHold").isJsonNull()) {
        element.setOnHold(obj.get("onHold").getAsBoolean());
      }
      //
      // element.setSaleOrderItemAlternateId(obj.get("saleOrderItemAlternateId").getAsString());
      //      element.setCancellationReason(obj.get("cancellationReason").getAsString());
      //      element.setColor(obj.get("color").getAsString());
      //      element.setBrand(obj.get("brand").getAsString());
      //      element.setSize(obj.get("size").getAsString());
      //
      // element.setReplacementSaleOrderCode(obj.get("replacementSaleOrderCode").getAsString());
      //      element.setBundleSkuCode(obj.get("bundleSkuCode").getAsString());
      if (!obj.get("hsnCode").isJsonNull()) {
        element.setHsnCode(obj.get("hsnCode").getAsString());
      }
      if (!obj.get("totalIntegratedGst").isJsonNull()) {
        element.setTotalIntegratedGst(obj.get("totalIntegratedGst").getAsInt());
      }
      if (!obj.get("integratedGstPercentage").isJsonNull()) {
        element.setIntegratedGstPercentage(obj.get("integratedGstPercentage").getAsInt());
      }
      if (!obj.get("totalUnionTerritoryGst").isJsonNull()) {
        element.setTotalUnionTerritoryGst(obj.get("totalUnionTerritoryGst").getAsInt());
      }
      if (!obj.get("unionTerritoryGstPercentage").isJsonNull()) {
        element.setUnionTerritoryGstPercentage(obj.get("unionTerritoryGstPercentage").getAsInt());
      }
      if (!obj.get("totalStateGst").isJsonNull()) {
        element.setTotalStateGst(obj.get("totalStateGst").getAsInt());
      }
      if (!obj.get("stateGstPercentage").isJsonNull()) {
        element.setStateGstPercentage(obj.get("stateGstPercentage").getAsInt());
      }
      if (!obj.get("totalCentralGst").isJsonNull()) {
        element.setTotalCentralGst(obj.get("totalCentralGst").getAsInt());
      }
      if (!obj.get("centralGstPercentage").isJsonNull()) {
        element.setCentralGstPercentage(obj.get("centralGstPercentage").getAsInt());
      }
      //      element.setMaxRetailPrice(obj.get("maxRetailPrice").getAsInt());
      //      element.setItemDetailFields(obj.get("itemDetailFields").getAsString());

      saleOrderItemsMetaDataArrayList.add(element);
    }

    return saleOrderItemsMetaDataArrayList;
  }

  @Override
  public void cancelSaleOrder(
      UnicommerceCancelSaleOrderRequest requestPayload, UnicommerceAuthenticationToken token)
      throws Exception {
    String url = "https://habbit.unicommerce.com/services/rest/v1/oms/saleOrder/cancel";
    LOGGER.info("url {} ", url);
    // token = validate(token);
    // LOGGER.info("token {} ", token.getAuthToken());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + token.getAuthToken());

    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<String> entity =
        new HttpEntity<>(new ObjectMapper().writeValueAsString(requestPayload), headers);
    try {
      String answer = restTemplate.postForObject(url, entity, String.class);
      // answer = "{\"successful\":true,\"message\":null,\"errors\":null,\"warnings\":null}";
      return;
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  @Override
  public UnicommerceAuthenticationToken getUnicommerceAccessToken() {
    UnicommerceAuthenticationToken token =
        cacheUtil.getObjectFromCache(
            UnicommerceConstant.UNICOMMERCE_ACCESS_TOKEN, UnicommerceAuthenticationToken.class);
    return token;
  }

  private UnicommerceAuthenticationToken getToken(String response, boolean isRefreshToken) {
    JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
    ;

    String authToken = jsonObject.get("access_token").getAsString();
    String tokenType = jsonObject.get("token_type").getAsString();
    String refreshToken = jsonObject.get("refresh_token").getAsString();
    int expiresIn =
        (int) (Calendar.getInstance().getTimeInMillis() / 1000)
            + jsonObject.get("expires_in").getAsInt();
    String scope = jsonObject.get("scope").getAsString();
    AuthenticationTokenStatus status = AuthenticationTokenStatus.ACTIVE;

    UnicommerceAuthenticationToken token =
        new UnicommerceAuthenticationToken(
            authToken, tokenType, refreshToken, expiresIn, scope, status);
    if (isRefreshToken) {
      token = unicommerceAuthenticationTokenFacade.update(token);
    } else {
      synchronized (lock) {
        token = unicommerceAuthenticationTokenFacade.create(token);
      }
    }
    cacheUtil.setObjectInCache(UnicommerceConstant.UNICOMMERCE_ACCESS_TOKEN, token);
    return token;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
