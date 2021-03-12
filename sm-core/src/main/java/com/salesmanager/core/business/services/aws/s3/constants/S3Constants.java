package com.salesmanager.core.business.services.aws.s3.constants;

import org.springframework.stereotype.Component;

@Component
public class S3Constants {
  //  public static String AWS_DEFAULT_REGION_NAME;
  //
  //  @Value("${config.cms.aws.region}")
  //  public void setRegion(String region) {
  //    AWS_DEFAULT_REGION_NAME = region;
  //  }
  //
  //  public static String AWS_CONFIG_ACCESS_KEY_ID;
  //
  //  @Value("${aws.access.key}")
  //  public void setAwsAccessKeyId(String awsAccessKeyId) {
  //    AWS_CONFIG_ACCESS_KEY_ID = awsAccessKeyId;
  //  }
  //
  //  public static String AWS_CONFIG_SECRET_KEY_ID;
  //
  //  @Value("${aws.secret.key}")
  //  public void setAwsSecretKeyIdKeyId(String awsSecretKeyIdKeyId) {
  //    AWS_CONFIG_SECRET_KEY_ID = awsSecretKeyIdKeyId;
  //  }

  private static String DEFAULT_REGION_NAME = "ap-south-1";
  private static String AWS_ACCESS_KEY_ID = "AKIASNGVRWBOBHCM2S7Q";
  private static String AWS_SECRET_KEY_ID = "9x44Fm9wxgwl3n4plO6e8gQ0b08qsOPH4vVaPFnv";

  //  private static String DEFAULT_REGION_NAME = "ap-southeast-1";
  //  private static String AWS_ACCESS_KEY_ID = "AKIAJK7F2FL46CF5OVMA";
  //  private static String AWS_SECRET_KEY_ID = "1xNPgrEFI4He/w2bjxpljyDrvVY8uZsN61l0kctM";

  public static String getDefaultRegionName() {
    return DEFAULT_REGION_NAME;
  }

  public static String getAwsAccessKeyId() {
    return AWS_ACCESS_KEY_ID;
  }

  public static String getAwsSecretKeyId() {
    return AWS_SECRET_KEY_ID;
  }
}
