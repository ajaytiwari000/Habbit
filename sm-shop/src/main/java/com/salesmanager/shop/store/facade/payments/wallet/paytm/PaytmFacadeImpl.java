package com.salesmanager.shop.store.facade.payments.wallet.paytm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.payment.wallet.PaytmServiceProvider;
import com.salesmanager.core.business.modules.payment.wallet.constants.PaytmConstants;
import com.salesmanager.core.business.services.payments.gateway.paytm.PaytmOrderService;
import com.salesmanager.core.model.order.CustomerOrder;
import com.salesmanager.core.model.order.OrderType;
import com.salesmanager.core.model.order.PaytmOrder;
import com.salesmanager.core.model.payments.PaymentStatus;
import com.salesmanager.shop.admin.controller.customer.membership.CustomerOrderFacade;
import com.salesmanager.shop.error.codes.PaymentErrorCodes;
import com.salesmanager.shop.model.customer.PersistablePaytmDetail;
import com.salesmanager.shop.model.customer.PersistablePaytmOrder;
import com.salesmanager.shop.model.customer.PersistablePaytmTransaction;
import com.salesmanager.shop.model.customer.mapper.PaytmOrderMapper;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.utils.SessionUtil;
import java.util.Calendar;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("paytmFacade")
public class PaytmFacadeImpl implements PaytmFacade {
  private static final Logger LOGGER = LoggerFactory.getLogger(PaytmFacadeImpl.class);

  @Inject private SessionUtil sessionUtil;
  @Inject private CustomerOrderFacade customerOrderFacade;
  @Inject private PaytmOrderMapper paytmOrderMapper;
  @Inject private PaytmOrderService paytmOrderService;

  //  public String getTrxToken(String orderId, String amountValue, String customerId)
  //      throws Exception {
  //
  //    String txnToken = null;
  //    JSONObject paytmParams = new JSONObject();
  //
  //    JSONObject body = new JSONObject();
  //    body.put("requestType", "Payment");
  //    body.put("mid", PaytmConstants.merchantId);
  //    body.put("websiteName", PaytmConstants.website);
  //    body.put("orderId", orderId);
  //    body.put("callbackUrl", PaytmConstants.callbackUrl);
  //
  //    JSONObject txnAmount = new JSONObject();
  //    txnAmount.put("value", Long.parseLong(amountValue));
  //    txnAmount.put("currency", "INR");
  //
  //    JSONObject userInfo = new JSONObject();
  //    userInfo.put("custId", customerId);
  //    body.put("txnAmount", txnAmount);
  //    body.put("userInfo", userInfo);
  //
  //    String checksum = getCheckSum(body);
  //
  //    JSONObject head = new JSONObject();
  //    head.put("signature", checksum);
  //
  //    paytmParams.put("body", body);
  //    paytmParams.put("head", head);
  //
  //    String post_data = paytmParams.toString();
  //
  //    /* for Staging */
  //    URL url =
  //        new URL(
  //            PaytmConstants.paytmInitiateUrl
  //                + "?mid="
  //                + PaytmConstants.merchantId
  //                + "&orderId="
  //                + orderId);
  //
  //    /* for Production */
  //    // URL url = new
  //    //
  // URL("https://securegw.paytm.in/theia/api/v1/initiateTransaction?mid=YOUR_MID_HERE&orderId=ORDERID_98765");
  //
  //    try {
  //      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
  //      connection.setRequestMethod("POST");
  //      connection.setRequestProperty("Content-Type", "application/json");
  //      connection.setDoOutput(true);
  //
  //      DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
  //      requestWriter.writeBytes(post_data);
  //      requestWriter.close();
  //      String responseData = "";
  //      InputStream is = connection.getInputStream();
  //      BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
  //      if ((responseData = responseReader.readLine()) != null) {
  //        System.out.append("Response: " + responseData);
  //      }
  //      JSONObject json = new JSONObject(responseData);
  //      txnToken = new JSONObject(json.get("body").toString()).getString("txnToken");
  //      responseReader.close();
  //    } catch (Exception exception) {
  //      exception.printStackTrace();
  //    }
  //    URL showPageUrl =
  //        new URL(
  //            PaytmConstants.paytmShowPageUrl
  //                + "?mid="
  //                + PaytmConstants.merchantId
  //                + "&orderId="
  //                + orderId
  //                + "&txnToken="
  //                + txnToken);
  //    return txnToken;
  //  }
  //
  //  public String getResponseRedirect(HttpServletRequest request, Model model) {
  //    Map<String, String[]> mapData = request.getParameterMap();
  //    System.out.printf(mapData.toString());
  //    TreeMap<String, String> parameters = new TreeMap<String, String>();
  //    mapData.forEach((key, val) -> parameters.put(key, val[0]));
  //    String paytmChecksum = "";
  //    if (mapData.containsKey("CHECKSUMHASH")) {
  //      paytmChecksum = mapData.get("CHECKSUMHASH")[0];
  //    }
  //    String result;
  //
  //    boolean isValideChecksum = false;
  //    System.out.println("RESULT : " + parameters.toString());
  //    try {
  //      isValideChecksum = validateCheckSum(parameters, paytmChecksum);
  //      if (isValideChecksum && parameters.containsKey("RESPCODE")) {
  //        if (parameters.get("RESPCODE").equals("01")) {
  //          result = "Payment Successful";
  //        } else {
  //          result = "Payment Failed";
  //        }
  //      } else {
  //        result = "Checksum mismatched";
  //      }
  //    } catch (Exception e) {
  //      result = e.toString();
  //    }
  //    model.addAttribute("result", result);
  //    parameters.remove("CHECKSUMHASH");
  //    model.addAttribute("parameters", parameters);
  //    return "report";
  //  }

