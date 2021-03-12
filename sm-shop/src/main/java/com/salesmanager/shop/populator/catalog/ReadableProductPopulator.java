package com.salesmanager.shop.populator.catalog;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.catalog.product.PricingService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.utils.ImageFilePath;
import java.util.Objects;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ReadableProductPopulator extends AbstractDataPopulator<Product, ReadableProduct> {

  private PricingService pricingService;
  private ImageFilePath imageUtils;

  public PricingService getPricingService() {
    return pricingService;
  }

  public void setPricingService(PricingService pricingService) {
    this.pricingService = pricingService;
  }

  public ImageFilePath getImageUtils() {
    return imageUtils;
  }

  public void setImageUtils(ImageFilePath imageUtils) {
    this.imageUtils = imageUtils;
  }

  /**
   * This populator is used to fetch the product detail for getAllCategoryProductLists api
   *
   * @param source
   * @param target
   * @param store
   * @param language
   * @return
   * @throws ConversionException
   */
  @Override
  public ReadableProduct populate(
      Product source, ReadableProduct target, MerchantStore store, Language language)
      throws ConversionException {
    // TO DO
    // need to explore the pricingService to get the product price
    Validate.notNull(pricingService, "Requires to set PricingService");
    Validate.notNull(imageUtils, "Requires to set imageUtils");
    try {
      if (Objects.nonNull(source)) {
        // TODO pricing service
        // FinalPrice price = pricingService.calculateProductPrice(source);
        target.setId(source.getId());
        target.setSkuId(source.getSku());
        target.setPremimumCard(source.isProductPremiumCard());
        if (Objects.nonNull(source.getProductStickerImage())) {
          target.setBadgeColorCode(source.getProductStickerImage().getBadgeColorCode());
          target.setBadgeIconUrl(source.getProductStickerImage().getBadgeIconUrl());
          target.setBadgeText(source.getProductStickerImage().getBadgeText());
        }
        target.setBoostAvailable(source.isBoostAvailable());
        // TODO pricing service
        //        if (price != null) {
        //          target.setActualPrice(
        //              Long.parseLong(pricingService.getDisplayAmount(price.getOriginalPrice(),
        // store)));
        //          target.setMrp(
        //              Long.parseLong(pricingService.getDisplayAmount(price.getFinalPrice(),
        // store)));
        //        }
        target.setName(source.getName());
        // flavour
        if (Objects.nonNull(source.getFlavour())) {
          target.setFlavourName(source.getFlavour().getName());
        }
        if (Objects.nonNull(source.getPackSize())) {
          target.setPackName(source.getPackSize().getPackSizeValue());
        }
        target.setDisplayPrice(source.getDisplayPrice().longValue());
        // images
        if (!CollectionUtils.isEmpty(source.getImages())) {
          for (ProductImage image : source.getImages()) {
            if (image.isDefaultImage()) {
              target.setPrimaryImage(image.getProductImageUrl());
              break;
            }
          }
        }
      }
      return target;
    } catch (Exception e) {
      throw new ConversionException(e);
    }
  }

  /**
   * This populator is to fetch the product details for
   * getFilteredProductLists,getCategoryProductLists apis
   *
   * @param source
   * @param target
   * @param store
   * @param language
   * @return
   * @throws ConversionException
   */
  public ReadableProduct populateFilteredProduct(
      Product source, ReadableProduct target, MerchantStore store, Language language)
      throws ConversionException {
    // TO DO
    // need to explore the pricingService to get the product price
    Validate.notNull(pricingService, "Requires to set PricingService");
    Validate.notNull(imageUtils, "Requires to set imageUtils");
    try {
      if (Objects.nonNull(source)) {
        // TODO pricing service
        // FinalPrice price = pricingService.calculateProductPrice(source);
        target.setId(source.getId());
        target.setSkuId(source.getSku());
        if (Objects.nonNull(source.getProductStickerImage())) {
          target.setBadgeColorCode(source.getProductStickerImage().getBadgeColorCode());
          target.setBadgeIconUrl(source.getProductStickerImage().getBadgeIconUrl());
          target.setBadgeText(source.getProductStickerImage().getBadgeText());
        }
        target.setBoostAvailable(source.isBoostAvailable());
        // TODO pricing service
        //        if (price != null) {
        //          target.setActualPrice(
        //              Long.parseLong(pricingService.getDisplayAmount(price.getOriginalPrice(),
        // store)));
        //          target.setDisplayPrice(
        //              Long.parseLong(pricingService.getDisplayAmount(price.getFinalPrice(),
        // store)));
        //        }
        // flavour
        if (Objects.nonNull(source.getFlavour())) {
          target.setFlavourName(source.getFlavour().getName());
        }
        if (Objects.nonNull(source.getPackSize())) {
          target.setPackName(source.getPackSize().getPackSizeValue());
        }
        target.setDisplayPrice(source.getDisplayPrice().longValue());
        target.setName(source.getName());
        // images
        if (!CollectionUtils.isEmpty(source.getImages())) {
          for (ProductImage image : source.getImages()) {
            if (image.isDefaultImage()) {
              target.setPrimaryImage(image.getProductImageUrl());
              break;
            }
          }
        }
      }
      return target;
    } catch (Exception e) {
      throw new ConversionException(e);
    }
  }

  @Override
  protected ReadableProduct createTarget() {
    return null;
  }
}
