package com.salesmanager.shop.model.catalog.product.attribute;

import com.salesmanager.shop.model.productAttribute.PersistableMembershipColor;
import java.util.ArrayList;
import java.util.List;

public class PersistableMembershipColorList {
  private static final long serialVersionUID = 1L;

  private List<PersistableMembershipColor> MembershipColors =
      new ArrayList<PersistableMembershipColor>();

  public List<PersistableMembershipColor> getMembershipColors() {
    return MembershipColors;
  }

  public void setMembershipColors(List<PersistableMembershipColor> MembershipColors) {
    this.MembershipColors = MembershipColors;
  }
}
