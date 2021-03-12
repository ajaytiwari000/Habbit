package com.salesmanager.shop.model.order;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnicommerceCreateSaleOrderRequestBody {
  private UnicommerceCreateSaleOrderRequest saleOrder;

  public UnicommerceCreateSaleOrderRequest getSaleOrder() {
    return saleOrder;
  }

  public void setSaleOrder(UnicommerceCreateSaleOrderRequest saleOrder) {
    this.saleOrder = saleOrder;
  }
}
