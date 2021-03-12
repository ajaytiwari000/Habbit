package com.salesmanager.core.business.modules.sms.model;

import com.salesmanager.core.business.modules.sms.constants.SMSType;
import java.io.Serializable;
import java.util.Map;

public class Sms implements Serializable {

  private static final long serialVersionUID = 6481794982612868757L;

  private String to;
  private String messageBody;
  private Map<String, Object> modelParams;
  private SMSType smsType;

  public Sms() {}

  public Sms(String to, SMSType smsType, Map<String, Object> modelParams) {
    this.to = to;
    this.smsType = smsType;
    this.modelParams = modelParams;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getMessageBody() {
    return messageBody;
  }

  public void setMessageBody(String messageBody) {
    this.messageBody = messageBody;
  }

  public Map<String, Object> getModelParams() {
    return modelParams;
  }

  public void setModelParams(Map<String, Object> modelParams) {
    this.modelParams = modelParams;
  }

  public SMSType getSmsType() {
    return smsType;
  }

  public void setSmsType(SMSType smsType) {
    this.smsType = smsType;
  }
}
