package com.salesmanager.shop.utils;

import lombok.Data;

@Data
public class SuccessResponse {

  private final boolean success = true;

  private String message = "";

  public static final SuccessResponse WITH_NO_CONTENT = new SuccessResponse();

  private Object result;

  public SuccessResponse(Object content, String msg) {
    this(content);
    this.message = msg;
  }

  public SuccessResponse(Object result) {
    this.result = result;
  }

  public SuccessResponse(String msg) {
    this.message = msg;
  }

  private SuccessResponse() {}

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public static SuccessResponse getWithNoContent() {
    return WITH_NO_CONTENT;
  }

  public Object getResult() {
    return result;
  }

  public void setResult(Object result) {
    this.result = result;
  }
}
