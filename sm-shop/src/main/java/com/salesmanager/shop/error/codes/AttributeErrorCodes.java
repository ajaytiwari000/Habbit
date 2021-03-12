package com.salesmanager.shop.error.codes;

public enum AttributeErrorCodes {

  // Error codes related to API's for Admin Portal

  PACK_CREATE_FAILURE("P_1000", "Error while creating pack"),
  PACK_UPDATE_FAILURE("P_1001", "Error while updating pack"),
  PACK_DELETE_FAILURE("P_1002", "Error while deleting pack for Id "),
  PACK_GET_BY_ID_FAILURE("P_1003", "Error while getting pack for Id "),
  PACK_GET_ALL_FAILURE("P_1004", "Error while getting Packs"),
  PACK_GET_BY_PACK_SIZE_FAILURE("P_1005", "Error while getting pack for packSizeValue "),

  FLAVOUR_CREATE_FAILURE("F_1020", "Error while creating Flavour"),
  FLAVOUR_UPDATE_FAILURE("F_1021", "Error while updating Flavour"),
  FLAVOUR_DELETE_FAILURE("F_1022", "Error while deleting Flavour for Id "),
  FLAVOUR_GET_BY_ID_FAILURE("F_1023", "Error while getting Flavour for Id "),
  FLAVOUR_GET_ALL_FAILURE("F_1024", "Error while getting Flavours"),
  FLAVOUR_GET_BY_NAME_FAILURE("F_1025", "Error while getting Flavour for flavourName  "),

  BOOST_CREATE_FAILURE("B_1040", "Error while creating Boost"),
  BOOST_UPDATE_FAILURE("B_1041", "Error while updating Boost"),
  BOOST_DELETE_FAILURE("B_1042", "Error while deleting Boost for Id  "),
  BOOST_GET_BY_ID_FAILURE("B_1043", "Error while getting Boost for Id "),
  BOOST_GET_ALL_FAILURE("B_1044", "Error while getting Boosts"),
  BOOST_GET_BY_BOOST_TYPE_FAILURE("B_1045", "Error while getting Boost for boost Type "),

  CATEGORY_CREATE_FAILURE("C_1060", "Error while creating category"),
  CATEGORY_UPDATE_FAILURE("C_1061", "Error while updating category"),
  CATEGORY_DELETE_FAILURE("C_1062", "Error while deleting category for Id "),
  CATEGORY_GET_ALL_CATEGORY_IDS_FAILURE("C_1064", "Error while getting category ids"),
  CATEGORY_GET_BY_CATEGORY_ID("C_1065", "Error while getting category for categoryId "),

  PRODUCT_CREATE_FAILURE("PR_1080", "Error while creating product"),
  PRODUCT_UPDATE_FAILURE("PR_1081", "Error while updating product"),
  PRODUCT_DELETE_FAILURE("PR_1082", "Error while deleting product for id "),
  PRODUCT_GET_BY_ID_FAILURE("PR_1083", "Error while getting product for id "),
  PRODUCT_GET_ALL_FAILURE("PR_1084", "Error while getting products"),
  PRODUCT_GET_BY_PRODUCT_SKU_ID_FAILURE("PR_1085", "Error while getting product for productSku "),

  NUTRIENTS_INFO_CREATE_FAILURE("NI_1120", "Error while creating CategoryNutrientsInfo"),
  NUTRIENTS_INFO_UPDATE_FAILURE("NI_1121", "Error while updating CategoryNutrientsInfo"),
  NUTRIENTS_INFO_DELETE_FAILURE("NI_1122", "Error while deleting CategoryNutrientsInfo for Id "),
  NUTRIENTS_INFO_GET_BY_ID_FAILURE("NI_1123", "Error while getting CategoryNutrientsInfo for Id "),
  NUTRIENTS_INFO_GET_ALL_FAILURE("NI_1124", "Error while getting all CategoryNutrientsInfo"),
  NUTRIENTS_INFO_GET_BY_DESCRIPTION_FAILURE(
      "NI_1125", "Error while getting CategoryNutrientsInfo for description "),

  PRODUCT_STICKER_CREATE_FAILURE("PS_1140", "Error while creating productSticker"),
  PRODUCT_STICKER_UPDATE_FAILURE("PS_1141", "Error while updating productSticker"),
  PRODUCT_STICKER_DELETE_FAILURE("PS_1142", "Error while deleting productSticker for Id "),
  PRODUCT_STICKER_GET_BY_ID_FAILURE("PS_1143", "Error while getting productSticker for Id "),
  PRODUCT_STICKER_GET_ALL_FAILURE("PS_1144", "Error while getting all productSticker"),
  PRODUCT_STICKER_GET_BY_BADGE_TEXT_FAILURE(
      "PS_1145", "Error while getting productSticker for badgeText "),

