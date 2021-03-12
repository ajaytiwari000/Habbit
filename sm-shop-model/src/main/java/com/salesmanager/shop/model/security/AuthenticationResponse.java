package com.salesmanager.shop.model.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.customer.PersistableCustomer;
import com.salesmanager.shop.model.entity.Entity;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse extends Entity implements Serializable {
  public AuthenticationResponse() {}

  /** */
  private static final long serialVersionUID = 1L;

  private PersistableCustomer customer;
  private String token;
  private boolean isNewUser;

  public AuthenticationResponse(Long userId, String token) {
    this.token = token;
    super.setId(userId);
  }

  public String getToken() {
    return token;
  }

  public boolean isNewUser() {
    return isNewUser;
  }

  public void setNewUser(boolean newUser) {
    isNewUser = newUser;
  }

  public PersistableCustomer getCustomer() {
    return customer;
  }

  public void setCustomer(PersistableCustomer customer) {
    this.customer = customer;
  }
}
