package com.salesmanager.shop.model.catalog.catalog;

import com.salesmanager.shop.model.entity.ReadableList;
import java.util.ArrayList;
import java.util.List;

public class ReadableCatalogEntryList extends ReadableList {

  /** */
  private static final long serialVersionUID = 1L;

  private List<ReadableCatalogEntry> catalogEntry = new ArrayList<ReadableCatalogEntry>();

  public List<ReadableCatalogEntry> getCatalogEntry() {
    return catalogEntry;
  }

  public void setCatalogEntry(List<ReadableCatalogEntry> catalogEntry) {
    this.catalogEntry = catalogEntry;
  }
}