  CATEGORY_REVIEW_CREATE_FAILURE("CR_1160", "Error while creating categoryReview"),
  CATEGORY_REVIEW_UPDATE_FAILURE("CR_1161", "Error while updating categoryReview"),
  CATEGORY_REVIEW_DELETE_FAILURE("CR_1162", "Error while deleting categoryReview for Id "),
  CATEGORY_REVIEW_GET_BY_ID_FAILURE("CR_1163", "Error while getting categoryReview for Id "),
  CATEGORY_REVIEW_GET_ALL_FAILURE("CR_1164", "Error while getting all categoryReview"),
  CATEGORY_REVIEW_GET_BY_CATEGORY_AND_REVIEWER_NAME_FAILURE(
      "CR_1165", "Error while getting categoryReview for category and reviewer name "),

  BOOST_IMAGE_GET_BY_BOOST_ICON_ID_FAILURE(
      "BI_1180", "Error getting boost image by boost icon id "),
  BOOST_IMAGE_GET_BY_BOOST_ID_FAILURE("BI_1181", "Error getting boost image by boost id "),
  BOOST_IMAGE_ADD_BOOST_ICON("BI_1182", "Error adding boost icon"),
  BOOST_IMAGE_REMOVE_BOOST_ICON("BI_1183", "Error removing boost icon for boost Id "),

  PACK_IMAGE_GET_BY_PACK_ICON_ID_FAILURE("PI_1200", "Error while getting pack for packSizeValue "),
  PACK_IMAGE_ADD_PACK_ICON_FAILURE("PI_1201", "Error adding pack icon"),
  PACK_IMAGE_REMOVE_PACK_ICON_FAILURE("PI_1202", "Error removing pack icon"),
  PACK_IMAGE_GET_BY_PACK_ID_FAILURE("PI_1203", "Error getting image by image pack id"),

  CATEGORY_BANNER_ADD_FAILURE("CB_1221", "Error adding category banner"),
  CATEGORY_BANNER_GET_BY_ID_FAILURE("CB_1222", "Error getting category banner by id"),
  CATEGORY_BANNER_REMOVE_FAILURE("CB_1223", "Error removing category banner"),
  CATEGORY_BANNER_GET_BY_CATEGORY_ID_FAILURE(
      "CB_1224", "Error getting category banner by category id"),

  // Error codes related to API's For Customer App

  CATEGORY_GET_ALL_UNIQUE_CATEGORY_IDS_FAILURE("C_2000", "Error while getting unique category Ids"),
  CATEGORY_GET_BY_ID_FAILURE("C_2001", "Error while getting category for Id "),

  PRODUCT_GET_FOR_HOME_FAILURE("P_2030", "Error getting product for home"),
  PRODUCT_GET_BY_FILTER_FAILURE("P_2031", "Error getting products by filter"),
  PRODUCT_GET_BY_CATEGORY_FLAVOR_FAILURE("P_2032", "Error while getting recommended products "),
  PRODUCT_DETAILS_BY_CATEGORY_FLAVOR_PACK_FAILURE(
      "P_2033", "Error getting product details by category and flavor pack"),
  PRODUCT_GET_BY_PRODUCT_ID_FAILURE("P_2034", "Error while getting product for id "),
  PRODUCT_GET_BY_MERCHANDISE_PRODUCT_NAME_FAILURE(
      "P_2035", "Error getting products by merchandise product name"),

  PINCODE_CREATE_FAILURE("PC_2050", "Error while creating pincode"),
  PINCODE_UPDATE_FAILURE("PC_2051", "Error while updating pincode"),
  PINCODE_DELETE_FAILURE("PC_2052", "Error while deleting pincode for Id "),
  PINCODE_GET_BY_ID_FAILURE("PC_2053", "Error while getting pincode for Id "),
  PINCODE_GET_ALL_FAILURE("PC_2054", "Error while getting pincodes"),
  PINCODE_GET_BY_CODE_FAILURE("PC_2055", "Error while getting pincode for Id "),

