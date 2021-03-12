package com.salesmanager.shop.store.security;

public class RequestCorrelation {
  private static final ThreadLocal<String> id = new ThreadLocal<String>();

  public static String getId() {
    return id.get();
  }

  public static void setId(String correlationId) {
    id.set(correlationId);
  }
}
