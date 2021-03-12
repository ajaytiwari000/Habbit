package com.salesmanager.shop.populator.catalog;

import com.salesmanager.core.business.HabbitCoreConstant;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.catalog.product.PricingService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategoryReview;
import com.salesmanager.core.model.catalog.product.*;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.product.CategoryReviewMapper;
import com.salesmanager.shop.model.catalog.product.*;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableBoost;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableCategoryReview;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableFlavour;
import com.salesmanager.shop.model.catalog.product.attribute.ReadablePack;
import com.salesmanager.shop.utils.ImageFilePath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ReadableProductDetailPopulator
    extends AbstractDataPopulator<Product, ReadableProductDetails> {
  @Inject CategoryReviewMapper categoryReviewMapper;
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
   * This populator is used to fetch the details for getProductDetail api
   *
   * @param source
   * @param target
   * @param store
   * @param language
   * @return
   * @throws ConversionException
   */
  @Override
  public ReadableProductDetails populate(
      Product source, ReadableProductDetails target, MerchantStore store, Language language)
      throws ConversionException {
    // TO DO
    // need to explore the pricingService to get the product price
    Validate.notNull(pricingService, "Requires to set PricingService");
    Validate.notNull(imageUtils, "Requires to set imageUtils");

    try {
      if (target == null) {
        target = new ReadableProductDetails();
      }
      target.setId(source.getId());
      target.setSkuId(source.getSku());
      target.setName(source.getName());
      target.setDescription(source.getDescription());
      target.setDetailDescription(source.getDetailDescription());

      // images
      if (!CollectionUtils.isEmpty(source.getImages())) {
        List<String> secondaryImgUrlList = new ArrayList<>();
        for (ProductImage image : source.getImages()) {
          if (image.isDefaultImage()) {
            target.setPrimaryImgUrl(image.getProductImageUrl());
          } else {
            secondaryImgUrlList.add(image.getProductImageUrl());
          }
        }
        target.setSecondaryImgUrlList(secondaryImgUrlList);
      }

      if (!CollectionUtils.isEmpty(source.getCategories())) {

        for (Category category : source.getCategories()) { // for one iteration only
          List<ReadableFlavour> flavourList = new ArrayList<ReadableFlavour>();

          target.setCategoryId(category.getId().toString());
          target.setCategoryName(category.getCategoryDetails().getName());

          // Available flavor
          for (Flavour flavour : category.getFlavours()) {
            ReadableFlavour readableFlavouritr = new ReadableFlavour();
            readableFlavouritr.setId(flavour.getId());
            readableFlavouritr.setColorCode(flavour.getPrimaryColor());
            readableFlavouritr.setColorCodeLight(flavour.getSecondaryColor());
            readableFlavouritr.setName(flavour.getName());
            flavourList.add(readableFlavouritr);
          }
          Collections.sort(flavourList);
          target.setAvailableFlavourList(flavourList);

          // Available pack
          List<ReadablePack> availablePackSizeList = new ArrayList<>();
          for (Pack pack : category.getPackSize()) {
            ReadablePack readablePack = new ReadablePack();
            readablePack.setId(pack.getId());
            readablePack.setPackSizeValue(pack.getPackSizeValue());
            if (Objects.nonNull(pack.getPackIcon())) {
              readablePack.setIconUrl(pack.getPackIcon().getIconUrl());
            }
            availablePackSizeList.add(readablePack);
          }
          Collections.sort(availablePackSizeList);
          target.setAvailablePackSizeList(availablePackSizeList);
          target.setDefaultPackSize(
              category.getCategoryDetails().getDefaultPackSize().getPackSizeValue());

          // review
          List<ReadableCategoryReview> categoryReviewList = new ArrayList<ReadableCategoryReview>();
          for (CategoryReview categoryReview : category.getCategoryDetails().getCategoryReviews()) {
            ReadableCategoryReview readableCategoryReview = new ReadableCategoryReview();
            readableCategoryReview.setName(categoryReview.getName());
            readableCategoryReview.setImageUrl(categoryReview.getImageUrl());
            readableCategoryReview.setAchievement(categoryReview.getAchievement());
            readableCategoryReview.setReview(categoryReview.getReview());
            readableCategoryReview.setReviewRating(categoryReview.getReviewRating());
            categoryReviewList.add(readableCategoryReview);
          }
          target.setReviewsList(categoryReviewList);
          break;
        }
      }

      // Available boost
      if (source.isBoostAvailable()) {
        if (!CollectionUtils.isEmpty(source.getCategories())) {
          for (Category category : source.getCategories()) { // for one iteration only
            List<ReadableBoost> boostList = new ArrayList<ReadableBoost>();
            for (Boost boost : category.getBoosts()) {
              ReadableBoost readableBoost = new ReadableBoost();
              readableBoost.setId(boost.getId());
              readableBoost.setPrice(boost.getPrice());
              readableBoost.setType(boost.getType());
              if (Objects.nonNull(boost.getBoostIcon())) {
                readableBoost.setIconUrl(boost.getBoostIcon().getIconUrl());
              }
              boostList.add(readableBoost);
            }
            Collections.sort(boostList);
            target.setAvailableBoostList(boostList);
            break;
          }
        }
      }

      // product pack
      ReadablePack readablePack = new ReadablePack();
      readablePack.setId(source.getPackSize().getId());
      readablePack.setPackSizeValue(source.getPackSize().getPackSizeValue());
      target.setPackSize(readablePack);

      // readableflavour product
      if (Objects.nonNull(source.getFlavour())) {
        ReadableFlavour readableFlavour = new ReadableFlavour();
        readableFlavour.setId(source.getFlavour().getId());
        readableFlavour.setName(source.getFlavour().getName());
        readableFlavour.setColorCode(source.getFlavour().getPrimaryColor());
        readableFlavour.setColorCodeLight(source.getFlavour().getSecondaryColor());
        target.setReadableFlavour(readableFlavour);
      }

      // price
      // we have to consider product availability product count
      // ToDo pricing
      //      FinalPrice price = pricingService.calculateProductPrice(source);
      //      if (price != null) {
      //        target.setPrice(price.getFinalPrice());
      //      }
      target.setPrice(source.getDisplayPrice().longValue());
      // Nutrition
      if (Objects.nonNull(source.getProductNutritionalInfo())) {
        ReadableNutritionalInfo readableNutritionalInfo = new ReadableNutritionalInfo();
        readableNutritionalInfo.setAbout(
            source.getProductNutritionalInfo().getNutritionalDescription());
        List<ReadableNutrientsInfo> readableNutrientsInfoList = new ArrayList<>();
        List<ReadableNutrientsFact> readableNutrientsFactList = new ArrayList<>();
        for (NutrientsInfo nutrientsInfo :
            source.getProductNutritionalInfo().getProductNutrientsInfo()) {
          ReadableNutrientsInfo readableNutrientsInfo = new ReadableNutrientsInfo();
          readableNutrientsInfo.setImgUrl(nutrientsInfo.getImgUrl());
          readableNutrientsInfo.setDescription(nutrientsInfo.getDescription());
          readableNutrientsInfo.setOrderNumber(nutrientsInfo.getOrderNumber());
          readableNutrientsInfoList.add(readableNutrientsInfo);
        }
        Collections.sort(readableNutrientsInfoList);
        readableNutritionalInfo.setReadableNutrientsInfoList(readableNutrientsInfoList);
        for (ProductNutrientsFact nutrientsFact :
            source.getProductNutritionalInfo().getProductNutrientsFacts()) {
          ReadableNutrientsFact readableNutrientsFact = new ReadableNutrientsFact();
          readableNutrientsFact.setName(nutrientsFact.getName());
          readableNutrientsFact.setValue(nutrientsFact.getContentValue());
          readableNutrientsFact.setOrderNumber(nutrientsFact.getOrderNumber());
          readableNutrientsFactList.add(readableNutrientsFact);
        }
        Collections.sort(readableNutrientsFactList);
        readableNutritionalInfo.setReadableNutrientsFactsList(readableNutrientsFactList);
        target.setReadableNutritionalInfo(readableNutritionalInfo);
      }
      return target;
    } catch (Exception e) {
      throw new ConversionException(e);
    }
  }

  public ReadableProductDetails populateRecommendedProductIList(
      ReadableProductDetails readableProduct, List<Product> recommendedProducts) {
    if (Objects.nonNull(recommendedProducts)) {
      List<ReadableProduct> recommendedProductList = new ArrayList<>();
      for (Product recommendedProduct : recommendedProducts) {
        if (!recommendedProduct
            .getCategories()
            .iterator()
            .next()
            .getCategoryDetails()
            .getName()
            .equals(HabbitCoreConstant.MERCHANDISE)) {
          ReadableProduct recommendedReadableProduct = new ReadableProduct();
          recommendedReadableProduct.setId(recommendedProduct.getId());
          recommendedReadableProduct.setName(recommendedProduct.getName());
          recommendedReadableProduct.setSkuId(recommendedProduct.getSku());
          if (Objects.nonNull(recommendedProduct.getFlavour())) {
            recommendedReadableProduct.setFlavourName(recommendedProduct.getFlavour().getName());
          }
          recommendedReadableProduct.setDisplayPrice(
              recommendedProduct.getDisplayPrice().longValue());
          if (Objects.nonNull(recommendedProduct.getProductStickerImage())) {
            recommendedReadableProduct.setBadgeColorCode(
                recommendedProduct.getProductStickerImage().getBadgeColorCode());
            recommendedReadableProduct.setBadgeIconUrl(
                recommendedProduct.getProductStickerImage().getBadgeIconUrl());
            recommendedReadableProduct.setBadgeText(
                recommendedProduct.getProductStickerImage().getBadgeText());
          }
          recommendedReadableProduct.setBoostAvailable(recommendedProduct.isBoostAvailable());
          if (!org.apache.commons.collections4.CollectionUtils.isEmpty(
              recommendedProduct.getImages()))
            recommendedReadableProduct.setPrimaryImage(
                recommendedProduct.getImages().iterator().next().getProductImageUrl());
          recommendedProductList.add(recommendedReadableProduct);
          if (recommendedProductList.size() > 5) {
            break;
          }
        }
      }
      readableProduct.setRecommendedProductList(recommendedProductList);
    }
    return readableProduct;
  }

  @Override
  protected ReadableProductDetails createTarget() {
    return null;
  }
}
