package com.salesmanager.shop.mapper.product;

import com.salesmanager.core.model.catalog.product.MembershipColor;
import com.salesmanager.shop.model.productAttribute.PersistableMembershipColor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MembershipColorMapper {
  MembershipColor toMembershipColor(PersistableMembershipColor persistableMembershipColor);

  PersistableMembershipColor toPersistableMembershipColor(MembershipColor membershipColor);
}
