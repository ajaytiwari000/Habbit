package com.salesmanager.shop.model.productAttribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableProductAvailability extends Entity {
  private Integer productQuantity;
  private Integer productQuantityOrderMin;
  private Integer productQuantityOrderMax;

  public Integer getProductQuantity() {
    return productQuantity;
  }

  public void setProductQuantity(Integer productQuantity) {
    this.productQuantity = productQuantity;
  }

  public Integer getProductQuantityOrderMin() {
    return productQuantityOrderMin;
  }

  public void setProductQuantityOrderMin(Integer productQuantityOrderMin) {
    this.productQuantityOrderMin = productQuantityOrderMin;
  }

  public Integer getProductQuantityOrderMax() {
    return productQuantityOrderMax;
  }

  public void setProductQuantityOrderMax(Integer productQuantityOrderMax) {
    this.productQuantityOrderMax = productQuantityOrderMax;
  }
}
