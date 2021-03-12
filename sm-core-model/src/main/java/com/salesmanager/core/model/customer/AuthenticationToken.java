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
@Table(name = "AUTHENTICATION_TOKEN", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class AuthenticationToken extends SalesManagerEntity<Long, AuthenticationToken>
    implements Auditable {
  private static final long serialVersionUID = -6966934116557219193L;

  @Id
  @Column(name = "AUTHENTICATION_TOKEN_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "AUTHENTICATION_TOKEN_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @JsonIgnore @Embedded private AuditSection auditSection = new AuditSection();

  @Column(name = "EXPIRATION_TIME", length = 100)
  private Long expirationTime;

  @Column(name = "TOKEN_KEY")
  private String phone;

  @Column(name = "TOKEN_VALUE")
  private String code;

  @Column(name = "DEVICE_ID")
  private String deviceId;

  @Column(name = "PHONE_DEVICE", unique = true)
  private String phoneDevice;

  private AuthenticationType type;

  private AuthenticationTokenStatus status;

  public AuthenticationToken() {}

  public AuthenticationToken(
      Long expirationTime,
      String phone,
      String code,
      AuthenticationTokenStatus status,
      String deviceId,
      String phoneDevice) {
    this.expirationTime = expirationTime;
    this.phone = phone;
    this.code = code;
    this.status = status;
    this.deviceId = deviceId;
    this.phoneDevice = phoneDevice;
  }

  public AuditSection getAuditSection() {
    return auditSection;
  }

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

  public Long getExpirationTime() {
    return expirationTime;
  }

  public void setExpirationTime(Long expirationTime) {
    this.expirationTime = expirationTime;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public AuthenticationType getType() {
    return type;
  }

  public void setType(AuthenticationType type) {
    this.type = type;
  }

  public AuthenticationTokenStatus getStatus() {
    return status;
  }

  public void setStatus(AuthenticationTokenStatus status) {
    this.status = status;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getPhoneDevice() {
    return phoneDevice;
  }

  public void setPhoneDevice(String phoneDevice) {
    this.phoneDevice = phoneDevice;
  }
}
