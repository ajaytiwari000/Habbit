package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.common.Coupon;
import com.salesmanager.shop.model.customer.PersistableCouponCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CouponCodeMapper {
  Coupon toCoupon(PersistableCouponCode persistableCoupon);

  PersistableCouponCode toPersistableCoupon(Coupon Coupon);
}
