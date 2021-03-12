package com.salesmanager.shop.model.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnicommerceGetSaleOrderRequest {
  private String code;
  private List<String> facilityCodes;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List<String> getFacilityCodes() {
    return facilityCodes;
  }

  public void setFacilityCodes(List<String> facilityCodes) {
    this.facilityCodes = facilityCodes;
  }
}
