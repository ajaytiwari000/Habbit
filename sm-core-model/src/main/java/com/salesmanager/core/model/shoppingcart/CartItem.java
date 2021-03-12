package com.salesmanager.core.model.shoppingcart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.catalog.product.Boost;
import com.salesmanager.core.model.catalog.product.Flavour;
import com.salesmanager.core.model.catalog.product.Pack;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "CART_ITEM", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class CartItem extends SalesManagerEntity<Long, CartItem>
    implements Auditable, Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "CART_ITEM_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CRT_ITM_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Embedded private AuditSection auditSection = new AuditSection();

  @JsonIgnore
  @ManyToOne(targetEntity = Cart.class)
  @JoinColumn(name = "CART_ID", nullable = false)
  private Cart cart;

  @Column(name = "PRODUCT_NAME")
  private String productName;

  @Column(name = "PRODUCT_SKU")
  private String productSku;

  @Column(name = "PRODUCT_IMAGE")
  private String productImage;

  @Column(name = "CART_ITEM_CODE")
  private String cartItemCode;

  @Column(name = "DISPLAY_PRICE")
  private Long displayPrice;

  @Column(name = "ITEM_PRICE")
  private Long itemPrice;

  @Column(name = "ADD_ON_PRICE")
  private Long addOnPrice;

  @Column(name = "QUANTITY")
  private int quantity;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "Flavour_ID", nullable = true, updatable = true)
  private Flavour flavour;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "PACK_ID", nullable = true, updatable = true)
  private Pack packSize;

  @ManyToMany(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.REFRESH})
  @JoinTable(
      name = "CART_ITEM_BOOST",
      schema = SchemaConstant.SALESMANAGER_SCHEMA,
      joinColumns = {@JoinColumn(name = "CART_ITEM_ID", nullable = false, updatable = false)},
      inverseJoinColumns = {@JoinColumn(name = "BOOST_ID", nullable = false, updatable = false)})
  @Cascade({
    org.hibernate.annotations.CascadeType.DETACH,
    org.hibernate.annotations.CascadeType.LOCK,
    org.hibernate.annotations.CascadeType.REFRESH,
    org.hibernate.annotations.CascadeType.REPLICATE
  })
  private Set<Boost> boosts = new HashSet<Boost>();

  @Override
  public AuditSection getAuditSection() {
    return auditSection;
  }

  @Override
  public void setAuditSection(AuditSection audit) {
    this.auditSection = audit;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public Cart getCart() {
    return cart;
  }

  public void setCart(Cart cart) {
    this.cart = cart;
  }

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

  public Flavour getFlavour() {
    return flavour;
  }

  public void setFlavour(Flavour flavour) {
    this.flavour = flavour;
  }

  public Pack getPackSize() {
    return packSize;
  }

  public void setPackSize(Pack packSize) {
    this.packSize = packSize;
  }

  public Set<Boost> getBoosts() {
    return boosts;
  }

  public void setBoosts(Set<Boost> boosts) {
    this.boosts = boosts;
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
