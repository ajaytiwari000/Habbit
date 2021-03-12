package com.salesmanager.shop.model.catalog.product.attribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReadablePack extends Entity implements Comparable<ReadablePack> {

  private String packSizeValue;

  private String iconUrl;

  public ReadablePack() {}

  public String getPackSizeValue() {
    return packSizeValue;
  }

  public void setPackSizeValue(String packSizeValue) {
    this.packSizeValue = packSizeValue;
  }

  public String getIconUrl() {
    return iconUrl;
  }

  public void setIconUrl(String iconUrl) {
    this.iconUrl = iconUrl;
  }

  @Override
  public int compareTo(ReadablePack o) {
    return (int) (this.getId() - o.getId());
  }
}
