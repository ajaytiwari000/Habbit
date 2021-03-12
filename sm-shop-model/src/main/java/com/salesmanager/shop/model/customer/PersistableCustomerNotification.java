package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.shop.model.entity.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCustomerNotification extends Entity {
  private boolean smsEnable;
  private boolean emailEnable;
  private boolean whatsAppEnable;
  private String phone;

  public boolean isSmsEnable() {
    return smsEnable;
  }

  public void setSmsEnable(boolean smsEnable) {
    this.smsEnable = smsEnable;
  }

  public boolean isEmailEnable() {
    return emailEnable;
  }

  public void setEmailEnable(boolean emailEnable) {
    this.emailEnable = emailEnable;
  }

  public boolean isWhatsAppEnable() {
    return whatsAppEnable;
  }

  public void setWhatsAppEnable(boolean whatsAppEnable) {
    this.whatsAppEnable = whatsAppEnable;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
