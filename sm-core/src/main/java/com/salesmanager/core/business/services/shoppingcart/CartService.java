package com.salesmanager.core.business.services.shoppingcart;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.shoppingcart.Cart;

public interface CartService extends SalesManagerEntityService<Long, Cart> {

  Cart getCartByCustomerPhone(String phone) throws ServiceException;

  Cart getAllCartByCustomerPhone(String phone) throws ServiceException;

  Cart getById(Long cartId);
}
