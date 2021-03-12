package com.salesmanager.core.business.modules.cms.products.productsticker.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.impl.CMSManager;
import com.salesmanager.core.business.services.aws.s3.AwsS3ClientService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.image.ProductStickerImage;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.content.OutputContentFile;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class S3ProductStickerContentFileManager
    implements com.salesmanager.core.business.modules.cms.products.productsticker
            .ProductStickerAssetsManager<
        Product, ProductStickerImage> {

  @Value("${config.cms.aws.bucket}")
  private String DEFAULT_BUCKET_NAME;

  @Inject AwsS3ClientService awsS3ClientService;

  private static final long serialVersionUID = 123456L;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(S3ProductStickerContentFileManager.class);

  private static S3ProductStickerContentFileManager fileManager;

  //  private static String DEFAULT_BUCKET_NAME = "habbitcontent";

  private static final String ROOT_NAME = "ProductSticker";

  private CMSManager cmsManager;
  // private static final AmazonS3 s3Client = AwsS3ClientServiceImpl.getAwsS3Client();

  public static S3ProductStickerContentFileManager getInstance() {
    if (fileManager == null) {
      fileManager = new S3ProductStickerContentFileManager();
    }
    return fileManager;
  }

  @Override
  public OutputContentFile getImage(String key) throws ServiceException {
    return null;
  }

  @Override
  public OutputContentFile getImage(Product p) throws ServiceException {
    return null;
  }

  @Override
  public OutputContentFile getImageFromChildEntity(ProductStickerImage c) throws ServiceException {
    return null;
  }

  @Override
  public void addImage(ProductStickerImage productStickerImage, ImageContentFile contentImage)
      throws ServiceException {
    AmazonS3 s3Client = awsS3ClientService.getAwsS3Client();
    try { // get buckets
      String bucketName = bucketName();
      String nodePath = this.nodePath(productStickerImage.getBadgeText());
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType(contentImage.getMimeType());
      PutObjectRequest request =
          new PutObjectRequest(
              bucketName, nodePath + contentImage.getFileName(), contentImage.getFile(), metadata);
      request.setCannedAcl(CannedAccessControlList.PublicRead);
      s3Client.putObject(request);
      productStickerImage.setBadgeIconUrl(
          s3Client.getUrl(bucketName, nodePath + contentImage.getFileName()).toString());
      LOGGER.info(
          "PackIcon added successfully for productsticker -{}", productStickerImage.getBadgeText());
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

  public CMSManager getCmsManager() {
    return cmsManager;
  }

  private String nodePath(String value) {
    return new StringBuilder()
        .append(ROOT_NAME)
        .append(Constants.SLASH)
        .append(value)
        .append(Constants.SLASH)
        .toString();
  }

  @Override
  public void removeImage(ProductStickerImage productStickerImage) throws ServiceException {
    try {
      AmazonS3 s3Client = awsS3ClientService.getAwsS3Client();
      // get buckets
      String bucketName = bucketName();
      s3Client.deleteObject(
          bucketName,
          nodePath(productStickerImage.getBadgeText()) + productStickerImage.getBadgeIcon());
      LOGGER.info("Removed Sticker file");
    } catch (final Exception e) {
      LOGGER.error("Error while removing file", e);
      throw new ServiceException(e);
    }
  }

  public void setCmsManager(CMSManager cmsManager) {
    this.cmsManager = cmsManager;
  }
}
