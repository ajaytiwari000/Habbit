package com.salesmanager.core.model.common.enumerator;

public enum TierType {
  HABBIT_BLUE(0, 499),
  HABBIT_GOLD(500, 999),
  HABBIT_PLATINUM(1000, 1499),
  HABBIT_DIAMOND(1500, 1999),
  HABBIT_BLACK(2000, 5000),
  ALL(Integer.MIN_VALUE, Integer.MAX_VALUE);

  private final int lowerLimit;
  private final int upperLimit;

  TierType(int lowerLimit, int upperLimit) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
  }

  private static TierType[] vals = values();

  public TierType next() {
    return vals[(this.ordinal() + 1) % vals.length];
  }

  public int getLowerLimit() {
    return lowerLimit;
  }

  public int getUpperLimit() {
    return upperLimit;
  }
}
