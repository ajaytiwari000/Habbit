package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistablePaytmTransaction extends Entity {
  private String paytmCheckSum;
  private PersistablePaytmOrder paytmOrder;
  private Map<String, Object> paytmInitTransactionRequest;

  public String getPaytmCheckSum() {
    return paytmCheckSum;
  }

  public void setPaytmCheckSum(String paytmCheckSum) {
    this.paytmCheckSum = paytmCheckSum;
  }

  public PersistablePaytmOrder getPaytmOrder() {
    return paytmOrder;
  }

  public void setPaytmOrder(PersistablePaytmOrder paytmOrder) {
    this.paytmOrder = paytmOrder;
  }

  public Map<String, Object> getPaytmInitTransactionRequest() {
    return paytmInitTransactionRequest;
  }

  public void setPaytmInitTransactionRequest(Map<String, Object> paytmInitTransactionRequest) {
    this.paytmInitTransactionRequest = paytmInitTransactionRequest;
  }
}
