package com.salesmanager.shop.store.facade.order.util;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderUtil {
  private static final Logger LOGGER = LoggerFactory.getLogger(OrderUtil.class);

  public String uniqueCustomerOrderCode() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
}
