package com.salesmanager.core.model.customer.attribute;

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
    name = "CUSTOMER_OPTION_DESC",
    schema = SchemaConstant.SALESMANAGER_SCHEMA,
    uniqueConstraints = {@UniqueConstraint(columnNames = {"CUSTOMER_OPTION_ID", "LANGUAGE_ID"})})
@TableGenerator(
    name = "description_gen",
    table = "SM_SEQUENCER",
    pkColumnName = "SEQ_NAME",
    valueColumnName = "SEQ_COUNT",
    pkColumnValue = "customer_option_description_seq",
    allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE,
    initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
// @SequenceGenerator(name = "description_gen", sequenceName = "customer_option_description_seq",
// allocationSize = SchemaConstant.DESCRIPTION_ID_SEQUENCE_START)
public class CustomerOptionDescription extends Description {
  private static final long serialVersionUID = 1L;

  @ManyToOne(targetEntity = CustomerOption.class)
  @JoinColumn(name = "CUSTOMER_OPTION_ID", nullable = false)
  private CustomerOption customerOption;

  @Column(name = "CUSTOMER_OPTION_COMMENT")
  @Type(type = "org.hibernate.type.TextType")
  private String customerOptionComment;

  public CustomerOptionDescription() {}

  public CustomerOption getCustomerOption() {
    return customerOption;
  }

  public void setCustomerOption(CustomerOption customerOption) {
    this.customerOption = customerOption;
  }

  public String getCustomerOptionComment() {
    return customerOptionComment;
  }

  public void setCustomerOptionComment(String customerOptionComment) {
    this.customerOptionComment = customerOptionComment;
  }
}
