package com.salesmanager.core.business.modules.payment.gateway.constants;

// TODO : Need to have application properties level segregation based on env - TEST/PROD
public class RazorPayConstants {
  public static final String RAZOR_PAY_ACCESS_KEY_ID =
      "rzp_test_AJAfeNwvO5x4yH"; // ""rzp_live_oPQUsbMtz0MhBt";
  public static final String RAZOR_PAY_SECRET_KEY_ID =
      "vhC8OZpG9cSOCEH23D0xYEwc"; // "0yVWFAEoyXFBFSkuJIALEaf5";

  public static String getRazorPayAccessKeyId() {
    return RAZOR_PAY_ACCESS_KEY_ID;
  }

  public static String getRazorPaySecretKeyId() {
    return RAZOR_PAY_SECRET_KEY_ID;
  }
}
