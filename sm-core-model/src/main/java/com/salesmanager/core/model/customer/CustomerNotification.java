package com.salesmanager.core.model.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "CUSTOMER_NOTIFICATION", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class CustomerNotification extends SalesManagerEntity<Long, CustomerNotification>
    implements Auditable {
  private static final long serialVersionUID = -6966934116557219193L;

  @Id
  @Column(name = "CUS_NOTIFY_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CUSTOMER_NOTIFICATION_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "SMS_ENABLE")
  private boolean smsEnable;

  @Column(name = "EMAIL_ENABLE")
  private boolean emailEnable;

  @Column(name = "WHATSAPP_ENABLE")
  private boolean whatsAppEnable;

  @Column(name = "PHONE", unique = true)
  private String phone;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  @JsonIgnore @Embedded private AuditSection auditSection = new AuditSection();

  public boolean isSmsEnable() {
    return smsEnable;
  }

  public void setSmsEnable(boolean smsEnable) {
    this.smsEnable = smsEnable;
  }

  public boolean isEmailEnable() {
    return emailEnable;
  }

  public void setEmailEnable(boolean emailEnable) {
    this.emailEnable = emailEnable;
  }

  public boolean isWhatsAppEnable() {
    return whatsAppEnable;
  }

  public void setWhatsAppEnable(boolean whatsAppEnable) {
    this.whatsAppEnable = whatsAppEnable;
  }

  @Override
  public AuditSection getAuditSection() {
    return auditSection;
  }

  @Override
  public void setAuditSection(AuditSection auditSection) {
    this.auditSection = auditSection;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
