package com.salesmanager.core.business.services.customer;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.customer.CustomerRepository;
import com.salesmanager.core.business.repositories.customer.aws.CustomerFileManager;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.services.customer.attribute.CustomerAttributeService;
import com.salesmanager.core.model.common.Addresss;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.CustomerCriteria;
import com.salesmanager.core.model.customer.CustomerList;
import com.salesmanager.core.model.customer.attribute.CustomerAttribute;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.modules.utils.GeoLocation;
import java.io.InputStream;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service("customerService")
public class CustomerServiceImpl extends SalesManagerEntityServiceImpl<Long, Customer>
    implements CustomerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

  private CustomerRepository customerRepository;

  @Inject private CustomerAttributeService customerAttributeService;

  @Inject private GeoLocation geoLocation;

  @Inject private CustomerFileManager customerFileManager;

  @Inject
  public CustomerServiceImpl(CustomerRepository customerRepository) {
    super(customerRepository);
    this.customerRepository = customerRepository;
  }

  @Override
  public List<Customer> getByName(String firstName) {
    return customerRepository.findByName(firstName);
  }

  @Override
  public Customer getById(Long id) {
    return customerRepository.findById(id).orElse(null);
  }

  @Override
  public Customer getByUserName(String userName) throws ServiceException {
    try {
      return customerRepository.getByUserName(userName).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public Customer getByUserName(String userName, int storeId) {
    return customerRepository.findByNick(userName, storeId);
  }

  @Override
  public List<Customer> getListByStore(MerchantStore store) {
    return customerRepository.findByStore(store.getId());
  }

  @Override
  public CustomerList getListByStore(MerchantStore store, CustomerCriteria criteria) {
    return customerRepository.listByStore(store, criteria);
  }

  @Override
  public Addresss getCustomerAddress(MerchantStore store, String ipAddress)
      throws ServiceException {

    try {
      return geoLocation.getAddress(ipAddress);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public Customer addCustomerImage(Long id, MultipartFile[] files) throws ServiceException {
    Customer customer = null;
    try {
      customer = getById(id);
      // remove old image
      if (!StringUtils.isEmpty(customer.getImageUrl())) {
        removeCustomerImage(customer);
      }
      for (MultipartFile multipartFile : files) {
        if (!multipartFile.isEmpty()) {
          customer.setInputStream(multipartFile.getInputStream());
          customer.setImage(multipartFile.getOriginalFilename());
        }
      }
      Assert.notNull(customer.getImage(), "Image cannot be null");
      InputStream inputStream = customer.getInputStream();
      ImageContentFile cmsContentImage = new ImageContentFile();
      cmsContentImage.setFileName(customer.getImage());
      cmsContentImage.setFile(inputStream);
      cmsContentImage.setFileContentType(FileContentType.CUSTOMER_IMAGE);
      customer = addCustomerImage(customer, cmsContentImage);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(e);
    }
    return customer;
  }

  @Override
  public void removeCustomerImage(Customer customer) throws ServiceException {
    try {
      if (customer != null) {
        customerFileManager.removeImage(customer);
        customer.setImage(null);
        customer.setImageUrl(null);
        update(customer);
      }
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  private Customer addCustomerImage(Customer customer, ImageContentFile inputImage)
      throws ServiceException {
    try {
      Assert.notNull(inputImage.getFile(), "ImageContentFile.file cannot be null");

      customerFileManager.addImage(customer, inputImage);
      customer = update(customer);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(e);
    } finally {
      try {
        if (inputImage.getFile() != null) {
          inputImage.getFile().close();
        }
      } catch (Exception ignore) {

      }
    }
    return customer;
  }

  @Override
  public void saveOrUpdate(Customer customer) throws ServiceException {

    LOGGER.debug("Creating Customer");

    if (customer.getId() != null && customer.getId() > 0) {
      super.update(customer);
    } else {

      super.create(customer);
    }
  }

  public void delete(Customer customer) throws ServiceException {
    customer = getById(customer.getId());

    // delete attributes
    List<CustomerAttribute> attributes =
        customerAttributeService.getByCustomer(customer.getMerchantStore(), customer);
    if (attributes != null) {
      for (CustomerAttribute attribute : attributes) {
        customerAttributeService.delete(attribute);
      }
    }
    customerRepository.delete(customer);
  }
}
