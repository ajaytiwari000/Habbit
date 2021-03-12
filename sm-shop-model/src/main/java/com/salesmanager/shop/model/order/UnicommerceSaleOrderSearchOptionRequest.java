package com.salesmanager.shop.model.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnicommerceSaleOrderSearchOptionRequest extends Entity {
  private String searchKey;
  private int displayLength;
  private int displayStart;
  private int columns;
  private int sortingCols;
  private int sortColumnIndex;
  private String sortDirection;
  private String columnNames;
  private boolean getCount;

  public String getSearchKey() {
    return searchKey;
  }

  public void setSearchKey(String searchKey) {
    this.searchKey = searchKey;
  }

  public int getDisplayLength() {
    return displayLength;
  }

  public void setDisplayLength(int displayLength) {
    this.displayLength = displayLength;
  }

  public int getDisplayStart() {
    return displayStart;
  }

  public void setDisplayStart(int displayStart) {
    this.displayStart = displayStart;
  }

  public int getColumns() {
    return columns;
  }

  public void setColumns(int columns) {
    this.columns = columns;
  }

  public int getSortingCols() {
    return sortingCols;
  }

  public void setSortingCols(int sortingCols) {
    this.sortingCols = sortingCols;
  }

  public int getSortColumnIndex() {
    return sortColumnIndex;
  }

  public void setSortColumnIndex(int sortColumnIndex) {
    this.sortColumnIndex = sortColumnIndex;
  }

  public String getSortDirection() {
    return sortDirection;
  }

  public void setSortDirection(String sortDirection) {
    this.sortDirection = sortDirection;
  }

  public String getColumnNames() {
    return columnNames;
  }

  public void setColumnNames(String columnNames) {
    this.columnNames = columnNames;
  }

  public boolean isGetCount() {
    return getCount;
  }

  public void setGetCount(boolean getCount) {
    this.getCount = getCount;
  }
}
