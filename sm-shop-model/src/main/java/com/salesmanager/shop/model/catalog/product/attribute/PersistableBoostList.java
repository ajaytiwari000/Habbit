package com.salesmanager.shop.model.catalog.product.attribute;

import com.salesmanager.shop.model.productAttribute.PersistableBoost;
import java.util.ArrayList;
import java.util.List;

public class PersistableBoostList {
  private static final long serialVersionUID = 1L;

  private List<PersistableBoost> boosts = new ArrayList<PersistableBoost>();

  public List<PersistableBoost> getBoosts() {
    return boosts;
  }

  public void setBoosts(List<PersistableBoost> boosts) {
    this.boosts = boosts;
  }
}
