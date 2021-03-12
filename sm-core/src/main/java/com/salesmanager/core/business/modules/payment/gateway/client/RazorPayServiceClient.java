package com.salesmanager.core.business.modules.payment.gateway.client;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorPayServiceClient {

  public static String accessKey;

  @Value("${razorPay.access.key}")
  public void setRazorPayAccessID(String key) {
    accessKey = key;
  }

  public static String secretKey;

  @Value("${razorPay.secret.key}")
  public void setRazorPaySecretId(String key) {
    secretKey = key;
  }

  private static RazorpayClient razorPayClient;

  public static RazorpayClient getRazorPayClient() throws RazorpayException {
    try {
      razorPayClient = new RazorpayClient(accessKey, secretKey);
    } catch (RazorpayException e) {
      throw new RazorpayException("RazorPay Client Init Failed", e);
    }
    return razorPayClient;
  }
}
