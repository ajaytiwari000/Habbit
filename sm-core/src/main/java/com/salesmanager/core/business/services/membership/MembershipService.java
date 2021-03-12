package com.salesmanager.core.business.services.membership;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.common.Membership;

public interface MembershipService extends SalesManagerEntityService<Long, Membership> {
  Membership getById(Long id);

  Membership getMembershipByPhone(String phone) throws ServiceException;
}
