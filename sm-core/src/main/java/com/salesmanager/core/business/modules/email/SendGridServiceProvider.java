package com.salesmanager.core.business.modules.email;

import com.salesmanager.core.business.EmailConstant;
import com.salesmanager.core.business.modules.karix.email.EmailServiceProvider;
import com.salesmanager.core.business.modules.payment.wallet.constants.SendGridConstant;
import com.salesmanager.core.business.modules.sms.constants.EmailType;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Personalization;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("sendGridServiceProvider")
public class SendGridServiceProvider implements EmailServiceProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(SendGridServiceProvider.class);

  @Value("${sendGrid.api.key}")
  private String apiKey;

  @Override
  public void sendEmail(Email email) throws Exception {
    com.sendgrid.helpers.mail.objects.Email from =
        new com.sendgrid.helpers.mail.objects.Email(email.getFrom(), EmailConstant.FROM_NAME);
    com.sendgrid.helpers.mail.objects.Email to =
        new com.sendgrid.helpers.mail.objects.Email(email.getTo());
    Mail mail = new Mail();
    mail.setFrom(from);
    Personalization personalization = new Personalization();
    personalization.addTo(to);
    addDynamicTemplateData(personalization, email, email.getEmailType());
    mail.setTemplateId(email.getTemplateName());
    mail.addPersonalization(personalization);
    SendGrid sg = new SendGrid(apiKey);
    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint(SendGridConstant.MAIL);
      request.setBody(mail.build());
      Response response = sg.api(request);
      System.out.println(response.getStatusCode());
      System.out.println(response.getBody());
      System.out.println(response.getHeaders());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void addDynamicTemplateData(
      Personalization personalization, Email email, EmailType emailType) {
    switch (emailType) {
      case ORDER_CANCEL:
        {
          personalization.addDynamicTemplateData(
              "ConsumerName", email.getTemplateTokens().get("ConsumerName"));
          personalization.addDynamicTemplateData(
              "OrderID", email.getTemplateTokens().get("OrderID"));
          break;
        }
      case SUCCESSFUL_REGISTRATION:
        {
          personalization.addDynamicTemplateData(
              "ConsumerName", email.getTemplateTokens().get("ConsumerName"));
          break;
        }
      case EMAIL_VERIFICATION:
        {
          personalization.addDynamicTemplateData(
              "ConsumerName", email.getTemplateTokens().get("ConsumerName"));
          personalization.addDynamicTemplateData("link", email.getTemplateTokens().get("link"));
          break;
        }
      case COMPLETE_YOUR_PROFILE:
        {
          personalization.addDynamicTemplateData(
              "ConsumerName", email.getTemplateTokens().get("ConsumerName"));
          personalization.addDynamicTemplateData(
              "profileCompletionHabbitPoints",
              email.getTemplateTokens().get("profileCompletionHabbitPoints"));
          personalization.addDynamicTemplateData("link", email.getTemplateTokens().get("link"));
          break;
        }
      case ORDER_SHIPPED:
        {
          personalization.addDynamicTemplateData(
              "ConsumerName", email.getTemplateTokens().get("ConsumerName"));
          personalization.addDynamicTemplateData(
              "OrderID", email.getTemplateTokens().get("OrderID"));
          personalization.addDynamicTemplateData(
              "EstimatedDeliveryTime", email.getTemplateTokens().get("EstimatedDeliveryTime"));
          break;
        }
      case ORDER_CONFIRMED:
        {
          personalization.addDynamicTemplateData(
              "ConsumerName", email.getTemplateTokens().get("ConsumerName"));
          personalization.addDynamicTemplateData(
              "OrderID", email.getTemplateTokens().get("OrderID"));
          personalization.addDynamicTemplateData(
              "OrderDate", email.getTemplateTokens().get("OrderDate"));
          personalization.addDynamicTemplateData(
              "PaidAmount", email.getTemplateTokens().get("PaidAmount"));
          break;
        }
      case ORDER_DELIVERED:
        {
          personalization.addDynamicTemplateData(
              "ConsumerName", email.getTemplateTokens().get("ConsumerName"));
          personalization.addDynamicTemplateData(
              "OrderID", email.getTemplateTokens().get("OrderID"));
          personalization.addDynamicTemplateData(
              "orderPlacedHabbitPointsEarned",
              email.getTemplateTokens().get("orderPlacedHabbitPointsEarned"));
          break;
        }
    }
  }
}
