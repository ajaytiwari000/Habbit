package com.salesmanager.core.model.catalog.category;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.catalog.product.Boost;
import com.salesmanager.core.model.catalog.product.Flavour;
import com.salesmanager.core.model.catalog.product.Pack;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.Cascade;

@Entity
@EntityListeners(value = com.salesmanager.core.model.common.audit.AuditListener.class)
@Table(
    name = "CATEGORY",
    schema = SchemaConstant.SALESMANAGER_SCHEMA,
    uniqueConstraints = @UniqueConstraint(columnNames = {"MERCHANT_ID", "CODE"}))
public class Category extends SalesManagerEntity<Long, Category> implements Auditable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "CATEGORY_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CATEGORY_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Valid
  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<CategoryDescription> descriptions = new HashSet<CategoryDescription>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MERCHANT_ID", nullable = false)
  private MerchantStore merchantStore;

  @ManyToOne
  @JoinColumn(name = "PARENT_ID")
  private Category parent;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
  private List<Category> categories = new ArrayList<Category>();

  @Column(name = "SORT_ORDER")
  private Integer sortOrder = 0;

  @Column(name = "CATEGORY_STATUS")
  private boolean categoryStatus;

  @Column(name = "VISIBLE")
  private boolean visible;

  @Column(name = "DEPTH")
  private Integer depth;

  @Column(name = "LINEAGE")
  private String lineage;

  @Column(name = "FEATURED")
  private boolean featured;

  @Column(name = "FLAVOUR_AVAILABLE")
  private boolean flavourAvailable;

  @NotEmpty
  @Column(name = "CODE", length = 100, nullable = false)
  private String code;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "CATEGORY_DETAILS_ID", nullable = true, updatable = true)
  private CategoryDetails categoryDetails;

  @ManyToMany(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.REFRESH})
  @JoinTable(
      name = "CATEGORY_FLAVOUR",
      schema = SchemaConstant.SALESMANAGER_SCHEMA,
      joinColumns = {@JoinColumn(name = "CATEGORY_ID", nullable = false, updatable = false)},
      inverseJoinColumns = {@JoinColumn(name = "FLAVOUR_ID", nullable = false, updatable = false)})
  @Cascade({
    org.hibernate.annotations.CascadeType.DETACH,
    org.hibernate.annotations.CascadeType.LOCK,
    org.hibernate.annotations.CascadeType.REFRESH,
    org.hibernate.annotations.CascadeType.REPLICATE
  })
  private Set<Flavour> flavours = new HashSet<Flavour>();

  @ManyToMany(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.REFRESH})
  @JoinTable(
      name = "CATEGORY_PACK",
      schema = SchemaConstant.SALESMANAGER_SCHEMA,
      joinColumns = {@JoinColumn(name = "CATEGORY_ID", nullable = false, updatable = false)},
      inverseJoinColumns = {@JoinColumn(name = "PACK_ID", nullable = false, updatable = false)})
  @Cascade({
    org.hibernate.annotations.CascadeType.DETACH,
    org.hibernate.annotations.CascadeType.LOCK,
    org.hibernate.annotations.CascadeType.REFRESH,
    org.hibernate.annotations.CascadeType.REPLICATE
  })
  private Set<Pack> packSize = new HashSet<Pack>();

  @ManyToMany(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.REFRESH})
  @JoinTable(
      name = "CATEGORY_BOOST",
      schema = SchemaConstant.SALESMANAGER_SCHEMA,
      joinColumns = {@JoinColumn(name = "CATEGORY_ID", nullable = false, updatable = false)},
      inverseJoinColumns = {@JoinColumn(name = "BOOST_ID", nullable = false, updatable = false)})
  @Cascade({
    org.hibernate.annotations.CascadeType.DETACH,
    org.hibernate.annotations.CascadeType.LOCK,
    org.hibernate.annotations.CascadeType.REFRESH,
    org.hibernate.annotations.CascadeType.REPLICATE
  })
  private Set<Boost> boosts = new HashSet<Boost>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "category")
  private List<CategoryFlavourCount> categoryFlavourCounts = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "category")
  private List<CategoryPackCount> categoryPackCounts = new ArrayList<>();

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Category() {}

  public Category(MerchantStore store) {
    this.merchantStore = store;
    this.id = 0L;
  }

  @Override
  public Long getId() {
    return this.id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public AuditSection getAuditSection() {
    return auditSection;
  }

  @Override
  public void setAuditSection(AuditSection auditSection) {
    this.auditSection = auditSection;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
  }

  public boolean isCategoryStatus() {
    return categoryStatus;
  }

  public void setCategoryStatus(boolean categoryStatus) {
    this.categoryStatus = categoryStatus;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public Integer getDepth() {
    return depth;
  }

  public void setDepth(Integer depth) {
    this.depth = depth;
  }

  public String getLineage() {
    return lineage;
  }

  public void setLineage(String lineage) {
    this.lineage = lineage;
  }

  public Category getParent() {
    return parent;
  }

  public void setParent(Category parent) {
    this.parent = parent;
  }

  public MerchantStore getMerchantStore() {
    return merchantStore;
  }

  public void setMerchantStore(MerchantStore merchantStore) {
    this.merchantStore = merchantStore;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public CategoryDescription getDescription() {
    if (descriptions != null && descriptions.size() > 0) {
      return descriptions.iterator().next();
    }

    return null;
  }

  public boolean isFeatured() {
    return featured;
  }

  public void setFeatured(boolean featured) {
    this.featured = featured;
  }

  public Set<CategoryDescription> getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(Set<CategoryDescription> descriptions) {
    this.descriptions = descriptions;
  }

  public Set<Boost> getBoosts() {
    return boosts;
  }

  public void setBoosts(Set<Boost> boosts) {
    this.boosts = boosts;
  }

  public List<CategoryFlavourCount> getCategoryFlavourCounts() {
    return categoryFlavourCounts;
  }

  public void setCategoryFlavourCounts(List<CategoryFlavourCount> categoryFlavourCounts) {
    this.categoryFlavourCounts = categoryFlavourCounts;
  }

  public List<CategoryPackCount> getCategoryPackCounts() {
    return categoryPackCounts;
  }

  public void setCategoryPackCounts(List<CategoryPackCount> categoryPackCounts) {
    this.categoryPackCounts = categoryPackCounts;
  }

  public CategoryDetails getCategoryDetails() {
    return categoryDetails;
  }

  public void setCategoryDetails(CategoryDetails categoryDetails) {
    this.categoryDetails = categoryDetails;
  }

  public Set<Flavour> getFlavours() {
    return flavours;
  }

  public void setFlavours(Set<Flavour> flavours) {
    this.flavours = flavours;
  }

  public Set<Pack> getPackSize() {
    return packSize;
  }

  public void setPackSize(Set<Pack> packSize) {
    this.packSize = packSize;
  }

  public boolean isFlavourAvailable() {
    return flavourAvailable;
  }

  public void setFlavourAvailable(boolean flavourAvailable) {
    this.flavourAvailable = flavourAvailable;
  }
}
