package com.salesmanager.shop.store.api.exception;

public class ResourceDuplicateException extends ServiceRuntimeException {

  private static final String ERROR_CODE = "409";
  /** */
  private static final long serialVersionUID = 1L;

  public ResourceDuplicateException(String errorCode, String message) {
    super(errorCode, message);
  }

  public ResourceDuplicateException(String message) {
    super(ERROR_CODE, message);
  }
}
