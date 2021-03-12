/** */
package com.salesmanager.shop.admin.controller.customer.membership;

import com.salesmanager.core.model.common.Membership;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.LoyaltyPageDetail;
import com.salesmanager.shop.model.customer.PersistableMembership;

public interface MembershipFacade {

  PersistableMembership create(
      PersistableMembership persistableMembership, MerchantStore merchantStore);

  PersistableMembership update(
      PersistableMembership persistableMembership, MerchantStore merchantStore);

  void deleteById(Long id);

  PersistableMembership getMembershipById(Long id, MerchantStore merchantStore, Language language);

  PersistableMembership getMembershipByPhone(String phone);

  void addRewardPoints(int rewardPoint, String phone, boolean friend);

  void consumeRewardPoints(int rewardPoint, String phone, boolean friend);

  void updateTier(Membership membership);

  int calculateRewardPoint(TierType tierType, long cashAmount);

  LoyaltyPageDetail loyaltyDetail(String phone);

  PersistableMembership getInviteDetail(String phone);

  String editRewardPoints(int rewardPoints, String phone);
}
