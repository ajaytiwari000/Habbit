package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCouponCodeList {
  private static final long serialVersionUID = 1L;

  private List<PersistableCouponCode> persistableCouponCodes =
      new ArrayList<PersistableCouponCode>();

  public List<PersistableCouponCode> getPersistableCouponCodes() {
    return persistableCouponCodes;
  }

  public void setPersistableCouponCodes(List<PersistableCouponCode> persistableCouponCodes) {
    this.persistableCouponCodes = persistableCouponCodes;
  }
}
