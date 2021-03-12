package com.salesmanager.shop.model.order;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnicommerceCancelSaleOrderRequest {
  private String saleOrderCode;

  public String getSaleOrderCode() {
    return saleOrderCode;
  }

  public void setSaleOrderCode(String saleOrderCode) {
    this.saleOrderCode = saleOrderCode;
  }
}
