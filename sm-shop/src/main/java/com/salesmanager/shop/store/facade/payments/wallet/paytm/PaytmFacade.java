package com.salesmanager.shop.store.facade.payments.wallet.paytm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.salesmanager.shop.model.customer.PersistablePaytmDetail;
import com.salesmanager.shop.model.customer.PersistablePaytmOrder;
import com.salesmanager.shop.model.customer.PersistablePaytmTransaction;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;

public interface PaytmFacade {
  //
  //   String getTrxToken(String orderId, String amountValue, String customerId)
  //      throws Exception ;
  //
  //   String getResponseRedirect(HttpServletRequest request, Model model) ;

  PersistablePaytmDetail getPaytmInitTransactionRequestWithCheckSum(
      String orderCode,
      String totalOrderValue,
      HttpServletRequest request,
      String channelId,
      String website);

  boolean validateCheckSumHash(
      String paytmCheckSum, JSONObject paytmInitTransactionRequest, HttpServletRequest request);

  PersistablePaytmOrder verifyCheckSumAndUpdateTxnStatus(
      PersistablePaytmTransaction persistablePaytmTransaction, HttpServletRequest request)
      throws JsonProcessingException;
}
