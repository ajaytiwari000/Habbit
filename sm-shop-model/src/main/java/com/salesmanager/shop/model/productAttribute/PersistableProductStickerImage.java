package com.salesmanager.shop.model.productAttribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableProductStickerImage extends Entity {
  private Long id;
  private String skuId;
  private String badgeIcon;
  private String badgeIconUrl;
  private String badgeText;
  private String badgeColorCode;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getSkuId() {
    return skuId;
  }

  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }

  public String getBadgeIcon() {
    return badgeIcon;
  }

  public void setBadgeIcon(String badgeIcon) {
    this.badgeIcon = badgeIcon;
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
}
