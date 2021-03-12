package com.salesmanager.core.business.services.aws.sqs;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.*;
import com.salesmanager.core.business.HabbitCoreConstant;
import com.salesmanager.core.business.modules.sms.model.Sms;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service("awsSqsService")
public class AwsSqsServiceImpl implements AwsSqsService {

  private static final AmazonSQSAsync sqsClient = AwsSqsClient.getAwsSqsClient();

  @Override
  public String createQueue() throws Exception {
    CreateQueueRequest createStandardQueueRequest =
        new CreateQueueRequest(HabbitCoreConstant.SQS_QUEUE);
    String standardQueueUrl = sqsClient.createQueue(createStandardQueueRequest).getQueueUrl();
    return standardQueueUrl;
  }

  @Override
  public void postMessageToSQS(Sms sms) throws Exception {
    Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
    messageAttributes.put(
        "phone", new MessageAttributeValue().withStringValue(sms.getTo()).withDataType("String"));

    SendMessageRequest sendMessageStandardQueue =
        new SendMessageRequest()
            .withQueueUrl(HabbitCoreConstant.SQS_URL)
            .withMessageBody(sms.toString())
            // .withDelaySeconds(30)
            .withMessageAttributes(messageAttributes);
    try {
      sqsClient.sendMessage(sendMessageStandardQueue);
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  @Override
  public List<Message> getMessageFromSQS(int waitTimeSec, int maxNumberMessage) throws Exception {
    ReceiveMessageRequest receiveMessageRequest =
        new ReceiveMessageRequest(HabbitCoreConstant.SQS_QUEUE)
            .withWaitTimeSeconds(waitTimeSec)
            .withMaxNumberOfMessages(maxNumberMessage);
    List<Message> sqsMessages;
    try {
      sqsMessages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();
    } catch (Exception e) {
      throw new Exception(e);
    }
    return sqsMessages;
  }
}
