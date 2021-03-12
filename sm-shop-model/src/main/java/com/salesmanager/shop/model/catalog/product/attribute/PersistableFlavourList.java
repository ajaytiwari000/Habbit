package com.salesmanager.shop.model.catalog.product.attribute;

import com.salesmanager.shop.model.productAttribute.PersistableFlavour;
import java.util.ArrayList;
import java.util.List;

public class PersistableFlavourList {
  private static final long serialVersionUID = 1L;

  private List<PersistableFlavour> flavours = new ArrayList<PersistableFlavour>();

  public List<PersistableFlavour> getFlavours() {
    return flavours;
  }

  public void setFlavours(List<PersistableFlavour> flavours) {
    this.flavours = flavours;
  }
}
