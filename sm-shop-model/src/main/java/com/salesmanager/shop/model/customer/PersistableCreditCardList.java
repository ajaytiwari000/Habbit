package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCreditCardList {
  private static final long serialVersionUID = 1L;

  private PersistableCustomer persistableCustomer;
  private List<PersistableCreditCard> persistableCreditCardList =
      new ArrayList<PersistableCreditCard>();

  public List<PersistableCreditCard> getPersistableCreditCardList() {
    return persistableCreditCardList;
  }

  public void setPersistableCreditCardList(List<PersistableCreditCard> persistableCreditCardList) {
    this.persistableCreditCardList = persistableCreditCardList;
  }

  public PersistableCustomer getPersistableCustomer() {
    return persistableCustomer;
  }

  public void setPersistableCustomer(PersistableCustomer persistableCustomer) {
    this.persistableCustomer = persistableCustomer;
  }
}
