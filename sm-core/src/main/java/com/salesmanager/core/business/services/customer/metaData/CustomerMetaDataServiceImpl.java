package com.salesmanager.core.business.services.customer.metaData;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.customer.metaData.CustomerMetaDataRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.customer.CustomerMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerMetaDataService")
public class CustomerMetaDataServiceImpl
    extends SalesManagerEntityServiceImpl<Long, CustomerMetaData>
    implements CustomerMetaDataService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerMetaDataServiceImpl.class);

  private CustomerMetaDataRepository customerMetaDataRepository;

  @Inject
  public CustomerMetaDataServiceImpl(CustomerMetaDataRepository customerMetaDataRepository) {
    super(customerMetaDataRepository);
    this.customerMetaDataRepository = customerMetaDataRepository;
  }

  @Override
  public CustomerMetaData getByPhone(String phone) throws ServiceException {
    try {
      return customerMetaDataRepository.getByPhone(phone).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public List<CustomerMetaData> getMetaDataList(Date from, Date to) throws ServiceException {
    try {
      return customerMetaDataRepository.getMetaDataList(from, to).orElse(new ArrayList<>());
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
