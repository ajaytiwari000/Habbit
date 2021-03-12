package com.salesmanager.core.business.services.coupon;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.common.Coupon;
import java.util.List;

public interface CouponCodeService extends SalesManagerEntityService<Long, Coupon> {
  Coupon getById(Long id);

  Coupon getCouponCodeByCode(String CouponCode) throws ServiceException;

  List<Coupon> getAllCouponCodeList() throws ServiceException;
}
