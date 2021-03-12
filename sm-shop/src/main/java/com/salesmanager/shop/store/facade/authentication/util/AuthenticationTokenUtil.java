package com.salesmanager.shop.store.facade.authentication.util;

import com.salesmanager.core.model.customer.AuthenticationTokenStatus;
import com.salesmanager.shop.store.security.JWTTokenUtil;
import com.salesmanager.shop.utils.DateUtil;
import java.util.Date;
import java.util.SplittableRandom;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationTokenUtil {
  @Inject private JWTTokenUtil jwtTokenUtil;

  public boolean isNotExpired(Long expirationTime) {
    return expirationTime > DateUtil.getDate().getTime() ? true : false;
  }

  // TODO :: Check if we need to maintain unique Code List to make sure we do not use any Active OTP
  // code
  public String generateCode() {
    StringBuilder generatedOTP = new StringBuilder();
    SplittableRandom splittableRandom = new SplittableRandom();
    for (int i = 0; i < 6; i++) {
      int randomNumber = splittableRandom.nextInt(0, 9);
      generatedOTP.append(randomNumber);
    }
    return generatedOTP.toString();
  }

  public Long calculateExpiryTime(Date date) {
    return date.getTime() + jwtTokenUtil.otpExpiration;
  }

  public boolean isActive(AuthenticationTokenStatus status) {
    return AuthenticationTokenStatus.ACTIVE.name().equals(status.name()) ? true : false;
  }
}
