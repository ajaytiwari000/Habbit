package com.salesmanager.shop.populator.customer;

import com.salesmanager.core.business.HabbitCoreConstant;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.modules.email.Email;
import com.salesmanager.core.business.modules.sms.constants.EmailType;
import com.salesmanager.core.business.services.customer.attribute.CustomerOptionService;
import com.salesmanager.core.business.services.customer.attribute.CustomerOptionValueService;
import com.salesmanager.core.business.services.reference.country.CountryService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.services.reference.zone.ZoneService;
import com.salesmanager.core.business.services.system.EmailService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.common.enumerator.ProductRewardPoint;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.CustomerMetaData;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.customer.membership.MembershipFacade;
import com.salesmanager.shop.error.codes.CustomerErrorCodes;
import com.salesmanager.shop.model.customer.PersistableCustomer;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.facade.customer.metaData.CustomerMetaDataFacade;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CustomerPopulator extends AbstractDataPopulator<PersistableCustomer, Customer> {

  protected static final Logger LOG = LoggerFactory.getLogger(CustomerPopulator.class);
  @Autowired private CountryService countryService;
  @Autowired private ZoneService zoneService;
  @Autowired private LanguageService languageService;
  @Autowired private CustomerOptionService customerOptionService;
  @Autowired private CustomerOptionValueService customerOptionValueService;
  @Autowired private PasswordEncoder passwordEncoder;
  @Inject private EmailService emailService;
  @Inject private MembershipFacade membershipFacade;
  @Inject private CustomerMetaDataFacade customerMetaDataFacade;

  /** Creates a Customer entity ready to be saved */
  @Override
  public Customer populate(
      PersistableCustomer source, Customer target, MerchantStore store, Language language)
      throws ConversionException {

    try {

      if (source.getId() != null && source.getId() > 0) {
        target.setId(source.getId());
      }
      target.setPassword(null);
      if (!StringUtils.isEmpty(source.getUserName())) {
        target.setUserName(source.getUserName());
      }
      target.setFirstName(source.getFirstName());
      target.setLastName(source.getLastName());
      target.setEmailAddress(source.getEmailAddress());

      if (!StringUtils.isEmpty(source.getPhoneNumber())) {
        target.setPhoneNumber(source.getPhoneNumber());
      }

      // create customer meta data for complete your profile mail
      if (Objects.nonNull(target.getEmailAddress())) {
        createCustomerMetaData(target);
      }

      if (!StringUtils.isEmpty(source.getEmailAddress()) && source.isNewEmail()) {
        String link = generateLink(source);
        sendEmailVerificationMail(source, link);
        target.setEmailVerified(false);
      }

      if (!StringUtils.isEmpty(source.getFriendPhone())) {
        target.setFriendPhone(source.getFriendPhone());
      }
      if (source.isReferralCodeApplied()) {
        target.setReferralCodeApplied(source.isReferralCodeApplied());
      }
      if (!StringUtils.isEmpty(source.getReferralCode())) {
        target.setReferralCode(source.getReferralCode());
      }
      if (source.isFirstOrderPlaced()) {
        target.setFirstOrderPlaced(source.isFirstOrderPlaced());
      }
      if (!StringUtils.isEmpty(source.getDateOfBirth())) {
        Date customerDob = convertDate(source.getDateOfBirth());
        target.setDateOfBirth(customerDob);
      }
      // set Razor Pay customer id
      if (!StringUtils.isEmpty(source.getRazorPayCustomerId())) {
        target.setRazorPayCustomerId(source.getRazorPayCustomerId());
      }
      target.setImageUrl(source.getImageUrl());
      target.setImage(source.getImage());
      if (source.getGender() != null) {
        target.setGender(
            com.salesmanager.core.model.customer.CustomerGender.valueOf(source.getGender().name()));
      }
      target.setMerchantStore(store);

      if (target.getDefaultLanguage() == null) {

        Language lang =
            source.getLanguage() == null
                ? language
                : languageService.getByCode(source.getLanguage());

        target.setDefaultLanguage(lang);
      }
      if (!target.isProfileCompleted()) {
        target.setProfileCompleted(
            IsCustomerProfileCompleted(source)); // execute until profile got completed
      }

    } catch (Exception e) {
      throw new ConversionException(e);
    }

    return target;
  }

  private String generateLink(PersistableCustomer source) {
    String encryptSecret =
        passwordEncoder.encode(source.getPhoneNumber() + HabbitCoreConstant.SECRET);
    String link =
        HabbitCoreConstant.DOMAIN
            + "/email/verification/"
            + source.getPhoneNumber()
            + "?secret="
            + encryptSecret;
    return link;
  }

  private void createCustomerMetaData(Customer customer) {
    CustomerMetaData customerMetaData = new CustomerMetaData();
    customerMetaData.setfName(customer.getFirstName());
    customerMetaData.setlName(customer.getLastName());
    customerMetaData.setMailSent(false);
    customerMetaData.setEmail(customer.getEmailAddress());
    customerMetaData.setPhone(customer.getPhoneNumber());

    customerMetaDataFacade.createCustomerMetaData(customerMetaData);
  }

  private void sendEmailVerificationMail(PersistableCustomer source, String link) {
    Email email = emailService.createMail(EmailType.EMAIL_VERIFICATION);
    email.setTo(source.getEmailAddress());
    email
        .getTemplateTokens()
        .put("ConsumerName", source.getFirstName() + " " + source.getLastName());
    // Todo : need to update link
    email.getTemplateTokens().put("link", link);
    try {
      emailService.sendEmail(email);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_EMAIL_VERIFICATION_MAIL_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_EMAIL_VERIFICATION_MAIL_FAILURE.getErrorMessage());
    }
  }

  private boolean IsCustomerProfileCompleted(PersistableCustomer source) {
    if (!source.isProfileCompleted()
        && (StringUtils.isEmpty(source.getFirstName())
            || StringUtils.isEmpty(source.getLastName())
            || StringUtils.isEmpty(source.getDateOfBirth())
            || Objects.isNull(source.getGender())
            || StringUtils.isEmpty(source.getEmailAddress()))) {
      return false;
    }
    // profile completion points
    membershipFacade.addRewardPoints(
        ProductRewardPoint.PROFILE_COMPLETION_POINT.getValue(), source.getPhoneNumber(), false);
    return true;
  }

  private Date convertDate(String dateOfBirth) throws ParseException {
    Date dob = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth);
    return dob;
  }

  public PersistableCustomer populateToPersistable(Customer source, PersistableCustomer target)
      throws ConversionException {

    try {

      if (source.getId() != null && source.getId() > 0) {
        target.setId(source.getId());
      }
      target.setPassword(null);
      target.setUserName(source.getUserName());
      target.setFirstName(source.getFirstName());
      target.setLastName(source.getLastName());
      target.setEmailAddress(source.getEmailAddress());
      target.setImage(source.getImage());
      target.setImageUrl(source.getImageUrl());
      target.setFirstOrderPlaced(source.isFirstOrderPlaced());
      target.setReferralCodeApplied(source.isReferralCodeApplied());
      target.setProfileCompleted(source.isProfileCompleted());
      target.setEmailVerified(source.isEmailVerified());
      // target.setFriendPhone(source.getFriendPhone());
      target.setReferralCode(source.getReferralCode());
      Date customerDob = source.getDateOfBirth();
      if (Objects.nonNull(customerDob)) {
        target.setDateOfBirth(convertString(customerDob));
      }
      target.setRazorPayCustomerId(source.getRazorPayCustomerId());
      if (source.getGender() != null) {
        target.setGender(
            com.salesmanager.core.model.customer.CustomerGender.valueOf(source.getGender().name()));
      }

    } catch (Exception e) {
      throw new ConversionException(e);
    }

    return target;
  }

  private String convertString(Date customerDob) {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
    String dob = dateFormat.format(customerDob);
    return dob;
  }

  @Override
  protected Customer createTarget() {
    return new Customer();
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOG.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