  @Override
  public PersistablePaytmDetail getPaytmInitTransactionRequestWithCheckSum(
      String orderCode,
      String totalOrderValue,
      HttpServletRequest request,
      String channelId,
      String website) {
    PersistablePaytmDetail persistablePaytmDetail = new PersistablePaytmDetail();
    PaytmServiceProvider paytmServiceProvider = new PaytmServiceProvider();
    JSONObject paytmInitTransactionRequestWithCheckSum = null;
    String phone = sessionUtil.getPhoneByAuthToken(request);
    try {
      paytmInitTransactionRequestWithCheckSum =
          paytmServiceProvider.getPaytmInitTransactionRequestWithCheckSum(
              orderCode, totalOrderValue, phone, channelId, website);
      populateToPaytmDetail(
          persistablePaytmDetail,
          paytmInitTransactionRequestWithCheckSum,
          orderCode,
          totalOrderValue,
          phone,
          channelId,
          website);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.PAYTM_CREATE_FAILURE.getErrorCode(),
          PaymentErrorCodes.PAYTM_CREATE_FAILURE.getErrorMessage());
    }
    return persistablePaytmDetail;
  }

  private void populateToPaytmDetail(
      PersistablePaytmDetail target,
      JSONObject source,
      String orderCode,
      String totalOrderValue,
      String phone,
      String channelId,
      String website) {
    target.setRequestType("Payment");
    target.setMid(PaytmConstants.getMerchantId());
    target.setWebsiteName(website);
    target.setOrderId(orderCode);
    target.setChannelId(channelId);
    target.setIndustryTypeId(PaytmConstants.getIndustryTypeId());
    target.setCallbackUrl(PaytmConstants.getCallbackUrl() + orderCode);
    target.setChecksum(source.get("CHECKSUMHASH").toString());

    target.getTxnAmount().setValue(totalOrderValue);
    target.getTxnAmount().setCurrency("INR");

    target.getUserInfo().setCustId(phone);
  }

  @Override
  public boolean validateCheckSumHash(
      String paytmCheckSum, JSONObject paytmInitTransactionRequest, HttpServletRequest request) {
    PaytmServiceProvider paytmServiceProvider = new PaytmServiceProvider();
    boolean valid = false;
    try {
      valid = paytmServiceProvider.validateCheckSumHash(paytmCheckSum, paytmInitTransactionRequest);
      // TODO : evaluate if we need retries as checksum is already validated,
      //  have to check if the same situation exists at any other place too in project where retry
      // may be necessary
      if (valid) {
        // Todo : remove cartcode to ordercode in redis
        CustomerOrder customerOrder =
            customerOrderFacade.getByOrderCode(
                paytmInitTransactionRequest.get("orderId").toString());
        updateCustomerOrderAfterPayment(customerOrder);
      }
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.PAYTM_VALIDATE_CHECKSUM_FAILURE.getErrorCode(),
          PaymentErrorCodes.PAYTM_VALIDATE_CHECKSUM_FAILURE.getErrorMessage());
    }
    return false;
  }

  @Override
  public PersistablePaytmOrder verifyCheckSumAndUpdateTxnStatus(
      PersistablePaytmTransaction persistablePaytmTransaction, HttpServletRequest request)
      throws JsonProcessingException {
    PaytmOrder paytmOrder = null;
    ObjectMapper mapper = new ObjectMapper();
    JSONObject jsonObject =
        new JSONObject(
            mapper.writeValueAsString(
                persistablePaytmTransaction.getPaytmInitTransactionRequest()));
    if (validateCheckSumHash(persistablePaytmTransaction.getPaytmCheckSum(), jsonObject, request)) {
      paytmOrder = paytmOrderMapper.toPaytmOrder(persistablePaytmTransaction.getPaytmOrder());
    }
    try {
      paytmOrder = paytmOrderService.create(paytmOrder);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.PAYTM_ORDER_CREATE_FAILURE.getErrorCode(),
          PaymentErrorCodes.PAYTM_ORDER_CREATE_FAILURE.getErrorMessage());
    }

    return paytmOrderMapper.toPersistablePaytmOrder(paytmOrder);
  }

  private void updateCustomerOrderAfterPayment(CustomerOrder customerOrder) {
    customerOrder.setDatePurchased(Calendar.getInstance().getTimeInMillis());
    if (customerOrder.getPaymentStatus().name().equals(PaymentStatus.SUCCESSFUL.name())) {
      customerOrder.setOrderType(OrderType.ORDER);
    }
    // todo : need to set require attribute..
    customerOrderFacade.update(customerOrder);
  }

  //  public String getCheckSum(JSONObject parameters) throws Exception {
  //    return PaytmChecksum.generateSignature(parameters.toString(),
  // PaytmConstants.merchantSecretKey);
  //  }

  //  public boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum)
  //      throws Exception {
  //    return PaytmChecksum.verifySignature(
  //        parameters, PaytmConstants.merchantSecretKey, paytmChecksum);
  //  }
  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
