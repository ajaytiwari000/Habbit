package com.salesmanager.shop.populator.catalog;

import com.salesmanager.core.model.order.CustomerOrder;
import com.salesmanager.core.model.order.OrderType;
import com.salesmanager.core.model.order.orderproduct.CustomerOrderProduct;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.shop.error.codes.CustomerErrorCodes;
import com.salesmanager.shop.mapper.product.BoostMapper;
import com.salesmanager.shop.mapper.product.FlavourMapper;
import com.salesmanager.shop.mapper.product.PackMapper;
import com.salesmanager.shop.model.customer.PersistableCheckoutCart;
import com.salesmanager.shop.model.customer.PersistableCustomerOrder;
import com.salesmanager.shop.model.customer.PersistableCustomerOrderAddress;
import com.salesmanager.shop.model.customer.PersistableCustomerOrderProduct;
import com.salesmanager.shop.model.customer.mapper.PersistableOrderAddressAndPersistableAddressMapper;
import com.salesmanager.shop.model.shoppingcart.PersistableCart;
import com.salesmanager.shop.model.shoppingcart.PersistableCartItem;
import com.salesmanager.shop.populator.customer.PersistableAddressPopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.facade.order.util.OrderUtil;
import java.util.Calendar;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PersistableCustomerOrderPopulator {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(PersistableCustomerOrderPopulator.class);

  @Inject private PersistableAddressPopulator persistableAddressPopulator;
  @Inject private FlavourMapper flavourMapper;
  @Inject private PackMapper packMapper;
  @Inject private BoostMapper boostMapper;
  @Inject private OrderUtil orderUtil;

  @Inject
  private PersistableOrderAddressAndPersistableAddressMapper
      persistableOrderAddressAndPersistableAddressMapper;

  public void cusomerOrderToPersistableCustomerOrderPopulator(
      CustomerOrder customerOrder, PersistableCustomerOrder persistableCustomerOrder) {
    PersistableCustomerOrderAddress persistableBillingAddress = null;
    PersistableCustomerOrderAddress persistableShippingAddress = null;
    try {
      persistableBillingAddress =
          persistableAddressPopulator.populate(
              customerOrder.getBillingAddress(), new PersistableCustomerOrderAddress(), null, null);
      persistableShippingAddress =
          persistableAddressPopulator.populate(
              customerOrder.getShippingAddress(),
              new PersistableCustomerOrderAddress(),
              null,
              null);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ADDRESS_POPULATOR_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ADDRESS_POPULATOR_FAILURE.getErrorMessage());
    }

    persistableCustomerOrder.setBillingAddress(persistableBillingAddress);
    persistableCustomerOrder.setShippingAddress(persistableShippingAddress);

    persistableCustomerOrder.setId(customerOrder.getId());
    persistableCustomerOrder.setStatus(customerOrder.getStatus());
    persistableCustomerOrder.setCustomerPhone(customerOrder.getCustomerPhone());
    persistableCustomerOrder.setCustomerFriendPhone(customerOrder.getCustomerFriendPhone());
    persistableCustomerOrder.setCustomerEmailAddress(customerOrder.getCustomerEmailAddress());
    persistableCustomerOrder.setRazorPayCustomerId(customerOrder.getRazorPayCustomerId());
    persistableCustomerOrder.setTierType(customerOrder.getTierType());
    persistableCustomerOrder.setDatePurchased(customerOrder.getDatePurchased());
    persistableCustomerOrder.setOrderType(customerOrder.getOrderType());
    persistableCustomerOrder.setAppliedRewardPoint(customerOrder.getAppliedRewardPoint());

    customerOrderProductToPersistableCustomerOrderProductPopulator(
        persistableCustomerOrder, customerOrder);

    persistableCustomerOrder.setPromoCode(customerOrder.getPromoCode());
    persistableCustomerOrder.setCouponDiscountType(customerOrder.getCouponDiscountType());
    persistableCustomerOrder.setDiscountVal(customerOrder.getDiscountVal());
    persistableCustomerOrder.setDisplayPrice(customerOrder.getDisplayPrice());
    persistableCustomerOrder.setTotalDiscountVal(customerOrder.getTotalDiscountVal());
    persistableCustomerOrder.setTotalPrice(customerOrder.getTotalPrice());
    persistableCustomerOrder.setEarnedPoints(customerOrder.getEarnedPoints());
    persistableCustomerOrder.setDeliveryChargesType(customerOrder.getDeliveryChargesType());
    persistableCustomerOrder.setDeliveryCharges(customerOrder.getDeliveryCharges());
    persistableCustomerOrder.setGiftWrapCharges(customerOrder.getGiftWrapCharges());
    persistableCustomerOrder.setGiftWrapMessage(customerOrder.getGiftWrapMessage());
    persistableCustomerOrder.setGiftWrap(customerOrder.isGiftWrap());
  }

  private void customerOrderProductToPersistableCustomerOrderProductPopulator(
      PersistableCustomerOrder persistableCustomerOrder, CustomerOrder customerOrder) {
    for (CustomerOrderProduct customerOrderProduct : customerOrder.getOrderProducts()) {
      PersistableCustomerOrderProduct orderProduct = new PersistableCustomerOrderProduct();
      orderProduct.setOrderProductCode(orderUtil.uniqueCustomerOrderCode());
      orderProduct.setSku(customerOrderProduct.getSku());
      orderProduct.setProductImage(customerOrderProduct.getProductImage());
      orderProduct.setProductName(customerOrderProduct.getProductName());
      orderProduct.setProductQuantity(customerOrderProduct.getProductQuantity());
      orderProduct.setItemPrice(customerOrderProduct.getItemPrice());
      orderProduct.setAddOnPrice(customerOrderProduct.getAddOnPrice());
      orderProduct.setDisplayPrice(customerOrderProduct.getDisplayPrice());
      orderProduct.setFlavour(
          flavourMapper.toPersistableFlavour(customerOrderProduct.getFlavour()));
      orderProduct.setPackSize(packMapper.toPersistablePack(customerOrderProduct.getPackSize()));
      if (customerOrderProduct.getBoosts().size() > 0) {
        orderProduct.setBoosts(
            customerOrderProduct.getBoosts().stream()
                .map(boostMapper::toPersistableBoost)
                .collect(Collectors.toSet()));
      }
      persistableCustomerOrder.getOrderProducts().add(orderProduct);
    }
  }

  public PersistableCustomerOrder persistableCheckoutCartToPersistableCustomerOrderPopulator(
      PersistableCheckoutCart persistableCheckoutCart) {
    PersistableCustomerOrder persistableCustomerOrder = new PersistableCustomerOrder();
    PersistableCart cart = persistableCheckoutCart.getCart();

    persistableCustomerOrder.setBillingAddress(
        persistableOrderAddressAndPersistableAddressMapper.toPersistableCustomerOrderAddress(
            persistableCheckoutCart.getShippingAddress()));
    persistableCustomerOrder.setShippingAddress(
        persistableOrderAddressAndPersistableAddressMapper.toPersistableCustomerOrderAddress(
            persistableCheckoutCart.getShippingAddress()));

    persistableCustomerOrder.setCartCode(cart.getCartCode());
    persistableCustomerOrder.setStatus(OrderStatus.PENDING);
    persistableCustomerOrder.setCustomerPhone(cart.getCustomerPhone());
    persistableCustomerOrder.setCustomerName(cart.getCustomerName());
    persistableCustomerOrder.setCustomerFriendPhone(cart.getFriendPhone());
    persistableCustomerOrder.setCustomerEmailAddress(cart.getCustomerEmail());
    persistableCustomerOrder.setRazorPayCustomerId(cart.getRazorPayCustomerId());
    persistableCustomerOrder.setTierType(cart.getTierType());
    persistableCustomerOrder.setDatePurchased(Calendar.getInstance().getTimeInMillis());
    persistableCustomerOrder.setOrderType(OrderType.ORDER);
    persistableCustomerOrder.setAppliedRewardPoint(cart.getAppliedRewardPoint());

    cartItemToPersistableCustomerOrderProductPopulator(persistableCustomerOrder, cart);

    persistableCustomerOrder.setCustomerEmailAddress(cart.getCustomerEmail());
    persistableCustomerOrder.setPromoCode(cart.getPromoCode());
    persistableCustomerOrder.setCouponDiscountType(cart.getCouponDiscountType());
    persistableCustomerOrder.setDiscountVal(cart.getCouponDiscountVal());
    persistableCustomerOrder.setDisplayPrice(cart.getDisplayPrice());
    persistableCustomerOrder.setTotalPrice(cart.getTotalPrice());
    persistableCustomerOrder.setEarnedPoints(cart.getEarnPoints());
    persistableCustomerOrder.setTotalDiscountVal(cart.getTotalDiscountVal());
    persistableCustomerOrder.setDeliveryCharges(cart.getDeliveryCharges());
    persistableCustomerOrder.setDeliveryChargesType(cart.getDeliveryChargesType());
    persistableCustomerOrder.setGiftWrapCharges(cart.getGiftWrappingCharges());
    persistableCustomerOrder.setGiftWrapMessage(cart.getGiftWrapMessage());
    persistableCustomerOrder.setGiftWrap(cart.isGiftWrapped());

    return persistableCustomerOrder;
  }

  private void cartItemToPersistableCustomerOrderProductPopulator(
      PersistableCustomerOrder persistableCustomerOrder, PersistableCart cart) {
    for (PersistableCartItem cartItem : cart.getCartItems()) {
      PersistableCustomerOrderProduct orderProduct = new PersistableCustomerOrderProduct();
      orderProduct.setOrderProductCode(orderUtil.uniqueCustomerOrderCode());
      orderProduct.setSku(cartItem.getProductSku());
      orderProduct.setCartItemCode(cartItem.getCartItemCode());
      orderProduct.setProductName(cartItem.getProductName());
      orderProduct.setProductImage(cartItem.getProductImage());
      orderProduct.setProductQuantity(cartItem.getQuantity());
      orderProduct.setItemPrice(cartItem.getItemPrice());
      orderProduct.setAddOnPrice(cartItem.getAddOnPrice());
      orderProduct.setDisplayPrice(cartItem.getDisplayPrice());
      orderProduct.setFlavour(cartItem.getFlavour());
      orderProduct.setPackSize(cartItem.getPackSize());
      orderProduct.setBoosts(cartItem.getBoosts());
      persistableCustomerOrder.getOrderProducts().add(orderProduct);
    }
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
