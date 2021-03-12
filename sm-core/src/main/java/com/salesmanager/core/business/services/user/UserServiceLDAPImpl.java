package com.salesmanager.core.business.services.user;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.common.GenericEntityList;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.user.User;
import com.salesmanager.core.model.user.UserCriteria;
import java.util.List;
import org.springframework.data.domain.Page;

public class UserServiceLDAPImpl implements UserService {

  @Override
  public User save(User entity) throws ServiceException {
    throw new ServiceException("Not implemented");
  }

  @Override
  public User update(User entity) throws ServiceException {
    throw new ServiceException("Not implemented");
  }

  @Override
  public User create(User entity) throws ServiceException {
    throw new ServiceException("Not implemented");
  }

  @Override
  public void delete(User entity) throws ServiceException {
    throw new ServiceException("Not implemented");
  }

  @Override
  public User getById(Long id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<User> list() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long count() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public User getByUserName(String userName) throws ServiceException {
    // TODO Auto-generated method stub
    throw new ServiceException("Not implemented");
  }

  @Override
  public List<User> listUser() throws ServiceException {
    // TODO Auto-generated method stub
    throw new ServiceException("Not implemented");
  }

  @Override
  public void saveOrUpdate(User user) throws ServiceException {
    throw new ServiceException("Not implemented");
  }

  @Override
  public List<User> listByStore(MerchantStore store) throws ServiceException {
    // TODO Auto-generated method stub
    throw new ServiceException("Not implemented");
  }

  /*
   * (non-Javadoc)
   *
   * @see com.salesmanager.core.business.services.common.generic.
   * SalesManagerEntityService#flush()
   */
  @Override
  public void flush() {
    // TODO Auto-generated method stub

  }

  @Override
  public User findByStore(Long userId, String storeCode) throws ServiceException {
    // TODO Auto-generated method stub
    throw new ServiceException("Not implemented");
  }

  @Override
  public GenericEntityList<User> listByCriteria(Criteria criteria) throws ServiceException {
    // TODO Auto-generated method stub
    throw new ServiceException("Not implemented");
  }

  @Override
  public User getByUserName(String userName, String storeCode) throws ServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Page<User> listByCriteria(UserCriteria criteria, int page, int count)
      throws ServiceException {
    // TODO Auto-generated method stub
    return null;
  }
}
