package com.salesmanager.core.business.modules.payment.gateway;

import com.razorpay.*;
import com.salesmanager.core.business.modules.payment.gateway.client.RazorPayServiceClient;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorPayServiceProvider implements PaymentGatewayServiceProvider {
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

  private static final Logger LOGGER = LoggerFactory.getLogger(RazorPayServiceProvider.class);

  public Order createOrder(int amount, String orderCode) throws RazorpayException {
    RazorpayClient razorPay = RazorPayServiceClient.getRazorPayClient();
    JSONObject options = new JSONObject();
    options.put("amount", amount * 100); // amount in paise
    options.put("currency", "INR");
    options.put("receipt", orderCode);
    options.put("payment_capture", 1);
    try {
      return razorPay.Orders.create(options);
    } catch (RazorpayException e) {
      throw new RazorpayException("RazorPay Order creation failed", e);
    }
  }

  public Order getOrder(String razorPayOrderId) throws RazorpayException {
    RazorpayClient razorPay = RazorPayServiceClient.getRazorPayClient();
    try {
      return razorPay.Orders.fetch(razorPayOrderId);
    } catch (RazorpayException e) {
      throw new RazorpayException("RazorPay Order fetch failed for Order", e);
    }
  }

  public List<Payment> getPaymentsForOrder(String razorPayOrderId) throws RazorpayException {
    RazorpayClient razorPay = RazorPayServiceClient.getRazorPayClient();
    try {
      return razorPay.Orders.fetchPayments(razorPayOrderId);
    } catch (RazorpayException e) {
      throw new RazorpayException("RazorPay Payment details fetch failed for Order", e);
    }
  }

  public Customer createCustomer(String name, String email, String customerPhone)
      throws RazorpayException {
    RazorpayClient razorPay = RazorPayServiceClient.getRazorPayClient();
    JSONObject options = new JSONObject();
    options.put("name", name);
    options.put("contact", customerPhone);
    options.put("email", email);
    try {
      return razorPay.Customers.create(options);
    } catch (RazorpayException e) {
      throw new RazorpayException("RazorPay Customer creation failed", e);
    }
  }

  public boolean verifyPaymentSignature(
      String orderCode, String paymentId, String razorpaySignature) throws RazorpayException {
    JSONObject options = new JSONObject();
    if (StringUtils.isNotBlank(paymentId)
        && StringUtils.isNotBlank(razorpaySignature)
        && StringUtils.isNotBlank(orderCode)) {
      try {
        options.put("razorpay_payment_id", paymentId);
        options.put("razorpay_order_id", orderCode);
        options.put("razorpay_signature", razorpaySignature);
        return Utils.verifyPaymentSignature(options, secretKey);
      } catch (RazorpayException e) {
        throw new RazorpayException("RazorPay Customer verification failed", e);
      }
    }
    return false;
  }

  public Refund refundOrder(int amount, String paymentId, boolean fullRefund)
      throws RazorpayException {
    Refund refund = null;
    RazorpayClient razorpay = RazorPayServiceClient.getRazorPayClient();
    try {
      if (fullRefund) {
        // Full Refund
        refund = razorpay.Payments.refund(paymentId);
      } else {
        // Partial Refund
        JSONObject refundRequest = new JSONObject();
        refundRequest.put("amount", amount * 100); // Amount should be in paise
        refund = razorpay.Payments.refund(paymentId, refundRequest);
      }
    } catch (RazorpayException e) {
      throw new RazorpayException("RazorPay payment refund failed", e);
    }
    return refund;
  }
}
