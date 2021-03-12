package com.salesmanager.shop.model.catalog.product;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReadableNutrientsInfo implements Comparable<ReadableNutrientsInfo> {
  private String imgUrl;
  private String description;
  private int orderNumber;

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(int orderNumber) {
    this.orderNumber = orderNumber;
  }

  @Override
  public int compareTo(ReadableNutrientsInfo o) {
    return this.getOrderNumber() - o.getOrderNumber();
  }
}
