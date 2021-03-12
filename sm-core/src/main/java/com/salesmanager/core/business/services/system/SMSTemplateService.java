package com.salesmanager.core.business.services.system;

import java.util.Map;

public interface SMSTemplateService {
  String getSmsMessage(String templateName, Map<String, Object> model);
}
