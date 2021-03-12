package com.salesmanager.core.business.repositories.catalog.attribute;

import com.salesmanager.core.model.catalog.product.Pack;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.common.CriteriaOrderBy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class PackRepositoryImpl implements PackRepositoryCustom {
  @PersistenceContext private EntityManager em;

  @Override
  public Optional<List<Pack>> getPacks(Criteria criteria) {
    List<Pack> packList = new ArrayList<>();
    StringBuilder countBuilderSelect = new StringBuilder();
    StringBuilder objectBuilderSelect = new StringBuilder();

    String resultOrderByCriteria = " order by p.id desc";
    if (criteria.getOrderBy() != null) {
      if (CriteriaOrderBy.ASC.name().equals(criteria.getOrderBy().name())) {
        resultOrderByCriteria = " order by p.id asc";
      }
    }

    String countBaseQuery = "select count(p) from Pack p";
    String baseQuery = "select p from Pack p ";
    countBuilderSelect.append(countBaseQuery);
    objectBuilderSelect.append(baseQuery);

    StringBuilder objectBuilderWhere = new StringBuilder();
    objectBuilderWhere.append(resultOrderByCriteria);

    // count query
    Query countQ = em.createQuery(countBuilderSelect.toString());

    // object query
    Query objectQ = em.createQuery(objectBuilderSelect.toString() + objectBuilderWhere.toString());

    Number count = (Number) countQ.getSingleResult();

    if (count.intValue() == 0) return Optional.ofNullable(packList);

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

    packList.addAll(objectQ.getResultList());
    return Optional.ofNullable(packList);
  }
}
