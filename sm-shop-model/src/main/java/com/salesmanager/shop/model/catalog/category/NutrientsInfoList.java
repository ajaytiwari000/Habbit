package com.salesmanager.shop.model.catalog.category;

import com.salesmanager.shop.model.productAttribute.PersistableNutrientsInfo;
import java.util.ArrayList;
import java.util.List;

public class NutrientsInfoList {
  private static final long serialVersionUID = 1L;

  private List<PersistableNutrientsInfo> nutrientsInfos = new ArrayList<PersistableNutrientsInfo>();

  public List<PersistableNutrientsInfo> getNutrientsInfos() {
    return nutrientsInfos;
  }

  public void setNutrientsInfos(List<PersistableNutrientsInfo> nutrientsInfos) {
    this.nutrientsInfos = nutrientsInfos;
  }
}
