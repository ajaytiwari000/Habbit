package com.salesmanager.shop.model.productAttribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;
import java.util.LinkedHashSet;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableNutritionalInfo extends Entity {
  private String nutritionalDescription;
  private LinkedHashSet<PersistableNutrientsInfo> nutrientsInfo = new LinkedHashSet<>();
  private LinkedHashSet<PersistableNutrientsFact> nutrientsFacts = new LinkedHashSet<>();

  public String getNutritionalDescription() {
    return nutritionalDescription;
  }

  public void setNutritionalDescription(String nutritionalDescription) {
    this.nutritionalDescription = nutritionalDescription;
  }

  public LinkedHashSet<PersistableNutrientsInfo> getNutrientsInfo() {
    return nutrientsInfo;
  }

  public void setNutrientsInfo(LinkedHashSet<PersistableNutrientsInfo> nutrientsInfo) {
    this.nutrientsInfo = nutrientsInfo;
  }

  public LinkedHashSet<PersistableNutrientsFact> getNutrientsFacts() {
    return nutrientsFacts;
  }

  public void setNutrientsFacts(LinkedHashSet<PersistableNutrientsFact> nutrientsFacts) {
    this.nutrientsFacts = nutrientsFacts;
  }
}
