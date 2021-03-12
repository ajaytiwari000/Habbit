package com.salesmanager.shop.store.model.search;

import org.json.simple.JSONObject;

public class AutoCompleteRequest {

  private String merchantCode;
  private String languageCode;

  private static final String WILDCARD_QUERY = "wildcard";
  private static final String KEYWORD = "keyword";
  private static final String UNDERSCORE = "_";
  private static final String ALL = "*";
  private static final String TYPE = "type";
  private static final String ANALYZER = "analyzer";
  private static final String STD_ANALYZER = "standard";
  private static final String TYPE_PHRASE = "phrase_prefix";
  private static final String QUERY = "query";
  private static final String MATCH = "match";

  public AutoCompleteRequest(String merchantCode, String languageCode) {
    this.merchantCode = merchantCode;
    this.languageCode = languageCode;
  }

  @SuppressWarnings("unchecked")
  @Deprecated
  public String toJSONString(String query) {

    // {"size": 10,"query": {"match": {"keyword": {"query": "wat","operator":"and"}}}}";

    JSONObject keyword = new JSONObject();
    JSONObject q = new JSONObject();
    JSONObject mq = new JSONObject();
    JSONObject match = new JSONObject();

    q.put(QUERY, query);
    q.put(ANALYZER, STD_ANALYZER);

    keyword.put(KEYWORD, q);

    match.put(MATCH, keyword);

    mq.put(QUERY, match);
    return mq.toJSONString();
  }

  /** keyword_en_default * */
  public String getCollectionName() {
    StringBuilder qBuilder = new StringBuilder();
    qBuilder
        .append(KEYWORD)
        .append(UNDERSCORE)
        .append(getLanguageCode())
        .append(UNDERSCORE)
        .append(getMerchantCode());

    return qBuilder.toString().toLowerCase();
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public String getLanguageCode() {
    return languageCode;
  }

  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }
}
