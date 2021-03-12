package com.salesmanager.core.model.catalog.product;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(
    name = "PACK_SIZE",
    schema = SchemaConstant.SALESMANAGER_SCHEMA,
    uniqueConstraints = @UniqueConstraint(columnNames = {"PACK_ID"}))
public class Pack extends SalesManagerEntity<Long, Pack> implements Auditable {

  private static final long serialVersionUID = 12304056009087L;
  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "PACK_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "PACK_SEQ_NEX_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "PACK_VALUE", nullable = false, unique = true)
  private String packSizeValue;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "PACK_ICON_ID", nullable = true, updatable = true)
  private PackIcon packIcon;

  public PackIcon getPackIcon() {
    return packIcon;
  }

  public void setPackIcon(PackIcon packIcon) {
    this.packIcon = packIcon;
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
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getPackSizeValue() {
    return packSizeValue;
  }

  public void setPackSizeValue(String packSizeValue) {
    this.packSizeValue = packSizeValue;
  }
}
