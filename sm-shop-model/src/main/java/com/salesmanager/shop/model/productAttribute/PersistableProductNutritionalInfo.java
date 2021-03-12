package com.salesmanager.shop.model.productAttribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;
import java.util.LinkedHashSet;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableProductNutritionalInfo extends Entity {
  private String nutritionalDescription;
  private LinkedHashSet<PersistableNutrientsInfo> productNutrientsInfo = new LinkedHashSet<>();
  private LinkedHashSet<PersistableProductNutrientsFact> productNutrientsFacts =
      new LinkedHashSet<>();

  public String getNutritionalDescription() {
    return nutritionalDescription;
  }

  public void setNutritionalDescription(String nutritionalDescription) {
    this.nutritionalDescription = nutritionalDescription;
  }

  public LinkedHashSet<PersistableNutrientsInfo> getProductNutrientsInfo() {
    return productNutrientsInfo;
  }

  public void setProductNutrientsInfo(
      LinkedHashSet<PersistableNutrientsInfo> productNutrientsInfo) {
    this.productNutrientsInfo = productNutrientsInfo;
  }

  public LinkedHashSet<PersistableProductNutrientsFact> getProductNutrientsFacts() {
    return productNutrientsFacts;
  }

  public void setProductNutrientsFacts(
      LinkedHashSet<PersistableProductNutrientsFact> productNutrientsFacts) {
    this.productNutrientsFacts = productNutrientsFacts;
  }
}
