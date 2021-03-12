package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.common.ReferralCode;
import com.salesmanager.shop.model.customer.PersistableReferralCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReferralCodeMapper {
  ReferralCode toReferralCode(PersistableReferralCode persistableReferralCode);

  PersistableReferralCode toPersistableReferralCode(ReferralCode referralCode);
}
