package com.salesmanager.core.business.repositories.catalog.category;

import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.common.CriteriaOrderBy;
import com.salesmanager.core.model.merchant.MerchantStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

  @PersistenceContext private EntityManager em;

  @Override
  public List<Object[]> countProductsByCategories(MerchantStore store, List<Long> categoryIds) {

    StringBuilder qs = new StringBuilder();
    qs.append("select categories, count(product.id) from Product product ");
    qs.append("inner join product.categories categories ");
    qs.append("where categories.id in (:cid) ");
    qs.append("and product.available=true and product.dateAvailable<=:dt ");
    qs.append("group by categories.id");

    String hql = qs.toString();
    Query q = this.em.createQuery(hql);

    q.setParameter("cid", categoryIds);
    q.setParameter("dt", new Date());

    @SuppressWarnings("unchecked")
    List<Object[]> counts = q.getResultList();

    return counts;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Category> listByStoreAndParent(MerchantStore store, Category category) {

    StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append("select c from Category c join fetch c.merchantStore cm ");

    if (store == null) {
      if (category == null) {
        // query.from(qCategory)
        queryBuilder.append(" where c.parent IsNull ");
        // .where(qCategory.parent.isNull())
        // .orderBy(qCategory.sortOrder.asc(),qCategory.id.desc());
      } else {
        // query.from(qCategory)
        queryBuilder.append(" join fetch c.parent cp where cp.id =:cid ");
        // .where(qCategory.parent.eq(category))
        // .orderBy(qCategory.sortOrder.asc(),qCategory.id.desc());
      }
    } else {
      if (category == null) {
        // query.from(qCategory)
        queryBuilder.append(" where c.parent IsNull and cm.id=:mid ");
        // .where(qCategory.parent.isNull()
        //	.and(qCategory.merchantStore.eq(store)))
        // .orderBy(qCategory.sortOrder.asc(),qCategory.id.desc());
      } else {
        // query.from(qCategory)
        queryBuilder.append(" join fetch c.parent cp where cp.id =:cId and cm.id=:mid ");
        // .where(qCategory.parent.eq(category)
        //	.and(qCategory.merchantStore.eq(store)))
        // .orderBy(qCategory.sortOrder.asc(),qCategory.id.desc());
      }
    }

    queryBuilder.append(" order by c.sortOrder asc");

    String hql = queryBuilder.toString();
    Query q = this.em.createQuery(hql);

    q.setParameter("cid", category.getId());
    if (store != null) {
      q.setParameter("mid", store.getId());
    }

    return q.getResultList();
  }

  @Override
  public List<Category> getCategorysByCriteria(Criteria criteria) {
    List<Category> categoryList = new ArrayList<>();
    StringBuilder countBuilderSelect = new StringBuilder();
    StringBuilder objectBuilderSelect = new StringBuilder();

    String resultOrderByCriteria = " order by c.id desc";
    if (criteria.getOrderBy() != null) {
      if (CriteriaOrderBy.ASC.name().equals(criteria.getOrderBy().name())) {
        resultOrderByCriteria = " order by c.id asc";
      }
    }

    String countBaseQuery = "select count(c) from Category c";
    String baseQuery = "select c from Category c";
    countBuilderSelect.append(countBaseQuery);
    objectBuilderSelect.append(baseQuery);

    StringBuilder objectBuilderWhere = new StringBuilder();
    objectBuilderWhere.append(resultOrderByCriteria);

    // count query
    Query countQ = em.createQuery(countBuilderSelect.toString());

    // object query
    Query objectQ = em.createQuery(objectBuilderSelect.toString() + objectBuilderWhere.toString());

    Number count = (Number) countQ.getSingleResult();

    if (count.intValue() == 0) return categoryList;

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

    categoryList.addAll(objectQ.getResultList());
    return categoryList;
  }
}
