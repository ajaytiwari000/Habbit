package com.salesmanager.core.business.modules.cms.category.categoryBanner.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.category.categoryBanner.CategoryAssetManager;
import com.salesmanager.core.business.modules.cms.impl.CMSManager;
import com.salesmanager.core.business.services.aws.s3.AwsS3ClientService;
import com.salesmanager.core.model.catalog.category.CategoryBanner;
import com.salesmanager.core.model.catalog.category.CategoryDetails;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.content.OutputContentFile;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class S3CategoryContentFileManager
    implements CategoryAssetManager<CategoryDetails, CategoryBanner> {

  @Value("${config.cms.aws.bucket}")
  private String DEFAULT_BUCKET_NAME;

  @Inject AwsS3ClientService awsS3ClientService;

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(S3CategoryContentFileManager.class);

  private static S3CategoryContentFileManager fileManager = null;

  //  private static String DEFAULT_BUCKET_NAME = "habbitcontent";

  private static final String ROOT_NAME = "category";

  private CMSManager cmsManager;

  // private static final AmazonS3 s3Client = AwsS3ClientServiceImpl.getAwsS3Client();

  public static S3CategoryContentFileManager getInstance() {

    if (fileManager == null) {
      fileManager = new S3CategoryContentFileManager();
    }

    return fileManager;
  }

  @Override
  public OutputContentFile getImage(String key) throws ServiceException {
    return null;
  }

  @Override
  public OutputContentFile getImage(CategoryDetails p) throws ServiceException {
    return null;
  }

  @Override
  public OutputContentFile getImageFromChildEntity(CategoryBanner c) throws ServiceException {
    return null;
  }

  @Override
  public void addImage(CategoryBanner categoryBanner, ImageContentFile contentImage)
      throws ServiceException {
    AmazonS3 s3Client = awsS3ClientService.getAwsS3Client();
    try {
      // get buckets
      String bucketName = bucketName();
      String nodePath = this.nodePath(categoryBanner.getCategoryName());
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType(contentImage.getMimeType());
      PutObjectRequest request =
          new PutObjectRequest(
              bucketName, nodePath + contentImage.getFileName(), contentImage.getFile(), metadata);
      request.setCannedAcl(CannedAccessControlList.PublicRead);
      s3Client.putObject(request);
      categoryBanner.setBannerImageUrl(
          s3Client.getUrl(bucketName, nodePath + contentImage.getFileName()).toString());
      LOGGER.info(
          "CategoryImage added successfully for Category -{}", categoryBanner.getCategoryName());
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

  private String nodePath(String categoryImage) {
    return new StringBuilder()
        .append(ROOT_NAME)
        .append(Constants.SLASH)
        .append(categoryImage)
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
  public void removeImage(CategoryBanner categoryBanner) throws ServiceException {
    AmazonS3 s3Client = awsS3ClientService.getAwsS3Client();
    try {
      // get buckets
      String bucketName = bucketName();
      s3Client.deleteObject(
          bucketName, nodePath(categoryBanner.getCategoryName()) + categoryBanner.getBannerImage());
      LOGGER.info("Removed Category file");
    } catch (final Exception e) {
      LOGGER.error("Error while removing file", e);
      throw new ServiceException(e);
    }
  }

  @Override
  public void removeImages(CategoryDetails categoryDetails) throws ServiceException {}
}
