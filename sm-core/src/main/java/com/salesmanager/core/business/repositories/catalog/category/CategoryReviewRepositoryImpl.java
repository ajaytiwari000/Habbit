package com.salesmanager.core.business.repositories.catalog.category;

import com.salesmanager.core.model.catalog.category.CategoryReview;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.common.CriteriaOrderBy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoryReviewRepositoryImpl implements CategoryReviewRepositoryCustom {

  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryReviewRepositoryImpl.class);

  @PersistenceContext private EntityManager em;

  @Override
  public Optional<List<CategoryReview>> getAllCategoryReview(Criteria criteria) {
    List<CategoryReview> categoryReviewList = new ArrayList<>();
    StringBuilder countBuilderSelect = new StringBuilder();
    StringBuilder objectBuilderSelect = new StringBuilder();

    String resultOrderByCriteria = " order by cr.id desc";
    if (criteria.getOrderBy() != null
        && CriteriaOrderBy.ASC.name().equals(criteria.getOrderBy().name())) {
      resultOrderByCriteria = " order by cr.id asc";
    }

    String countBaseQuery = "select count(cr) from CategoryReview cr";
    String baseQuery = "select cr from CategoryReview cr";
    countBuilderSelect.append(countBaseQuery);
    objectBuilderSelect.append(baseQuery);

    StringBuilder objectBuilderWhere = new StringBuilder();
    if (Objects.nonNull(criteria.getName())) {
      countBuilderSelect.append(" where cr.categoryName = '" + criteria.getName() + "'");
      objectBuilderSelect.append(" where cr.categoryName = '" + criteria.getName() + "'");
    }
    objectBuilderWhere.append(resultOrderByCriteria);
    // count query
    Query countQ = em.createQuery(countBuilderSelect.toString());
    // object query
    Query objectQ = em.createQuery(objectBuilderSelect.toString() + objectBuilderWhere.toString());
    Number count = (Number) countQ.getSingleResult();

    if (count.intValue() == 0) return Optional.ofNullable(categoryReviewList);

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

    categoryReviewList.addAll(objectQ.getResultList());
    return Optional.ofNullable(categoryReviewList);
  }
}
