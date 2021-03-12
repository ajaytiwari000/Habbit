package com.salesmanager.core.business.repositories.customer.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.impl.CMSManager;
import com.salesmanager.core.business.services.aws.s3.AwsS3ClientService;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.customer.Customer;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class S3CustomerImageFileManager implements CustomerAssetsManager<Customer> {
  @Value("${config.cms.aws.bucket}")
  private String DEFAULT_BUCKET_NAME;

  @Inject AwsS3ClientService awsS3ClientService;

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(S3CustomerImageFileManager.class);

  private static S3CustomerImageFileManager fileManager = null;

  // private static String DEFAULT_BUCKET_NAME = "habbitcontent";

  private static final String ROOT_NAME = "customer";

  private CMSManager cmsManager;

  // private static final AmazonS3 s3Client = AwsS3ClientServiceImpl.getAwsS3Client();

  public static S3CustomerImageFileManager getInstance() {

    if (fileManager == null) {
      fileManager = new S3CustomerImageFileManager();
    }

    return fileManager;
  }

  @Override
  public void addImage(Customer customer, ImageContentFile contentImage) throws ServiceException {
    try {
      AmazonS3 s3Client = awsS3ClientService.getAwsS3Client();
      // get buckets
      String bucketName = bucketName();
      String nodePath = this.nodePath(customer.getUserName());
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType(contentImage.getMimeType());
      PutObjectRequest request =
          new PutObjectRequest(
              bucketName, nodePath + contentImage.getFileName(), contentImage.getFile(), metadata);
      request.setCannedAcl(CannedAccessControlList.PublicRead);
      s3Client.putObject(request);
      customer.setImageUrl(
          s3Client.getUrl(bucketName, nodePath + contentImage.getFileName()).toString());
      LOGGER.info("Customer image added successfully for username -{}", customer.getUserName());
    } catch (final Exception e) {
      LOGGER.error("Error while uploading file", e);
      throw new ServiceException(e);
    }
  }

  private String bucketName() {
    String bucketName = getCmsManager().getRootName();
    if (StringUtils.isBlank(bucketName)) {
      bucketName = DEFAULT_BUCKET_NAME;
    }
    return bucketName;
  }

  private String nodePath(String image) {
    return new StringBuilder()
        .append(ROOT_NAME)
        .append(Constants.SLASH)
        .append(image)
        .append(Constants.SLASH)
        .toString();
  }

  public CMSManager getCmsManager() {
    return cmsManager;
  }

  public void setCmsManager(CMSManager cmsManager) {
    this.cmsManager = cmsManager;
  }

  @Override
  public void removeImage(Customer customer) throws ServiceException {
    try {
      AmazonS3 s3Client = awsS3ClientService.getAwsS3Client();
      // get buckets
      String bucketName = bucketName();
      s3Client.deleteObject(bucketName, nodePath(customer.getUserName()) + customer.getImage());
      LOGGER.info("Removed customer image ");
    } catch (final Exception e) {
      LOGGER.error("Error while customer image", e);
      throw new ServiceException(e);
    }
  }
}
