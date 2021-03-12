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
@Table(name = "CUSTOMER_META_DATA", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class CustomerMetaData extends SalesManagerEntity<Long, CustomerMetaData>
    implements Auditable {
  private static final long serialVersionUID = -6966934116557219193L;
  /**
   * This table will have data for triggering complete your profile mail,Entry will persist when
   * customer email is entered.
   */
  @Id
  @Column(name = "CUS_MD_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CUSTOMER_META_DATA_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "MAIL_SENT")
  private boolean mailSent;

  @Column(name = "PHONE", unique = true)
  private String phone;

  @Column(name = "EMAIL")
  private String email;
  //
  //  @Column(name = "EMAIL_VERIFIED")
  //  private boolean emailVerified;

  @Column(name = "FIRST_NAME")
  private String fName;

  @Column(name = "LAST_NAME")
  private String lName;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  @JsonIgnore @Embedded private AuditSection auditSection = new AuditSection();

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

  public boolean isMailSent() {
    return mailSent;
  }

  public void setMailSent(boolean mailSent) {
    this.mailSent = mailSent;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  //
  //  public boolean isEmailVerified() {
  //    return emailVerified;
  //  }
  //
  //  public void setEmailVerified(boolean emailVerified) {
  //    this.emailVerified = emailVerified;
  //  }

  public String getfName() {
    return fName;
  }

  public void setfName(String fName) {
    this.fName = fName;
  }

  public String getlName() {
    return lName;
  }

  public void setlName(String lName) {
    this.lName = lName;
  }
}
