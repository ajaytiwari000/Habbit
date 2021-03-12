package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.catalog.product.attribute.PersistablePack;
import com.salesmanager.shop.model.entity.Entity;
import com.salesmanager.shop.model.productAttribute.PersistableBoost;
import com.salesmanager.shop.model.productAttribute.PersistableFlavour;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCustomerOrderProduct extends Entity {
  private String orderProductCode;
  private String sku;
  private String productName;
  private String productImage;
  private String cartItemCode;
  private int productQuantity;
  private Long itemPrice;
  private Long addOnPrice;
  private Long displayPrice;
  private PersistableFlavour flavour;
  private PersistablePack packSize;
  private Set<PersistableBoost> boosts = new HashSet<PersistableBoost>();

  public String getOrderProductCode() {
    return orderProductCode;
  }

  public void setOrderProductCode(String orderProductCode) {
    this.orderProductCode = orderProductCode;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getProductQuantity() {
    return productQuantity;
  }

  public void setProductQuantity(int productQuantity) {
    this.productQuantity = productQuantity;
  }

  public Long getDisplayPrice() {
    return displayPrice;
  }

  public void setDisplayPrice(Long displayPrice) {
    this.displayPrice = displayPrice;
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

  public Set<PersistableBoost> getBoosts() {
    return boosts;
  }

  public void setBoosts(Set<PersistableBoost> boosts) {
    this.boosts = boosts;
  }

  public String getCartItemCode() {
    return cartItemCode;
  }

  public void setCartItemCode(String cartItemCode) {
    this.cartItemCode = cartItemCode;
  }

  public String getProductImage() {
    return productImage;
  }

  public void setProductImage(String productImage) {
    this.productImage = productImage;
  }

  public Long getItemPrice() {
    return itemPrice;
  }

  public void setItemPrice(Long itemPrice) {
    this.itemPrice = itemPrice;
  }

  public Long getAddOnPrice() {
    return addOnPrice;
  }

  public void setAddOnPrice(Long addOnPrice) {
    this.addOnPrice = addOnPrice;
  }
}
