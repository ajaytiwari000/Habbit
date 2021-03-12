package com.salesmanager.core.business.services.system;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.sms.SmsServiceProvider;
import com.salesmanager.core.business.modules.sms.model.Sms;
import com.salesmanager.core.business.services.aws.sqs.AwsSqsService;
import javax.inject.Inject;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("smsService")
public class SmsServiceImpl implements SmsService {

  @Autowired private SmsServiceProvider karixServiceProvider;

  @Autowired private SMSTemplateService velocityTemplateService;

  @Inject private AwsSqsService awsSqsService;
  @Inject private EmailService emailService;

  @Override
  public void sendSms(Sms sms) throws ServiceException {
    preVerify(sms);
    try {
      String messageBody = sms.getMessageBody();
      if (StringUtils.isEmpty(messageBody) && !MapUtils.isEmpty(sms.getModelParams())) {
        messageBody =
            velocityTemplateService.getSmsMessage(
                sms.getSmsType().getValue(), sms.getModelParams());
      }
      sms.setMessageBody(messageBody);

      // awsSqsService.postMessageToSQS(sms);
      // emailService.sendEmail(null);
      karixServiceProvider.send(sms.getTo(), messageBody);
    } catch (Exception e) {
      throw new ServiceException("Error while sending SMS on ", e);
    }
  }

  private void preVerify(Sms sms) throws ServiceException {
    if (StringUtils.isEmpty(sms.getTo())) {
      throw new ServiceException("Phone number null");
    }
  }
}
