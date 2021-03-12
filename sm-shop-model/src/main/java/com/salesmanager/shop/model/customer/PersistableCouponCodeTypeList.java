package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.core.model.common.enumerator.CouponCodeType;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCouponCodeTypeList {
  private static final long serialVersionUID = 1L;

  private List<CouponCodeType> couponCodeTypes = new ArrayList<CouponCodeType>();

  public List<CouponCodeType> getCouponCodeTypes() {
    return couponCodeTypes;
  }

  public void setCouponCodeTypes(List<CouponCodeType> couponCodeTypes) {
    this.couponCodeTypes = couponCodeTypes;
  }
}
