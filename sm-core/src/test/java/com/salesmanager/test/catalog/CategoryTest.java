package com.salesmanager.test.catalog;

import static org.junit.Assert.assertNotNull;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategoryDescription;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import java.util.HashSet;
import java.util.Set;
import junit.framework.Assert;
import org.junit.Test;

public class CategoryTest extends com.salesmanager.test.common.AbstractSalesManagerCoreTestCase {

  /**
   * This method creates multiple products using multiple catalog APIs
   *
   * @throws ServiceException
   */
  @Test
  public void testCategory() throws Exception {

    Language en = languageService.getByCode("en");
    Language fr = languageService.getByCode("fr");

    MerchantStore store = merchantService.getByCode(MerchantStore.DEFAULT_STORE);

    Category materingstuff = new Category();
    materingstuff.setMerchantStore(store);
    materingstuff.setCode("materingstuff");

    CategoryDescription bookEnglishDescription = new CategoryDescription();
    bookEnglishDescription.setName("Book");
    bookEnglishDescription.setCategory(materingstuff);
    bookEnglishDescription.setLanguage(en);

    CategoryDescription bookFrenchDescription = new CategoryDescription();
    bookFrenchDescription.setName("Livre");
    bookFrenchDescription.setCategory(materingstuff);
    bookFrenchDescription.setLanguage(fr);

    Set<CategoryDescription> descriptions = new HashSet<CategoryDescription>();
    descriptions.add(bookEnglishDescription);
    descriptions.add(bookFrenchDescription);
    materingstuff.setDescriptions(descriptions);

    categoryService.create(materingstuff);

    assertNotNull(materingstuff.getId());

    Long bookId = materingstuff.getId();

    Category fetchedBook = categoryService.getById(bookId, store.getId());
    Assert.assertTrue(2 == fetchedBook.getDescriptions().size());
  }
}
