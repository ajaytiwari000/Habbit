package com.salesmanager.core.model.unicommerce;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.customer.AuthenticationTokenStatus;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import javax.persistence.*;

@Entity
@Table(name = "UNICOM_AUTH_TOKEN", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class UnicommerceAuthenticationToken
    extends SalesManagerEntity<Long, UnicommerceAuthenticationToken> implements Auditable {
  private static final long serialVersionUID = -6966934116557219193L;

  @Id
  @Column(name = "UNICOM_AUTH_TOKEN_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "UNICOM_AUTH_TOKEN_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @JsonIgnore @Embedded private AuditSection auditSection = new AuditSection();

  @Column(name = "AUTH_TOKEN")
  private String authToken;

  @Column(name = "TOKEN_TYPE")
  private String tokenType;

  @Column(name = "REFRESH_TOKEN")
  private String refreshToken;

  @Column(name = "EXPIRES_IN")
  private int expiresIn;

  @Column(name = "SCOPE")
  private String scope;

  @Column(name = "STATUS")
  private AuthenticationTokenStatus status;

  public UnicommerceAuthenticationToken() {}

  public UnicommerceAuthenticationToken(
      String authToken,
      String tokenType,
      String refreshToken,
      int expiresIn,
      String scope,
      AuthenticationTokenStatus status) {
    this.authToken = authToken;
    this.tokenType = tokenType;
    this.refreshToken = refreshToken;
    this.expiresIn = expiresIn;
    this.scope = scope;
    this.status = status;
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

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public int getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(int expiresIn) {
    this.expiresIn = expiresIn;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public AuthenticationTokenStatus getStatus() {
    return status;
  }

  public void setStatus(AuthenticationTokenStatus status) {
    this.status = status;
  }
}
