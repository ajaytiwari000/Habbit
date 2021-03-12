package com.salesmanager.test.shop.integration.customer;

import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.shop.model.customer.PersistableCustomer;
import com.salesmanager.shop.model.customer.address.Address;
import com.salesmanager.shop.model.security.AuthenticationRequest;
import com.salesmanager.shop.model.security.AuthenticationResponse;
import com.salesmanager.test.shop.common.ServicesTestSupport;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public class CustomerRegistrationIntegrationTest extends ServicesTestSupport {

  @Test
  public void registerCustomer() {

    final PersistableCustomer testCustomer = new PersistableCustomer();
    testCustomer.setEmailAddress("customer1@test.com");
    testCustomer.setPassword("clear123");
    testCustomer.setDateOfBirth("20/02/1993");
    testCustomer.setPhoneNumber("1231231231");

    //    testCustomer.setGender(CustomerGender.M.name());
    testCustomer.setLanguage("en");
    final Address billing = new Address();
    billing.setFirstName("customer1");
    billing.setLastName("ccstomer1");
    billing.setCountry("BE");
    //    testCustomer.setBilling(billing);
    testCustomer.setStoreCode(Constants.DEFAULT_STORE);
    final HttpEntity<PersistableCustomer> entity = new HttpEntity<>(testCustomer, getHeader());

    final ResponseEntity<PersistableCustomer> response =
        testRestTemplate.postForEntity(
            "/api/v1/customer/test/register", entity, PersistableCustomer.class);
    // assertThat(response.getStatusCode(), is(OK));

    // created customer can login

    final ResponseEntity<AuthenticationResponse> loginResponse =
        testRestTemplate.postForEntity(
            "/api/v1/customer/test/login",
            new HttpEntity<>(new AuthenticationRequest("customer1@test.com", "clear123")),
            AuthenticationResponse.class);
    // assertThat(loginResponse.getStatusCode(), is(OK));
    // assertNotNull(loginResponse.getBody().getToken());
  }
}
