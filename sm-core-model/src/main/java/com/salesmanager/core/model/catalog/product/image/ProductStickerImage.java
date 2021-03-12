package com.salesmanager.core.model.catalog.product.image;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import java.io.InputStream;
import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_STICKER_IMAGE", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class ProductStickerImage extends SalesManagerEntity<Long, ProductStickerImage> {
  private static final long serialVersionUID = 247514890386076337L;

  @Id
  @Column(name = "PRODUCT_STICKER_IMAGE_ID")
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "PRODUCT_STICKER_IMG_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "SKU_ID", unique = true)
  private String skuId;

  @Column(name = "BADGE_ICON")
  private String badgeIcon;

  @Column(name = "DEFAULT_IMAGE")
  private boolean defaultImage = true;

  @Column(name = "BADGE_ICON_URL")
  private String badgeIconUrl;

  @Column(name = "BADGE_TEXT")
  private String badgeText;

  @Column(name = "BADGE_COLOR_CODE")
  private String badgeColorCode;

  /** default to 0 for images managed by the system */
  @Column(name = "IMAGE_TYPE")
  private int imageType;

  @Column(name = "IMAGE_CROP")
  private boolean imageCrop;

  @Transient private InputStream image = null;

  // private MultiPartFile image

  public ProductStickerImage() {}

  public boolean isDefaultImage() {
    return defaultImage;
  }

  public void setDefaultImage(boolean defaultImage) {
    this.defaultImage = defaultImage;
  }

  public int getImageType() {
    return imageType;
  }

  public void setImageType(int imageType) {
    this.imageType = imageType;
  }

  public boolean isImageCrop() {
    return imageCrop;
  }

  public void setImageCrop(boolean imageCrop) {
    this.imageCrop = imageCrop;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public InputStream getImage() {
    return image;
  }

  public void setImage(InputStream image) {
    this.image = image;
  }

  public String getSkuId() {
    return skuId;
  }

  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }

  public String getBadgeIcon() {
    return badgeIcon;
  }

  public void setBadgeIcon(String badgeIcon) {
    this.badgeIcon = badgeIcon;
  }

  public String getBadgeIconUrl() {
    return badgeIconUrl;
  }

  public void setBadgeIconUrl(String badgeIconUrl) {
    this.badgeIconUrl = badgeIconUrl;
  }

  public String getBadgeText() {
    return badgeText;
  }

  public void setBadgeText(String badgeText) {
    this.badgeText = badgeText;
  }

  public String getBadgeColorCode() {
    return badgeColorCode;
  }

  public void setBadgeColorCode(String badgeColorCode) {
    this.badgeColorCode = badgeColorCode;
  }
}
