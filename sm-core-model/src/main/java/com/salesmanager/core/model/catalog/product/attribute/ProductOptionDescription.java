package com.salesmanager.core.model.catalog.product.attribute;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.description.Description;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Type;

@Entity
@Table(
    name = "PRODUCT_OPTION_DESC",
    schema = SchemaConstant.SALESMANAGER_SCHEMA,
    uniqueConstraints = {@UniqueConstraint(columnNames = {"PRODUCT_OPTION_ID", "LANGUAGE_ID"})})
@TableGenerator(
    name = "description_gen",
    table = "SM_SEQUENCER",
    pkColumnName = "SEQ_NAME",
    valueColumnName = "SEQ_COUNT",
    pkColumnValue = "product_option_description_seq",
    allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE,
    initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
// @SequenceGenerator(name = "description_gen", sequenceName = "product_option_description_seq",
// allocationSize = SchemaConstant.DESCRIPTION_ID_SEQUENCE_START)
public class ProductOptionDescription extends Description {
  private static final long serialVersionUID = -3158504904707188465L;

  @ManyToOne(targetEntity = ProductOption.class)
  @JoinColumn(name = "PRODUCT_OPTION_ID", nullable = false)
  private ProductOption productOption;

  @Column(name = "PRODUCT_OPTION_COMMENT")
  @Type(type = "org.hibernate.type.TextType")
  private String productOptionComment;

  public ProductOptionDescription() {}

  public String getProductOptionComment() {
    return productOptionComment;
  }

  public void setProductOptionComment(String productOptionComment) {
    this.productOptionComment = productOptionComment;
  }

  public ProductOption getProductOption() {
    return productOption;
  }

  public void setProductOption(ProductOption productOption) {
    this.productOption = productOption;
  }
}
