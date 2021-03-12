package com.salesmanager.core.business.services.aws.sqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AwsSqsListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(AwsSqsListener.class);

  //  @QueueListener(HabbitCoreConstant.SQS_URL)
  //  public void listen(@Payload Sms payload) {
  //    System.out.printf("got msg from sqs" + payload);
  //  }
}
