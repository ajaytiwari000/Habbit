package com.salesmanager.shop.model.entity;

import java.io.Serializable;

public class ReadCategory implements Serializable {
  private static final long serialVersionUID = 1L;
  private long recordsTotal; // total number of records in db

  public long getRecordsTotal() {
    return recordsTotal;
  }

  public void setRecordsTotal(long recordsTotal) {
    this.recordsTotal = recordsTotal;
  }
}
