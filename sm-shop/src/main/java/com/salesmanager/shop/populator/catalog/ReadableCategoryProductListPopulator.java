package com.salesmanager.shop.populator.catalog;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.catalog.product.PricingService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.category.ReadableCategoryProductsList;
import com.salesmanager.shop.utils.ImageFilePath;
import javax.inject.Inject;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;

@Component
public class ReadableCategoryProductListPopulator
    extends AbstractDataPopulator<Product, ReadableCategoryProductsList> {

  @Inject private ReadableCategoryPopulator readableCategoryPopulator;

  @Inject private ReadableProductDetailPopulator readableProductDetailPopulator;

  private PricingService pricingService;

  public ImageFilePath getImageUtils() {
    return imageUtils;
  }

  public void setImageUtils(ImageFilePath imageUtils) {
    this.imageUtils = imageUtils;
  }

  private ImageFilePath imageUtils;

  public PricingService getPricingService() {
    return pricingService;
  }

  public void setPricingService(PricingService pricingService) {
    this.pricingService = pricingService;
  }

  public ReadableCategoryProductListPopulator() {}

  @Override
  public ReadableCategoryProductsList populate(
      Product source,
      ReadableCategoryProductsList readableCategory,
      MerchantStore store,
      Language language)
      throws ConversionException {
    Validate.notNull(pricingService, "Requires to set PricingService");
    Validate.notNull(imageUtils, "Requires to set imageUtils");

    return null;
  }

  @Override
  protected ReadableCategoryProductsList createTarget() {
    return null;
  }
}
