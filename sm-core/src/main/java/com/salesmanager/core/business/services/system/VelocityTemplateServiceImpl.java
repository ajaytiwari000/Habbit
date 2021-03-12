package com.salesmanager.core.business.services.system;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

@Service("velocityTemplateService")
public class VelocityTemplateServiceImpl implements SMSTemplateService {
  VelocityEngine ve = new VelocityEngine();
  private Properties properties;

  @PostConstruct
  void init() {
    properties = new Properties();
    properties.put("resource.loader", "class");
    properties.put(
        "class.resource.loader.class",
        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    properties.setProperty("class.resource.loader.path", "/");
    ve.init(properties);
  }

  @Override
  public String getSmsMessage(String templateName, Map<String, Object> model) {
    Template t = ve.getTemplate("/" + templateName + ".vsl");
    VelocityContext context = new VelocityContext(model);
    StringWriter writer = new StringWriter();
    t.merge(context, writer);
    return writer.toString();
  }
}
