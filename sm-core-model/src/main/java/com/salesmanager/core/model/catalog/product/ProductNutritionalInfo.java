package com.salesmanager.core.model.catalog.product;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(
    name = "PRODUCT_NUTRITIONAL_INFO",
    schema = SchemaConstant.SALESMANAGER_SCHEMA,
    uniqueConstraints = @UniqueConstraint(columnNames = {"PRODUCT_NUTRITIONAL_INFO_ID"}))
public class ProductNutritionalInfo extends SalesManagerEntity<Long, ProductNutritionalInfo>
    implements Auditable {
  private static final long serialVersionUID = 123004560987L;

  @Id
  @Column(name = "PRODUCT_NUTRITIONAL_INFO_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "PRODUCT_NUTRITIONAL_INFO_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Column(name = "NUTRITIONAL_DESCRIPTION", nullable = true)
  private String nutritionalDescription;

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.REFRESH})
  @JoinTable(
      name = "PRODUCTNUTRITIONALINFO_NUTRIENTSINFO",
      schema = SchemaConstant.SALESMANAGER_SCHEMA,
      joinColumns = {
        @JoinColumn(name = "PRODUCT_NUTRITIONAL_INFO_ID", nullable = false, updatable = false)
      },
      inverseJoinColumns = {
        @JoinColumn(name = "NUTRIENTS_INFO_ID", nullable = false, updatable = false)
      })
  @Cascade({
    org.hibernate.annotations.CascadeType.DETACH,
    org.hibernate.annotations.CascadeType.LOCK,
    org.hibernate.annotations.CascadeType.REFRESH,
    org.hibernate.annotations.CascadeType.REPLICATE
  })
  private Set<NutrientsInfo> productNutrientsInfo = new LinkedHashSet<>();

  @OneToMany(
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      mappedBy = "productNutritionalInfo")
  private Set<ProductNutrientsFact> productNutrientsFacts = new LinkedHashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNutritionalDescription() {
    return nutritionalDescription;
  }

  public void setNutritionalDescription(String nutritionalDescription) {
    this.nutritionalDescription = nutritionalDescription;
  }

  public Set<NutrientsInfo> getProductNutrientsInfo() {
    return productNutrientsInfo;
  }

  public void setProductNutrientsInfo(Set<NutrientsInfo> productNutrientsInfo) {
    this.productNutrientsInfo = productNutrientsInfo;
  }

  public Set<ProductNutrientsFact> getProductNutrientsFacts() {
    return productNutrientsFacts;
  }

  public void setProductNutrientsFacts(Set<ProductNutrientsFact> nutrientsFacts) {
    this.productNutrientsFacts = nutrientsFacts;
  }

  @Override
  public AuditSection getAuditSection() {
    return auditSection;
  }

  @Override
  public void setAuditSection(AuditSection auditSection) {
    this.auditSection = auditSection;
  }
}
