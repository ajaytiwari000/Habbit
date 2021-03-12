package com.salesmanager.core.business.repositories;

import com.salesmanager.core.model.common.Coupon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CouponCodeRepository extends JpaRepository<Coupon, Long> {
  @Query("select distinct cc from Coupon cc where cc.id= ?1")
  Optional<Coupon> getById(Long id);

  @Query("select distinct cc from Coupon cc where cc.codeName= ?1")
  Optional<Coupon> getCouponCodeByCode(String CouponCode);

  @Query("select distinct cc from Coupon cc")
  Optional<List<Coupon>> getAllCouponCode();
}
