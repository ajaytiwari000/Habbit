package com.salesmanager.core.model.common.enumerator;

public enum TierTypeEarnPoint {
  HABBIT_BLUE(25),
  HABBIT_GOLD(20),
  HABBIT_PLATINUM(15),
  HABBIT_DIAMOND(10),
  HABBIT_BLACK(8);

  private final int spendPerPoint;

  TierTypeEarnPoint(int spendPerPoint) {
    this.spendPerPoint = spendPerPoint;
  }

  public int getSpendPerPoint() {
    return spendPerPoint;
  }
}
