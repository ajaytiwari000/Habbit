package com.salesmanager.core.business.modules.sms;

public interface SmsServiceProvider {
  public void send(final String toNumber, final String message) throws Exception;
}
