package com.salesmanager.core.model.catalog.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import java.util.Objects;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT_NUTRIENTS_FACTS", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class ProductNutrientsFact extends SalesManagerEntity<Long, ProductNutrientsFact>
    implements Auditable {

  private static final long serialVersionUID = 1200034560987L;

  @Id
  @Column(name = "PRODUCT_NUTRIENT_FACTS_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "PRODUCT_NUTRIENT_FACTS_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Embedded private AuditSection auditSection = new AuditSection();

  @JsonIgnore
  @ManyToOne(targetEntity = ProductNutritionalInfo.class)
  @JoinColumn(name = "PRODUCT_NUTRITIONAL_INFO_ID", nullable = false)
  private ProductNutritionalInfo productNutritionalInfo;

  @Column(name = "CONTENT")
  private String contentValue;

  @Column(name = "NAME")
  private String name;

  @Column(name = "ORDER_NUMBER")
  private int orderNumber = 0;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ProductNutritionalInfo getProductNutritionalInfo() {
    return productNutritionalInfo;
  }

  public void setProductNutritionalInfo(ProductNutritionalInfo productNutritionalInfo) {
    this.productNutritionalInfo = productNutritionalInfo;
  }

  public String getContentValue() {
    return contentValue;
  }

  public void setContentValue(String contentValue) {
    this.contentValue = contentValue;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(int orderNumber) {
    this.orderNumber = orderNumber;
  }

  @Override
  public AuditSection getAuditSection() {
    return auditSection;
  }

  @Override
  public void setAuditSection(AuditSection auditSection) {
    this.auditSection = auditSection;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    ProductNutrientsFact that = (ProductNutrientsFact) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name);
  }
}
