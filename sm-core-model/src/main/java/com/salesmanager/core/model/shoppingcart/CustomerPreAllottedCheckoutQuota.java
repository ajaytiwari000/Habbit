/** */
package com.salesmanager.core.model.shoppingcart;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "CUSTOMER_PRE_ALLOTTED_CHECKOUT_QUOTA", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class CustomerPreAllottedCheckoutQuota
    extends SalesManagerEntity<Long, CustomerPreAllottedCheckoutQuota> implements Auditable {

  private static final long serialVersionUID = 1L;

  @Embedded private AuditSection auditSection = new AuditSection();

  @Id
  @Column(name = "CPACQ_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CPACQ_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "CUSTOMER_PHONE")
  private String customerPhone;

  @Column(name = "PRODUCT_SKU")
  private String productSku;

  @Column(name = "COUNT")
  private int count;

  @Column(name = "CART_ITEM_CODE")
  private String cartItemCode;

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

  public String getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
  }

  public String getProductSku() {
    return productSku;
  }

  public void setProductSku(String productSku) {
    this.productSku = productSku;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getCartItemCode() {
    return cartItemCode;
  }

  public void setCartItemCode(String cartItemCode) {
    this.cartItemCode = cartItemCode;
  }
}
