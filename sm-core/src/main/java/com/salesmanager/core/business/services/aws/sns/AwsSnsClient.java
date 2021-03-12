package com.salesmanager.core.business.services.aws.sns;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.metrics.AwsSdkMetrics;
import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.salesmanager.core.business.services.aws.s3.constants.S3Constants;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AwsSnsClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(AwsSnsClient.class);

  private static final AWSCredentials awsCredentials =
      new BasicAWSCredentials(S3Constants.getAwsAccessKeyId(), S3Constants.getAwsSecretKeyId());
  private static final AmazonSNSAsync snsClinet =
      AmazonSNSAsyncClientBuilder.standard()
          .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
          .withRegion(S3Constants.getDefaultRegionName())
          .build();

  public static AmazonSNSAsync getAwsSnsClient() {
    return snsClinet;
  }

  @PreDestroy
  public void cleanUp() {
    try {
      if (snsClinet != null) {
        snsClinet.shutdown();
      }
      AwsSdkMetrics.unregisterMetricAdminMBean();
    } catch (Exception e) {
      LOGGER.error("Error in shutting down snsClient ", e);
    }
  }
}
