/** */
package com.salesmanager.shop.admin.controller.customer.membership;

import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.PersistableRewardConsumptionCriteria;
import com.salesmanager.shop.model.customer.PersistableRewardConsumptionCriteriaList;
import com.salesmanager.shop.model.customer.PersistableTierNameTypeList;

public interface RewardConsumptionCriteriaFacade {

  PersistableRewardConsumptionCriteria create(
      PersistableRewardConsumptionCriteria persistableRewardConsumptionCriteria,
      MerchantStore merchantStore);

  PersistableRewardConsumptionCriteria update(
      PersistableRewardConsumptionCriteria persistableRewardConsumptionCriteria,
      MerchantStore merchantStore);

  void deleteById(Long id);

  PersistableRewardConsumptionCriteria getRewardConsumptionCriteriaById(
      Long id, MerchantStore merchantStore, Language language);

  PersistableRewardConsumptionCriteria getRewardConsumptionCriteriaByType(
      TierType tierType, MerchantStore merchantStore, Language language);

  PersistableRewardConsumptionCriteriaList getAllRewardConsumptionCriteriaFacade(
      MerchantStore merchantStore, Language language);

  PersistableTierNameTypeList getAllTierNameType(MerchantStore merchantStore, Language language);
}
