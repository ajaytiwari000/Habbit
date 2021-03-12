package com.salesmanager.test.shop.integration.search;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.shop.application.ShopApplication;
import com.salesmanager.shop.model.catalog.SearchProductList;
import com.salesmanager.shop.model.catalog.SearchProductRequest;
import com.salesmanager.shop.model.catalog.product.PersistablesProduct;
import com.salesmanager.test.shop.common.ServicesTestSupport;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ShopApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Ignore
public class SearchApiIntegrationTest extends ServicesTestSupport {

  @Autowired private TestRestTemplate testRestTemplate;

  /**
   * Add a product then search for it This tests is disabled since it requires Elastic search server
   * started
   *
   * @throws Exception
   */
  @Test
  public void searchItem() throws Exception {

    PersistablesProduct product = super.product("TESTPRODUCT");

    final HttpEntity<PersistablesProduct> entity = new HttpEntity<>(product, getHeader());

    final ResponseEntity<PersistablesProduct> response =
        testRestTemplate.postForEntity(
            "/api/v1/private/product?store=" + Constants.DEFAULT_STORE,
            entity,
            PersistablesProduct.class);
    assertThat(response.getStatusCode(), is(CREATED));

    SearchProductRequest searchRequest = new SearchProductRequest();
    searchRequest.setQuery("TEST");
    final HttpEntity<SearchProductRequest> searchEntity =
        new HttpEntity<>(searchRequest, getHeader());

    final ResponseEntity<SearchProductList> searchResponse =
        testRestTemplate.postForEntity(
            "/api/v1/search?store=" + Constants.DEFAULT_STORE,
            searchEntity,
            SearchProductList.class);
    assertThat(searchResponse.getStatusCode(), is(CREATED));
  }
}
