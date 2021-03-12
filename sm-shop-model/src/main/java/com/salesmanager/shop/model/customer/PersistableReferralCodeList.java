package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableReferralCodeList {
  private static final long serialVersionUID = 1L;

  private List<PersistableReferralCode> persistableReferralCodes =
      new ArrayList<PersistableReferralCode>();

  public List<PersistableReferralCode> getPersistableReferralCodes() {
    return persistableReferralCodes;
  }

  public void setPersistableReferralCodes(List<PersistableReferralCode> persistableReferralCodes) {
    this.persistableReferralCodes = persistableReferralCodes;
  }
}
