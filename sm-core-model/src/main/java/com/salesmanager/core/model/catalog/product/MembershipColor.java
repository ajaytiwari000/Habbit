package com.salesmanager.core.model.catalog.product;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(
    name = "MEMBERSHIP_COLOR",
    schema = SchemaConstant.SALESMANAGER_SCHEMA,
    uniqueConstraints = @UniqueConstraint(columnNames = {"MEMBERSHIP_COLOR_ID"}))
public class MembershipColor extends SalesManagerEntity<Long, MembershipColor>
    implements Auditable {

  private static final long serialVersionUID = 1230400560987L;
  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "MEMBERSHIP_COLOR_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "MEMBERSHIP_COLOR__SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "TIER_TYPE", unique = true)
  @Enumerated(EnumType.STRING)
  private TierType tierType;

  @Column(name = "PRIMARY_COLOR", nullable = true)
  private String primaryColor;

  @Column(name = "SECONDARY_COLOR", nullable = true)
  private String secondaryColor;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public TierType getTierType() {
    return tierType;
  }

  public void setTierType(TierType tierType) {
    this.tierType = tierType;
  }

  public String getPrimaryColor() {
    return primaryColor;
  }

  public void setPrimaryColor(String primaryColor) {
    this.primaryColor = primaryColor;
  }

  public String getSecondaryColor() {
    return secondaryColor;
  }

  public void setSecondaryColor(String secondaryColor) {
    this.secondaryColor = secondaryColor;
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
