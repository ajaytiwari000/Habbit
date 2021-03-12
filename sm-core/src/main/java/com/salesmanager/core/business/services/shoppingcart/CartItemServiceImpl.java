package com.salesmanager.core.business.services.shoppingcart;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.shoppingcart.CartItemRepository;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.shoppingcart.CartItem;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("cartItemService")
public class CartItemServiceImpl extends SalesManagerEntityServiceImpl<Long, CartItem>
    implements CartItemService {
  @Inject private ProductService productService;

  private CartItemRepository cartItemRepository;

  private static final Logger LOGGER = LoggerFactory.getLogger(CartItemServiceImpl.class);

  @Inject
  public CartItemServiceImpl(CartItemRepository cartItemRepository) {
    super(cartItemRepository);
    this.cartItemRepository = cartItemRepository;
  }

  @Override
  public void deleteById(Long id) throws ServiceException {
    try {
      cartItemRepository.deleteById(id);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public void deleteAllByCartId(Long id) throws ServiceException {
    try {
      cartItemRepository.deleteAllByCartId(id);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
