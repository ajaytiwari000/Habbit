package com.salesmanager.shop.model.catalog;

import com.salesmanager.shop.model.catalog.category.ReadableCategorys;
import java.util.ArrayList;
import java.util.List;

/** Not used in habit */

/**
 * Object representing the results of a search query
 *
 * @author Carl Samson
 */
public class SearchProductList extends ProductList {

  private static final long serialVersionUID = 1L;
  private List<ReadableCategorys> categoryFacets = new ArrayList<ReadableCategorys>();

  public List<ReadableCategorys> getCategoryFacets() {
    return categoryFacets;
  }

  public void setCategoryFacets(List<ReadableCategorys> categoryFacets) {
    this.categoryFacets = categoryFacets;
  }
}
