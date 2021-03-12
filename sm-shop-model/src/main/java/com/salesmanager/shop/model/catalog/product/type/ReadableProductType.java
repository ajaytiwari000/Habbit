package com.salesmanager.shop.model.catalog.product.type;

import com.salesmanager.shop.model.entity.Entity;
import java.io.Serializable;

public class ReadableProductType extends Entity implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  private String code;
  private String name;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
