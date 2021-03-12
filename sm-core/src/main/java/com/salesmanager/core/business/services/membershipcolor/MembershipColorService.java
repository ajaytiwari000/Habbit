package com.salesmanager.core.business.services.membershipcolor;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.MembershipColor;
import com.salesmanager.core.model.common.enumerator.TierType;
import java.util.List;

public interface MembershipColorService extends SalesManagerEntityService<Long, MembershipColor> {

  List<MembershipColor> getAllMembershipColor() throws ServiceException;

  MembershipColor getByTierType(TierType name) throws ServiceException;
}
