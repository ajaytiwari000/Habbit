package com.salesmanager.shop.store.facade.authentication.util;

import com.salesmanager.core.business.HabbitCoreConstant;
import java.security.SecureRandom;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component("customerUtil")
public class CustomerUtil {
  public String getReferralCode() {
    char[] chars = HabbitCoreConstant.ALPHANUMERIC.toCharArray();
    StringBuilder sb = new StringBuilder();
    Random random = new SecureRandom();
    for (int i = 0; i < 6; i++) {
      char c = chars[random.nextInt(chars.length)];
      sb.append(c);
    }
    String output = sb.toString();
    return output;
  }
}
