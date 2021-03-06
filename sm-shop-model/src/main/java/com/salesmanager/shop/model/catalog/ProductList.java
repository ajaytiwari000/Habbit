package com.salesmanager.shop.model.catalog;

import com.salesmanager.shop.model.catalog.product.ReadableProducts;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductList implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  private int productCount;
  private BigDecimal minPrice;
  private BigDecimal maxPrice;
  private List<ReadableProducts> products = new ArrayList<ReadableProducts>();

  public void setProducts(List<ReadableProducts> products) {
    this.products = products;
  }

  public List<ReadableProducts> getProducts() {
    return products;
  }

  public int getProductCount() {
    return productCount;
  }

  public void setProductCount(int productCount) {
    this.productCount = productCount;
  }

  public BigDecimal getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(BigDecimal minPrice) {
    this.minPrice = minPrice;
  }

  public BigDecimal getMaxPrice() {
    return maxPrice;
  }

  public void setMaxPrice(BigDecimal maxPrice) {
    this.maxPrice = maxPrice;
  }
}
