package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistablePinCodeList {
  private static final long serialVersionUID = 1L;

  private List<PersistablePinCode> persistablePinCodes = new ArrayList<PersistablePinCode>();

  public List<PersistablePinCode> getPersistablePinCodes() {
    return persistablePinCodes;
  }

  public void setPersistablePinCodes(List<PersistablePinCode> persistablePinCodes) {
    this.persistablePinCodes = persistablePinCodes;
  }
}
