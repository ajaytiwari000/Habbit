package com.salesmanager.core.model.common.enumerator;

public enum ProductRewardPoint {
  SIGN_UP_REFERRER_POINT(0),
  SIGN_UP_REFEREE_POINT(100),
  PROFILE_COMPLETION_POINT(50),
  PROFILE_CREATION_POINT(100);
  private final int value;

  ProductRewardPoint(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
