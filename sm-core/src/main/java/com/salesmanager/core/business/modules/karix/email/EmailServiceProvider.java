package com.salesmanager.core.business.modules.karix.email;

import com.salesmanager.core.business.modules.email.Email;

public interface EmailServiceProvider {
  public void sendEmail(Email email) throws Exception;
}
