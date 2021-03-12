package com.salesmanager.shop.model.catalog.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReadableNutritionalInfo {

  private String about;
  List<ReadableNutrientsInfo> readableNutrientsInfoList;
  List<ReadableNutrientsFact> readableNutrientsFactsList;

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

  public List<ReadableNutrientsInfo> getReadableNutrientsInfoList() {
    return readableNutrientsInfoList;
  }

  public void setReadableNutrientsInfoList(List<ReadableNutrientsInfo> readableNutrientsInfoList) {
    this.readableNutrientsInfoList = readableNutrientsInfoList;
  }

  public List<ReadableNutrientsFact> getReadableNutrientsFactsList() {
    return readableNutrientsFactsList;
  }

  public void setReadableNutrientsFactsList(
      List<ReadableNutrientsFact> readableNutrientsFactsList) {
    this.readableNutrientsFactsList = readableNutrientsFactsList;
  }
}
