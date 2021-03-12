package com.salesmanager.core.model.common;

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
    name = "REWARD_CONSUMPTION_CRETERIA",
    schema = SchemaConstant.SALESMANAGER_SCHEMA,
    uniqueConstraints = @UniqueConstraint(columnNames = {"REWARD_CONSUMPTION_CRETERIA_ID"}))
public class RewardConsumptionCriteria extends SalesManagerEntity<Long, RewardConsumptionCriteria>
    implements Auditable {

  private static final long serialVersionUID = 1230456009807L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "REWARD_CONSUMPTION_CRETERIA_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "REWARD_CONSUMPTION_CRETERIA_SEQ_NEX_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "TIER_TYPE")
  @Enumerated(EnumType.STRING)
  private TierType tierType;

  @Column(name = "MIN_CART_VALUE")
  private int minCartValue;

  @Column(name = "MIN_REWARD_POINT")
  private int minRewardPoint;

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

  public TierType getTierType() {
    return tierType;
  }

  public void setTierType(TierType tierType) {
    this.tierType = tierType;
  }

  public int getMinCartValue() {
    return minCartValue;
  }

  public void setMinCartValue(int minCartValue) {
    this.minCartValue = minCartValue;
  }

  public int getMinRewardPoint() {
    return minRewardPoint;
  }

  public void setMinRewardPoint(int minRewardPoint) {
    this.minRewardPoint = minRewardPoint;
  }
}
