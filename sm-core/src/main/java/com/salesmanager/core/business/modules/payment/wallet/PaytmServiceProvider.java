package com.salesmanager.core.business.modules.payment.wallet;

import com.paytm.pg.merchant.PaytmChecksum;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.payment.wallet.constants.PaytmConstants;
import java.util.Objects;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaytmServiceProvider implements WalletServiceProvider {
  private static final Logger LOGGER = LoggerFactory.getLogger(PaytmServiceProvider.class);

  public JSONObject getPaytmInitTransactionRequest(
      String orderCode, String totalOrderValue, String userName, String channelId, String website) {
    JSONObject paytmInitTransactionRequest = new JSONObject();
    paytmInitTransactionRequest.put("requestType", "Payment");
    paytmInitTransactionRequest.put("MID", PaytmConstants.getMerchantId());
    paytmInitTransactionRequest.put("WEBSITE", website);
    paytmInitTransactionRequest.put("ORDER_ID", orderCode);
    paytmInitTransactionRequest.put("CHANNEL_ID", channelId);
    paytmInitTransactionRequest.put("INDUSTRY_TYPE_ID", PaytmConstants.getIndustryTypeId());
    paytmInitTransactionRequest.put("CALLBACK_URL", PaytmConstants.getCallbackUrl() + orderCode);

    JSONObject txnAmount = new JSONObject();
    txnAmount.put("value", totalOrderValue);
    txnAmount.put("currency", "INR");

    JSONObject userInfo = new JSONObject();
    userInfo.put("CUST_ID", userName);
    paytmInitTransactionRequest.put("TXN_AMOUNT", txnAmount);
    paytmInitTransactionRequest.put("userInfo", userInfo);
    LOGGER.info("paytmInitTransactionRequest - {} ", paytmInitTransactionRequest);

    //    paytmInitTransactionRequest.put("MID", PaytmConstants.getMerchantId());
    //    paytmInitTransactionRequest.put("WEBSITE", website);
    //    paytmInitTransactionRequest.put("ORDER_ID", orderCode);
    //    paytmInitTransactionRequest.put("CHANNEL_ID", channelId);
    //    paytmInitTransactionRequest.put("INDUSTRY_TYPE_ID", PaytmConstants.getIndustryTypeId());
    //    paytmInitTransactionRequest.put("CALLBACK_URL", PaytmConstants.getCallbackUrl() +
    // orderCode);
    //    paytmInitTransactionRequest.put("CUST_ID", userName);
    //    paytmInitTransactionRequest.put("TXN_AMOUNT", totalOrderValue);
    //
    //    LOGGER.info("paytmInitTransactionRequest - {} ", paytmInitTransactionRequest);
    //

    return paytmInitTransactionRequest;
  }

  public JSONObject getPaytmInitTransactionRequestWithCheckSum(
      String orderCode, String totalOrderValue, String userName, String channelId, String website)
      throws Exception {
    JSONObject paytmInitTransactionRequestWithCheckSum =
        getPaytmInitTransactionRequest(orderCode, totalOrderValue, userName, channelId, website);
    String checkSum =
        PaytmChecksum.generateSignature(
            paytmInitTransactionRequestWithCheckSum.toString(),
            PaytmConstants.getMerchantSecretKey());
    LOGGER.info("Checksum - {} ", checkSum);
    paytmInitTransactionRequestWithCheckSum.put("CHECKSUMHASH", checkSum);
    return paytmInitTransactionRequestWithCheckSum;
  }

  public boolean validateCheckSumHash(String paytmCheckSum, JSONObject paytmInitTransactionRequest)
      throws Exception {
    if (Objects.isNull(paytmInitTransactionRequest) || paytmInitTransactionRequest.length() == 0) {
      throw new ServiceException(
          "paytmJsonObjectForCheckSumVerificationProcess can't be null/empty.");
    }
    boolean isVerifySignature =
        PaytmChecksum.verifySignature(
            paytmInitTransactionRequest.toString(),
            PaytmConstants.getMerchantSecretKey(),
            paytmCheckSum);
    if (isVerifySignature) {
      LOGGER.info("Checksum Matched");
      return true;
    } else {
      LOGGER.info("Checksum Mismatched");
      return false;
    }
  }
}
