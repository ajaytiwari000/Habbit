package com.salesmanager.shop.store.controller.cron;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.salesmanager.core.business.modules.email.Email;
import com.salesmanager.core.business.modules.sms.constants.EmailType;
import com.salesmanager.core.business.services.system.EmailService;
import com.salesmanager.core.model.common.enumerator.ProductRewardPoint;
import com.salesmanager.core.model.customer.CustomerMetaData;
import com.salesmanager.core.model.unicommerce.UnicommerceAuthenticationToken;
import com.salesmanager.shop.admin.controller.unicom.UnicommerceService;
import com.salesmanager.shop.error.codes.CustomerErrorCodes;
import com.salesmanager.shop.error.codes.UnicommerceErrorCodes;
import com.salesmanager.shop.model.order.UnicommerceGetSaleOrderRequest;
import com.salesmanager.shop.model.order.UnicommerceSearchSaleOrderElementResponse;
import com.salesmanager.shop.model.order.UnicommerceSearchSaleOrderRequest;
import com.salesmanager.shop.model.order.UnicommerceSearchSaleOrderResponse;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.facade.customer.metaData.CustomerMetaDataFacade;
import com.salesmanager.shop.utils.DateUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class CronJob {

  @Value("${unicom.flag}")
  private String unicomFlag;

  private static final Logger LOGGER = LoggerFactory.getLogger(CronJob.class);
  @Inject private EmailService emailService;
  @Inject private UnicommerceService unicommerceService;
  @Inject private CustomerMetaDataFacade customerMetaDataFacade;

  @Scheduled(cron = "0 55 9 * * *")
  void createNewUnicomToken() throws JsonProcessingException {
    if (Boolean.parseBoolean(unicomFlag)) {
      UnicommerceAuthenticationToken token = null;
      if (Objects.isNull(token)) {
        try {
          token = unicommerceService.createToken();
        } catch (Exception e) {
          throwServiceRuntImeException(
              e,
              UnicommerceErrorCodes.UNICOM_ACCESS_TOKEN.getErrorCode(),
              UnicommerceErrorCodes.UNICOM_ACCESS_TOKEN.getErrorMessage());
        }
      }
    }
  }

  @Scheduled(cron = "0 */10 * * * *")
  void SearchSaleOrder() throws JsonProcessingException {
    if (Boolean.parseBoolean(unicomFlag)) {
      LOGGER.info("Cron job started : create sale order");
      UnicommerceSearchSaleOrderRequest searchSaleOrderRequest =
          new UnicommerceSearchSaleOrderRequest();
      UnicommerceSearchSaleOrderResponse searchSaleOrderResponse =
          new UnicommerceSearchSaleOrderResponse();
      // todo : need to change in min value
      searchSaleOrderRequest.setUpdatedSinceInMinutes(11);
      UnicommerceAuthenticationToken token = null;
      try {
        searchSaleOrderResponse = unicommerceService.searchSaleOrder(searchSaleOrderRequest, token);
      } catch (Exception e) {
        throwServiceRuntImeException(
            e,
            UnicommerceErrorCodes.SEARCH_UNICOM_SALE_ORDER.getErrorCode(),
            UnicommerceErrorCodes.SEARCH_UNICOM_SALE_ORDER.getErrorMessage());
      }
      for (UnicommerceSearchSaleOrderElementResponse orderElementResponse :
          searchSaleOrderResponse.getElementResponseList()) {
        token = unicommerceService.getUnicommerceAccessToken();
        UnicommerceGetSaleOrderRequest getSaleOrderRequest = new UnicommerceGetSaleOrderRequest();
        getSaleOrderRequest.setCode(orderElementResponse.getCode());
        try {
          unicommerceService.getSaleOrder(getSaleOrderRequest, token);
        } catch (Exception e) {
          throwServiceRuntImeException(
              e,
              UnicommerceErrorCodes.GET_UNICOM_SALE_ORDER.getErrorCode(),
              UnicommerceErrorCodes.GET_UNICOM_SALE_ORDER.getErrorMessage());
        }
      }
    }
  }

  @Scheduled(cron = "0 2 */3 * * *")
  void sendCompleteYourProfileMail() {
    Date to = DateUtil.getDateFormat(new Date());
    Calendar t = Calendar.getInstance();
    t.setTime(new Date());
    t.add(Calendar.HOUR_OF_DAY, -3);
    Date from = DateUtil.getDateFormat(t.getTime());

    List<CustomerMetaData> customerMetaDataList = customerMetaDataFacade.getMetaDataList(from, to);
    for (CustomerMetaData customerMetaData : customerMetaDataList) {
      Email email = emailService.createMail(EmailType.COMPLETE_YOUR_PROFILE);
      email.setTo(customerMetaData.getEmail());
      email
          .getTemplateTokens()
          .put("ConsumerName", customerMetaData.getfName() + " " + customerMetaData.getlName());
      // Todo : need to update point
      email
          .getTemplateTokens()
          .put(
              "profileCompletionHabbitPoints",
              String.valueOf(ProductRewardPoint.PROFILE_COMPLETION_POINT.getValue()));
      email.getTemplateTokens().put("link", "link");
      try {
        emailService.sendEmail(email);
      } catch (Exception e) {
        throwServiceRuntImeException(
            e,
            CustomerErrorCodes.CUSTOMER_COMPLETE_YOUR_PROFILE_MAIL_FAILURE.getErrorCode(),
            CustomerErrorCodes.CUSTOMER_COMPLETE_YOUR_PROFILE_MAIL_FAILURE.getErrorMessage());
      }
      customerMetaData.setMailSent(true);
      customerMetaDataFacade.updateCustomerMetaData(customerMetaData);
    }
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
