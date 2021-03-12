package com.salesmanager.core.business.services.aws.sqs;

import com.amazonaws.services.sqs.model.Message;
import com.salesmanager.core.business.modules.sms.model.Sms;
import java.util.List;

public interface AwsSqsService {

  String createQueue() throws Exception;

  void postMessageToSQS(Sms sms) throws Exception;

  List<Message> getMessageFromSQS(int waitTimeSec, int maxNumberMessage) throws Exception;
}
