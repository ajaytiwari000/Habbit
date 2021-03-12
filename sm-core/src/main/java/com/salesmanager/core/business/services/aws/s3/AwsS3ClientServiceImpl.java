package com.salesmanager.core.business.services.aws.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("awsS3ClientService")
public class AwsS3ClientServiceImpl implements AwsS3ClientService {

  @Value("${config.cms.aws.region}")
  private String AWS_DEFAULT_REGION_NAME;

  @Value("${aws.access.key}")
  private String AWS_CONFIG_ACCESS_KEY_ID;

  @Value("${aws.secret.key}")
  private String AWS_CONFIG_SECRET_KEY_ID;

  private static final Logger LOGGER = LoggerFactory.getLogger(AwsS3ClientServiceImpl.class);

  public AmazonS3 getAwsS3Client() {
    AWSCredentials awsCredentials =
        new BasicAWSCredentials(AWS_CONFIG_ACCESS_KEY_ID, AWS_CONFIG_SECRET_KEY_ID);
    AmazonS3 s3Client =
        AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .withRegion(AWS_DEFAULT_REGION_NAME)
            .build();
    return s3Client;
  }
  //
  //  @PreDestroy
  //  public void cleanUp() {
  //    try {
  //      if (s3Client != null) {
  //        s3Client.shutdown();
  //      }
  //      AwsSdkMetrics.unregisterMetricAdminMBean();
  //    } catch (Exception e) {
  //      LOGGER.error("Error in shutting down s3Client ", e);
  //    }
  //  }
}
