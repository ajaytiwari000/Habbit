package com.salesmanager.core.business.services.shoppingcart;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.shoppingcart.CartRepository;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.shoppingcart.Cart;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("cartService")
public class CartServiceImpl extends SalesManagerEntityServiceImpl<Long, Cart>
    implements CartService {

  private CartRepository cartRepository;

  @Inject private ProductService productService;

  private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

  @Inject
  public CartServiceImpl(CartRepository CartRepository) {
    super(CartRepository);
    this.cartRepository = CartRepository;
  }

  @Override
  public Cart getCartByCustomerPhone(String phone) throws ServiceException {
    try {
      Cart cart = cartRepository.getByCustomerPhone(phone).orElse(null);
      if (Objects.nonNull(cart)) {
        cart.setCartItems(
            Optional.ofNullable(cart.getCartItems())
                .map(Collection::stream)
                .orElseGet(Stream::empty)
                .filter(cartItem -> cartItem.getQuantity() > 0)
                .collect(Collectors.toList()));
      }
      return cart;
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public Cart getAllCartByCustomerPhone(String phone) throws ServiceException {
    try {
      return cartRepository.getByCustomerPhone(phone).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public Cart getById(Long cartId) {
    Cart cart = cartRepository.findById(cartId).orElse(null);
    if (Objects.nonNull(cart)) {
      cart.setCartItems(
          Optional.ofNullable(cart.getCartItems())
              .map(Collection::stream)
              .orElseGet(Stream::empty)
              .filter(cartItem -> cartItem.getQuantity() > 0)
              .collect(Collectors.toList()));
    }
    return cart;
  }
}
