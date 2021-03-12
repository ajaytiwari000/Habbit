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
@Table(name = "NUTRIENTS_FACTS", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class NutrientsFact extends SalesManagerEntity<Long, NutrientsFact> implements Auditable {

  private static final long serialVersionUID = 1200034560987L;

  @Id
  @Column(name = "NUTRIENT_FACTS_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CATEGORY_NUTRIENT_FACTS_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Embedded private AuditSection auditSection = new AuditSection();

  @JsonIgnore
  @ManyToOne(targetEntity = NutritionalInfo.class)
  @JoinColumn(name = "NUTRITIONAL_INFO_ID", nullable = false)
  private NutritionalInfo nutritionalInfo;

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

  public NutritionalInfo getNutritionalInfo() {
    return nutritionalInfo;
  }

  public void setNutritionalInfo(NutritionalInfo nutritionalInfo) {
    this.nutritionalInfo = nutritionalInfo;
  }

  public String getContentValue() {
    return contentValue;
  }

  public void setContentValue(String contentValue) {
    this.contentValue = contentValue;
  }

  public int getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(int orderNumber) {
    this.orderNumber = orderNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
    NutrientsFact that = (NutrientsFact) o;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name);
  }
}
