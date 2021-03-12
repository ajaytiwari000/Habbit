package com.salesmanager.shop.tags;

import com.salesmanager.core.business.services.catalog.product.PricingService;
import com.salesmanager.core.business.services.catalog.product.relationship.ProductRelationshipService;
import com.salesmanager.core.business.utils.CacheUtils;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.relationship.ProductRelationship;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.catalog.product.ReadableProducts;
import com.salesmanager.shop.populator.catalog.ReadableProductsPopulator;
import com.salesmanager.shop.utils.ImageFilePath;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

public class ShopProductRelationshipTag extends RequestContextAwareTag {

  /** */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(ShopProductRelationshipTag.class);

  @Inject private ProductRelationshipService productRelationshipService;

  @Inject private PricingService pricingService;

  @Inject private CacheUtils cache;

  @Inject
  @Qualifier("img")
  private ImageFilePath imageUtils;

  private String groupName;

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected int doStartTagInternal() throws Exception {
    if (productRelationshipService == null || pricingService == null || imageUtils == null) {
      LOGGER.debug("Autowiring ProductRelationshipService");
      WebApplicationContext wac = getRequestContext().getWebApplicationContext();
      AutowireCapableBeanFactory factory = wac.getAutowireCapableBeanFactory();
      factory.autowireBean(this);
    }

    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

    MerchantStore store = (MerchantStore) request.getAttribute(Constants.MERCHANT_STORE);

    Language language = (Language) request.getAttribute(Constants.LANGUAGE);

    StringBuilder groupKey = new StringBuilder();
    groupKey
        .append(store.getId())
        .append("_")
        .append(Constants.PRODUCTS_GROUP_CACHE_KEY)
        .append("-")
        .append(this.getGroupName())
        .append("_")
        .append(language.getCode());

    StringBuilder groupKeyMissed = new StringBuilder();
    groupKeyMissed.append(groupKey.toString()).append(Constants.MISSED_CACHE_KEY);

    List<ReadableProducts> objects = null;

    if (store.isUseCache()) {

      // get from the cache
      objects = (List<ReadableProducts>) cache.getFromCache(groupKey.toString());
      Boolean missedContent = null;

      if (objects == null && missedContent == null) {
        objects = getProducts(request);

        // put in cache
        cache.putInCache(objects, groupKey.toString());

      } else {
        // put in missed cache
        // cache.putInCache(new Boolean(true), groupKeyMissed.toString());
      }

    } else {
      objects = getProducts(request);
    }
    if (objects != null && objects.size() > 0) {
      request.setAttribute(this.getGroupName(), objects);
    }

    return SKIP_BODY;
  }

  public int doEndTag() {
    return EVAL_PAGE;
  }

  private List<ReadableProducts> getProducts(HttpServletRequest request) throws Exception {

    MerchantStore store = (MerchantStore) request.getAttribute(Constants.MERCHANT_STORE);
    Language language = (Language) request.getAttribute(Constants.LANGUAGE);

    List<ProductRelationship> relationships =
        productRelationshipService.getByGroup(store, this.getGroupName(), language);

    ReadableProductsPopulator populator = new ReadableProductsPopulator();
    populator.setPricingService(pricingService);
    populator.setimageUtils(imageUtils);

    List<ReadableProducts> products = new ArrayList<ReadableProducts>();
    for (ProductRelationship relationship : relationships) {

      Product product = relationship.getRelatedProduct();

      ReadableProducts proxyProduct =
          populator.populate(product, new ReadableProducts(), store, language);
      products.add(proxyProduct);
    }

    return products;
  }
}
