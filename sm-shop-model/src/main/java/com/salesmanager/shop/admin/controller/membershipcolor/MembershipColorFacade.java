package com.salesmanager.shop.admin.controller.membershipcolor;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableMembershipColorList;
import com.salesmanager.shop.model.productAttribute.PersistableMembershipColor;

public interface MembershipColorFacade {

  /**
   * Delete MembershipColor
   *
   * @param id
   * @param store
   * @throws Exception
   */
  void deleteMembershipColor(Long id, MerchantStore store);

  /**
   * Get MembershipColor
   *
   * @param id
   * @param store
   * @return
   */
  PersistableMembershipColor getMembershipColor(Long id, MerchantStore store);

  /**
   * @param MembershipColor
   * @param merchantStore
   */
  PersistableMembershipColor createMembershipColor(
      PersistableMembershipColor MembershipColor, MerchantStore merchantStore);

  /**
   * @param MembershipColor
   * @param merchantStore
   * @return
   * @throws Exception
   */
  PersistableMembershipColor updateMembershipColor(
      PersistableMembershipColor MembershipColor, MerchantStore merchantStore);

  /**
   * @param page
   * @param pageSize
   * @param merchantStore
   * @return
   * @throws Exception
   */
  PersistableMembershipColorList getAllUniqueMembershipColor(
      Integer page, Integer pageSize, MerchantStore merchantStore);
}
