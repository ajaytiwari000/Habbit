package com.salesmanager.core.model.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.catalog.product.review.ProductReview;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.customer.attribute.CustomerAttribute;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.orderstatus.CustomerOrderStatusHistory;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shoppingcart.Cart;
import com.salesmanager.core.model.user.Group;
import com.salesmanager.core.utils.CloneUtils;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "CUSTOMER", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class Customer extends SalesManagerEntity<Long, Customer> implements Auditable {
  private static final long serialVersionUID = -6966934116557219193L;

  @Id
  @Column(name = "CUSTOMER_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CUSTOMER_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @JsonIgnore @Embedded private AuditSection auditSection = new AuditSection();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customer")
  private Set<CustomerAttribute> attributes = new HashSet<CustomerAttribute>();

  @Column(name = "CUSTOMER_GENDER", length = 2, nullable = true)
  @Enumerated(value = EnumType.STRING)
  private CustomerGender gender;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CUSTOMER_DOB")
  private Date dateOfBirth;

  //  @Email
  //  @NotEmpty
  @Column(name = "CUSTOMER_EMAIL_ADDRESS", length = 96) // , nullable = false
  private String emailAddress;

  @Column(name = "CUSTOMER_COMPANY", length = 100)
  private String company;

  @JsonIgnore
  @Column(name = "CUSTOMER_PASSWORD", length = 60)
  private String password;

  @Column(name = "CUSTOMER_ANONYMOUS")
  private boolean anonymous;

  @Column(name = "REVIEW_AVG")
  private BigDecimal customerReviewAvg;

  @Column(name = "REVIEW_COUNT")
  private Integer customerReviewCount;

  @Column(name = "USERNAME", unique = true)
  private String userName;

  @Column(name = "PHONE_NUMBER", unique = true)
  private String phoneNumber;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "EMAIL_VERIFIED")
  private boolean emailVerified;

  @Column(name = "PREMIUM_CUSTOMER")
  private boolean premiumCustomer;

  @Column(name = "PROFILE_COMPLETED")
  private boolean profileCompleted;

  @Column(name = "FRIEND_PHONE")
  private String friendPhone;

  @Column(name = "REFERRAL_CODE")
  private String referralCode;

  @Column(name = "REFERRER_CODE")
  private String referrerCode;

  @Column(name = "IMAGE_URL")
  private String imageUrl;

  @Column(name = "IMAGE")
  private String image;

  @Column(name = "FIRST_ORDER_PLACED")
  private boolean firstOrderPlaced;

  @Column(name = "REFERRAL_CODE_APPLIED")
  private boolean referralCodeApplied;

  @Column(name = "REWARD_POINT")
  private Integer rewardPoint;

  @Column(name = "MEMBER_SHIP")
  private String memberShip;

  @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  private List<Address> addressList = new ArrayList<Address>();

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "CART_ID", nullable = true, updatable = true)
  private Cart cart;

  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Language.class)
  @JoinColumn(name = "LANGUAGE_ID", nullable = false)
  private Language defaultLanguage;

  @OneToMany(mappedBy = "customer", targetEntity = ProductReview.class)
  private List<ProductReview> reviews = new ArrayList<ProductReview>();

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  private Set<CustomerOrderStatusHistory> orderHistory = new LinkedHashSet<>();

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MERCHANT_ID", nullable = false)
  private MerchantStore merchantStore;

  @JsonIgnore
  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.REFRESH})
  @JoinTable(
      name = "CUSTOMER_GROUP",
      schema = SchemaConstant.SALESMANAGER_SCHEMA,
      joinColumns = {@JoinColumn(name = "CUSTOMER_ID", nullable = false, updatable = false)},
      inverseJoinColumns = {@JoinColumn(name = "GROUP_ID", nullable = false, updatable = false)})
  @Cascade({
    org.hibernate.annotations.CascadeType.DETACH,
    org.hibernate.annotations.CascadeType.LOCK,
    org.hibernate.annotations.CascadeType.REFRESH,
    org.hibernate.annotations.CascadeType.REPLICATE
  })
  private List<Group> groups = new ArrayList<Group>();

  @Column(name = "RAZOR_PAY_CUSTOMER_ID")
  private String razorPayCustomerId;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "CUS_NOTIFY_ID", nullable = true, updatable = true)
  private CustomerNotification customerNotification;

  @Transient private InputStream inputStream = null;

  public Customer() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getDateOfBirth() {
    return CloneUtils.clone(dateOfBirth);
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = CloneUtils.clone(dateOfBirth);
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
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

  public List<ProductReview> getReviews() {
    return reviews;
  }

  public void setReviews(List<ProductReview> reviews) {
    this.reviews = reviews;
  }

  public void setMerchantStore(MerchantStore merchantStore) {
    this.merchantStore = merchantStore;
  }

  public MerchantStore getMerchantStore() {
    return merchantStore;
  }

  public void setGroups(List<Group> groups) {
    this.groups = groups;
  }

  public List<Group> getGroups() {
    return groups;
  }

  public Language getDefaultLanguage() {
    return defaultLanguage;
  }

  public void setDefaultLanguage(Language defaultLanguage) {
    this.defaultLanguage = defaultLanguage;
  }

  public void setAttributes(Set<CustomerAttribute> attributes) {
    this.attributes = attributes;
  }

  public Set<CustomerAttribute> getAttributes() {
    return attributes;
  }

  public void setGender(CustomerGender gender) {
    this.gender = gender;
  }

  public CustomerGender getGender() {
    return gender;
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

  @Override
  public AuditSection getAuditSection() {
    return auditSection;
  }

  @Override
  public void setAuditSection(AuditSection auditSection) {
    this.auditSection = auditSection;
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

  public List<Address> getAddressList() {
    return addressList;
  }

  public void setAddressList(List<Address> addressList) {
    this.addressList = addressList;
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

  public Cart getCart() {
    return cart;
  }

  public void setCart(Cart cart) {
    this.cart = cart;
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

  public Set<CustomerOrderStatusHistory> getOrderHistory() {
    return orderHistory;
  }

  public void setOrderHistory(Set<CustomerOrderStatusHistory> orderHistory) {
    this.orderHistory = orderHistory;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public InputStream getInputStream() {
    return inputStream;
  }

  public void setInputStream(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  public boolean isProfileCompleted() {
    return profileCompleted;
  }

  public void setProfileCompleted(boolean profileCompleted) {
    this.profileCompleted = profileCompleted;
  }

  public CustomerNotification getCustomerNotification() {
    return customerNotification;
  }

  public void setCustomerNotification(CustomerNotification customerNotification) {
    this.customerNotification = customerNotification;
  }
}
