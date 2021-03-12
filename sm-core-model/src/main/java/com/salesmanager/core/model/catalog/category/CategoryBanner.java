package com.salesmanager.core.model.catalog.category;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import java.io.InputStream;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(
    name = "CATEGORY_BANNER",
    schema = SchemaConstant.SALESMANAGER_SCHEMA,
    uniqueConstraints = @UniqueConstraint(columnNames = {"CATEGORY_BANNER_ID"}))
public class CategoryBanner extends SalesManagerEntity<Long, CategoryBanner> implements Auditable {

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "CATEGORY_BANNER_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CATEGORY_BANNER_SEQ_NEX_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @ManyToOne(targetEntity = CategoryDetails.class)
  @JoinColumn(name = "CATEGORY_DETAILS_ID", nullable = false)
  private CategoryDetails categoryDetails;

  @Column(name = "BANNER_LINKED_SKUID")
  private String bannerLinkedSkuId;

  @Column(name = "BANNER_IMAGE")
  private String bannerImage;

  @Column(name = "BANNER_IMAGE_URL")
  private String bannerImageUrl;

  @Transient private InputStream image = null;
  @Transient private String categoryName = null;

  @Override
  public AuditSection getAuditSection() {
    return auditSection;
  }

  @Override
  public void setAuditSection(AuditSection auditSection) {
    this.auditSection = auditSection;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBannerLinkedSkuId() {
    return bannerLinkedSkuId;
  }

  public void setBannerLinkedSkuId(String bannerLinkedSkuId) {
    this.bannerLinkedSkuId = bannerLinkedSkuId;
  }

  public String getBannerImage() {
    return bannerImage;
  }

  public void setBannerImage(String bannerImage) {
    this.bannerImage = bannerImage;
  }

  public String getBannerImageUrl() {
    return bannerImageUrl;
  }

  public void setBannerImageUrl(String bannerImageUrl) {
    this.bannerImageUrl = bannerImageUrl;
  }

  public InputStream getImage() {
    return image;
  }

  public void setImage(InputStream image) {
    this.image = image;
  }

  public CategoryDetails getCategoryDetails() {
    return categoryDetails;
  }

  public void setCategoryDetails(CategoryDetails categoryDetails) {
    this.categoryDetails = categoryDetails;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }
}
