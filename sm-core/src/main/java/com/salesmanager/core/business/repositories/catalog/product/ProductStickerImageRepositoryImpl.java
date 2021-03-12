package com.salesmanager.core.business.repositories.catalog.product;

import com.salesmanager.core.model.catalog.product.image.ProductStickerImage;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.common.CriteriaOrderBy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductStickerImageRepositoryImpl implements ProductStickerImageRepositoryCustom {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ProductStickerImageRepositoryImpl.class);

  @PersistenceContext private EntityManager em;

  @Override
  public Optional<List<ProductStickerImage>> getProductStickerImages(Criteria criteria) {
    List<ProductStickerImage> productStickerImages = new ArrayList<>();
    StringBuilder countBuilderSelect = new StringBuilder();
    StringBuilder objectBuilderSelect = new StringBuilder();

    String resultOrderByCriteria = " order by ps.id desc";
    if (criteria.getOrderBy() != null
        && CriteriaOrderBy.ASC.name().equals(criteria.getOrderBy().name())) {
      resultOrderByCriteria = " order by ps.id asc";
    }

    String countBaseQuery = "select count(ps) from ProductStickerImage ps";
    String baseQuery = "select ps from ProductStickerImage ps";
    countBuilderSelect.append(countBaseQuery);
    objectBuilderSelect.append(baseQuery);

    StringBuilder objectBuilderWhere = new StringBuilder();
    objectBuilderWhere.append(resultOrderByCriteria);

    // count query
    Query countQ = em.createQuery(countBuilderSelect.toString());

    // object query
    Query objectQ = em.createQuery(objectBuilderSelect.toString() + objectBuilderWhere.toString());

    Number count = (Number) countQ.getSingleResult();

    if (count.intValue() == 0) return Optional.ofNullable(productStickerImages);

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

    productStickerImages.addAll(objectQ.getResultList());
    return Optional.ofNullable(productStickerImages);
  }
}
