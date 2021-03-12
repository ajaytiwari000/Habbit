package com.salesmanager.shop.store.facade.category;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductAttributeService;
import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategoryDetails;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.attribute.ProductOption;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValue;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.error.codes.AttributeErrorCodes;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.mapper.product.CategoryDetailsMapper;
import com.salesmanager.shop.mapper.product.CategoryMapper;
import com.salesmanager.shop.model.catalog.category.PersistablesCategory;
import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.catalog.category.ReadableCategoryList;
import com.salesmanager.shop.model.catalog.category.ReadableCategorys;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableCategoryList;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductVariant;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductVariantValue;
import com.salesmanager.shop.model.entity.ListCriteria;
import com.salesmanager.shop.model.productAttribute.PersistableNutrientsFact;
import com.salesmanager.shop.model.productAttribute.PersistableNutrientsInfo;
import com.salesmanager.shop.populator.catalog.PersistableCategoryPopulator;
import com.salesmanager.shop.populator.catalog.ReadableCategoryPopulator;
import com.salesmanager.shop.populator.catalog.ReadableCategorysPopulator;
import com.salesmanager.shop.store.api.exception.*;
import com.salesmanager.shop.store.controller.category.facade.CategoryFacade;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service(value = "categoryFacade")
public class CategoryFacadeImpl implements CategoryFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryFacadeImpl.class);
  @Inject private CategoryService categoryService;

  @Inject private MerchantStoreService merchantStoreService;

  @Inject private PersistableCategoryPopulator persistableCatagoryPopulator;

  @Inject private Mapper<Category, ReadableCategorys> categoryReadableCategorysConverter;

  @Inject private ReadableCategoryPopulator readableCategoryPopulator;

  @Inject private ProductAttributeService productAttributeService;

  @Inject private CategoryDetailsMapper categoryDetailsMapper;

  @Inject private CategoryMapper categoryMapper;

  private static final String FEATURED_CATEGORY = "featured";
  private static final String VISIBLE_CATEGORY = "visible";

  @Override
  public ReadableCategoryList getCategoryHierarchy(
      MerchantStore store,
      ListCriteria criteria,
      int depth,
      Language language,
      List<String> filter,
      int page,
      int count) {

    Validate.notNull(store, "MerchantStore can not be null");

    // get parent store
    try {

      MerchantStore parent = merchantStoreService.getParent(store.getCode());

      List<Category> categories = null;
      ReadableCategoryList returnList = new ReadableCategoryList();
      // total count
      int total = categoryService.count(parent);
      returnList.setTotalPages(total);
      if (!CollectionUtils.isEmpty(filter) && filter.contains(FEATURED_CATEGORY)) {
        categories = categoryService.getListByDepthFilterByFeatured(parent, depth, language);
        returnList.setRecordsTotal(categories.size());
        returnList.setNumber(categories.size());
      } else {
        org.springframework.data.domain.Page<Category> pageable =
            categoryService.getListByDepth(
                parent, language, criteria != null ? criteria.getName() : null, depth, page, count);
        categories = pageable.getContent();
        returnList.setRecordsTotal(pageable.getTotalElements());
        returnList.setNumber(pageable.getNumber());
      }

      List<ReadableCategorys> readableCategories = null;
      if (filter != null && filter.contains(VISIBLE_CATEGORY)) {
        readableCategories =
            categories.stream()
                .filter(Category::isVisible)
                .map(cat -> categoryReadableCategorysConverter.convert(cat, store, language))
                .collect(Collectors.toList());
      } else {
        readableCategories =
            categories.stream()
                .map(cat -> categoryReadableCategorysConverter.convert(cat, store, language))
                .collect(Collectors.toList());
      }

      Map<Long, ReadableCategorys> readableCategoryMap =
          readableCategories.stream()
              .collect(Collectors.toMap(ReadableCategorys::getId, Function.identity()));

      readableCategories.stream()
          // .filter(ReadableCategory::isVisible)
          .filter(cat -> Objects.nonNull(cat.getParent()))
          .filter(cat -> readableCategoryMap.containsKey(cat.getParent().getId()))
          .forEach(
              readableCategory -> {
                ReadableCategorys parentCategory =
                    readableCategoryMap.get(readableCategory.getParent().getId());
                if (parentCategory != null) {
                  parentCategory.getChildren().add(readableCategory);
                }
              });

      List<ReadableCategorys> filteredList =
          readableCategoryMap.values().stream()
              .filter(cat -> cat.getDepth() == 0)
              .sorted(Comparator.comparing(ReadableCategorys::getSortOrder))
              .collect(Collectors.toList());

      returnList.setCategories(filteredList);

      return returnList;

    } catch (ServiceException e) {
      throw new ServiceRuntimeException(e);
    }
  }

  @Override
  public boolean existByCode(MerchantStore store, String code) {
    try {
      Category c = categoryService.getByCode(store, code);
      return c != null ? true : false;
    } catch (ServiceException e) {
      throw new ServiceRuntimeException(e);
    }
  }

  @Override
  public PersistablesCategory saveCategory(MerchantStore store, PersistablesCategory category) {
    try {

      Long categoryId = category.getId();
      Category target =
          Optional.ofNullable(categoryId)
              .filter(merchant -> store != null)
              .filter(id -> id > 0)
              .map(categoryService::getById)
              .orElse(new Category());

      Category dbCategory = populateCategory(store, category, target);
      saveCategory(store, dbCategory, null);

      // set category id
      category.setId(dbCategory.getId());
      return category;
    } catch (ServiceException e) {
      throw new ServiceRuntimeException("Error while updating category", e);
    }
  }

  private Category populateCategory(
      MerchantStore store, PersistablesCategory category, Category target) {
    try {
      return persistableCatagoryPopulator.populate(
          category, target, store, store.getDefaultLanguage());
    } catch (ConversionException e) {
      throw new ServiceRuntimeException(e);
    }
  }

  private void saveCategory(MerchantStore store, Category category, Category parent)
      throws ServiceException {

    /**
     * c.children1
     *
     * <p>children1.children1 children1.children2
     *
     * <p>children1.children2.children1
     */

    /** set lineage * */
    if (parent != null) {
      category.setParent(category);

      String lineage = parent.getLineage();
      int depth = parent.getDepth();

      category.setDepth(depth + 1);
      category.setLineage(new StringBuilder().append(lineage).toString()); // service
      // will
      // adjust
      // lineage
    }

    category.setMerchantStore(store);

    // remove children
    List<Category> children = category.getCategories();
    List<Category> saveAfter =
        children.stream()
            .filter(c -> c.getId() == null || c.getId().longValue() == 0)
            .collect(Collectors.toList());
    List<Category> saveNow =
        children.stream()
            .filter(c -> c.getId() != null && c.getId().longValue() > 0)
            .collect(Collectors.toList());
    category.setCategories(saveNow);

    /** set parent * */
    if (parent != null) {
      category.setParent(parent);
    }

    categoryService.saveOrUpdate(category);

    if (!CollectionUtils.isEmpty(saveAfter)) {
      parent = category;
      for (Category c : saveAfter) {
        if (c.getId() == null || c.getId().longValue() == 0) {
          for (Category sub : children) {
            saveCategory(store, sub, parent);
          }
        }
      }
    }

    /*		if (!CollectionUtils.isEmpty(children)) {
    	parent = category;
    	for (Category sub : children) {
    		saveCategory(store, sub, parent);
    	}
    }*/
  }

  @Override
  public ReadableCategorys getById(MerchantStore store, Long id, Language language) {
    try {
      Category categoryModel = null;
      if (language != null) {
        categoryModel = getCategoryById(id, language);
      } else { // all langs
        categoryModel = getById(store, id);
      }

      if (categoryModel == null)
        throw new ResourceNotFoundException("Categori id [" + id + "] not found");

      StringBuilder lineage = new StringBuilder().append(categoryModel.getLineage());

      ReadableCategorys readableCategorys =
          categoryReadableCategorysConverter.convert(categoryModel, store, language);

      // get children
      List<Category> children = getListByLineage(store, lineage.toString());

      List<ReadableCategorys> childrenCats =
          children.stream()
              .map(cat -> categoryReadableCategorysConverter.convert(cat, store, language))
              .collect(Collectors.toList());

      addChildToParent(readableCategorys, childrenCats);
      return readableCategorys;
    } catch (Exception e) {
      throw new ServiceRuntimeException(e);
    }
  }

  private void addChildToParent(
      ReadableCategorys readableCategorys, List<ReadableCategorys> childrenCats) {
    Map<Long, ReadableCategorys> categoryMap =
        childrenCats.stream()
            .collect(Collectors.toMap(ReadableCategorys::getId, Function.identity()));
    categoryMap.put(readableCategorys.getId(), readableCategorys);

    // traverse map and add child to parent
    for (ReadableCategorys readable : childrenCats) {

      if (readable.getParent() != null) {

        ReadableCategorys rc = categoryMap.get(readable.getParent().getId());
        if (rc != null) {
          rc.getChildren().add(readable);
        }
      }
    }
  }

  @Override
  public ReadableCategory getByCategoryId(MerchantStore store, Long id, Language language) {
    try {
      Category categoryModel = null;
      if (language != null) {
        categoryModel = getCategoryById(id, language);
      } else { // all langs
        categoryModel = getById(store, id);
      }

      if (categoryModel == null)
        throw new ResourceNotFoundException("Categori id [" + id + "] not found");

      ReadableCategory readableCategory = new ReadableCategory();
      readableCategoryPopulator.populate(categoryModel, readableCategory, store, language);
      return readableCategory;
    } catch (Exception e) {
      throw new ServiceRuntimeException(e);
    }
  }

  private List<Category> getListByLineage(MerchantStore store, String lineage) {
    try {
      return categoryService.getListByLineage(store, lineage);
    } catch (ServiceException e) {
      throw new ServiceRuntimeException(
          String.format("Error while getting root category %s", e.getMessage()), e);
    }
  }

  private Category getCategoryById(Long id, Language language) {
    return Optional.ofNullable(categoryService.getOneByLanguage(id, language))
        .orElseThrow(() -> new ResourceNotFoundException("Category id not found"));
  }

  @Override
  public void deleteCategory(Category category) {
    try {
      categoryService.delete(category);
    } catch (ServiceException e) {
      throw new ServiceRuntimeException("Error while deleting category", e);
    }
  }

  @Override
  public ReadableCategorys getByCode(MerchantStore store, String code, Language language)
      throws Exception {

    Validate.notNull(code, "category code must not be null");
    ReadableCategorysPopulator categoryPopulator = new ReadableCategorysPopulator();
    ReadableCategorys readableCategorys = new ReadableCategorys();

    Category category = categoryService.getByCode(store, code);
    categoryPopulator.populate(category, readableCategorys, store, language);

    return readableCategorys;
  }

  private Category getById(MerchantStore store, Long id) throws Exception {
    Validate.notNull(id, "category id must not be null");
    Validate.notNull(store, "MerchantStore must not be null");
    Category category = categoryService.getById(id, store.getId());
    if (category == null) {
      throw new ResourceNotFoundException("Category with id [" + id + "] not found");
    }
    if (category.getMerchantStore().getId().intValue() != store.getId().intValue()) {
      throw new UnauthorizedException("Unauthorized");
    }
    return category;
  }

  @Override
  public void deleteCategory(Long categoryId, MerchantStore store) {
    Category category = getOne(categoryId, store.getId());
    deleteCategory(category);
  }

  private Category getOne(Long categoryId, int storeId) {
    return Optional.ofNullable(categoryService.getById(categoryId))
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    String.format("No Category found for ID : %s", categoryId)));
  }

  @Override
  public List<ReadableProductVariant> categoryProductVariants(
      Long categoryId, MerchantStore store, Language language) {
    Category category = categoryService.getById(categoryId, store.getId());

    List<ReadableProductVariant> variants = new ArrayList<ReadableProductVariant>();

    if (category == null) {
      throw new ResourceNotFoundException("Category [" + categoryId + "] not found");
    }

    try {
      List<ProductAttribute> attributes =
          productAttributeService.getProductAttributesByCategoryLineage(
              store, category.getLineage(), language);

      /** Option NAME OptionValueName OptionValueName */
      Map<String, List<ProductOptionValue>> rawFacet =
          new HashMap<String, List<ProductOptionValue>>();
      Map<String, ProductOption> references = new HashMap<String, ProductOption>();
      for (ProductAttribute attr : attributes) {
        references.put(attr.getProductOption().getCode(), attr.getProductOption());
        List<ProductOptionValue> values = rawFacet.get(attr.getProductOption().getCode());
        if (values == null) {
          values = new ArrayList<ProductOptionValue>();
          rawFacet.put(attr.getProductOption().getCode(), values);
        }
        values.add(attr.getProductOptionValue());
      }

      // for each reference set Option
      Iterator<Entry<String, ProductOption>> it = references.entrySet().iterator();
      while (it.hasNext()) {
        @SuppressWarnings("rawtypes")
        Map.Entry pair = (Map.Entry) it.next();
        ProductOption option = (ProductOption) pair.getValue();
        List<ProductOptionValue> values = rawFacet.get(option.getCode());

        ReadableProductVariant productVariant = new ReadableProductVariant();
        productVariant.setName(option.getDescriptionsSettoList().get(0).getName());
        List<ReadableProductVariantValue> optionValues =
            new ArrayList<ReadableProductVariantValue>();
        for (ProductOptionValue value : values) {
          ReadableProductVariantValue v = new ReadableProductVariantValue();
          v.setName(value.getDescriptionsSettoList().get(0).getName());
          v.setOption(option.getId());
          v.setValue(value.getId());
          optionValues.add(v);
        }
        productVariant.setOptions(optionValues);
        variants.add(productVariant);
      }

      return variants;
    } catch (Exception e) {
      throw new ServiceRuntimeException("An error occured while retrieving ProductAttributes", e);
    }
  }

  @Override
  public void move(Long child, Long parent, MerchantStore store) {

    Validate.notNull(child, "Child category must not be null");
    Validate.notNull(parent, "Parent category must not be null");
    Validate.notNull(store, "Merhant must not be null");
    try {

      Category c = categoryService.getById(child, store.getId());
      Category p = categoryService.getById(parent, store.getId());

      if (c.getParent().getId() == parent) {
        return;
      }

      if (c.getMerchantStore().getId().intValue() != store.getId().intValue()) {
        throw new OperationNotAllowedException(
            "Invalid identifiers for Merchant [" + c.getMerchantStore().getCode() + "]");
      }

      if (p.getMerchantStore().getId().intValue() != store.getId().intValue()) {
        throw new OperationNotAllowedException(
            "Invalid identifiers for Merchant [" + c.getMerchantStore().getCode() + "]");
      }

      p.getAuditSection().setModifiedBy("Api");
      categoryService.addChild(p, c);

    } catch (Exception e) {
      throw new ServiceRuntimeException(e);
    }
  }

  @Override
  public Category getByCode(String code, MerchantStore store) {
    try {
      return categoryService.getByCode(store, code);
    } catch (ServiceException e) {
      throw new ServiceRuntimeException("Exception while reading category code [" + code + "]", e);
    }
  }

  @Override
  public void setVisible(PersistablesCategory category, MerchantStore store) {
    Validate.notNull(category, "Category must not be null");
    Validate.notNull(store, "Store must not be null");
    try {
      Category c = this.getById(store, category.getId());
      c.setVisible(category.isVisible());
      categoryService.saveOrUpdate(c);
    } catch (Exception e) {
      throw new ServiceRuntimeException(
          "Error while getting category [" + category.getId() + "]", e);
    }
  }

  /**
   * Admin Portal CRUD Operation API's
   *
   * @param persistableCategory
   * @param store
   */
  @Override
  public com.salesmanager.shop.model.productAttribute.PersistableCategory createCategory(
      com.salesmanager.shop.model.productAttribute.PersistableCategory persistableCategory,
      MerchantStore store) {
    String code = persistableCategory.getCode();
    if (org.apache.commons.lang.StringUtils.isNotEmpty(code)
        && (persistableCategory.getId() == null || persistableCategory.getId().longValue() == 0)) {
      Category categoryExist = null;
      categoryExist = getCategoryByCode(code);
      if (Objects.nonNull(categoryExist)) {
        LOGGER.error("Category Already exists for code {}", code);
        throw new ResourceDuplicateException("Category Already exists for " + code);
      }
    }
    Category category = null;
    try {
      category = categoryMapper.toCategory(persistableCategory);
      setParentDetailsAtChildLevel(category, store);
      category = categoryService.createOrUpdate(category);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.CATEGORY_CREATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.CATEGORY_CREATE_FAILURE.getErrorMessage());
    }
    return categoryMapper.toPersistableCategory(category);
  }

  public void setParentDetailsAtChildLevel(Category dbCategory, MerchantStore store) {
    dbCategory.setMerchantStore(store);
    CategoryDetails categoryDetails = dbCategory.getCategoryDetails();
    if (Objects.nonNull(categoryDetails) && Objects.nonNull(categoryDetails.getNutritionalInfo())) {
      dbCategory.getCategoryDetails().getNutritionalInfo().getNutrientsFacts().stream()
          .forEach(
              nutrientsFact ->
                  nutrientsFact.setNutritionalInfo(categoryDetails.getNutritionalInfo()));
    }
    if (Objects.nonNull(dbCategory.getCategoryFlavourCounts())) {
      dbCategory.getCategoryFlavourCounts().stream()
          .forEach(categoryFlavourCount -> categoryFlavourCount.setCategory(dbCategory));
    }
    if (Objects.nonNull(dbCategory.getCategoryPackCounts())) {
      dbCategory.getCategoryPackCounts().stream()
          .forEach(categoryPackCount -> categoryPackCount.setCategory(dbCategory));
    }
  }

  /**
   * @param persistableCategory
   * @param merchantStore
   */
  @Override
  public com.salesmanager.shop.model.productAttribute.PersistableCategory updateCategory(
      com.salesmanager.shop.model.productAttribute.PersistableCategory persistableCategory,
      MerchantStore merchantStore) {
    Category dbCategory =
        categoryService.getById(persistableCategory.getId(), merchantStore.getId());
    Category category = null;
    try {
      category = categoryMapper.toCategory(persistableCategory);
      category.setFlavours(dbCategory.getFlavours());
      category.setPackSize(dbCategory.getPackSize());
      category.setCategoryFlavourCounts(dbCategory.getCategoryFlavourCounts());
      category.setCategoryPackCounts(dbCategory.getCategoryPackCounts());
      setParentDetailsAtChildLevel(category, merchantStore);
      category = categoryService.createOrUpdate(category);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.CATEGORY_UPDATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.CATEGORY_UPDATE_FAILURE.getErrorMessage());
    }
    return categoryMapper.toPersistableCategory(category);
  }

  /** @param id */
  @Override
  public void deleteCategory(Long id) {
    Validate.notNull(id, "Category id cannot be null");
    Category category = getCategory(id);
    try {
      categoryService.delete(category);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.CATEGORY_DELETE_FAILURE.getErrorCode(),
          AttributeErrorCodes.CATEGORY_DELETE_FAILURE.getErrorMessage() + id);
    }
  }

  /**
   * @param id
   * @param store
   * @param language
   * @return
   */
  @Override
  public com.salesmanager.shop.model.productAttribute.PersistableCategory getCategory(
      Long id, MerchantStore store, Language language) {
    Validate.notNull(id, "Category id cannot be null");
    Category category = getCategory(id);
    com.salesmanager.shop.model.productAttribute.PersistableCategory persistableCategory =
        categoryMapper.toPersistableCategory(category);
    LinkedHashSet<PersistableNutrientsFact> nutrientsFacts =
        persistableCategory.getCategoryDetails().getNutritionalInfo().getNutrientsFacts();
    LinkedHashSet<PersistableNutrientsInfo> nutrientsInfo =
        persistableCategory.getCategoryDetails().getNutritionalInfo().getNutrientsInfo();

    List<PersistableNutrientsInfo> listNutrientInfo = new ArrayList<>(nutrientsInfo);
    List<PersistableNutrientsFact> listFact = new ArrayList<>(nutrientsFacts);

    Collections.sort(listNutrientInfo);
    Collections.sort(listFact);

    persistableCategory
        .getCategoryDetails()
        .getNutritionalInfo()
        .setNutrientsInfo(new LinkedHashSet<>(listNutrientInfo));
    persistableCategory
        .getCategoryDetails()
        .getNutritionalInfo()
        .setNutrientsFacts(new LinkedHashSet<>(listFact));

    return persistableCategory;
  }

  /**
   * @param page
   * @param pageSize
   * @param store
   * @return
   * @throws Exception
   */
  @Override
  public PersistableCategoryList getAllUniqueCategory(
      Integer page, Integer pageSize, MerchantStore store) throws Exception {
    PersistableCategoryList persistableCategoryList = new PersistableCategoryList();
    List<Category> categoryList = null;
    try {
      categoryList = categoryService.getCategoryIds();
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.CATEGORY_GET_ALL_CATEGORY_IDS_FAILURE.getErrorCode(),
          AttributeErrorCodes.CATEGORY_GET_ALL_CATEGORY_IDS_FAILURE.getErrorMessage() + "");
    }
    for (Category category : categoryList) {
      persistableCategoryList.getCategorys().add(categoryMapper.toPersistableCategory(category));
    }
    return persistableCategoryList;
  }

  private Category getCategoryByCode(String code) {
    Category category = null;
    try {
      category = categoryService.getByCode(code);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.CATEGORY_CREATE_FAILURE.getErrorCode(),
          AttributeErrorCodes.CATEGORY_CREATE_FAILURE.getErrorMessage() + code);
    }
    return category;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }

  private Category getCategory(Long id) {
    Category category = null;
    try {
      category = categoryService.getById(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          AttributeErrorCodes.CATEGORY_GET_BY_CATEGORY_ID.getErrorCode(),
          AttributeErrorCodes.CATEGORY_GET_BY_CATEGORY_ID.getErrorMessage() + id);
    }
    if (Objects.isNull(category)) {
      LOGGER.error("Category with id [" + id + " ] not found in DB.");
      throw new ResourceNotFoundException("Category not found");
    }
    return category;
  }
}
