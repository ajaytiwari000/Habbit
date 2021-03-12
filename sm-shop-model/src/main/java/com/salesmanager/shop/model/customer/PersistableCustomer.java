package com.salesmanager.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesmanager.core.model.customer.CustomerGender;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.entity.Entity;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistableCustomer extends Entity {
  private CustomerGender gender;
  private String dateOfBirth;
  private String emailAddress;
  private String password;
  private boolean anonymous;
  private boolean newEmail;
  private BigDecimal customerReviewAvg;
  private Integer customerReviewCount;
  private String razorPayCustomerId;
  private String userName;
  private String phoneNumber;
  private String firstName;
  private String lastName;
  private boolean emailVerified;
  private boolean premiumCustomer;
  private boolean profileCompleted;
  private String friendPhone;
  private String referralCode;
  private String referrerCode;
  private String imageUrl;
  private String image;
  private boolean firstOrderPlaced;
  private boolean referralCodeApplied;
  private Integer rewardPoint;
  private String memberShip;
  private List<PersistableAddress> addressList;
  private List<PersistableCreditCard> creditCardList;
  private Language defaultLanguage;
  private String language;
  private String storeCode;

  public CustomerGender getGender() {
    return gender;
  }

  public void setGender(CustomerGender gender) {
    this.gender = gender;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isAnonymous() {
    return anonymous;
  }

  public void setAnonymous(boolean anonymous) {
    this.anonymous = anonymous;
  }

  public BigDecimal getCustomerReviewAvg() {
    return customerReviewAvg;
  }

  public void setCustomerReviewAvg(BigDecimal customerReviewAvg) {
    this.customerReviewAvg = customerReviewAvg;
  }

  public Integer getCustomerReviewCount() {
    return customerReviewCount;
  }

  public void setCustomerReviewCount(Integer customerReviewCount) {
    this.customerReviewCount = customerReviewCount;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public boolean isEmailVerified() {
    return emailVerified;
  }

  public void setEmailVerified(boolean emailVerified) {
    this.emailVerified = emailVerified;
  }

  public boolean isPremiumCustomer() {
    return premiumCustomer;
  }

  public void setPremiumCustomer(boolean premiumCustomer) {
    this.premiumCustomer = premiumCustomer;
  }

  public String getReferralCode() {
    return referralCode;
  }

  public void setReferralCode(String referralCode) {
    this.referralCode = referralCode;
  }

  public String getReferrerCode() {
    return referrerCode;
  }

  public void setReferrerCode(String referrerCode) {
    this.referrerCode = referrerCode;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Integer getRewardPoint() {
    return rewardPoint;
  }

  public void setRewardPoint(Integer rewardPoint) {
    this.rewardPoint = rewardPoint;
  }

  public String getMemberShip() {
    return memberShip;
  }

  public void setMemberShip(String memberShip) {
    this.memberShip = memberShip;
  }

  public Language getDefaultLanguage() {
    return defaultLanguage;
  }

  public void setDefaultLanguage(Language defaultLanguage) {
    this.defaultLanguage = defaultLanguage;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getStoreCode() {
    return storeCode;
  }

  public void setStoreCode(String storeCode) {
    this.storeCode = storeCode;
  }

  public List<PersistableAddress> getAddressList() {
    return addressList;
  }

  public void setAddressList(List<PersistableAddress> addressList) {
    this.addressList = addressList;
  }

  public List<PersistableCreditCard> getCreditCardList() {
    return creditCardList;
  }

  public void setCreditCardList(List<PersistableCreditCard> creditCardList) {
    this.creditCardList = creditCardList;
  }

  public String getFriendPhone() {
    return friendPhone;
  }

  public void setFriendPhone(String friendPhone) {
    this.friendPhone = friendPhone;
  }

  public String getRazorPayCustomerId() {
    return razorPayCustomerId;
  }

  public void setRazorPayCustomerId(String razorPayCustomerId) {
    this.razorPayCustomerId = razorPayCustomerId;
  }

  public boolean isFirstOrderPlaced() {
    return firstOrderPlaced;
  }

  public void setFirstOrderPlaced(boolean firstOrderPlaced) {
    this.firstOrderPlaced = firstOrderPlaced;
  }

  public boolean isReferralCodeApplied() {
    return referralCodeApplied;
  }

  public void setReferralCodeApplied(boolean referralCodeApplied) {
    this.referralCodeApplied = referralCodeApplied;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public boolean isProfileCompleted() {
    return profileCompleted;
  }

  public void setProfileCompleted(boolean profileCompleted) {
    this.profileCompleted = profileCompleted;
  }

  public boolean isNewEmail() {
    return newEmail;
  }

  public void setNewEmail(boolean newEmail) {
    this.newEmail = newEmail;
  }
}
