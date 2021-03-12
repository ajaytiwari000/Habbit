package com.salesmanager.core.business.services.catalog.category;

import com.salesmanager.core.business.repositories.catalog.category.CategoryFlavourCountRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.category.CategoryFlavourCount;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("categoryFlavourCountService")
public class CategoryFlavourCountServiceImpl
    extends SalesManagerEntityServiceImpl<Long, CategoryFlavourCount>
    implements CategoryFlavourCountService {

  private CategoryFlavourCountRepository categoryFlavourCountRepository;

  @Inject
  public CategoryFlavourCountServiceImpl(
      CategoryFlavourCountRepository categoryFlavourCountRepository) {
    super(categoryFlavourCountRepository);
    this.categoryFlavourCountRepository = categoryFlavourCountRepository;
  }
}
