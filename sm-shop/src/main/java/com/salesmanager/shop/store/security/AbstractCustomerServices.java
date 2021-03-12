package com.salesmanager.shop.store.security;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.services.user.GroupService;
import com.salesmanager.core.business.services.user.PermissionService;
import com.salesmanager.core.model.common.enumerator.OwnerType;
import com.salesmanager.core.model.common.enumerator.ProductRewardPoint;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.user.Group;
import com.salesmanager.core.model.user.Permission;
import com.salesmanager.shop.admin.controller.customer.membership.ReferralCodeFacade;
import com.salesmanager.shop.admin.security.SecurityDataAccessException;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.customer.PersistableCustomer;
import com.salesmanager.shop.model.customer.PersistableReferralCode;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.store.facade.StoreFacade;
import com.salesmanager.shop.store.facade.cart.CartFacade;
import com.salesmanager.shop.store.facade.customer.CustomerFacade;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public abstract class AbstractCustomerServices implements UserDetailsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCustomerServices.class);

  @Inject private CustomerFacade customerFacade;
  @Inject private StoreFacade storeFacade;
  @Inject LanguageService languageService;
  protected CustomerService customerService;
  protected PermissionService permissionService;
  protected GroupService groupService;
  @Inject private ReferralCodeFacade referralCodeFacade;
  @Inject private CartFacade cartFacade;

  public static final String ROLE_PREFIX = "ROLE_"; // Spring Security 4

  public AbstractCustomerServices(
      CustomerService customerService,
      PermissionService permissionService,
      GroupService groupService) {

    this.customerService = customerService;
    this.permissionService = permissionService;
    this.groupService = groupService;
  }

  protected abstract UserDetails userDetails(
      String userName, Customer customer, Collection<GrantedAuthority> authorities);

  /**
   * This API is used in Authenticating Customer
   *
   * @param userName
   * @return
   * @throws UsernameNotFoundException
   * @throws DataAccessException
   */
  public UserDetails loadUserByUsername(String userName)
      throws UsernameNotFoundException, DataAccessException {
    Customer user = null;
    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    try {

      LOGGER.debug("Loading user by user id: {}", userName);

      user = customerService.getByUserName(userName);
      if (user == null) {
        PersistableCustomer customer = new PersistableCustomer();
        customer.setUserName(userName);
        customer.setPhoneNumber(userName);
        MerchantStore merchantStore =
            storeFacade.get(com.salesmanager.core.business.constants.Constants.DEFAULT_STORE);
        customerFacade.registerCustomer(
            customer, merchantStore, languageService.defaultLanguage()); // creating customer
        cartFacade.createCustomerCartPostSignup(userName); // creating customer cart
        user = customerService.getByUserName(userName);
        createReferralCode(user);
      }
      GrantedAuthority role =
          new SimpleGrantedAuthority(
              ROLE_PREFIX + Constants.PERMISSION_CUSTOMER_AUTHENTICATED); // required to login
      authorities.add(role);

      List<Integer> groupsId = new ArrayList<Integer>();
      List<Group> groups = user.getGroups();
      for (Group group : groups) {
        groupsId.add(group.getId());
      }

      if (CollectionUtils.isNotEmpty(groupsId)) {
        List<Permission> permissions = permissionService.getPermissions(groupsId);
        for (Permission permission : permissions) {
          GrantedAuthority auth = new SimpleGrantedAuthority(permission.getPermissionName());
          authorities.add(auth);
        }
      }

    } catch (Exception e) {
      LOGGER.error("Exception while querrying customer", e);
      throw new SecurityDataAccessException("Cannot authenticate customer", e);
    }
    return userDetails(userName, user, authorities);
  }

  // TODO : need to explore whether it will be helpful to have one to one relationship with customer
  // and referral code where customer PhoneNUmber is used as a key in referral code
  private void createReferralCode(Customer user) {
    PersistableReferralCode persistableReferralCode = new PersistableReferralCode();
    persistableReferralCode.setOwnerType(OwnerType.CUSTOMER);
    persistableReferralCode.setPhoneNumber(user.getPhoneNumber());
    persistableReferralCode.setActive(true);
    persistableReferralCode.setReferrerPoints(ProductRewardPoint.SIGN_UP_REFERRER_POINT.getValue());
    persistableReferralCode.setRefereePoints(ProductRewardPoint.SIGN_UP_REFEREE_POINT.getValue());
    persistableReferralCode.setUnlimitedUse(true);
    referralCodeFacade.create(persistableReferralCode, null);
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
