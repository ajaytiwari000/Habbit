package com.salesmanager.shop.model.productAttribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.catalog.product.attribute.PersistablePack;
import com.salesmanager.shop.model.entity.Entity;
import java.math.BigDecimal;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableProduct extends Entity {
  private String name;
  private String sku;
  private String description;
  private String detailDescription;
  private Set<PersistableCategory> categories;
  private PersistableFlavour flavour;
  private PersistablePack packSize;
  private PersistableProductStickerImage productStickerImage;
  private boolean boostAvailable = false;
  private PersistableProductNutritionalInfo productNutritionalInfo;
  private BigDecimal actualPrice;
  private BigDecimal displayPrice;
  private boolean unlimited = false;
  private boolean productPremiumCard;
  private Set<PersistableProductAvailability> availabilities;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDetailDescription() {
    return detailDescription;
  }

  public void setDetailDescription(String detailDescription) {
    this.detailDescription = detailDescription;
  }

  public PersistableFlavour getFlavour() {
    return flavour;
  }

  public void setFlavour(PersistableFlavour flavour) {
    this.flavour = flavour;
  }

  public PersistablePack getPackSize() {
    return packSize;
  }

  public void setPackSize(PersistablePack packSize) {
    this.packSize = packSize;
  }

  public boolean isBoostAvailable() {
    return boostAvailable;
  }

  public void setBoostAvailable(boolean boostAvailable) {
    this.boostAvailable = boostAvailable;
  }

  public BigDecimal getActualPrice() {
    return actualPrice;
  }

  public void setActualPrice(BigDecimal actualPrice) {
    this.actualPrice = actualPrice;
  }

  public BigDecimal getDisplayPrice() {
    return displayPrice;
  }

  public void setDisplayPrice(BigDecimal displayPrice) {
    this.displayPrice = displayPrice;
  }

  public boolean isUnlimited() {
    return unlimited;
  }

  public void setUnlimited(boolean unlimited) {
    this.unlimited = unlimited;
  }

  public Set<PersistableProductAvailability> getAvailabilities() {
    return availabilities;
  }

  public void setAvailabilities(Set<PersistableProductAvailability> availabilities) {
    this.availabilities = availabilities;
  }

  public PersistableProductNutritionalInfo getProductNutritionalInfo() {
    return productNutritionalInfo;
  }

  public void setProductNutritionalInfo(PersistableProductNutritionalInfo productNutritionalInfo) {
    this.productNutritionalInfo = productNutritionalInfo;
  }

  public Set<PersistableCategory> getCategories() {
    return categories;
  }

  public void setCategories(Set<PersistableCategory> categories) {
    this.categories = categories;
  }

  public PersistableProductStickerImage getProductStickerImage() {
    return productStickerImage;
  }

  public void setProductStickerImage(PersistableProductStickerImage productStickerImage) {
    this.productStickerImage = productStickerImage;
  }

  public boolean isProductPremiumCard() {
    return productPremiumCard;
  }

  public void setProductPremiumCard(boolean productPremiumCard) {
    this.productPremiumCard = productPremiumCard;
  }
}
