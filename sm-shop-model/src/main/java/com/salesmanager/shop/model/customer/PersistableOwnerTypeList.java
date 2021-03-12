package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.core.model.common.enumerator.OwnerType;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableOwnerTypeList {
  private static final long serialVersionUID = 1L;

  private List<OwnerType> ownerTypes = new ArrayList<OwnerType>();

  public List<OwnerType> getOwnerTypes() {
    return ownerTypes;
  }

  public void setOwnerTypes(List<OwnerType> ownerTypes) {
    this.ownerTypes = ownerTypes;
  }
}
