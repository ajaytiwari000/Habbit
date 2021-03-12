package com.salesmanager.core.model.customer;

import com.salesmanager.core.model.common.EntityList;
import java.util.List;

public class CustomerList extends EntityList {

  /** */
  private static final long serialVersionUID = -3108842276158069739L;

  private List<Customer> customers;

  public void setCustomers(List<Customer> customers) {
    this.customers = customers;
  }

  public List<Customer> getCustomers() {
    return customers;
  }
}
