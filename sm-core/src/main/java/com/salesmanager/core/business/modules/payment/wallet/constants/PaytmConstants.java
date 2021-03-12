package com.salesmanager.core.business.modules.payment.wallet.constants;

// TODO : Need to have application properties level segregation based on env - TEST/PROD
public class PaytmConstants {
  public static final String merchantId = "QgTQAY67362731023947"; // FWRkqa14181146451790
  public static final String merchantSecretKey = "U9zvtbLDaveiIV7g"; // 42_HNzhDIeADXp_W
  //  public static final String channelId = "WEB";
  //  public static final String website = "WEBSTAGING"; // DEFAULT
  public static final String industryTypeId = "Retail";
  public static final String paytmInitiateUrl =
      "https://securegw-stage.paytm.in/theia/api/v1/initiateTransaction";
  public static final String paytmShowPageUrl =
      "https://securegw-stage.paytm.in/theia/api/v1/showPaymentPage";
  public static final String callbackUrl =
      "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=";
  public static final String trxToken = "txnToken";

  public static String getMerchantId() {
    return merchantId;
  }

  public static String getMerchantSecretKey() {
    return merchantSecretKey;
  }

  public static String getIndustryTypeId() {
    return industryTypeId;
  }

  public static String getPaytmInitiateUrl() {
    return paytmInitiateUrl;
  }

  public static String getPaytmShowPageUrl() {
    return paytmShowPageUrl;
  }

  public static String getCallbackUrl() {
    return callbackUrl;
  }

  public static String getTrxToken() {
    return trxToken;
  }
}
