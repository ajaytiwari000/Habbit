package com.salesmanager.core.business.constants;

import java.nio.charset.Charset;
import java.util.Currency;
import java.util.Locale;

/**
 * Constants used for sm-core
 *
 * @author carlsamson
 */
public class Constants {

  public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
  public static final Charset UTF_8 = Charset.forName("UTF-8");

  public static final String TEST_ENVIRONMENT = "TEST";
  public static final String PRODUCTION_ENVIRONMENT = "PROD";
  public static final String SHOP_URI = "/shop";

  public static final String ALL_REGIONS = "*";

  public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
  public static final String DATE_FORMAT = "yyyy-MM-dd HH:MM:SS";
  public static final String DEFAULT_DATE_FORMAT_YEAR = "yyyy";
  public static final String DEFAULT_LANGUAGE = "en";
  public static final String DEFAULT_COUNTRY = "CA";

  public static final String EMAIL_CONFIG = "EMAIL_CONFIG";

  public static final String UNDERSCORE = "_";
  public static final String SLASH = "/";
  public static final String TRUE = "true";
  public static final String FALSE = "false";
  public static final String OT_ITEM_PRICE_MODULE_CODE = "itemprice";
  public static final String OT_SUBTOTAL_MODULE_CODE = "subtotal";
  public static final String OT_TOTAL_MODULE_CODE = "total";
  public static final String OT_SHIPPING_MODULE_CODE = "shipping";
  public static final String OT_HANDLING_MODULE_CODE = "handling";
  public static final String OT_TAX_MODULE_CODE = "tax";
  public static final String OT_REFUND_MODULE_CODE = "refund";
  public static final String OT_DISCOUNT_TITLE = "order.total.discount";

  public static final String DEFAULT_STORE = "DEFAULT";

  public static final Locale DEFAULT_LOCALE = Locale.US;
  public static final Currency DEFAULT_CURRENCY = Currency.getInstance(Locale.US);
  public static final String CORRELATION_ID_HEADER_NAME = "correlation-id";
  public static final String CORRELATION_ID_LOG_VAR_NAME = "correlationId";
}
