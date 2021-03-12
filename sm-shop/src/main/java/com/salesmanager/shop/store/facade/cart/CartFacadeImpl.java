/** */
package com.salesmanager.shop.store.facade.cart;

import com.salesmanager.core.business.HabbitCoreConstant;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.shoppingcart.CartItemService;
import com.salesmanager.core.business.services.shoppingcart.CartService;
import com.salesmanager.core.model.common.Coupon;
import com.salesmanager.core.model.common.RewardConsumptionCriteria;
import com.salesmanager.core.model.common.enumerator.CouponDiscountType;
import com.salesmanager.core.model.common.enumerator.TierType;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.enums.DeliveryChargesType;
import com.salesmanager.core.model.shoppingcart.Cart;
import com.salesmanager.core.model.shoppingcart.CartItem;
import com.salesmanager.shop.admin.controller.checkout.CouponCodeFacade;
import com.salesmanager.shop.admin.controller.customer.membership.CustomerPreAllottedCheckoutQuotaFacade;
import com.salesmanager.shop.admin.controller.customer.membership.MembershipFacade;
import com.salesmanager.shop.admin.controller.customer.membership.RewardConsumptionCriteriaFacade;
import com.salesmanager.shop.cache.util.CacheUtil;
import com.salesmanager.shop.error.codes.CustomerErrorCodes;
import com.salesmanager.shop.error.codes.PaymentErrorCodes;
import com.salesmanager.shop.model.customer.PersistableCouponCode;
import com.salesmanager.shop.model.customer.PersistableCustomerPreAllottedCheckoutQuota;
import com.salesmanager.shop.model.customer.PersistableRewardConsumptionCriteria;
import com.salesmanager.shop.model.customer.mapper.CartItemMapper;
import com.salesmanager.shop.model.customer.mapper.CartMapper;
import com.salesmanager.shop.model.customer.mapper.RewardConsumptionCriteriaMapper;
import com.salesmanager.shop.model.productAttribute.PersistableProduct;
import com.salesmanager.shop.model.productAttribute.PersistableProductAvailability;
import com.salesmanager.shop.model.shoppingcart.PersistableCart;
import com.salesmanager.shop.model.shoppingcart.PersistableCartItem;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.product.facade.ProductFacade;
import com.salesmanager.shop.store.facade.cart.enums.ValidateCartEvent;
import com.salesmanager.shop.store.facade.cart.util.CartUtil;
import com.salesmanager.shop.utils.SessionUtil;
import java.util.Objects;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "cartFacade")
public class CartFacadeImpl implements CartFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(CartFacadeImpl.class);

  @Inject private CartService cartService;
  @Inject private CartItemService cartItemService;
  @Inject private CustomerService customerService;
  @Inject private CartItemMapper cartItemMapper;
  @Inject private CartMapper cartMapper;
  @Inject private CacheUtil cacheUtil;
  @Inject private MembershipFacade membershipFacade;
  @Inject private CouponCodeFacade couponCodeFacade;
  @Inject private CustomerPreAllottedCheckoutQuotaFacade cpcpFacade;
  @Inject private ProductFacade productFacade;
  @Inject private CartUtil cartUtil;
  @Inject private SessionUtil sessionUtil;
  @Inject private RewardConsumptionCriteriaFacade rewardConsumptionCriteriaFacade;
  @Inject private RewardConsumptionCriteriaMapper rewardConsumptionCriteriaMapper;

  @Override
  public Cart update(Cart cartModel) {
    Cart cart = null;
    try {
      cart = cartService.update(cartModel);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.CART_UPDATE_FAILURE.getErrorCode(),
          PaymentErrorCodes.CART_UPDATE_FAILURE.getErrorMessage());
    }
    return cart;
  }

  private Cart create(Cart cartModel) {
    Cart cart = null;
    try {
      cart = cartService.create(cartModel);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.CART_CREATE_FAILURE.getErrorCode(),
          PaymentErrorCodes.CART_CREATE_FAILURE.getErrorMessage());
    }
    return cart;
  }

  private CartItem createCartItem(CartItem cartItem) {
    CartItem cartItemModel = null;
    try {
      cartItemModel = cartItemService.create(cartItem);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.CART_ITEM_CREATE_FAILURE.getErrorCode(),
          PaymentErrorCodes.CART_ITEM_CREATE_FAILURE.getErrorMessage());
    }
    return cartItemModel;
  }

  private CartItem updateCartItem(CartItem cartItem) {
    CartItem cartItemModel = null;
    try {
      cartItemModel = cartItemService.update(cartItem);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.CART_ITEM_UPDATE_FAILURE.getErrorCode(),
          PaymentErrorCodes.CART_ITEM_UPDATE_FAILURE.getErrorMessage());
    }
    return cartItemModel;
  }

  // TODO: create a new API which have list of cartItems for bulk delete -done
  @Override
  public void deleteCartItem(CartItem cartItem) {
    try {
      cartItemService.deleteById(cartItem.getId());
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.CART_ITEM_GET_BY_ID_FAILURE.getErrorCode(),
          PaymentErrorCodes.CART_ITEM_GET_BY_ID_FAILURE.getErrorMessage());
    }
  }

  @Override
  public void deleteAllCartItem(CartItem cartItem) {
    try {
      cartItemService.deleteAllByCartId(cartItem.getId());
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.CART_ITEM_DELETE_ALL_FAILURE.getErrorCode(),
          PaymentErrorCodes.CART_ITEM_DELETE_ALL_FAILURE.getErrorMessage());
    }
  }

  @Override
  public String freeCart(HttpServletRequest request, MerchantStore merchantStore) {
    String phone = sessionUtil.getPhoneByAuthToken(request);
    freeCartByPhone(phone);
    return "Cart got free successfully..!";
  }

  @Override
  public void freeCartByPhone(String phone) {
    String cartCode = cacheUtil.getObjectFromCache(phone, String.class);
    String orderCode = cacheUtil.getObjectFromCache(cartCode, String.class);
    if (StringUtils.isNotEmpty(orderCode)) {
      cacheUtil.deleteObjectFromCache(cartCode);
    }
  }

  @Override
  public PersistableCart getCustomerCart(HttpServletRequest request) {
    String phone = sessionUtil.getPhoneByAuthToken(request);
    return cartMapper.toPersistableCart(getCartByPhone(phone));
  }

  @Override
  public Cart getCartByPhone(String phone) {
    Cart cartModel = null;
    try {
      cartModel = cartService.getCartByCustomerPhone(phone);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.CART_CREATE_FAILURE.getErrorCode(),
          PaymentErrorCodes.CART_CREATE_FAILURE.getErrorMessage());
    }
    PersistableCart persistableCart = cartMapper.toPersistableCart(cartModel);
    // TODO : just re verify if we need to reset it here or not - this check not required
    //    if (persistableCart.getCartItems().size() == 0) {
    //      resetCartValues(persistableCart);
    //    }
    return cartModel;
  }

  @Override
  public PersistableCart createCustomerCartPostSignup(String phone) {
    Cart cartModel = null;
    Customer customer = null;
    Coupon coupon =
        cacheUtil.getObjectFromCache(HabbitCoreConstant.DEFAULT_COUPON_CODE, Coupon.class);
    try {
      customer = customerService.getByUserName(phone);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_GET_BY_USERNAME_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_GET_BY_USERNAME_FAILURE.getErrorMessage());
    }
    if (cartModel == null) {
      TierType tierType = TierType.HABBIT_BLUE;
      cartModel = new Cart();
      cartModel.setCustomerPhone(customer.getPhoneNumber());
      cartModel.setFriendPhone(customer.getFriendPhone());
      cartModel.setRazorPayCustomerId(customer.getRazorPayCustomerId());
      cartModel.setCustomerCouponApplied(false);
      cartModel.setCustomerEmail(customer.getEmailAddress());
      cartModel.setCartCode(cartUtil.uniqueShoppingCartCode());
      if (Objects.nonNull(coupon)) {
        cartModel.setPromoCode(coupon.getCodeName());
        cartModel.setCouponDiscountType(coupon.getCouponDiscountType());
        cartModel.setCouponDiscountVal(coupon.getDiscountVal());
      }
      cartModel.setTierType(tierType);
    }
    cartModel = create(cartModel);
    return cartMapper.toPersistableCart(cartModel);
  }

  // TODO : Need to fetch cart using session token -done
  @Override
  public PersistableCart updateCart(PersistableCart cart, HttpServletRequest request)
      throws ValidationException {

    // fetch cart from DB
    String phone = sessionUtil.getPhoneByAuthToken(request);
    Cart cartModel = getCartByPhone(phone);

    // remove preApplied Coupon code
    if ((StringUtils.isBlank(cart.getPromoCode())
        || StringUtils.isBlank(cartModel.getPromoCode()))) {
      cartModel.setCustomerCouponApplied(true);
    }

    // update pre applied coupon code
    if (!cartModel.isCustomerCouponApplied()
        && !(cartModel.getPromoCode().equals(cart.getPromoCode()))) {
      cartModel.setCustomerCouponApplied(true);
    }
    if (cart.getAppliedRewardPoint() > cart.getRewardPoint()) {
      cart.setAppliedRewardPoint(cart.getRewardPoint());
    }

    cartModel.setPromoCode(cart.getPromoCode());
    cartModel.setCouponDiscountType(cart.getCouponDiscountType());
    cartModel.setCouponDiscountVal(cart.getCouponDiscountVal());

    cartModel.setAppliedRewardPoint(cart.getAppliedRewardPoint());
    cartModel.setRewardPoint(cart.getRewardPoint());
    cartModel.setTotalDiscountVal(0);
    cartModel.setCouponCodeErrorMsg("");

    PersistableCart persistableCart = null;
    for (PersistableCartItem cartItem : cart.getCartItems()) {
      cartItem.setDisplayPrice(
          (cartItem.getItemPrice() + cartItem.getAddOnPrice()) * cartItem.getQuantity());
      Long id = cartItem.getCartItemId();
      if (Objects.nonNull(id)) {
        Long displayPrice = cartItem.getDisplayPrice();
        cartModel.getCartItems().stream()
            .filter(i -> i.getId() == id)
            .forEach(o -> o.setDisplayPrice(displayPrice));
      }
      createOrUpdateCartItemInCart(cartModel, cartItem);
    }
    // TODO : just reverify if ve get updated cart model from this or not
    getUpdatedDisplayPriceDeliveryChargesForCart(cartModel);

    persistableCart = cartMapper.toPersistableCart(getCartByPhone(phone));
    if (persistableCart.getCartItems().size() == 0) {
      persistableCart = resetCartValues(persistableCart);
    }
    return persistableCart;
  }

  // TODO: we need to make sure if cart tier type is getting updated when tier is upgraded
  private PersistableCart resetCartValues(PersistableCart persistableCart) {
    persistableCart.setAppliedRewardPoint(0);
    persistableCart.setCouponCodeApplied(false);
    persistableCart.setTotalDiscountVal(0);
    persistableCart.setCouponCodeErrorMsg("");
    persistableCart.setDisplayPrice(0L);
    persistableCart.setTotalPrice(0L);
    persistableCart.setEarnPoints(0);
    persistableCart.setMinCartValDeliveryCharges(0);

    Coupon coupon =
        cacheUtil.getObjectFromCache(HabbitCoreConstant.DEFAULT_COUPON_CODE, Coupon.class);
    if (Objects.nonNull(coupon)) {
      persistableCart.setPromoCode(coupon.getCodeName());
    }
    persistableCart.setCouponDiscountType(null);
    persistableCart.setCouponDiscountVal(0);
    persistableCart.setCustomerCouponApplied(false);

    persistableCart.setDeliveryCharges(0);
    persistableCart.setDeliveryChargesType(null);
    persistableCart.setGiftWrapMessage("");
    persistableCart.setGiftWrapped(false);
    persistableCart.setGiftWrappingCharges(0);
    return cartMapper.toPersistableCart(update(cartMapper.toCart(persistableCart)));
  }

  @Override
  public PersistableCart updateCartPostCouponValidation(
      PersistableCart persistableCart, HttpServletRequest request) throws ValidationException {
    persistableCart = updateCart(persistableCart, request);
    return persistableCart;
  }

  @Override
  public int getEarnPoint(TierType tierType, Long amount) {
    return membershipFacade.calculateRewardPoint(tierType, amount);
  }

  @Override
  public boolean validationCartItem(
      PersistableCartItem cartItem,
      HttpServletRequest request,
      MerchantStore merchantStore,
      String phone)
      throws Exception {
    boolean unlimited = false;
    if (Objects.isNull(phone)) {
      phone = sessionUtil.getPhoneByAuthToken(request);
    }
    PersistableProduct product = productFacade.getProductBySkuId(cartItem.getProductSku(), null);
    PersistableProductAvailability persistableProductAvailability =
        product.getAvailabilities().iterator().next();
    if (Objects.isNull(persistableProductAvailability.getProductQuantity())) {
      unlimited = true;
    }

    if (!unlimited
        && persistableProductAvailability.getProductQuantityOrderMax() < cartItem.getQuantity()) {
      throw new Exception(
          "Product quantity out of max order for product "
              + cartItem.getProductSku()
              + " count :"
              + cartItem.getQuantity());
    }

    PersistableCustomerPreAllottedCheckoutQuota pcpcp = null;
    pcpcp =
        cpcpFacade.getCustomerProductConsumptionPendingByCartItemCode(
            cartItem.getCartItemCode(), phone);

    if (!unlimited
        && Objects.isNull(pcpcp)
        && (cartItem.getQuantity()) > persistableProductAvailability.getProductQuantity()) {
      throw new Exception(
          "Product quantity out of max order for product :"
              + cartItem.getProductSku()
              + " flavour : "
              + cartItem.getFlavour()
              + ", pack :"
              + cartItem.getPackSize().getPackSizeValue());

    } else if ((unlimited && Objects.isNull(pcpcp))
        || (Objects.isNull(pcpcp)
            && (cartItem.getQuantity()) <= persistableProductAvailability.getProductQuantity())) {
      pcpcp = new PersistableCustomerPreAllottedCheckoutQuota();
      pcpcp.setCustomerPhone(phone);
      pcpcp.setProductSku(cartItem.getProductSku());
      pcpcp.setCount(cartItem.getQuantity());
      pcpcp.setCartItemCode(cartItem.getCartItemCode());
      cpcpFacade.create(pcpcp, null);
      if (!unlimited) {
        product
            .getAvailabilities()
            .iterator()
            .next()
            .setProductQuantity(
                persistableProductAvailability.getProductQuantity() - cartItem.getQuantity());
        productFacade.updateProduct(product, merchantStore);
      }

    } else if (unlimited && (Objects.nonNull(pcpcp))
        || (Objects.nonNull(pcpcp)
            && (cartItem.getQuantity()) <= persistableProductAvailability.getProductQuantity())) {
      pcpcp.setCount(cartItem.getQuantity());
      cpcpFacade.update(pcpcp, null);
      if (!unlimited) {
        product
            .getAvailabilities()
            .iterator()
            .next()
            .setProductQuantity(
                persistableProductAvailability.getProductQuantity()
                    - (cartItem.getQuantity() - pcpcp.getCount()));
        productFacade.updateProduct(product, merchantStore);
      }

    } else if (!unlimited
        && Objects.nonNull(pcpcp)
        && (pcpcp.getCount() + cartItem.getQuantity())
            > persistableProductAvailability.getProductQuantity()) {
      throw new Exception(
          "Product quantity out of max order for product :"
              + cartItem.getProductSku()
              + " flavour : "
              + cartItem.getFlavour()
              + ", pack :"
              + cartItem.getPackSize().getPackSizeValue());
    }

    return true;
  }

  @Override
  public PersistableCart validateCart(
      ValidateCartEvent validateCartEvent,
      PersistableCart persistableCart,
      HttpServletRequest request,
      MerchantStore merchantStore)
      throws Exception {
    if (Objects.isNull(validateCartEvent)) {
      throwServiceRuntImeException(new Exception(), "ABC", "No Event Selected");
    }
    PersistableCart cartResponse = null;

    switch (validateCartEvent) {
      case GO_TO_CART:
        cartResponse = validationCart(persistableCart, request, merchantStore);
        break;
      case INCREMENT_QTY_COUPON:
        cartResponse = validationCart(persistableCart, request, merchantStore);
        break;
      case INCREMENT_QTY_REWARD:
        cartResponse = validationCart(persistableCart, request, merchantStore);
        break;
      case INCREMENT_QTY_NONE:
        cartResponse = validationCart(persistableCart, request, merchantStore);
        break;
      case DECREMENT_QTY_COUPON:
        cartResponse = updateCartPostCouponValidation(persistableCart, request);
        break;
      case DECREMENT_QTY_REWARD:
        cartResponse = validationCart(persistableCart, request, merchantStore);
        break;
      case DECREMENT_QTY_NONE:
        cartResponse = validationCart(persistableCart, request, merchantStore);
        break;
      case APPLY_COUPON:
        cartResponse = updateCartPostCouponValidation(persistableCart, request);
        break;
      case APPLY_REWARD:
        cartResponse = validationCart(persistableCart, request, merchantStore);
        break;
      case REMOVE_COUPON:
        cartResponse = validationCart(persistableCart, request, merchantStore);
        break;
      case REMOVE_REWARD:
        cartResponse = validationCart(persistableCart, request, merchantStore);
        break;
      default:
        throwServiceRuntImeException(
            new Exception(),
            PaymentErrorCodes.CART_INVALID_EVENT.getErrorCode(),
            PaymentErrorCodes.CART_INVALID_EVENT.getErrorMessage());
        break;
    }
    return cartResponse;
  }

  @Override
  public PersistableCart validationCart(
      PersistableCart cart, HttpServletRequest request, MerchantStore merchantStore)
      throws Exception {
    String phone = cart.getCustomerPhone();
    for (PersistableCartItem cartItem : cart.getCartItems()) {
      if (!validationCartItem(cartItem, request, merchantStore, phone)) {
        throw new Exception(
            "Product quantity out of max order for product :"
                + cartItem.getProductSku()
                + " flavour : "
                + cartItem.getFlavour()
                + ", pack :{}"
                + cartItem.getPackSize().getPackSizeValue());
      }
    }
    cart = updateCart(cart, request);
    return cart;
  }

  private CartItem createOrUpdateCartItemInCart(
      Cart cartModel, PersistableCartItem persistableCartItem) {
    CartItem cartItem = cartItemMapper.toCartItem(persistableCartItem);
    cartItem.setCart(cartModel);
    if (Objects.nonNull(cartItem.getId()) && cartItem.getId() != 0) {
      return updateCartItem(cartItem);
    } else {
      cartItem = createCartItem(cartItem);
      cartModel.getCartItems().add(cartItem);
      update(cartModel);
      return cartItem;
    }
  }

  private Cart getUpdatedDisplayPriceDeliveryChargesForCart(Cart cartModel) {
    long totalPrice =
        cartModel.getCartItems().stream()
            .mapToLong(cartItemModel -> cartItemModel.getDisplayPrice())
            .sum();
    long displayPrice = totalPrice;

    cartModel.setTotalPrice(totalPrice);
    cartModel.setDisplayPrice(displayPrice);
    if (totalPrice < DeliveryChargesType.PREMIUM.getOrderAmount()) {
      cartModel.setDeliveryChargesType(DeliveryChargesType.DEFAULT);
      cartModel.setDeliveryCharges(DeliveryChargesType.DEFAULT.getDeliveryCharges());
      cartModel.setDisplayPrice(cartModel.getDisplayPrice() + cartModel.getDeliveryCharges());
      cartModel.setMinCartValDeliveryCharges(DeliveryChargesType.DEFAULT.getOrderAmount());
    } else {
      cartModel.setDeliveryChargesType(DeliveryChargesType.PREMIUM);
      cartModel.setDeliveryCharges(DeliveryChargesType.PREMIUM.getDeliveryCharges());
    }

    if (cartModel.getAppliedRewardPoint() > 0 && StringUtils.isEmpty(cartModel.getPromoCode())) {
      cartModel.setCouponDiscountType(null);
      cartModel.setCouponDiscountVal(0);
      cartModel.setCouponCodeApplied(false);
      setApplicableRewardPoint(cartModel);
      // cartModel.setTotalDiscountVal(cartModel.getAppliedRewardPoint());
    } else if (StringUtils.isNotEmpty(cartModel.getPromoCode())) {
      calculateTotalDiscountPriceForCoupon(displayPrice, cartModel, cartModel.getPromoCode());
    }

    cartModel.setDisplayPrice(cartModel.getDisplayPrice() - cartModel.getTotalDiscountVal());

    cartModel.setEarnPoints(
        membershipFacade.calculateRewardPoint(
            cartModel.getTierType(), cartModel.getDisplayPrice()));
    cartModel = update(cartModel);
    return cartModel;
  }

  private void setApplicableRewardPoint(Cart cartModel) {
    RewardConsumptionCriteria rewardConsumptionCriteria =
        cacheUtil.getObjectFromCache(TierType.ALL.name(), RewardConsumptionCriteria.class);
    if (Objects.isNull(rewardConsumptionCriteria)) {
      PersistableRewardConsumptionCriteria persistableRewardConsumptionCriteria =
          rewardConsumptionCriteriaFacade.getRewardConsumptionCriteriaByType(
              TierType.ALL, null, null);
      cacheUtil.setObjectInCache(
          TierType.ALL.name(),
          rewardConsumptionCriteriaMapper.toRewardConsumptionCriteria(
              persistableRewardConsumptionCriteria));
    }
    if (cartModel.getRewardPoint() < rewardConsumptionCriteria.getMinRewardPoint()) {
      cartModel.setAppliedRewardPoint(0);
      cartModel.setTotalDiscountVal(0);
      cartModel.setCouponCodeErrorMsg(
          "Minimum" + rewardConsumptionCriteria.getMinRewardPoint() + " Habbit Points required");
      return;
      //      throwServiceRuntImeException(
      //          new Exception(),
      //          PaymentErrorCodes.CART_CUSTOMER_MINIMUM_REWARD_FAILURE.getErrorCode(),
      //          PaymentErrorCodes.CART_CUSTOMER_MINIMUM_REWARD_FAILURE.getErrorMessage()
      //              + rewardConsumptionCriteria.getMinRewardPoint());
    }
    if (cartModel.getTotalPrice() < rewardConsumptionCriteria.getMinCartValue()) {
      cartModel.setAppliedRewardPoint(0);
      cartModel.setTotalDiscountVal(0);
      cartModel.setCouponCodeErrorMsg(
          "Order Value should be atleast Rs. " + rewardConsumptionCriteria.getMinCartValue());
      return;
      //      throwServiceRuntImeException(
      //          new Exception(),
      //          PaymentErrorCodes.CART_CUSTOMER_MINIMUM_CART_VALUE.getErrorCode(),
      //          PaymentErrorCodes.CART_CUSTOMER_MINIMUM_CART_VALUE.getErrorMessage()
      //              + rewardConsumptionCriteria.getMinCartValue());
    }

    if (cartModel.getDisplayPrice() > cartModel.getAppliedRewardPoint()) {
      cartModel.setTotalDiscountVal(cartModel.getAppliedRewardPoint());
    } else {
      cartModel.setAppliedRewardPoint(Integer.parseInt(cartModel.getDisplayPrice().toString()) - 1);
      cartModel.setTotalDiscountVal(cartModel.getAppliedRewardPoint());
    }
  }

  private void calculateTotalDiscountPriceForCoupon(long totalPrice, Cart cart, String couponCode) {
    PersistableCouponCode persistableCouponCode = couponCodeFacade.getCouponCodeByCode(couponCode);
    long totalDiscountAmount = 0;
    // to handle the scenario when invalid coupon code entered by user
    if (Objects.isNull(persistableCouponCode)) {
      LOGGER.error("coupon code {} entered by user is not valid ", couponCode);
      cart.setCouponCodeApplied(false);
      cart.setCouponCodeErrorMsg("Invalid Coupon Code! Please check again.");
      cart.setPromoCode(null);
      cart.setCouponDiscountVal(0);
      cart.setCouponDiscountType(null);
      cart.setTotalDiscountVal(0);
      return;
    }
    cart.setCouponDiscountType(persistableCouponCode.getCouponDiscountType());
    cart.setCouponDiscountVal(persistableCouponCode.getDiscountVal());

    if (couponCodePreValidationNotSuccessful(totalPrice, cart, persistableCouponCode)) {
      return;
    }

    CouponDiscountType couponDiscountType = persistableCouponCode.getCouponDiscountType();
    int discountValue = persistableCouponCode.getDiscountVal();
    totalDiscountAmount =
        getDiscountAfterCouponApplied(
            totalPrice, persistableCouponCode, couponDiscountType, discountValue);

    cart.setTotalDiscountVal((int) totalDiscountAmount);
    //  totalPrice = totalPrice - totalDiscountAmount;
    cart.setCouponCodeApplied(true);
    cart.setCouponCodeErrorMsg("");
  }

  // TODO : need to check if we have to round off the discount or consider decimal
  private long getDiscountAfterCouponApplied(
      long totalPrice,
      PersistableCouponCode persistableCouponCode,
      CouponDiscountType couponDiscountType,
      int discountValue) {
    long totalDiscountAmount = 0L;
    if (CouponDiscountType.PERCENTAGE.name().equals(couponDiscountType.name())) {
      totalDiscountAmount = ((totalPrice * discountValue) / 100);
      totalDiscountAmount =
          (totalDiscountAmount > persistableCouponCode.getMaxDiscountVal())
              ? persistableCouponCode.getMaxDiscountVal()
              : totalDiscountAmount;
    } else if (couponDiscountType.name().equals(CouponDiscountType.AMOUNT_VALUE.name())) {
      totalDiscountAmount = discountValue;
      totalDiscountAmount =
          (totalDiscountAmount > totalPrice) ? (totalPrice - 1) : totalDiscountAmount;
    }
    return totalDiscountAmount;
  }

  private boolean couponCodePreValidationNotSuccessful(
      long totalPrice, Cart cart, PersistableCouponCode persistableCouponCode) {
    if (totalPrice < persistableCouponCode.getMinCartValue()) {
      cart.setCouponCodeApplied(false);
      cart.setCouponCodeErrorMsg(
          "Order Value should be atleast Rs. " + persistableCouponCode.getMinCartValue());
      cart.setPromoCode(null);
      cart.setCouponDiscountVal(0);
      cart.setCouponDiscountType(null);
      cart.setTotalDiscountVal(0);
      return true;
    }

    if (persistableCouponCode.isCouponExpires()) {
      LOGGER.error("coupon code is expired", cart.getTierType().name());
      cart.setCouponCodeApplied(false);
      cart.setCouponCodeErrorMsg("Promo code is expired OR Promo code is no longer valid");
      cart.setPromoCode(null);
      cart.setCouponDiscountVal(0);
      cart.setCouponDiscountType(null);
      cart.setTotalDiscountVal(0);
      return true;
    }

    if (persistableCouponCode.isTierSpecific()
        && !persistableCouponCode.getAllowedTierTypes().contains(cart.getTierType())) {
      LOGGER.error("coupon code is not applicable for this tierType {}", cart.getTierType().name());
      cart.setCouponCodeApplied(false);
      cart.setCouponCodeErrorMsg("coupon code is not applicable for current tier type.");
      cart.setPromoCode(null);
      cart.setCouponDiscountVal(0);
      cart.setCouponDiscountType(null);
      cart.setTotalDiscountVal(0);
      return true;
    }
    return false;
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
