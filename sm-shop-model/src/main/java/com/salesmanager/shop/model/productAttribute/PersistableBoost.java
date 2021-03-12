package com.salesmanager.shop.model.productAttribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableBoost extends Entity {
  private String price;
  private String type;

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
