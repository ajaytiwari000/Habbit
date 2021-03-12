package com.salesmanager.shop.model.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnicommerceSearchSaleOrderResponse extends Entity {
  private int totalRecords;
  private List<UnicommerceSearchSaleOrderElementResponse> elementResponseList = new ArrayList<>();

  public int getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords(int totalRecords) {
    this.totalRecords = totalRecords;
  }

  public List<UnicommerceSearchSaleOrderElementResponse> getElementResponseList() {
    return elementResponseList;
  }

  public void setElementResponseList(
      List<UnicommerceSearchSaleOrderElementResponse> elementResponseList) {
    this.elementResponseList = elementResponseList;
  }
}
