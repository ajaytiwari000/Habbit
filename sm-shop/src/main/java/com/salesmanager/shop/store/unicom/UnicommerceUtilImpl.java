package com.salesmanager.shop.store.unicom;

import com.salesmanager.core.model.unicommerce.UnicommerceAuthenticationToken;
import java.util.Calendar;
import org.springframework.stereotype.Service;

@Service("unicommerceUtil")
public class UnicommerceUtilImpl implements UnicommerceUtil {

  @Override
  public boolean isRefreshTokenExpired(UnicommerceAuthenticationToken token) {
    // todo : need to update in every 28 days
    Calendar cal = Calendar.getInstance();
    long miliSec = cal.getTimeInMillis();
    int expirationTime = token.getExpiresIn();
    return expirationTime > (miliSec / 1000) ? false : true;
  }

  @Override
  public boolean isAccessTokenExpired(UnicommerceAuthenticationToken token) {
    Calendar cal = Calendar.getInstance();
    long miliSec = cal.getTimeInMillis();
    int expirationTime = token.getExpiresIn();
    return expirationTime > (miliSec / 1000) ? false : true;
  }
}
