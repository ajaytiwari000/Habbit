package com.salesmanager.shop.model.store;

import com.salesmanager.shop.model.references.PersistablesAddress;
import java.util.List;

public class PersistableMerchantStore extends MerchantStoreEntity {

  /** */
  private static final long serialVersionUID = 1L;

  private PersistablesAddress address;
  // code of parent store (can be null if retailer)
  private String retailerStore;
  private List<String> supportedLanguages;

  public List<String> getSupportedLanguages() {
    return supportedLanguages;
  }

  public void setSupportedLanguages(List<String> supportedLanguages) {
    this.supportedLanguages = supportedLanguages;
  }

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
