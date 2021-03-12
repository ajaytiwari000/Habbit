package com.salesmanager.shop.model.catalog.category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** This file is not using in habbit project */
public class PersistablesCategory extends CategoryEntity implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  private List<CategoryDescription> descriptions; // always persist description
  private List<PersistablesCategory> children = new ArrayList<PersistablesCategory>();

  public List<CategoryDescription> getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(List<CategoryDescription> descriptions) {
    this.descriptions = descriptions;
  }

  public List<PersistablesCategory> getChildren() {
    return children;
  }

  public void setChildren(List<PersistablesCategory> children) {
    this.children = children;
  }
}
