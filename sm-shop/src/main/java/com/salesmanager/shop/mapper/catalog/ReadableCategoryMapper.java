package com.salesmanager.shop.mapper.catalog;

import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategoryDescription;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.catalog.category.ReadableCategorys;
import com.salesmanager.shop.model.catalog.category.ReadableCategorysFull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/** Not in Habbit */
@Component
public class ReadableCategoryMapper implements Mapper<Category, ReadableCategorys> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReadableCategoryMapper.class);

  @Override
  public ReadableCategorys convert(Category source, MerchantStore store, Language language) {
    ReadableCategorys target = category(language);

    feedDescription(source, language, target);

    Optional<com.salesmanager.shop.model.catalog.category.Category> parentCategory =
        createParentCategory(source);
    parentCategory.ifPresent(target::setParent);

    Optional.ofNullable(source.getDepth()).ifPresent(target::setDepth);

    target.setLineage(source.getLineage());
    target.setStore(source.getMerchantStore().getCode());
    target.setCode(source.getCode());
    target.setId(source.getId());
    target.setSortOrder(source.getSortOrder());
    target.setVisible(source.isVisible());
    target.setFeatured(source.isFeatured());
    return target;
  }

  private void feedDescription(Category source, Language language, ReadableCategorys target) {
    List<com.salesmanager.shop.model.catalog.category.CategoryDescription> descriptions =
        new ArrayList<com.salesmanager.shop.model.catalog.category.CategoryDescription>();
    for (CategoryDescription description : source.getDescriptions()) {
      if (language == null) {
        descriptions.add(convertDescription(description));
      } else {
        if (language.getId().intValue() == description.getLanguage().getId().intValue()) {
          target.setDescription(convertDescription(description));
        }
      }
    }

    if (target instanceof ReadableCategorysFull) {
      ((ReadableCategorysFull) target).setDescriptions(descriptions);
    }
  }

  private com.salesmanager.shop.model.catalog.category.CategoryDescription convertDescription(
      CategoryDescription description) {
    final com.salesmanager.shop.model.catalog.category.CategoryDescription desc =
        new com.salesmanager.shop.model.catalog.category.CategoryDescription();

    desc.setFriendlyUrl(description.getSeUrl());
    desc.setName(description.getName());
    desc.setId(description.getId());
    desc.setDescription(description.getDescription());
    desc.setKeyWords(description.getMetatagKeywords());
    desc.setHighlights(description.getCategoryHighlight());
    desc.setLanguage(description.getLanguage().getCode());
    desc.setTitle(description.getMetatagTitle());
    desc.setMetaDescription(description.getMetatagDescription());
    return desc;
  }

  private Optional<com.salesmanager.shop.model.catalog.category.Category> createParentCategory(
      Category source) {
    return Optional.ofNullable(source.getParent())
        .map(
            parentValue -> {
              final com.salesmanager.shop.model.catalog.category.Category parent =
                  new com.salesmanager.shop.model.catalog.category.Category();
              parent.setCode(source.getParent().getCode());
              parent.setId(source.getParent().getId());
              return parent;
            });
  }

  private ReadableCategorys category(Language language) {

    if (language == null) {
      return new ReadableCategorysFull();
    } else {
      return new ReadableCategorys();
    }
  }

  @Override
  public ReadableCategorys convert(
      Category source, ReadableCategorys destination, MerchantStore store, Language language) {
    return destination;
  }
}
