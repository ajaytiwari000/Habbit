package com.salesmanager.shop.model.catalog.product.attribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReadableFlavour extends Entity implements Comparable<ReadableFlavour> {

  private String name;
  private String colorCode;
  private String colorCodeLight;
  private List<String> images;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getColorCode() {
    return colorCode;
  }

  public void setColorCode(String colorCode) {
    this.colorCode = colorCode;
  }

  public String getColorCodeLight() {
    return colorCodeLight;
  }

  public void setColorCodeLight(String colorCodeLight) {
    this.colorCodeLight = colorCodeLight;
  }

  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }

  @Override
  public int compareTo(ReadableFlavour o) {
    return this.getName().compareTo(o.getName());
  }
}
