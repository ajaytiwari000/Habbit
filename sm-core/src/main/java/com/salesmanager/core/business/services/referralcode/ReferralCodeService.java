package com.salesmanager.core.business.services.referralcode;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.common.ReferralCode;
import java.util.List;

public interface ReferralCodeService extends SalesManagerEntityService<Long, ReferralCode> {
  ReferralCode getById(Long id);

  ReferralCode getReferralCodeByCode(String referralCode) throws ServiceException;

  ReferralCode getReferralCodeByPhone(String phoneNumber) throws ServiceException;

  List<ReferralCode> getAllReferralCodeList() throws ServiceException;
}
