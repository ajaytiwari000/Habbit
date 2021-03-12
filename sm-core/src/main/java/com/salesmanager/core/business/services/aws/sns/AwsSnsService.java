package com.salesmanager.core.business.services.aws.sns;

public interface AwsSnsService {

  String createTopicAndGetArn() throws Exception;

  void subscribeTopic(String protocol, String emailAddress) throws Exception;

  void publicToTopic(String emailMessageBody, String subject) throws Exception;
}
