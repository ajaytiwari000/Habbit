package com.salesmanager.shop.store.api.v1.product;

import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.image.ProductImageService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.product.ProductImageMapper;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductImage;
import com.salesmanager.shop.model.catalog.product.attribute.ProductImageList;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api/v1")
public class ProductImageApi {

  @Inject private ProductImageService productImageService;

  @Inject private ProductService productService;
  @Inject private ProductImageMapper productImageMapper;

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductImageApi.class);

  /**
   * @param id
   * @param files
   * @param merchantStore
   * @param language
   */
  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(
      value = {"/private/products/{id}/images", "/auth/products/{id}/images"},
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
      method = RequestMethod.POST)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public void uploadImages(
      @PathVariable Long id,
      @RequestParam(value = "file[]", required = true) MultipartFile[] files,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language) {

    try {

      // get the product
      Product product = productService.getById(id);
      if (product == null) {
        throw new ResourceNotFoundException("Product not found");
      }

      // security validation
      // product belongs to merchant store
      if (product.getMerchantStore().getId().intValue() != merchantStore.getId().intValue()) {
        throw new UnauthorizedException("Resource not authorized for this merchant");
      }

      boolean hasDefaultImage = false;
      Set<ProductImage> images = product.getImages();
      if (!CollectionUtils.isEmpty(images)) {
        for (ProductImage image : images) {
          if (image.isDefaultImage()) {
            hasDefaultImage = true;
            break;
          }
        }
      }

      List<ProductImage> contentImagesList = new ArrayList<ProductImage>();

      for (MultipartFile multipartFile : files) {
        if (!multipartFile.isEmpty()) {
          ProductImage productImage = new ProductImage();
          productImage.setImage(multipartFile.getInputStream());
          productImage.setProductImage(multipartFile.getOriginalFilename());
          productImage.setProduct(product);

          if (!hasDefaultImage) {
            productImage.setDefaultImage(true);
            hasDefaultImage = true;
          }

          contentImagesList.add(productImage);
        }
      }

      if (CollectionUtils.isNotEmpty(contentImagesList)) {
        productImageService.addProductImages(product, contentImagesList);
      }

    } catch (Exception e) {
      LOGGER.error("Error while creating ProductImage", e);
      throw new ServiceRuntimeException("Error while creating image");
    }
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/products/images/{id}", "/auth/products/images/{id}"},
      method = RequestMethod.DELETE)
  public void deleteImage(
      @PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    try {
      ProductImage productImage = productImageService.getById(id);
      if (productImage.isDefaultImage()) {
        List<PersistableProductImage> imageList =
            Optional.ofNullable(
                    productImageService.getProductImagesByProductId(
                        productImage.getProduct().getId()))
                .map(Collection::stream)
                .orElseGet(Stream::empty)
                .map(productImageMapper::toPersistableProductImage)
                .collect(Collectors.toList());
        if (imageList.size() > 1) {
          throw new Exception("Can't remove primary image till you have secondary image.");
        }
      }
      if (productImage != null) {
        productImageService.removeProductImage(productImage);
        productImageService.delete(productImage);
      } else {
        response.sendError(404, "No ProductImage found for ID : " + id);
      }

    } catch (Exception e) {
      LOGGER.error("Error while deleting ProductImage", e);
      try {
        response.sendError(503, "Error while deleting ProductImage " + e.getMessage());
      } catch (Exception ignore) {
      }
    }
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = {"/private/products/{skuId}/images"},
      method = RequestMethod.GET)
  @ApiOperation(value = "Get Product images by skuId")
  public @ResponseBody ProductImageList getImages(
      @PathVariable String skuId, HttpServletRequest request, HttpServletResponse response) {
    ProductImageList productImages = new ProductImageList();
    try {
      productImages.setProductImageList(
          Optional.ofNullable(productImageService.getProductImagesByProductSkuId(skuId))
              .map(Collection::stream)
              .orElseGet(Stream::empty)
              .map(productImageMapper::toPersistableProductImage)
              .collect(Collectors.toList()));
    } catch (Exception e) {
      LOGGER.error("Error while getting Product Images", e);
    }
    return productImages;
  }
}
