package com.salesmanager.core.business.services.aws.s3;

import com.amazonaws.services.s3.AmazonS3;

public interface AwsS3ClientService {
  AmazonS3 getAwsS3Client();
}
