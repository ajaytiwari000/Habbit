package com.salesmanager.shop.admin.controller.unicom;

import com.salesmanager.core.model.order.CustomerSaleOrder;
import com.salesmanager.core.model.unicommerce.UnicommerceAuthenticationToken;
import com.salesmanager.shop.model.order.*;

public interface UnicommerceService {
  UnicommerceAuthenticationToken createToken() throws Exception;

  UnicommerceAuthenticationToken refreshAccessToken(String refreshToken) throws Exception;

  CustomerSaleOrder createUnicommerceSaleOrder(
      UnicommerceCreateSaleOrderRequest request, UnicommerceAuthenticationToken token)
      throws Exception;

  UnicommerceSearchSaleOrderResponse searchSaleOrder(
      UnicommerceSearchSaleOrderRequest request, UnicommerceAuthenticationToken token)
      throws Exception;

  void getSaleOrder(UnicommerceGetSaleOrderRequest request, UnicommerceAuthenticationToken token)
      throws Exception;

  void cancelSaleOrder(
      UnicommerceCancelSaleOrderRequest request, UnicommerceAuthenticationToken token)
      throws Exception;

  UnicommerceAuthenticationToken getUnicommerceAccessToken();
}
