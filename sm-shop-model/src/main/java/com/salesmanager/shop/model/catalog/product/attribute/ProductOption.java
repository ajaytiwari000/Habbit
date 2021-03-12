package com.salesmanager.shop.model.catalog.product.attribute;

import com.salesmanager.shop.model.entity.Entity;
import java.io.Serializable;

public class ProductOption extends Entity implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  private String code;
  private String type;
  private boolean readOnly;
  private String feature;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isReadOnly() {
    return readOnly;
  }

  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }

  public String getFeature() {
    return feature;
  }

  public void setFeature(String feature) {
    this.feature = feature;
  }
}
