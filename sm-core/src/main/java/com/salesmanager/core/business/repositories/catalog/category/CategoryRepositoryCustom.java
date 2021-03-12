package com.salesmanager.core.business.repositories.catalog.category;

import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import java.util.List;

public interface CategoryRepositoryCustom {

  List<Object[]> countProductsByCategories(MerchantStore store, List<Long> categoryIds);

  List<Category> listByStoreAndParent(MerchantStore store, Category category);

  List<Category> getCategorysByCriteria(Criteria criteria);
}
