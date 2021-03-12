package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableAddressList {
  private static final long serialVersionUID = 1L;

  private PersistableCustomer persistableCustomer;
  private List<PersistableAddress> persistableAddressList = new ArrayList<PersistableAddress>();

  public List<PersistableAddress> getPersistableAddressList() {
    return persistableAddressList;
  }

  public void setPersistableAddressList(List<PersistableAddress> persistableAddressList) {
    this.persistableAddressList = persistableAddressList;
  }

  public PersistableCustomer getPersistableCustomer() {
    return persistableCustomer;
  }

  public void setPersistableCustomer(PersistableCustomer persistableCustomer) {
    this.persistableCustomer = persistableCustomer;
  }
}
