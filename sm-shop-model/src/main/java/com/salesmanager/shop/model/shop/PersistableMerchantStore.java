package com.salesmanager.shop.model.shop;

import com.salesmanager.shop.model.references.PersistablesAddress;
import com.salesmanager.shop.model.store.MerchantStoreEntity;

public class PersistableMerchantStore extends MerchantStoreEntity {

  /** */
  private static final long serialVersionUID = 1L;

  private PersistablesAddress address;
  // code of parent store (can be null if retailer)
  private String retailerStore;

  public PersistablesAddress getAddress() {
    return address;
  }

  public void setAddress(PersistablesAddress address) {
    this.address = address;
  }

  public String getRetailerStore() {
    return retailerStore;
  }

  public void setRetailerStore(String retailerStore) {
    this.retailerStore = retailerStore;
  }
}
