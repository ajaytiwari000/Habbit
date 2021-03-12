package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCustomerOrderStatusHistoryList {
  private static final long serialVersionUID = 1L;

  private List<PersistableCustomerOrderStatusHistory> customerOrderStatusHistories =
      new ArrayList<PersistableCustomerOrderStatusHistory>();

  public List<PersistableCustomerOrderStatusHistory> getCustomerOrderStatusHistories() {
    return customerOrderStatusHistories;
  }

  public void setCustomerOrderStatusHistories(
      List<PersistableCustomerOrderStatusHistory> customerOrderStatusHistories) {
    this.customerOrderStatusHistories = customerOrderStatusHistories;
  }
}
