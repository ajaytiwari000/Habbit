package com.salesmanager.core.model.order.orderproduct;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.catalog.product.Boost;
import com.salesmanager.core.model.catalog.product.Flavour;
import com.salesmanager.core.model.catalog.product.Pack;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.order.CustomerOrder;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "CUSTOMER_ORDER_PRODUCT", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class CustomerOrderProduct extends SalesManagerEntity<Long, CustomerOrderProduct> {
  private static final long serialVersionUID = 176131742783954627L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "CUS_ORDER_PRODUCT_ID")
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CUS_ORDER_PRODUCT_ID_NEXT_VALUE")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "ORDER_PRODUCT_CODE")
  private String orderProductCode;

  @Column(name = "PRODUCT_SKU") // yess !!! rename to code
  private String sku;

  @Column(name = "PRODUCT_NAME", length = 64, nullable = false)
  private String productName;

  @Column(name = "PRODUCT_IMAGE")
  private String productImage;

  @Column(name = "PRODUCT_QUANTITY")
  private int productQuantity;

  @Column(name = "ITEM_PRICE")
  private Long itemPrice;

  @Column(name = "ADD_ON_PRICE")
  private Long addOnPrice;

  @Column(name = "DISPLAY_PRICE")
  private Long displayPrice;

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
      name = "CUSTOMER_ORDER_PRODUCT_BOOST",
      schema = SchemaConstant.SALESMANAGER_SCHEMA,
      joinColumns = {
        @JoinColumn(name = "CUS_ORDER_PRODUCT_ID", nullable = false, updatable = false)
      },
      inverseJoinColumns = {@JoinColumn(name = "BOOST_ID", nullable = false, updatable = false)})
  @Cascade({
    org.hibernate.annotations.CascadeType.DETACH,
    org.hibernate.annotations.CascadeType.LOCK,
    org.hibernate.annotations.CascadeType.REFRESH,
    org.hibernate.annotations.CascadeType.REPLICATE
  })
  private Set<Boost> boosts = new HashSet<Boost>();

  @JsonIgnore
  @ManyToOne(targetEntity = CustomerOrder.class)
  @JoinColumn(name = "CUS_ORDER_ID", nullable = false)
  private CustomerOrder customerOrder;

  public AuditSection getAuditSection() {
    return auditSection;
  }

  public void setAuditSection(AuditSection auditSection) {
    this.auditSection = auditSection;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

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

  public CustomerOrder getCustomerOrder() {
    return customerOrder;
  }

  public void setCustomerOrder(CustomerOrder customerOrder) {
    this.customerOrder = customerOrder;
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
