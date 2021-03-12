package com.salesmanager.shop.model.productAttribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCategory extends Entity {
  private String code;
  private PersistableCategoryDetails categoryDetails;
  private List<PersistableBoost> boosts;
  private boolean flavourAvailable;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public PersistableCategoryDetails getCategoryDetails() {
    return categoryDetails;
  }

  public void setCategoryDetails(PersistableCategoryDetails categoryDetails) {
    this.categoryDetails = categoryDetails;
  }

  public List<PersistableBoost> getBoosts() {
    return boosts;
  }

  public void setBoosts(List<PersistableBoost> boosts) {
    this.boosts = boosts;
  }

  public boolean isFlavourAvailable() {
    return flavourAvailable;
  }

  public void setFlavourAvailable(boolean flavourAvailable) {
    this.flavourAvailable = flavourAvailable;
  }
}
