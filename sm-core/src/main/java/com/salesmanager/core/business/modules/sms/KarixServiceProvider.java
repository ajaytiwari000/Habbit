package com.salesmanager.core.business.modules.sms;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("karixServiceProvider")
public class KarixServiceProvider implements SmsServiceProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(KarixServiceProvider.class);

  @Value("${config.sms.keyVal}")
  private String key;

  @Value("${config.sms.senderId}")
  private String senderId;

  @Override
  public void send(String toNumber, String message) throws Exception {
    LOGGER.info("Entering. to: {} , message: {} ", toNumber, message);
    final Map<String, String> variables = new HashMap<>();
    variables.put("textMsg", message);
    String url =
        "https://japi.instaalerts.zone/httpapi/QueryStringReceiver?ver=1.0&key="
            + key
            + "&encrpt=0&dest=91"
            + toNumber
            + "&send="
            + senderId
            + "&text={textMsg}";
    // + URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
    LOGGER.info("url {} ", url);
    RestTemplate restTemplate = new RestTemplate();
    try {
      ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, variables);
      LOGGER.info(
          "Response {} from karix while sending sms to {} with message {}",
          response.toString(),
          toNumber,
          message);
    } catch (Exception e) {
      throw new Exception(e);
    }

    // TODO need to create a webUil to send POST request and do proper exception/error handling
    // using the DOC shared by KARIX based on error codes
  }
}
