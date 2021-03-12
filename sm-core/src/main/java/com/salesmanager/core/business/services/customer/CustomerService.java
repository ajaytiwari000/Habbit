package com.salesmanager.core.business.services.customer;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.common.Addresss;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.CustomerCriteria;
import com.salesmanager.core.model.customer.CustomerList;
import com.salesmanager.core.model.merchant.MerchantStore;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerService extends SalesManagerEntityService<Long, Customer> {

  public List<Customer> getByName(String firstName);

  List<Customer> getListByStore(MerchantStore store);

  Customer getByUserName(String nick) throws ServiceException;

  Customer getById(Long id);

  void saveOrUpdate(Customer customer) throws ServiceException;

  CustomerList getListByStore(MerchantStore store, CustomerCriteria criteria);

  Customer getByUserName(String nick, int storeId);

  /**
   * Return an {@link com.salesmanager.core.business.common.model.Address} object from the client IP
   * address. Uses underlying GeoLocation module
   *
   * @param store
   * @param ipAddress
   * @return
   * @throws ServiceException
   */
  Addresss getCustomerAddress(MerchantStore store, String ipAddress) throws ServiceException;

  Customer addCustomerImage(Long id, MultipartFile[] files) throws ServiceException;

  void removeCustomerImage(Customer customer) throws ServiceException;
}
