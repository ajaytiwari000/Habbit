package com.salesmanager.core.business.services.system;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.email.Email;
import com.salesmanager.core.business.modules.sms.constants.EmailType;

public interface EmailService {

  public void sendEmail(Email email) throws ServiceException;

  Email createMail(EmailType emailType);
}
