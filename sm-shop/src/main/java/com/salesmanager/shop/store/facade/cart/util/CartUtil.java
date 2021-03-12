package com.salesmanager.shop.store.facade.cart.util;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CartUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(CartUtil.class);

  public String uniqueShoppingCartCode() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
}
