package com.salesmanager.core.business.services.catalog.category;

import com.salesmanager.core.business.repositories.catalog.category.CategoryPackCountRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.category.CategoryPackCount;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("categoryPackCountService")
public class CategoryPackCountServiceImpl
    extends SalesManagerEntityServiceImpl<Long, CategoryPackCount>
    implements CategoryPackCountService {
  private CategoryPackCountRepository categoryPackCountRepository;

  @Inject
  public CategoryPackCountServiceImpl(CategoryPackCountRepository categoryPackCountRepository) {
    super(categoryPackCountRepository);
    this.categoryPackCountRepository = categoryPackCountRepository;
  }
}
