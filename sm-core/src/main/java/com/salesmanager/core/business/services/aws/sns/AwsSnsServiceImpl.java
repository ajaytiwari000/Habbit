package com.salesmanager.core.business.services.aws.sns;

import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.salesmanager.core.business.HabbitCoreConstant;
import org.springframework.stereotype.Service;

@Service("awsSnsService")
public class AwsSnsServiceImpl implements AwsSnsService {

  private static final AmazonSNSAsync snsClient = AwsSnsClient.getAwsSnsClient();

  @Override
  public String createTopicAndGetArn() throws Exception {

    final CreateTopicRequest createTopicRequest =
        new CreateTopicRequest(HabbitCoreConstant.SNS_TOPIC);
    final CreateTopicResult createTopicResponse = snsClient.createTopic(createTopicRequest);
    String topicArn = createTopicResponse.getTopicArn();
    return topicArn;
  }

  @Override
  public void subscribeTopic(String protocol, String emailAddress) throws Exception {
    String topicArn = HabbitCoreConstant.TOPIC_URL;
    final SubscribeRequest subscribeRequest =
        new SubscribeRequest(topicArn, protocol, emailAddress);
    try {
      snsClient.subscribe(subscribeRequest);
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  @Override
  public void publicToTopic(String emailMessageBody, String subject) throws Exception {
    String topicArn = HabbitCoreConstant.TOPIC_URL;
    try {
      snsClient.publish(topicArn, emailMessageBody, subject);
    } catch (Exception e) {
      throw new Exception(e);
    }
  }
}
