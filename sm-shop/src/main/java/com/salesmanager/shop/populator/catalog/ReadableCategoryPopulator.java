package com.salesmanager.shop.populator.catalog;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.catalog.product.PricingService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.NutrientsFact;
import com.salesmanager.core.model.catalog.product.NutrientsInfo;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.product.CategoryReadableBannerMapper;
import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.catalog.product.ReadableNutrientsFact;
import com.salesmanager.shop.model.catalog.product.ReadableNutrientsInfo;
import com.salesmanager.shop.model.catalog.product.ReadableNutritionalInfo;
import com.salesmanager.shop.utils.ImageFilePath;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;

@Component
public class ReadableCategoryPopulator extends AbstractDataPopulator<Category, ReadableCategory> {

  @Inject private CategoryReadableBannerMapper categoryReadableBannerMapper;
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

  public ReadableCategoryPopulator() {}

  @Override
  public ReadableCategory populate(
      Category source, ReadableCategory readableCategory, MerchantStore store, Language language)
      throws ConversionException {

    try {
      if (Objects.nonNull(source) && Objects.nonNull(source.getCategoryDetails())) {
        readableCategory.setId(source.getId());
        readableCategory.setName(source.getCategoryDetails().getName());
        readableCategory.setDetailDescription(source.getCategoryDetails().getDetailDescription());
        // Nutrition
        if (Objects.nonNull(source.getCategoryDetails().getNutritionalInfo())) {
          ReadableNutritionalInfo readableNutritionalInfo = new ReadableNutritionalInfo();
          readableNutritionalInfo.setAbout(
              source.getCategoryDetails().getNutritionalInfo().getNutritionalDescription());
          List<ReadableNutrientsInfo> readableNutrientsInfoList = new ArrayList<>();
          List<ReadableNutrientsFact> readableNutrientsFactList = new ArrayList<>();
          for (NutrientsInfo nutrientsInfo :
              source.getCategoryDetails().getNutritionalInfo().getNutrientsInfo()) {
            ReadableNutrientsInfo readableNutrientsInfo = new ReadableNutrientsInfo();
            readableNutrientsInfo.setImgUrl(nutrientsInfo.getImgUrl());
            readableNutrientsInfo.setDescription(nutrientsInfo.getDescription());
            readableNutrientsInfo.setOrderNumber(nutrientsInfo.getOrderNumber());
            readableNutrientsInfoList.add(readableNutrientsInfo);
          }
          Collections.sort(readableNutrientsInfoList);
          readableNutritionalInfo.setReadableNutrientsInfoList(readableNutrientsInfoList);
          for (NutrientsFact nutrientsFact :
              source.getCategoryDetails().getNutritionalInfo().getNutrientsFacts()) {
            ReadableNutrientsFact readableNutrientsFact = new ReadableNutrientsFact();
            readableNutrientsFact.setName(nutrientsFact.getName());
            readableNutrientsFact.setValue(nutrientsFact.getContentValue());
            readableNutrientsFact.setOrderNumber(nutrientsFact.getOrderNumber());
            readableNutrientsFactList.add(readableNutrientsFact);
          }
          Collections.sort(readableNutrientsFactList);
          readableNutritionalInfo.setReadableNutrientsFactsList(readableNutrientsFactList);
          readableCategory.setReadableNutritionalInfoList(readableNutritionalInfo);
        }
      }
      return readableCategory;

    } catch (Exception e) {
      throw new ConversionException(e);
    }
  }

  public ReadableCategory populateCategorySelected(
      Category source, ReadableCategory readableCategory, MerchantStore store, Language language)
      throws ConversionException {
    try {
      if (Objects.nonNull(source) && Objects.nonNull(source.getCategoryDetails())) {
        readableCategory.setId(source.getId());
        readableCategory.setName(source.getCategoryDetails().getName());
        readableCategory.setDescription(source.getCategoryDetails().getDescription());
      }
      return readableCategory;
    } catch (Exception e) {
      throw new ConversionException(e);
    }
  }

  public ReadableCategory populateFromProduct(
      Product source, ReadableCategory readableCategory, MerchantStore store, Language language)
      throws ConversionException {
    Validate.notNull(pricingService, "Requires to set PricingService");
    Validate.notNull(imageUtils, "Requires to set imageUtils");
    try {

      if (!CollectionUtils.isEmpty(source.getCategories())) {
        for (Category category : source.getCategories()) { // for one iteration only
          readableCategory.setId(category.getId());
          readableCategory.setName(category.getCategoryDetails().getName());
          readableCategory.setDescription(category.getCategoryDetails().getDescription());
          readableCategory.setCategoryBannerList(
              Optional.ofNullable(category.getCategoryDetails().getCategoryBanners())
                  .map(Collection::stream)
                  .orElseGet(Stream::empty)
                  .map(categoryReadableBannerMapper::toReadableCategoryBanner)
                  .collect(Collectors.toList()));
          break;
        }
      }
      return readableCategory;

    } catch (Exception e) {
      throw new ConversionException(e);
    }
  }

  public ReadableCategory populateFromMerchandisesProduct(
      Product source, ReadableCategory readableCategory, MerchantStore store, Language language)
      throws ConversionException {
    Validate.notNull(pricingService, "Requires to set PricingService");
    Validate.notNull(imageUtils, "Requires to set imageUtils");
    try {

      if (!CollectionUtils.isEmpty(source.getCategories())) {
        for (Category category : source.getCategories()) { // for one iteration only
          readableCategory.setId(category.getId());
          readableCategory.setName(category.getCategoryDetails().getName());
          readableCategory.setDescription(category.getCategoryDetails().getDescription());
          readableCategory.setCategoryBannerList(
              Optional.ofNullable(category.getCategoryDetails().getCategoryBanners())
                  .map(Collection::stream)
                  .orElseGet(Stream::empty)
                  .map(categoryReadableBannerMapper::toReadableCategoryBanner)
                  .collect(Collectors.toList()));
          break;
        }
      }
      return readableCategory;

    } catch (Exception e) {
      throw new ConversionException(e);
    }
  }

  @Override
  protected ReadableCategory createTarget() {
    return null;
  }
}
