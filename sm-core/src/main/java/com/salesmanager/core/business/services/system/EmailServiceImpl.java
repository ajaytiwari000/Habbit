package com.salesmanager.core.business.services.system;

import com.salesmanager.core.business.DynamicTemplateId;
import com.salesmanager.core.business.EmailConstant;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.email.Email;
import com.salesmanager.core.business.modules.karix.email.EmailServiceProvider;
import com.salesmanager.core.business.modules.sms.constants.EmailType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

  @Autowired private EmailServiceProvider sendGridServiceProvider;

  @Override
  public void sendEmail(Email email) throws ServiceException {
    preVerify(email);
    try {
      sendGridServiceProvider.sendEmail(email);
    } catch (Exception e) {
      throw new ServiceException("Error while sending Email.", e);
    }
  }

  @Override
  public Email createMail(EmailType emailType) {
    Email email = new Email();
    switch (emailType) {
      case ORDER_CANCEL:
        {
          email.setEmailType(EmailType.ORDER_CANCEL);
          email.setTemplateName(DynamicTemplateId.ORDER_CANCEL);
          email.setFrom(EmailConstant.FROM);
          break;
        }
      case SUCCESSFUL_REGISTRATION:
        {
          email.setEmailType(EmailType.SUCCESSFUL_REGISTRATION);
          email.setTemplateName(DynamicTemplateId.SUCCESSFUL_REGISTRATION);
          email.setFrom(EmailConstant.FROM);
          break;
        }
      case EMAIL_VERIFICATION:
        {
          email.setEmailType(EmailType.EMAIL_VERIFICATION);
          email.setTemplateName(DynamicTemplateId.EMAIL_VERIFICATION);
          email.setFrom(EmailConstant.FROM);
          break;
        }
      case COMPLETE_YOUR_PROFILE:
        {
          email.setEmailType(EmailType.COMPLETE_YOUR_PROFILE);
          email.setTemplateName(DynamicTemplateId.COMPLETE_YOUR_PROFILE);
          email.setFrom(EmailConstant.FROM);
          break;
        }
      case ORDER_CONFIRMED:
        {
          email.setEmailType(EmailType.ORDER_CONFIRMED);
          email.setTemplateName(DynamicTemplateId.ORDER_CONFIRMED);
          email.setFrom(EmailConstant.FROM);
          break;
        }
      case ORDER_SHIPPED:
        {
          email.setEmailType(EmailType.ORDER_SHIPPED);
          email.setTemplateName(DynamicTemplateId.ORDER_SHIPPED);
          email.setFrom(EmailConstant.FROM);
          break;
        }
      case ORDER_DELIVERED:
        {
          email.setEmailType(EmailType.ORDER_DELIVERED);
          email.setTemplateName(DynamicTemplateId.ORDER_DELIVERED);
          email.setFrom(EmailConstant.FROM);
          break;
        }
    }
    return email;
  }

  private void preVerify(Email email) throws ServiceException {
    if (StringUtils.isEmpty(email.getTo())) {
      throw new ServiceException("Email To address null");
    }
    if (StringUtils.isEmpty(email.getFrom())) {
      throw new ServiceException("Email From address null");
    }
  }
}
