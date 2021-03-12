package com.salesmanager.shop.model.productAttribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableNutrientsFact extends Entity
    implements Comparable<PersistableNutrientsFact> {
  private String contentValue;
  private String name;
  private int orderNumber;

  public String getContentValue() {
    return contentValue;
  }

  public void setContentValue(String contentValue) {
    this.contentValue = contentValue;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(int orderNumber) {
    this.orderNumber = orderNumber;
  }

  @Override
  public int compareTo(PersistableNutrientsFact o) {
    return this.getOrderNumber() - o.getOrderNumber();
  }
}
