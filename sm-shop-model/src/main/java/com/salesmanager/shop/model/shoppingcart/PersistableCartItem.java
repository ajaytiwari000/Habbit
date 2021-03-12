package com.salesmanager.shop.model.shoppingcart;

import com.salesmanager.shop.model.catalog.product.attribute.PersistablePack;
import com.salesmanager.shop.model.productAttribute.PersistableBoost;
import com.salesmanager.shop.model.productAttribute.PersistableFlavour;
import java.util.HashSet;
import java.util.Set;

/**
 * compatible with v1 version
 *
 * @author c.samson
 */
public class PersistableCartItem {
  private Long cartItemId;
  private String productName;
  private String productSku;
  private String productImage;
  private Long displayPrice;
  private Long itemPrice;
  private Long addOnPrice;
  private int quantity;
  private String cartItemCode;
  private PersistableFlavour flavour;
  private PersistablePack packSize;
  private Set<PersistableBoost> boosts = new HashSet<PersistableBoost>();

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProductSku() {
    return productSku;
  }

  public void setProductSku(String productSku) {
    this.productSku = productSku;
  }

  public Long getDisplayPrice() {
    return displayPrice;
  }

  public void setDisplayPrice(Long displayPrice) {
    this.displayPrice = displayPrice;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
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

  public String getProductImage() {
    return productImage;
  }

  public void setProductImage(String productImage) {
    this.productImage = productImage;
  }

  public String getCartItemCode() {
    return cartItemCode;
  }

  public void setCartItemCode(String cartItemCode) {
    this.cartItemCode = cartItemCode;
  }

  public Long getCartItemId() {
    return cartItemId;
  }

  public void setCartItemId(Long cartItemId) {
    this.cartItemId = cartItemId;
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
