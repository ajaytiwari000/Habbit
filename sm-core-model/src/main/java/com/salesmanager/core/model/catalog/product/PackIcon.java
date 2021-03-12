package com.salesmanager.core.model.catalog.product;

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
    name = "PACK_ICON",
    schema = SchemaConstant.SALESMANAGER_SCHEMA,
    uniqueConstraints = @UniqueConstraint(columnNames = {"PACK_ICON_ID"}))
public class PackIcon extends SalesManagerEntity<Long, PackIcon> implements Auditable {

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "PACK_ICON_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "PACK_ICON_SEQ_NEX_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "ICON")
  private String icon;

  @Column(name = "ICON_URL")
  private String iconUrl;

  @Transient private InputStream image = null;
  @Transient private String packValue = null;

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getIconUrl() {
    return iconUrl;
  }

  public void setIconUrl(String iconUrl) {
    this.iconUrl = iconUrl;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public InputStream getImage() {
    return image;
  }

  public void setImage(InputStream image) {
    this.image = image;
  }

  @Override
  public AuditSection getAuditSection() {
    return auditSection;
  }

  @Override
  public void setAuditSection(AuditSection auditSection) {
    this.auditSection = auditSection;
  }

  public String getPackValue() {
    return packValue;
  }

  public void setPackValue(String packValue) {
    this.packValue = packValue;
  }
}
