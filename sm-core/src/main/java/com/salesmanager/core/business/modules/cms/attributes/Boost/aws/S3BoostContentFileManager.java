package com.salesmanager.core.business.modules.cms.attributes.Boost.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.attributes.Boost.BoostAssetsManager;
import com.salesmanager.core.business.modules.cms.impl.CMSManager;
import com.salesmanager.core.business.services.aws.s3.AwsS3ClientService;
import com.salesmanager.core.model.catalog.product.Boost;
import com.salesmanager.core.model.catalog.product.BoostIcon;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.content.OutputContentFile;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class S3BoostContentFileManager implements BoostAssetsManager<Boost, BoostIcon> {

  @Inject AwsS3ClientService awsS3ClientService;

  @Value("${config.cms.aws.bucket}")
  private String DEFAULT_BUCKET_NAME;

  private static final long serialVersionUID = 123456L;

  private static final Logger LOGGER = LoggerFactory.getLogger(S3BoostContentFileManager.class);

  private static S3BoostContentFileManager fileManager;

  //  private static String DEFAULT_BUCKET_NAME = "habbitcontent";
  private static final String ROOT_NAME = "boost";

  private CMSManager cmsManager;
  // private static final AmazonS3 s3Client = AwsS3ClientServiceImpl.getAwsS3Client();

  public static S3BoostContentFileManager getInstance() {
    if (fileManager == null) {
      fileManager = new S3BoostContentFileManager();
    }
    return fileManager;
  }

  @Override
  public OutputContentFile getImage(String key) throws ServiceException {
    return null;
  }

  @Override
  public OutputContentFile getImage(Boost p) throws ServiceException {
    return null;
  }

  @Override
  public OutputContentFile getImageFromChildEntity(BoostIcon c) throws ServiceException {
    return null;
  }

  @Override
  public void addImage(BoostIcon boostIcon, ImageContentFile contentImage) throws ServiceException {
    try {
      AmazonS3 s3Client = awsS3ClientService.getAwsS3Client();
      // get buckets
      String bucketName = bucketName();
      String nodePath = this.nodePath(boostIcon.getBoostName());
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType(contentImage.getMimeType());
      PutObjectRequest request =
          new PutObjectRequest(
              bucketName, nodePath + contentImage.getFileName(), contentImage.getFile(), metadata);
      request.setCannedAcl(CannedAccessControlList.PublicRead);
      s3Client.putObject(request);
      boostIcon.setIconUrl(
          s3Client.getUrl(bucketName, nodePath + contentImage.getFileName()).toString());
      LOGGER.info("BoostIcon added successfully for Boost -{}", boostIcon.getBoostName());
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

  private String nodePath(String boostName) {
    return new StringBuilder()
        .append(ROOT_NAME)
        .append(Constants.SLASH)
        .append(boostName)
        .append(Constants.SLASH)
        .toString();
  }

  @Override
  public void removeImage(BoostIcon boostIcon) throws ServiceException {
    try {
      AmazonS3 s3Client = awsS3ClientService.getAwsS3Client();
      // get buckets
      String bucketName = bucketName();
      s3Client.deleteObject(bucketName, nodePath(boostIcon.getBoostName()) + boostIcon.getIcon());
      LOGGER.info("Removed Boost file");
    } catch (final Exception e) {
      LOGGER.error("Error while removing file", e);
      throw new ServiceException(e);
    }
  }

  public CMSManager getCmsManager() {
    return cmsManager;
  }

  public void setCmsManager(CMSManager cmsManager) {
    this.cmsManager = cmsManager;
  }
}
