package com.salesmanager.core.model.catalog.category;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "CATEGORY_PACK_COUNT", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class CategoryPackCount extends SalesManagerEntity<Long, CategoryPackCount> {
  @Id
  @Column(name = "CATEGORY_PACK_COUNT_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CATEGORY_PACK_COUNT_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "PACK_ID")
  private Long packId;

  @Column(name = "COUNT")
  private int count;

  @ManyToOne(targetEntity = Category.class)
  @JoinColumn(name = "CATEGORY_ID", nullable = false)
  private Category category;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public Long getPackId() {
    return packId;
  }

  public void setPackId(Long packId) {
    this.packId = packId;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
}
