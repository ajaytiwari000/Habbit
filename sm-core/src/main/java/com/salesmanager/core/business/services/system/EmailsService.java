package com.salesmanager.core.business.services.system;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.email.Email;
import com.salesmanager.core.business.modules.email.EmailConfig;
import com.salesmanager.core.model.merchant.MerchantStore;

/* not habbit part*/
public interface EmailsService {

  public void sendHtmlEmail(MerchantStore store, Email email) throws ServiceException, Exception;

  public EmailConfig getEmailConfiguration(MerchantStore store) throws ServiceException;

  public void saveEmailConfiguration(EmailConfig emailConfig, MerchantStore store)
      throws ServiceException;
}
