package com.salesmanager.core.business.services.system;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.sms.model.Sms;

public interface SmsService {

  public void sendSms(Sms sms) throws ServiceException;
}
