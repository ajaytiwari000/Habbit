/** */
package com.salesmanager.shop.store.facade.customer;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.CustomerCriteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.shop.model.customer.*;
import com.salesmanager.shop.model.customer.address.Address;
import com.salesmanager.shop.model.customer.optin.PersistableCustomerOptin;
import com.salesmanager.shop.populator.customer.ReadableCustomerList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * Customer language working as a bridge between {@link CustomerService} and Controller It will take
 * care about interacting with Service API and doing any pre and post processing
 *
 * @author Umesh Awasthi
 * @version 2.2.1 - 2.7.0
 * @author carlsamson
 */
public interface CustomerFacade {

  /**
   * Method used to fetch customer based on the username and storecode. Customer username is unique
   * to each store.
   *
   * @param userName
   * @param store
   * @param store
   * @param language
   * @throws Exception
   */
  public CustomerEntity getCustomerDataByUserName(
      final String userName, final MerchantStore store, final Language language) throws Exception;

  /**
   * Creates a ReadableCustomer
   *
   * @param id
   * @param merchantStore
   * @param language
   * @return
   */
  ReadableCustomer getCustomerById(
      final Long id, final MerchantStore merchantStore, final Language language);

  /**
   * @param id
   * @param merchantStore
   * @return
   */
  PersistableCustomer getCustomerById(final Long id, final MerchantStore merchantStore);

  /**
   * @param userName
   * @return
   */
  PersistableCustomer getCustomerByUserName(String userName);

  /**
   * Get Customer using unique username
   *
   * @param userName
   * @param merchantStore
   * @param language
   * @return
   * @throws Exception
   */
  ReadableCustomer getByUserName(String userName, MerchantStore merchantStore, Language language);
  /**
   * Method responsible for merging cart during authentication, Method will perform following
   * operations
   * <li>Merge Customer Shopping Cart with Session Cart if any.
   * <li>Convert Customer to {@link CustomerEntity}
   *
   * @param customer username of Customer
   * @param sessionShoppingCartId session shopping cart, if user already have few items in Cart.
   * @throws Exception
   */
  public ShoppingCart mergeCart(
      final Customer customer,
      final String sessionShoppingCartId,
      final MerchantStore store,
      final Language language)
      throws Exception;

  public Customer getCustomerByUserName(final String userName, final MerchantStore store);

  public boolean checkIfUserExists(final String userName, final MerchantStore store)
      throws Exception;

  public PersistableCustomer registerCustomer(
      final PersistableCustomer customer,
      final MerchantStore merchantStore,
      final Language language)
      throws Exception;

  public Address getAddress(
      final Long userId, final MerchantStore merchantStore, boolean isBillingAddress)
      throws Exception;

  public void updateAddress(
      Long userId, MerchantStore merchantStore, Address address, final Language language)
      throws Exception;

  public void setCustomerModelDefaultProperties(Customer customer, MerchantStore store)
      throws Exception;

  public void authenticate(Customer customer, String userName, String password) throws Exception;

  Customer getCustomerModel(
      PersistableCustomer customer, MerchantStore merchantStore, Language language)
      throws Exception;

  Customer populateCustomerModel(
      Customer customerModel,
      PersistableCustomer customer,
      MerchantStore merchantStore,
      Language language)
      throws Exception;

  /*
   * Creates a Customer from a PersistableCustomer received from REST API
   */
  PersistableCustomer create(PersistableCustomer customer, MerchantStore store);

  /**
   * Reset customer password
   *
   * @param customer
   * @param store
   * @param language
   * @throws Exception
   */
  void resetPassword(Customer customer, MerchantStore store, Language language) throws Exception;

  /**
   * Updates a Customer
   *
   * @param customer
   * @param store
   * @throws Exception
   */
  PersistableCustomer update(PersistableCustomer customer, MerchantStore store);

  PersistableCustomer update(String userName, PersistableCustomer customer, MerchantStore store);

  /**
   * Save or update a CustomerReview
   *
   * @param review
   * @param store
   * @param language
   * @throws Exception
   */
  PersistableCustomerReview saveOrUpdateCustomerReview(
      PersistableCustomerReview review, MerchantStore store, Language language);

  /**
   * List all customer reviews by reviewed
   *
   * @param customer
   * @param store
   * @param language
   * @return
   */
  List<ReadableCustomerReview> getAllCustomerReviewsByReviewed(
      Long customerId, MerchantStore store, Language language);

  /**
   * Deletes a customer review
   *
   * @param review
   * @param store
   * @param language
   */
  void deleteCustomerReview(Long customerId, Long reviewId, MerchantStore store, Language language);

  /**
   * Optin a customer to newsletter
   *
   * @param optin
   * @param store
   * @throws Exception
   */
  void optinCustomer(PersistableCustomerOptin optin, MerchantStore store);

  ReadableCustomer getCustomerByNick(
      String userName, MerchantStore merchantStore, Language language);

  void deleteByNick(String nick);

  void deleteById(Long id);

  void delete(Customer entity);

  ReadableCustomerList getListByStore(
      MerchantStore store, CustomerCriteria criteria, Language language);

  PersistableCustomerReview createCustomerReview(
      Long customerId,
      PersistableCustomerReview review,
      MerchantStore merchantStore,
      Language language);

  PersistableCustomerReview updateCustomerReview(
      Long id,
      Long reviewId,
      PersistableCustomerReview review,
      MerchantStore store,
      Language language);

  boolean passwordMatch(String rawPassword, Customer customer);

  void changePassword(Customer customer, String newPassword);

  PersistableCustomer uploadImage(MultipartFile[] files, MerchantStore merchantStore, Long id);

  void removeCustomerImage(Long id);

  boolean getValidateReferralCode(String referralCode, HttpServletRequest request);

  String updateEmail(Long id, String customerEmail, MerchantStore merchantStore);

  boolean newUser(String phone);

  String emailVerification(String email, String secret);
}
