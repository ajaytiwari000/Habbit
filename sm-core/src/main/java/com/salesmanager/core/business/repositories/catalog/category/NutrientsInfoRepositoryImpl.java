package com.salesmanager.core.business.repositories.catalog.category;

import com.salesmanager.core.model.catalog.product.NutrientsInfo;
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

public class NutrientsInfoRepositoryImpl implements NutrientsInfoRepositoryCustom {

  private static final Logger LOGGER = LoggerFactory.getLogger(NutrientsInfoRepositoryImpl.class);

  @PersistenceContext private EntityManager em;

  @Override
  public Optional<List<NutrientsInfo>> getNutrientsInfos(Criteria criteria) {
    List<NutrientsInfo> nutrientsInfoArrayList = new ArrayList<>();
    StringBuilder countBuilderSelect = new StringBuilder();
    StringBuilder objectBuilderSelect = new StringBuilder();

    String resultOrderByCriteria = " order by n.id desc";
    if (criteria.getOrderBy() != null
        && CriteriaOrderBy.ASC.name().equals(criteria.getOrderBy().name())) {
      resultOrderByCriteria = " order by n.id asc";
    }

    String countBaseQuery = "select count(n) from NutrientsInfo n";
    String baseQuery = "select n from NutrientsInfo n";
    countBuilderSelect.append(countBaseQuery);
    objectBuilderSelect.append(baseQuery);

    StringBuilder objectBuilderWhere = new StringBuilder();
    objectBuilderWhere.append(resultOrderByCriteria);

    // count query
    Query countQ = em.createQuery(countBuilderSelect.toString());

    // object query
    Query objectQ = em.createQuery(objectBuilderSelect.toString() + objectBuilderWhere.toString());

    Number count = (Number) countQ.getSingleResult();

    if (count.intValue() == 0) return Optional.ofNullable(nutrientsInfoArrayList);

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

    nutrientsInfoArrayList.addAll(objectQ.getResultList());
    return Optional.ofNullable(nutrientsInfoArrayList);
  }
}
