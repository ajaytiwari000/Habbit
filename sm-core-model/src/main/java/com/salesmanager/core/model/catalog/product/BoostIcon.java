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
    name = "BOOST_ICON",
    schema = SchemaConstant.SALESMANAGER_SCHEMA,
    uniqueConstraints = @UniqueConstraint(columnNames = {"BOOST_ICON_ID"}))
public class BoostIcon extends SalesManagerEntity<Long, BoostIcon> implements Auditable {

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "BOOST_ICON_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "BOOST_ICON_SEQ_NEX_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "ICON")
  private String icon;

  @Column(name = "ICON_URL")
  private String iconUrl;

  @Transient private InputStream image = null;
  @Transient private String boostName = null;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public String getBoostName() {
    return boostName;
  }

  public void setBoostName(String boostName) {
    this.boostName = boostName;
  }
}
