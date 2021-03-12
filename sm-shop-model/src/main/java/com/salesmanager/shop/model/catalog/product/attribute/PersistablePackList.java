package com.salesmanager.shop.model.catalog.product.attribute;

import java.util.ArrayList;
import java.util.List;

public class PersistablePackList {

  private static final long serialVersionUID = 1L;

  private List<PersistablePack> packs = new ArrayList<PersistablePack>();

  public void setPacks(List<PersistablePack> packs) {
    this.packs = packs;
  }

  public List<PersistablePack> getPacks() {
    return packs;
  }
}
