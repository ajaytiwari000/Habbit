package com.salesmanager.shop.model.catalog.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReadableProduct extends Entity {

  /** */
  private static final long serialVersionUID = 1L;

  private String skuId;
  private String primaryImage;
  private String name;
  private String description;
  private Long displayPrice;
  private Long actualPrice;
  private boolean premimumCard;
  private boolean onSale;
  private String badgeIconUrl;
  private String badgeText;
  private String badgeColorCode;
  private boolean boostAvailable;
  private String flavourName;
  private String packName;

  public ReadableProduct() {}

  public String getPrimaryImage() {
    return primaryImage;
  }

  public void setPrimaryImage(String primaryImage) {
    this.primaryImage = primaryImage;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getDisplayPrice() {
    return displayPrice;
  }

  public void setDisplayPrice(Long displayPrice) {
    this.displayPrice = displayPrice;
  }

  public Long getActualPrice() {
    return actualPrice;
  }

  public void setActualPrice(Long actualPrice) {
    this.actualPrice = actualPrice;
  }

  public boolean isPremimumCard() {
    return premimumCard;
  }

  public void setPremimumCard(boolean premimumCard) {
    this.premimumCard = premimumCard;
  }

  public boolean isOnSale() {
    return onSale;
  }

  public void setOnSale(boolean onSale) {
    this.onSale = onSale;
  }

  public String getSkuId() {
    return skuId;
  }

  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }

  public String getBadgeIconUrl() {
    return badgeIconUrl;
  }

  public void setBadgeIconUrl(String badgeIconUrl) {
    this.badgeIconUrl = badgeIconUrl;
  }

  public String getBadgeText() {
    return badgeText;
  }

  public void setBadgeText(String badgeText) {
    this.badgeText = badgeText;
  }

  public String getBadgeColorCode() {
    return badgeColorCode;
  }

  public void setBadgeColorCode(String badgeColorCode) {
    this.badgeColorCode = badgeColorCode;
  }

  public boolean isBoostAvailable() {
    return boostAvailable;
  }

  public void setBoostAvailable(boolean boostAvailable) {
    this.boostAvailable = boostAvailable;
  }

  public String getFlavourName() {
    return flavourName;
  }

  public void setFlavourName(String flavourName) {
    this.flavourName = flavourName;
  }

  public String getPackName() {
    return packName;
  }

  public void setPackName(String packName) {
    this.packName = packName;
  }
}
