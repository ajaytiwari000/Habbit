package com.salesmanager.shop.model.customer.mapper;

import com.salesmanager.core.model.common.Membership;
import com.salesmanager.shop.model.customer.PersistableMembership;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MembershipMapper {
  Membership toMembership(PersistableMembership persistableMembership);

  PersistableMembership toPersistableMembership(Membership Membership);
}
