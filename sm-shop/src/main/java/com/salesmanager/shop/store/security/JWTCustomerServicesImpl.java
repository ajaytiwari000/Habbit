package com.salesmanager.shop.store.security;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.user.GroupService;
import com.salesmanager.core.business.services.user.PermissionService;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.shop.store.security.user.JWTUser;
import java.util.Collection;
import java.util.Date;
import javax.inject.Inject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service("jwtCustomerDetailsService")
public class JWTCustomerServicesImpl extends AbstractCustomerServices {

  @Inject
  public JWTCustomerServicesImpl(
      CustomerService customerService,
      PermissionService permissionService,
      GroupService groupService) {
    super(customerService, permissionService, groupService);
    this.customerService = customerService;
    this.permissionService = permissionService;
    this.groupService = groupService;
  }

  @Override
  protected UserDetails userDetails(
      String userName, Customer customer, Collection<GrantedAuthority> authorities) {

    AuditSection section = null;
    section = customer.getAuditSection();
    Date lastModified = null;
    // if(section != null) {//does not represent password change
    //	lastModified = section.getDateModified();
    // }

    return new JWTUser(
        customer.getId(),
        userName,
        null, // customer.getBilling().getFirstName(),
        null, // customer.getBilling().getLastName(),
        customer.getEmailAddress(),
        customer.getPassword(),
        authorities,
        true,
        lastModified);
  }
}