  REFERRAL_CODE_CREATE_FAILURE("RC_2070", "Error while creating referral code"),
  REFERRAL_CODE_UPDATE_FAILURE("RC_2071", "Error while updating referral code"),
  REFERRAL_CODE_DELETE_FAILURE("RC_2072", "Error while deleting referral code for Id "),
  REFERRAL_CODE_GET_BY_ID_FAILURE("RC_2073", "Error while getting referral code for Id "),
  REFERRAL_CODE_GET_ALL_FAILURE("RC_2074", "Error while getting referral codes"),
  REFERRAL_CODE_GET_BY_CODE_FAILURE("RC_2075", "Error while getting referral code for code "),
  REFERRAL_CODE_GET_BY_USERNAME_FAILURE(
      "RC_2076", "Error while getting referral code for username "),
  REFERRAL_CODE_GET_ALL_OWNER_TYPE_FAILURE("RC_2077", "Error while getting all owner type "),
  REFERRAL_CODE_GET_BY_PHONE_FAILURE("RC_2078", "Error while getting referral code for phone "),
  REFERRAL_CODE_APPLY_OWN("RC_2079", "You cannot use your own Referral Code "),

  REWARD_CONSUMPTION_CRITERIA_CREATE_FAILURE(
      "RC_2090", "Error while creating reward consumption criteria"),
  REWARD_CONSUMPTION_CRITERIA_UPDATE_FAILURE(
      "RC_2091", "Error while updating reward consumption criteria"),
  REWARD_CONSUMPTION_CRITERIA_DELETE_FAILURE(
      "RC_2092", "Error while deleting reward consumption criteria for Id "),
  REWARD_CONSUMPTION_CRITERIA_GET_BY_ID_FAILURE(
      "RC_2093", "Error while getting reward consumption criteria for Id "),
  REWARD_CONSUMPTION_CRITERIA_GET_ALL_FAILURE(
      "RC_2094", "Error while getting reward consumption criterias"),
  REWARD_CONSUMPTION_CRITERIA_GET_BY_TYPE_FAILURE(
      "RC_2095", "Error while getting reward consumption criteria for type "),
  REWARD_CONSUMPTION_CRITERIA_GET_ALL_TIER_NAME_TYPE_FAILURE(
      "RC_2096", "Error while getting all tier name type "),

  MEMBERSHIP_CREATE_FAILURE("MS_3000", "Error while creating membership"),
  MEMBERSHIP_UPDATE_FAILURE("MS_3001", "Error while updating membership"),
  MEMBERSHIP_DELETE_FAILURE("MS_3002", "Error while deleting membership for Id "),
  MEMBERSHIP_GET_BY_ID_FAILURE("MS_3003", "Error while getting membership for Id "),
  MEMBERSHIP_GET_ALL_FAILURE("MS_3004", "Error while getting all membership"),
  MEMBERSHIP_GET_BY_PHONE_FAILURE("MS_3005", "Error while getting membership for phone "),

  COUPON_CODE_CREATE_FAILURE("CC_3020", "Error while creating coupon code"),
  COUPON_CODE_UPDATE_FAILURE("CC_3021", "Error while updating coupon code"),
  COUPON_CODE_DELETE_FAILURE("CC_3022", "Error while deleting coupon code for Id "),
  COUPON_CODE_GET_BY_ID_FAILURE("CC_3023", "Error while getting coupon code for Id "),
  COUPON_CODE_GET_ALL_FAILURE("CC_3024", "Error while getting coupon codes"),
  COUPON_CODE_GET_BY_CODE_FAILURE("CC_3025", "Error while getting coupon code for code "),
  COUPON_CODE_GET_BY_USERNAME_FAILURE("CC_3026", "Error while getting coupon code for username "),
  COUPON_CODE_GET_ALL_DISCOUNT_TYPE_FAILURE("CC_3027", "Error while getting all discount type "),
  COUPON_CODE_GET_ALL_COUPON_CODE_TYPE_FAILURE(
      "CC_3028", "Error while getting all coupon code type "),

  MEMBERSHIP_COLOR_CREATE_FAILURE("MC_3040", "Error while creating membership color"),
  MEMBERSHIP_COLOR_UPDATE_FAILURE("MC_3041", "Error while updating membership color"),
  MEMBERSHIP_COLOR_DELETE_FAILURE("MC_3042", "Error while deleting membership color for Id "),
  MEMBERSHIP_COLOR_GET_BY_ID_FAILURE("MC_3043", "Error while getting membership color for Id "),
  MEMBERSHIP_COLOR_GET_ALL_FAILURE("MC_3044", "Error while getting all membership color"),
  MEMBERSHIP_COLOR_GET_BY_TIER_TYPE_FAILURE(
      "MS_3045", "Error while getting membership color for tier type ");

  private final String errorCode;
  private final String errorMessage;

  AttributeErrorCodes(String errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
