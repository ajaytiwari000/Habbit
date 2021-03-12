package com.salesmanager.core.business.services.system;

import java.util.Map;

public interface EmailTemplateService {
  String getEmailMessage(String templateName, Map<String, String> model);
}
