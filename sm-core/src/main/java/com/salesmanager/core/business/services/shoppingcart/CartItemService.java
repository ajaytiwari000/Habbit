package com.salesmanager.core.business.services.shoppingcart;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.shoppingcart.CartItem;

public interface CartItemService extends SalesManagerEntityService<Long, CartItem> {

  void deleteById(Long id) throws ServiceException;

  void deleteAllByCartId(Long id) throws ServiceException;
}
