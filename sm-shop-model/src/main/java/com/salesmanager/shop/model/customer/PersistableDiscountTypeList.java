package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.core.model.common.enumerator.CouponDiscountType;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableDiscountTypeList {
  private static final long serialVersionUID = 1L;

  private List<CouponDiscountType> couponDiscountTypes = new ArrayList<CouponDiscountType>();

  public List<CouponDiscountType> getCouponDiscountTypes() {
    return couponDiscountTypes;
  }

  public void setCouponDiscountTypes(List<CouponDiscountType> couponDiscountTypes) {
    this.couponDiscountTypes = couponDiscountTypes;
  }
}
