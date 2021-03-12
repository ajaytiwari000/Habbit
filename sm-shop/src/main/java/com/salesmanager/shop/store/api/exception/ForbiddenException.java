package com.salesmanager.shop.store.api.exception;

public class ForbiddenException extends GenericRuntimeException {
  public ForbiddenException(String errorCode, String errorMessage) {
    super(errorCode, errorMessage);
  }

  public ForbiddenException(String errorMessage) {
    super(errorMessage);
  }

  public ForbiddenException(Throwable exception) {
    super(exception);
  }

  public ForbiddenException(String errorMessage, Throwable exception) {
    super(errorMessage, exception);
  }

  public ForbiddenException(String errorCode, String errorMessage, Throwable exception) {
    super(errorCode, errorMessage, exception);
  }
}
