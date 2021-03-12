package com.salesmanager.core.business.services.coupon;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.CouponCodeRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.common.Coupon;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("couponCodeService")
public class CouponCodeServiceImpl extends SalesManagerEntityServiceImpl<Long, Coupon>
    implements CouponCodeService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CouponCodeServiceImpl.class);
  private CouponCodeRepository couponCodeRepository;

  @Inject
  public CouponCodeServiceImpl(CouponCodeRepository couponCodeRepository) {
    super(couponCodeRepository);
    this.couponCodeRepository = couponCodeRepository;
  }

  @Override
  public Coupon getById(Long id) {
    return couponCodeRepository.getById(id).orElse(null);
  }

  @Override
  public Coupon getCouponCodeByCode(String CouponCode) throws ServiceException {
    try {
      return couponCodeRepository.getCouponCodeByCode(CouponCode).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public List<Coupon> getAllCouponCodeList() throws ServiceException {
    try {
      return couponCodeRepository.getAllCouponCode().orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
