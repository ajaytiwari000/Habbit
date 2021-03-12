package com.salesmanager.core.model.catalog.category;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.catalog.product.NutritionalInfo;
import com.salesmanager.core.model.catalog.product.Pack;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(
    name = "CATEGORY_DETAILS",
    schema = SchemaConstant.SALESMANAGER_SCHEMA,
    uniqueConstraints = @UniqueConstraint(columnNames = {"CATEGORY_DETAILS_ID"}))
public class CategoryDetails extends SalesManagerEntity<Long, CategoryDetails>
    implements Auditable {

  private static final long serialVersionUID = 1234560987L;

  @Id
  @Column(name = "CATEGORY_DETAILS_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CATEGORY_DETAILS_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Column(name = "NAME", unique = true, nullable = false, length = 120)
  private String name;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "META_DESCRIPTION")
  @Type(type = "org.hibernate.type.TextType")
  private String detailDescription;

  @Column(name = "BANNER_LINKED_SKUID")
  private String bannerLinkedSkuId;

  @Column(name = "CATEGORY_IMAGE", length = 100)
  private String categoryBannerImage;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "categoryDetails")
  private List<CategoryBanner> categoryBanners = new ArrayList<>();

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "PACK_ID", nullable = true, updatable = true)
  private Pack defaultPackSize;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "NUTRITIONAL_INFO_ID", nullable = true, updatable = true)
  private NutritionalInfo nutritionalInfo;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
  @JoinTable(
      name = "CATEGORYDETAILS_REVIEWS",
      joinColumns = {@JoinColumn(name = "CATEGORY_DETAILS_ID")},
      inverseJoinColumns = {@JoinColumn(name = "CATEGORY_REVIEW_ID")})
  @Cascade({
    org.hibernate.annotations.CascadeType.DETACH,
    org.hibernate.annotations.CascadeType.LOCK,
    org.hibernate.annotations.CascadeType.REFRESH,
    org.hibernate.annotations.CascadeType.REPLICATE
  })
  private List<CategoryReview> categoryReviews = new ArrayList<>();

  public CategoryDetails() {}

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

  public String getDetailDescription() {
    return detailDescription;
  }

  public void setDetailDescription(String detailDescription) {
    this.detailDescription = detailDescription;
  }

  public String getBannerLinkedSkuId() {
    return bannerLinkedSkuId;
  }

  public void setBannerLinkedSkuId(String bannerLinkedSkuId) {
    this.bannerLinkedSkuId = bannerLinkedSkuId;
  }

  public String getCategoryBannerImage() {
    return categoryBannerImage;
  }

  public void setCategoryBannerImage(String categoryBannerImage) {
    this.categoryBannerImage = categoryBannerImage;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public Pack getDefaultPackSize() {
    return defaultPackSize;
  }

  public void setDefaultPackSize(Pack defaultPackSize) {
    this.defaultPackSize = defaultPackSize;
  }

  public NutritionalInfo getNutritionalInfo() {
    return nutritionalInfo;
  }

  public void setNutritionalInfo(NutritionalInfo nutritionalInfo) {
    this.nutritionalInfo = nutritionalInfo;
  }

  public List<CategoryReview> getCategoryReviews() {
    return categoryReviews;
  }

  public void setCategoryReviews(List<CategoryReview> categoryReviews) {
    this.categoryReviews = categoryReviews;
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

  public List<CategoryBanner> getCategoryBanners() {
    return categoryBanners;
  }

  public void setCategoryBanners(List<CategoryBanner> categoryBanners) {
    this.categoryBanners = categoryBanners;
  }
}
