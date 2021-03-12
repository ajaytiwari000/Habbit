package com.salesmanager.shop.model.catalog.product.attribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistablePack extends Entity {

  private String packSizeValue;

  public String getPackSizeValue() {
    return packSizeValue;
  }

  public void setPackSizeValue(String packSizeValue) {
    this.packSizeValue = packSizeValue;
  }

  public PersistablePack() {}
}
