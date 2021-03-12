package com.salesmanager.core.business.repositories.catalog.product;

import com.salesmanager.core.business.HabbitCoreConstant;
import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.core.model.catalog.product.FilterCriteria;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.ProductCriteria;
import com.salesmanager.core.model.catalog.product.ProductList;
import com.salesmanager.core.model.catalog.product.attribute.AttributeCriteria;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.common.CriteriaOrderBy;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.tax.taxclass.TaxClass;
import java.util.*;
import javax.persistence.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductRepositoryImpl.class);

  @PersistenceContext private EntityManager em;

  @Override
  public Product getById(Long productId, MerchantStore store) {
    return this.get(productId, store);
  }

  @Override
  public Product getById(Long productId) {
    return this.get(productId, null);
  }

  @Override
  public Product getProductWithOnlyMerchantStoreById(Long productId) {
    final String hql =
        "select distinct p from Product as p "
            + "join fetch p.merchantStore merch "
            + "where p.id=:pid";

    final Query q = this.em.createQuery(hql);
    q.setParameter("pid", productId);

    try {
      return (Product) q.getSingleResult();
    } catch (NoResultException ignored) {
      return null;
    }
  }

  private Product get(Long productId, MerchantStore merchant) {

    try {

      //			StringBuilder qs = new StringBuilder();
      //			qs.append("select distinct p from Product as p ");
      //			qs.append("join fetch p.availabilities pa ");
      //			qs.append("join fetch p.merchantStore merch ");
      //			qs.append("join fetch p.descriptions pd ");
      //
      //			qs.append("left join fetch p.categories categs ");
      //			qs.append("left join fetch categs.descriptions categsd ");
      //
      //			qs.append("left join fetch pa.prices pap ");
      //			qs.append("left join fetch pap.descriptions papd ");
      //
      //			// images
      //			qs.append("left join fetch p.images images ");
      //			// options
      //			qs.append("left join fetch p.attributes pattr ");
      //			qs.append("left join fetch pattr.productOption po ");
      //			qs.append("left join fetch po.descriptions pod ");
      //			qs.append("left join fetch pattr.productOptionValue pov ");
      //			qs.append("left join fetch pov.descriptions povd ");
      //			qs.append("left join fetch p.relationships pr ");
      //			// other lefts
      //			qs.append("left join fetch p.manufacturer manuf ");
      //			qs.append("left join fetch manuf.descriptions manufd ");
      //			qs.append("left join fetch p.type type ");
      //			qs.append("left join fetch p.taxClass tx ");
      //
      //			// RENTAL
      //			qs.append("left join fetch p.owner owner ");

      StringBuilder qs = new StringBuilder();
      qs.append("select distinct p from Product as p ");
      qs.append("join fetch p.merchantStore merch ");
      qs.append("left join fetch p.categories categs ");

      qs.append("where p.id=:pid");
      if (merchant != null) {
        qs.append(" and merch.id=:mid");
      }

      String hql = qs.toString();
      Query q = this.em.createQuery(hql);

      q.setParameter("pid", productId);
      if (merchant != null) {
        q.setParameter("mid", merchant.getId());
      }

      Product p = (Product) q.getSingleResult();

      return p;

    } catch (javax.persistence.NoResultException ers) {
      return null;
    }
  }

  @Override
  public Product getByCode(String productCode, Language language) {

    try {

      StringBuilder qs = new StringBuilder();
      qs.append("select distinct p from Product as p ");
      qs.append("join fetch p.availabilities pa ");
      qs.append("join fetch p.descriptions pd ");
      qs.append("join fetch p.merchantStore pm ");
      qs.append("left join fetch pa.prices pap ");
      qs.append("left join fetch pap.descriptions papd ");

      qs.append("left join fetch p.categories categs ");
      qs.append("left join fetch categs.descriptions categsd ");

      // images
      qs.append("left join fetch p.images images ");
      // options
      qs.append("left join fetch p.attributes pattr ");
      qs.append("left join fetch pattr.productOption po ");
      qs.append("left join fetch po.descriptions pod ");
      qs.append("left join fetch pattr.productOptionValue pov ");
      qs.append("left join fetch pov.descriptions povd ");
      qs.append("left join fetch p.relationships pr ");
      // other lefts
      qs.append("left join fetch p.manufacturer manuf ");
      qs.append("left join fetch manuf.descriptions manufd ");
      qs.append("left join fetch p.type type ");
      qs.append("left join fetch p.taxClass tx ");

      // RENTAL
      qs.append("left join fetch p.owner owner ");

      qs.append("where p.sku=:code ");
      qs.append("and pd.language.id=:lang and papd.language.id=:lang");
      // this cannot be done on child elements from left join
      // qs.append("and pod.languageId=:lang and povd.languageId=:lang");

      String hql = qs.toString();
      Query q = this.em.createQuery(hql);

      q.setParameter("code", productCode);
      q.setParameter("lang", language.getId());

      Product p = (Product) q.getSingleResult();

      return p;

    } catch (javax.persistence.NoResultException ers) {
      return null;
    }
  }

  public Product getByFriendlyUrl(MerchantStore store, String seUrl, Locale locale) {

    List regionList = new ArrayList();
    regionList.add("*");
    regionList.add(locale.getCountry());

    StringBuilder qs = new StringBuilder();
    qs.append("select distinct p from Product as p ");
    qs.append("join fetch p.availabilities pa ");
    qs.append("join fetch p.descriptions pd ");
    qs.append("join fetch p.merchantStore pm ");
    qs.append("left join fetch pa.prices pap ");
    qs.append("left join fetch pap.descriptions papd ");

    qs.append("left join fetch p.categories categs ");
    qs.append("left join fetch categs.descriptions categsd ");

    // images
    qs.append("left join fetch p.images images ");
    // options
    qs.append("left join fetch p.attributes pattr ");
    qs.append("left join fetch pattr.productOption po ");
    qs.append("left join fetch po.descriptions pod ");
    qs.append("left join fetch pattr.productOptionValue pov ");
    qs.append("left join fetch pov.descriptions povd ");
    qs.append("left join fetch p.relationships pr ");
    // other lefts
    qs.append("left join fetch p.manufacturer manuf ");
    qs.append("left join fetch manuf.descriptions manufd ");
    qs.append("left join fetch p.type type ");
    qs.append("left join fetch p.taxClass tx ");

    // RENTAL
    qs.append("left join fetch p.owner owner ");

    qs.append("where pa.region in (:lid) ");
    qs.append("and pd.seUrl=:seUrl ");
    qs.append("and p.available=true and p.dateAvailable<=:dt ");
    qs.append("order by pattr.productOptionSortOrder ");

    String hql = qs.toString();
    Query q = this.em.createQuery(hql);

    q.setParameter("lid", regionList);
    q.setParameter("dt", new Date());
    q.setParameter("seUrl", seUrl);

    Product p = null;

    try {
      List<Product> products = q.getResultList();
      if (products.size() > 1) {
        LOGGER.error(
            "Found multiple products for list of criterias with main criteria [" + seUrl + "]");
      }
      // p = (Product)q.getSingleResult();
      p = products.get(0);
    } catch (javax.persistence.NoResultException ignore) {

    }

    return p;
  }

  @Override
  public List<Product> getProductsForLocale(
      MerchantStore store, Set<Long> categoryIds, Language language, Locale locale) {

    ProductList products =
        this.getProductsListForLocale(store, categoryIds, language, locale, 0, -1);

    return products.getProducts();
  }

  @Override
  public Product getProductForLocale(long productId, Language language, Locale locale) {

    List regionList = new ArrayList();
    regionList.add("*");
    regionList.add(locale.getCountry());

    StringBuilder qs = new StringBuilder();
    qs.append("select distinct p from Product as p ");
    qs.append("join fetch p.availabilities pa ");
    qs.append("join fetch p.descriptions pd ");
    qs.append("join fetch p.merchantStore pm ");
    qs.append("left join fetch pa.prices pap ");
    qs.append("left join fetch pap.descriptions papd ");

    // images
    qs.append("left join fetch p.images images ");
    // options
    qs.append("left join fetch p.attributes pattr ");
    qs.append("left join fetch pattr.productOption po ");
    qs.append("left join fetch po.descriptions pod ");
    qs.append("left join fetch pattr.productOptionValue pov ");
    qs.append("left join fetch pov.descriptions povd ");
    qs.append("left join fetch p.relationships pr ");
    // other lefts
    qs.append("left join fetch p.manufacturer manuf ");
    qs.append("left join fetch manuf.descriptions manufd ");
    qs.append("left join fetch p.type type ");
    qs.append("left join fetch p.taxClass tx ");

    // RENTAL
    qs.append("left join fetch p.owner owner ");

    qs.append("where p.id=:pid and pa.region in (:lid) ");
    qs.append("and pd.language.id=:lang and papd.language.id=:lang ");
    qs.append("and p.available=true and p.dateAvailable<=:dt ");
    // this cannot be done on child elements from left join
    // qs.append("and pod.languageId=:lang and povd.languageId=:lang");

    String hql = qs.toString();
    Query q = this.em.createQuery(hql);

    q.setParameter("pid", productId);
    q.setParameter("lid", regionList);
    q.setParameter("dt", new Date());
    q.setParameter("lang", language.getId());

    @SuppressWarnings("unchecked")
    List<Product> results = q.getResultList();
    if (results.isEmpty()) return null;
    else if (results.size() == 1) return (Product) results.get(0);
    throw new NonUniqueResultException();
  }

  @SuppressWarnings("rawtypes")
  @Override
  public List<Product> getProductsListByCategories(Set categoryIds) {

    // List regionList = new ArrayList();
    // regionList.add("*");
    // regionList.add(locale.getCountry());

    // TODO Test performance
    /** Testing in debug mode takes a long time with this query running in normal mode is fine */
    StringBuilder qs = new StringBuilder();
    qs.append("select distinct p from Product as p ");
    qs.append("join fetch p.merchantStore merch ");
    qs.append("join fetch p.availabilities pa ");
    qs.append("left join fetch pa.prices pap ");

    qs.append("join fetch p.descriptions pd ");
    qs.append("join fetch p.categories categs ");

    qs.append("left join fetch pap.descriptions papd ");

    // images
    qs.append("left join fetch p.images images ");

    // options (do not need attributes for listings)
    qs.append("left join fetch p.attributes pattr ");
    qs.append("left join fetch pattr.productOption po ");
    qs.append("left join fetch po.descriptions pod ");
    qs.append("left join fetch pattr.productOptionValue pov ");
    qs.append("left join fetch pov.descriptions povd ");

    // other lefts
    qs.append("left join fetch p.manufacturer manuf ");
    qs.append("left join fetch p.type type ");
    qs.append("left join fetch p.taxClass tx ");

    // RENTAL
    qs.append("left join fetch p.owner owner ");

    // qs.append("where pa.region in (:lid) ");
    qs.append("where categs.id in (:cid)");

    String hql = qs.toString();
    Query q = this.em.createQuery(hql);

    q.setParameter("cid", categoryIds);

    @SuppressWarnings("unchecked")
    List<Product> products = q.getResultList();

    return products;
  }

  @Override
  public List<Product> getProductsListByCategories(Set<Long> categoryIds, Language language) {

    // List regionList = new ArrayList();
    // regionList.add("*");
    // regionList.add(locale.getCountry());

    // TODO Test performance
    /** Testing in debug mode takes a long time with this query running in normal mode is fine */
    StringBuilder qs = new StringBuilder();
    qs.append("select distinct p from Product as p ");
    qs.append("join fetch p.merchantStore merch ");
    qs.append("join fetch p.availabilities pa ");
    qs.append("left join fetch pa.prices pap ");

    qs.append("join fetch p.descriptions pd ");
    qs.append("join fetch p.categories categs ");

    qs.append("left join fetch pap.descriptions papd ");

    // images
    qs.append("left join fetch p.images images ");

    // options (do not need attributes for listings)
    qs.append("left join fetch p.attributes pattr ");
    qs.append("left join fetch pattr.productOption po ");
    qs.append("left join fetch po.descriptions pod ");
    qs.append("left join fetch pattr.productOptionValue pov ");
    qs.append("left join fetch pov.descriptions povd ");

    // other lefts
    qs.append("left join fetch p.manufacturer manuf ");
    qs.append("left join fetch manuf.descriptions manufd ");
    qs.append("left join fetch p.type type ");
    qs.append("left join fetch p.taxClass tx ");

    // RENTAL
    qs.append("left join fetch p.owner owner ");

    // qs.append("where pa.region in (:lid) ");
    qs.append("where categs.id in (:cid) ");
    // qs.append("and pd.language.id=:lang and papd.language.id=:lang and
    // manufd.language.id=:lang ");
    qs.append("and pd.language.id=:lang and papd.language.id=:lang ");
    qs.append("and p.available=true and p.dateAvailable<=:dt ");

    String hql = qs.toString();
    Query q = this.em.createQuery(hql);

    q.setParameter("cid", categoryIds);
    q.setParameter("lang", language.getId());
    q.setParameter("dt", new Date());

    @SuppressWarnings("unchecked")
    List<Product> products = q.getResultList();

    return products;
  }

  /**
   * This query is used for category listings. All collections are not fully loaded, only the
   * required objects so the listing page can display everything related to all products
   */
  @SuppressWarnings({"rawtypes", "unchecked", "unused"})
  private ProductList getProductsListForLocale(
      MerchantStore store, Set categoryIds, Language language, Locale locale, int first, int max) {

    List regionList = new ArrayList();
    regionList.add(Constants.ALL_REGIONS);
    if (locale != null) {
      regionList.add(locale.getCountry());
    }

    ProductList productList = new ProductList();

    Query countQ =
        this.em.createQuery(
            "select count(p) from Product as p INNER JOIN p.availabilities pa INNER JOIN p.categories categs where p.merchantStore.id=:mId and categs.id in (:cid) and pa.region in (:lid) and p.available=1 and p.dateAvailable<=:dt");

    countQ.setParameter("cid", categoryIds);
    countQ.setParameter("lid", regionList);
    countQ.setParameter("dt", new Date());
    countQ.setParameter("mId", store.getId());

    Number count = (Number) countQ.getSingleResult();

    productList.setTotalCount(count.intValue());

    if (count.intValue() == 0) return productList;

    StringBuilder qs = new StringBuilder();
    qs.append("select p from Product as p ");
    qs.append("join fetch p.merchantStore merch ");
    qs.append("join fetch p.availabilities pa ");
    qs.append("left join fetch pa.prices pap ");

    qs.append("join fetch p.descriptions pd ");
    qs.append("join fetch p.categories categs ");

    // not necessary
    // qs.append("join fetch pap.descriptions papd ");

    // images
    qs.append("left join fetch p.images images ");

    // options (do not need attributes for listings)
    // qs.append("left join fetch p.attributes pattr ");
    // qs.append("left join fetch pattr.productOption po ");
    // qs.append("left join fetch po.descriptions pod ");
    // qs.append("left join fetch pattr.productOptionValue pov ");
    // qs.append("left join fetch pov.descriptions povd ");

    // other lefts
    qs.append("left join fetch p.manufacturer manuf ");
    qs.append("left join fetch manuf.descriptions manufd ");
    qs.append("left join fetch p.type type ");
    qs.append("left join fetch p.taxClass tx ");

    // RENTAL
    qs.append("left join fetch p.owner owner ");

    // qs.append("where pa.region in (:lid) ");
    qs.append("where p.merchantStore.id=mId and categs.id in (:cid) and pa.region in (:lid) ");
    // qs.append("and p.available=true and p.dateAvailable<=:dt and
    // pd.language.id=:lang and manufd.language.id=:lang");
    qs.append("and p.available=true and p.dateAvailable<=:dt and pd.language.id=:lang");
    qs.append(" order by p.sortOrder asc");

    String hql = qs.toString();
    Query q = this.em.createQuery(hql);

    q.setParameter("cid", categoryIds);
    q.setParameter("lid", regionList);
    q.setParameter("dt", new Date());
    q.setParameter("lang", language.getId());
    q.setParameter("mId", store.getId());

    q.setFirstResult(first);
    if (max > 0) {
      int maxCount = first + max;

      if (maxCount < count.intValue()) {
        q.setMaxResults(maxCount);
      } else {
        q.setMaxResults(count.intValue());
      }
    }

    List<Product> products = q.getResultList();
    productList.setProducts(products);

    return productList;
  }

  /**
   * This query is used for filtering products based on criterias
   *
   * @param store
   * @param language
   * @param criteria
   * @return
   */
  @Override
  public ProductList listByStore(MerchantStore store, Language language, ProductCriteria criteria) {

    ProductList productList = new ProductList();

    StringBuilder countBuilderSelect = new StringBuilder();
    countBuilderSelect.append("select count(distinct p) from Product as p");

    StringBuilder countBuilderWhere = new StringBuilder();
    countBuilderWhere.append(" where p.merchantStore.id=:mId");

    if (!CollectionUtils.isEmpty(criteria.getProductIds())) {
      countBuilderWhere.append(" and p.id in (:pId)");
    }

    countBuilderSelect.append(" inner join p.descriptions pd");
    if (criteria.getLanguage() != null && !criteria.getLanguage().equals("_all")) {
      countBuilderWhere.append(" and pd.language.code=:lang");
    }

    if (!StringUtils.isBlank(criteria.getProductName())) {
      countBuilderWhere.append(" and lower(pd.name) like:nm");
    }

    if (!CollectionUtils.isEmpty(criteria.getCategoryIds())) {
      countBuilderSelect.append(" INNER JOIN p.categories categs");
      countBuilderWhere.append(" and categs.id in (:cid)");
    }

    if (criteria.getManufacturerId() != null) {
      countBuilderSelect.append(" INNER JOIN p.manufacturer manuf");
      countBuilderWhere.append(" and manuf.id = :manufid");
    }

    if (!StringUtils.isBlank(criteria.getCode())) {
      countBuilderWhere.append(" and lower(p.sku) like :sku");
    }

    // RENTAL
    if (!StringUtils.isBlank(criteria.getStatus())) {
      countBuilderWhere.append(" and p.rentalStatus = :status");
    }

    if (criteria.getOwnerId() != null) {
      countBuilderSelect.append(" INNER JOIN p.owner owner");
      countBuilderWhere.append(" and owner.id = :ownerid");
    }

    if (!CollectionUtils.isEmpty(criteria.getAttributeCriteria())) {

      countBuilderSelect.append(" INNER JOIN p.attributes pattr");
      countBuilderSelect.append(" INNER JOIN pattr.productOption po");
      countBuilderSelect.append(" INNER JOIN pattr.productOptionValue pov ");
      countBuilderSelect.append(" INNER JOIN pov.descriptions povd ");
      int count = 0;
      for (AttributeCriteria attributeCriteria : criteria.getAttributeCriteria()) {
        if (count == 0) {
          countBuilderWhere.append(" and po.code =:").append(attributeCriteria.getAttributeCode());
          countBuilderWhere
              .append(" and povd.description like :")
              .append("val")
              .append(count)
              .append(attributeCriteria.getAttributeCode());
        }
        count++;
      }
      if (criteria.getLanguage() != null && !criteria.getLanguage().equals("_all")) {
        countBuilderWhere.append(" and povd.language.code=:lang");
      }
    }

    if (criteria.getAvailable() != null) {
      if (criteria.getAvailable().booleanValue()) {
        countBuilderWhere.append(" and p.available=true and p.dateAvailable<=:dt");
      } else {
        countBuilderWhere.append(" and p.available=false or p.dateAvailable>:dt");
      }
    }

    Query countQ =
        this.em.createQuery(countBuilderSelect.toString() + countBuilderWhere.toString());

    countQ.setParameter("mId", store.getId());

    if (!CollectionUtils.isEmpty(criteria.getCategoryIds())) {
      countQ.setParameter("cid", criteria.getCategoryIds());
    }

    if (criteria.getAvailable() != null) {
      countQ.setParameter("dt", new Date());
    }

    if (!StringUtils.isBlank(criteria.getCode())) {
      countQ.setParameter(
          "sku",
          new StringBuilder()
              .append("%")
              .append(criteria.getCode().toLowerCase())
              .append("%")
              .toString());
    }

    if (criteria.getManufacturerId() != null) {
      countQ.setParameter("manufid", criteria.getManufacturerId());
    }

    if (!CollectionUtils.isEmpty(criteria.getAttributeCriteria())) {
      int count = 0;
      for (AttributeCriteria attributeCriteria : criteria.getAttributeCriteria()) {
        countQ.setParameter(
            attributeCriteria.getAttributeCode(), attributeCriteria.getAttributeCode());
        countQ.setParameter(
            "val" + count + attributeCriteria.getAttributeCode(),
            "%" + attributeCriteria.getAttributeValue() + "%");
        count++;
      }
    }

    if (criteria.getLanguage() != null && !criteria.getLanguage().equals("_all")) {
      countQ.setParameter("lang", language.getCode());
    }

    if (!StringUtils.isBlank(criteria.getProductName())) {
      countQ.setParameter(
          "nm",
          new StringBuilder()
              .append("%")
              .append(criteria.getProductName().toLowerCase())
              .append("%")
              .toString());
    }

    if (!CollectionUtils.isEmpty(criteria.getProductIds())) {
      countQ.setParameter("pId", criteria.getProductIds());
    }

    // RENTAL
    if (!StringUtils.isBlank(criteria.getStatus())) {
      countQ.setParameter("status", criteria.getStatus());
    }

    if (criteria.getOwnerId() != null) {
      countQ.setParameter("ownerid", criteria.getOwnerId());
    }

    Number count = (Number) countQ.getSingleResult();

    productList.setTotalCount(count.intValue());

    if (count.intValue() == 0) return productList;

    StringBuilder qs = new StringBuilder();
    qs.append("select distinct p from Product as p ");
    qs.append("join fetch p.merchantStore merch ");
    qs.append("join fetch p.availabilities pa ");
    qs.append("left join fetch pa.prices pap ");

    qs.append("join fetch p.descriptions pd ");
    qs.append("left join fetch p.categories categs ");
    qs.append("left join fetch categs.descriptions cd ");

    // images
    qs.append("left join fetch p.images images ");

    // other lefts
    qs.append("left join fetch p.manufacturer manuf ");
    qs.append("left join fetch manuf.descriptions manufd ");
    qs.append("left join fetch p.type type ");
    qs.append("left join fetch p.taxClass tx ");

    // RENTAL
    qs.append("left join fetch p.owner owner ");

    // attributes
    if (!CollectionUtils.isEmpty(criteria.getAttributeCriteria())) {
      qs.append(" inner join p.attributes pattr");
      qs.append(" inner join pattr.productOption po");
      qs.append(" inner join po.descriptions pod");
      qs.append(" inner join pattr.productOptionValue pov ");
      qs.append(" inner join pov.descriptions povd");
    } else {
      qs.append(" left join fetch p.attributes pattr");
      qs.append(" left join fetch pattr.productOption po");
      qs.append(" left join fetch po.descriptions pod");
      qs.append(" left join fetch pattr.productOptionValue pov");
      qs.append(" left join fetch pov.descriptions povd");
    }

    qs.append(" left join fetch p.relationships pr");

    qs.append(" where merch.id=:mId");
    if (criteria.getLanguage() != null && !criteria.getLanguage().equals("_all")) {
      qs.append(" and pd.language.code=:lang");
    }

    if (!CollectionUtils.isEmpty(criteria.getProductIds())) {
      qs.append(" and p.id in (:pId)");
    }

    if (!CollectionUtils.isEmpty(criteria.getCategoryIds())) {
      qs.append(" and categs.id in (:cid)");
    }

    if (criteria.getManufacturerId() != null) {
      qs.append(" and manuf.id = :manufid");
    }

    if (criteria.getAvailable() != null) {
      if (criteria.getAvailable().booleanValue()) {
        qs.append(" and p.available=true and p.dateAvailable<=:dt");
      } else {
        qs.append(" and p.available=false and p.dateAvailable>:dt");
      }
    }

    if (!StringUtils.isBlank(criteria.getProductName())) {
      qs.append(" and lower(pd.name) like :nm");
    }

    if (!StringUtils.isBlank(criteria.getCode())) {
      qs.append(" and lower(p.sku) like :sku");
    }

    // RENTAL
    if (!StringUtils.isBlank(criteria.getStatus())) {
      qs.append(" and p.rentalStatus = :status");
    }

    if (criteria.getOwnerId() != null) {
      qs.append(" and owner.id = :ownerid");
    }

    if (!CollectionUtils.isEmpty(criteria.getAttributeCriteria())) {
      int cnt = 0;
      for (AttributeCriteria attributeCriteria : criteria.getAttributeCriteria()) {
        qs.append(" and po.code =:").append(attributeCriteria.getAttributeCode());
        qs.append(" and povd.description like :")
            .append("val")
            .append(cnt)
            .append(attributeCriteria.getAttributeCode());
        cnt++;
      }
      if (criteria.getLanguage() != null && !criteria.getLanguage().equals("_all")) {
        qs.append(" and povd.language.code=:lang");
      }
    }
    qs.append(" order by p.sortOrder asc");

    String hql = qs.toString();
    Query q = this.em.createQuery(hql);

    if (criteria.getLanguage() != null && !criteria.getLanguage().equals("_all")) {
      q.setParameter("lang", language.getCode());
    }
    q.setParameter("mId", store.getId());

    if (!CollectionUtils.isEmpty(criteria.getCategoryIds())) {
      q.setParameter("cid", criteria.getCategoryIds());
    }

    if (!CollectionUtils.isEmpty(criteria.getProductIds())) {
      q.setParameter("pId", criteria.getProductIds());
    }

    if (criteria.getAvailable() != null) {
      q.setParameter("dt", new Date());
    }

    if (criteria.getManufacturerId() != null) {
      q.setParameter("manufid", criteria.getManufacturerId());
    }

    if (!StringUtils.isBlank(criteria.getCode())) {
      q.setParameter(
          "sku",
          new StringBuilder()
              .append("%")
              .append(criteria.getCode().toLowerCase())
              .append("%")
              .toString());
    }

    if (!CollectionUtils.isEmpty(criteria.getAttributeCriteria())) {
      int cnt = 0;
      for (AttributeCriteria attributeCriteria : criteria.getAttributeCriteria()) {
        q.setParameter(attributeCriteria.getAttributeCode(), attributeCriteria.getAttributeCode());
        q.setParameter(
            "val" + cnt + attributeCriteria.getAttributeCode(),
            "%" + attributeCriteria.getAttributeValue() + "%");
        cnt++;
      }
    }

    // RENTAL
    if (!StringUtils.isBlank(criteria.getStatus())) {
      q.setParameter("status", criteria.getStatus());
    }

    if (criteria.getOwnerId() != null) {
      q.setParameter("ownerid", criteria.getOwnerId());
    }

    if (!StringUtils.isBlank(criteria.getProductName())) {
      q.setParameter(
          "nm",
          new StringBuilder()
              .append("%")
              .append(criteria.getProductName().toLowerCase())
              .append("%")
              .toString());
    }

    if (criteria.getMaxCount() > 0) {

      q.setFirstResult(criteria.getStartIndex());
      if (criteria.getMaxCount() < count.intValue()) {
        q.setMaxResults(criteria.getMaxCount());
      } else {
        q.setMaxResults(count.intValue());
      }
    }

    @SuppressWarnings("unchecked")
    List<Product> products = q.getResultList();
    productList.setProducts(products);

    return productList;
  }

  @Override
  public List<Product> listByStore(MerchantStore store) {

    /** Testing in debug mode takes a long time with this query running in normal mode is fine */
    StringBuilder qs = new StringBuilder();
    qs.append("select p from Product as p ");
    qs.append("join fetch p.merchantStore merch ");
    qs.append("join fetch p.availabilities pa ");
    qs.append("left join fetch pa.prices pap ");

    qs.append("join fetch p.descriptions pd ");
    qs.append("left join fetch p.categories categs ");

    qs.append("left join fetch pap.descriptions papd ");

    // images
    qs.append("left join fetch p.images images ");

    // options (do not need attributes for listings)
    qs.append("left join fetch p.attributes pattr ");
    qs.append("left join fetch pattr.productOption po ");
    qs.append("left join fetch po.descriptions pod ");
    qs.append("left join fetch pattr.productOptionValue pov ");
    qs.append("left join fetch pov.descriptions povd ");

    // other lefts
    qs.append("left join fetch p.manufacturer manuf ");
    qs.append("left join fetch manuf.descriptions manufd ");
    qs.append("left join fetch p.type type ");
    qs.append("left join fetch p.taxClass tx ");

    // RENTAL
    qs.append("left join fetch p.owner owner ");

    // qs.append("where pa.region in (:lid) ");
    qs.append("where merch.id=:mid");

    String hql = qs.toString();
    Query q = this.em.createQuery(hql);

    q.setParameter("mid", store.getId());

    @SuppressWarnings("unchecked")
    List<Product> products = q.getResultList();

    return products;
  }

  @Override
  public List<Product> listByTaxClass(TaxClass taxClass) {

    /** Testing in debug mode takes a long time with this query running in normal mode is fine */
    StringBuilder qs = new StringBuilder();
    qs.append("select p from Product as p ");
    qs.append("join fetch p.merchantStore merch ");
    qs.append("join fetch p.availabilities pa ");
    qs.append("left join fetch pa.prices pap ");

    qs.append("join fetch p.descriptions pd ");
    qs.append("join fetch p.categories categs ");

    qs.append("left join fetch pap.descriptions papd ");

    // images
    qs.append("left join fetch p.images images ");

    // options (do not need attributes for listings)
    qs.append("left join fetch p.attributes pattr ");
    qs.append("left join fetch pattr.productOption po ");
    qs.append("left join fetch po.descriptions pod ");
    qs.append("left join fetch pattr.productOptionValue pov ");
    qs.append("left join fetch pov.descriptions povd ");

    // other lefts
    qs.append("left join fetch p.manufacturer manuf ");
    qs.append("left join fetch manuf.descriptions manufd ");
    qs.append("left join fetch p.type type ");
    qs.append("left join fetch p.taxClass tx ");

    // RENTAL
    qs.append("left join fetch p.owner owner ");

    // qs.append("where pa.region in (:lid) ");
    qs.append("where tx.id=:tid");

    String hql = qs.toString();
    Query q = this.em.createQuery(hql);

    q.setParameter("tid", taxClass.getId());

    @SuppressWarnings("unchecked")
    List<Product> products = q.getResultList();

    return products;
  }

  @Override
  public FilterCriteria getHomeFilters() {
    FilterCriteria filterCriteria = new FilterCriteria();

    return new FilterCriteria();
  }

  @Override
  public Optional<List<Product>> listByFilter(
      MerchantStore store, Language language, ProductCriteria criteria) {

    StringBuilder qs = new StringBuilder();
    qs.append("select distinct p from Product as p ");
    qs.append("join fetch p.availabilities avail ");
    qs.append("join fetch p.merchantStore merch ");
    qs.append("left join fetch p.categories categs ");
    qs.append("left join fetch p.flavour proflav ");
    // images
    qs.append("left join fetch p.images images ");
    qs.append(" where merch.id=:mId");
    if (!CollectionUtils.isEmpty(criteria.getProductFlavourTypeIds())) {
      qs.append(" and proflav.id in (:productFlavourTypeId)");
    }
    qs.append(" and (p.unlimited = true or (p.unlimited = false and avail.productQuantity >0)) ");
    //
    if (!CollectionUtils.isEmpty(criteria.getProductFeatureTypes())) {
      if (criteria.getProductFeatureTypes().contains(HabbitCoreConstant.BOOST_AVAILABLE)) {
        qs.append(" and p.boostAvailable = 1 ");
      }
      // to do
      //				if (criteria.getProductFeatureTypes().contains(HabbitCoreConstant.NEWLY_LAUNCHED)) {
      //					qs.append(" and p.boostAvailable = 1 ");
      //				}
    }
    if (!CollectionUtils.isEmpty(criteria.getProductTypeCategoryIds())) {
      qs.append(" and categs.id in (:cid)");
    }
    if (StringUtils.isNotEmpty(criteria.getProductSortType())) {
      if (criteria.getProductSortType().equals(HabbitCoreConstant.LOW_HIGH)) {
        qs.append(" order by p.displayPrice ");
      }
      if (criteria.getProductSortType().equals(HabbitCoreConstant.HIGH_LOW)) {
        qs.append(" order by p.displayPrice desc ");
      }
    }
    String hql = qs.toString();
    Query q = this.em.createQuery(hql);

    q.setParameter("mId", store.getId());

    if (!CollectionUtils.isEmpty(criteria.getProductTypeCategoryIds())) {
      q.setParameter("cid", criteria.getProductTypeCategoryIds());
    }
    if (!CollectionUtils.isEmpty(criteria.getProductFlavourTypeIds())) {
      q.setParameter("productFlavourTypeId", criteria.getProductFlavourTypeIds());
    }
    @SuppressWarnings("unchecked")
    Optional<List<Product>> products = Optional.ofNullable(q.getResultList());
    return products;
  }

  @Override
  public Optional<List<Product>> listByMerchandiseProductName(
      MerchantStore store, Language language, ProductCriteria criteria) {

    StringBuilder qs = new StringBuilder();
    qs.append("select distinct p from Product as p ");
    qs.append("join fetch p.availabilities avail ");
    qs.append("join fetch p.merchantStore merch ");
    qs.append("left join fetch p.categories categs ");
    qs.append("left join fetch categs.categoryDetails categsDet ");
    // images
    qs.append("left join fetch p.images images ");
    qs.append(" where merch.id=:mId");
    if (!StringUtils.isEmpty(criteria.getCategoryName())) {
      qs.append(" and categsDet.name = :categoryName");
    }
    qs.append(" and (p.unlimited = true or (p.unlimited = false and avail.productQuantity >0)) ");
    if (!StringUtils.isEmpty(criteria.getProductName())) {
      qs.append(" and p.name = :productName");
    }
    String hql = qs.toString();
    Query q = this.em.createQuery(hql);

    q.setParameter("mId", store.getId());

    if (!StringUtils.isEmpty(criteria.getCategoryName())) {
      q.setParameter("categoryName", criteria.getCategoryName());
    }
    if (!StringUtils.isEmpty(criteria.getProductName())) {
      q.setParameter("productName", criteria.getProductName());
    }
    @SuppressWarnings("unchecked")
    Optional<List<Product>> products = Optional.ofNullable(q.getResultList());
    return products;
  }

  /**
   * @param store
   * @param flavourId
   * @param categoryId
   * @param excludeProductName
   * @return Product
   */
  @Override
  public Optional<List<Product>> getProductDetailsByFlavorIdAndCategoryId(
      MerchantStore store, Long flavourId, Long categoryId, String excludeProductName) {
    try {

      StringBuilder qs = new StringBuilder();
      qs.append("select distinct p from Product as p ");
      qs.append("join fetch p.merchantStore merch ");
      qs.append("left join fetch p.categories categs ");
      qs.append("left join fetch categs.flavours cateflav ");

      qs.append("where p.name<>:pName");
      if (store != null) {
        qs.append(" and merch.id=:mid");
      }
      //      if (categoryId != null) {
      //        qs.append(" and categs.id=:catId");
      //      }
      //      if (flavourId != null) {
      //        qs.append(" and cateflav.id<>:flavId");
      //      }

      String hql = qs.toString();
      Query q = this.em.createQuery(hql);

      q.setParameter("pName", excludeProductName);
      if (store != null) {
        q.setParameter("mid", store.getId());
      }

      //      if (flavourId != null) {
      //        q.setParameter("flavId", flavourId);
      //      }
      //
      //      if (categoryId != null) {
      //        q.setParameter("catId", categoryId);
      //      }

      List<Product> products = q.getResultList();
      return Optional.ofNullable(products);

    } catch (javax.persistence.NoResultException ers) {
      return null;
    }
  }

  /**
   * @param packSelectedByUser
   * @param store
   * @param categoryId
   * @param flavourId
   * @param packId
   * @return Product
   */
  @Override
  public Optional<Product> getProductDetailsByCategoryFlavorAndPack(
      Boolean packSelectedByUser,
      MerchantStore store,
      Long categoryId,
      Long flavourId,
      Long packId) {
    try {
      // 1) Flavour and Pack both together belongs to product
      // 2) when product doesn't have flavour
      StringBuilder query = new StringBuilder();
      query.append("select distinct p from Product as p ");
      query.append("join fetch p.merchantStore merch ");
      query.append("join fetch p.categories categs ");
      query.append("left join fetch p.flavour proflav ");
      query.append("left join fetch p.packSize propack ");

      query.append("where categs.id =:catid");
      if (store != null) {
        query.append(" and merch.id=:mid");
      }
      if (flavourId != null && flavourId > 0) {
        query.append(" and proflav.id=:flavid");
      }
      query.append(" and propack.id=:packid");

      String hq = query.toString();
      Query qu = this.em.createQuery(hq);

      qu.setParameter("catid", categoryId);
      if (flavourId != null && flavourId > 0) {
        qu.setParameter("flavid", flavourId);
      }
      qu.setParameter("packid", packId);
      if (store != null) {
        qu.setParameter("mid", store.getId());
      }

      List<Product> p = qu.getResultList();
      if (p.size() > 0) {
        return Optional.ofNullable(p.get(0));
      }
      // Flavour or pack (any one)
      StringBuilder qs = new StringBuilder();
      qs.append("select distinct p from Product as p ");
      qs.append("join fetch p.merchantStore merch ");
      qs.append("join fetch p.categories categs ");
      qs.append("left join fetch p.flavour proflav ");
      qs.append("left join fetch p.packSize propack ");

      qs.append("where categs.id =:catid");
      if (store != null) {
        qs.append(" and merch.id=:mid");
      }
      if (packSelectedByUser) {
        qs.append(" and propack.id=:packid");
      } else {
        qs.append(" and proflav.id=:flavid");
      }
      String hql = qs.toString();
      Query q = this.em.createQuery(hql);

      q.setParameter("catid", categoryId);
      if (packSelectedByUser) {
        q.setParameter("packid", packId);
      } else {
        q.setParameter("flavid", flavourId);
      }
      if (store != null) {
        q.setParameter("mid", store.getId());
      }

      Product product = (Product) q.getResultList().get(0);
      return Optional.ofNullable(product);
    } catch (javax.persistence.NoResultException ers) {
      return null;
    }
  }

  /**
   * @param store
   * @param language
   * @param criteria
   * @param count
   * @return List<Poduct>
   */
  @Override
  public Optional<List<Product>> getProductListForHome(
      MerchantStore store, Language language, ProductCriteria criteria, int count) {
    StringBuilder qs = new StringBuilder();
    qs.append("select distinct p from Product as p ");
    qs.append("join fetch p.availabilities avail ");
    qs.append("join fetch p.merchantStore merch ");
    qs.append("left join fetch p.categories categs ");
    qs.append(" where merch.id=:mId");
    qs.append(" and categs.id in (:cid)");
    qs.append(" and (p.unlimited = true or (p.unlimited = false and avail.productQuantity >0)) ");

    String hql = qs.toString();
    Query q = this.em.createQuery(hql);

    q.setParameter("mId", store.getId());
    q.setParameter("cid", criteria.getProductTypeCategoryIds());

    Optional<List<Product>> products = Optional.ofNullable(q.getResultList());
    return products;
  }

  /**
   * @param criteria
   * @return
   */
  @Override
  public Optional<List<Product>> getProducts(Criteria criteria) {
    List<Product> productList = new ArrayList<>();
    StringBuilder countBuilderSelect = new StringBuilder();
    StringBuilder objectBuilderSelect = new StringBuilder();

    String resultOrderByCriteria = " order by p.id desc";
    if (criteria.getOrderBy() != null
        && CriteriaOrderBy.ASC.name().equals(criteria.getOrderBy().name())) {
      resultOrderByCriteria = " order by p.id asc";
    }

    String countBaseQuery = "select count(p) from Product p";
    String baseQuery = "select p from Product p";
    countBuilderSelect.append(countBaseQuery);
    objectBuilderSelect.append(baseQuery);

    StringBuilder objectBuilderWhere = new StringBuilder();
    objectBuilderWhere.append(resultOrderByCriteria);

    // count query
    Query countQ = em.createQuery(countBuilderSelect.toString());

    // object query
    Query objectQ = em.createQuery(objectBuilderSelect.toString() + objectBuilderWhere.toString());

    Number count = (Number) countQ.getSingleResult();

    if (count.intValue() == 0) return Optional.ofNullable(productList);

    int max = criteria.getMaxCount();
    int first = criteria.getStartIndex();

    objectQ.setFirstResult(first);

    if (max > 0) {
      int maxCount = first + max;

      if (maxCount < count.intValue()) {
        objectQ.setMaxResults(maxCount);
      } else {
        objectQ.setMaxResults(count.intValue());
      }
    }

    productList.addAll(objectQ.getResultList());
    return Optional.ofNullable(productList);
  }

  @Override
  public Optional<Product> getMerchandiseProductDetailsByCategoryFlavorAndPack(
      MerchantStore store, Long categoryId, Long packId, String productName) {
    try {
      // 1) Flavour and Pack both together belongs to product
      // 2) when product doesn't have flavour
      StringBuilder query = new StringBuilder();
      query.append("select distinct p from Product as p ");
      query.append("join fetch p.merchantStore merch ");
      query.append("join fetch p.categories categs ");
      query.append("left join fetch categs.categoryDetails categsDet ");
      query.append("left join fetch p.flavour proflav ");
      query.append("left join fetch p.packSize propack ");

      query.append("where categs.id =:catid");
      if (store != null) {
        query.append(" and merch.id=:mid");
      }
      query.append(" and propack.id=:packid");
      if (!StringUtils.isEmpty(productName)) {
        query.append(" and p.name = :productName");
      }

      String hq = query.toString();
      Query qu = this.em.createQuery(hq);

      qu.setParameter("catid", categoryId);

      qu.setParameter("packid", packId);
      if (store != null) {
        qu.setParameter("mid", store.getId());
      }
      if (!StringUtils.isEmpty(productName)) {
        qu.setParameter("productName", productName);
      }

      List<Product> p = qu.getResultList();
      if (p.size() > 0) {
        return Optional.ofNullable(p.get(0));
      }
      // Flavour or pack (any one)
      StringBuilder qs = new StringBuilder();
      qs.append("select distinct p from Product as p ");
      qs.append("join fetch p.merchantStore merch ");
      qs.append("join fetch p.categories categs ");
      query.append("left join fetch categs.categoryDetails categsDet ");
      qs.append("left join fetch p.flavour proflav ");
      qs.append("left join fetch p.packSize propack ");

      qs.append("where categs.id =:catid");
      if (store != null) {
        qs.append(" and merch.id=:mid");
      }

      qs.append(" and propack.id=:packid");
      if (!StringUtils.isEmpty(productName)) {
        query.append(" and p.name = :productName");
      }
      String hql = qs.toString();
      Query q = this.em.createQuery(hql);

      q.setParameter("catid", categoryId);
      q.setParameter("packid", packId);

      if (store != null) {
        q.setParameter("mid", store.getId());
      }
      if (!StringUtils.isEmpty(productName)) {
        qu.setParameter("productName", productName);
      }

      Product product = (Product) q.getResultList().get(0);
      return Optional.ofNullable(product);
    } catch (javax.persistence.NoResultException ers) {
      return null;
    }
  }
}
