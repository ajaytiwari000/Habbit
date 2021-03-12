package com.salesmanager.core.business.services.aws.sqs;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.salesmanager.core.business.services.aws.s3.constants.S3Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Service
public class AwsSqsClient {
  private static final Logger LOGGER = LoggerFactory.getLogger(AwsSqsClient.class);

  private static final AWSCredentials awsCredentials =
      new BasicAWSCredentials(S3Constants.getAwsAccessKeyId(), S3Constants.getAwsSecretKeyId());
  private static final AmazonSQSAsync sqsClinet =
      AmazonSQSAsyncClientBuilder.standard()
          .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
          .withRegion(S3Constants.getDefaultRegionName())
          .build();

  public static AmazonSQSAsync getAwsSqsClient() {
    return sqsClinet;
  }

  @Bean
  public SqsAsyncClient getSqsAsyncClient() {
    return SqsAsyncClient.builder()
        .region(Region.of(S3Constants.getDefaultRegionName()))
        .credentialsProvider(
            StaticCredentialsProvider.create(
                new AwsCredentials() {
                  @Override
                  public String accessKeyId() {
                    return S3Constants.getAwsAccessKeyId();
                  }

                  @Override
                  public String secretAccessKey() {
                    return S3Constants.getAwsSecretKeyId();
                  }
                }))
        .build();
  }
}
