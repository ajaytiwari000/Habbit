/** */
package com.salesmanager.shop.store.facade.payments.gateway.razorpay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.razorpay.Customer;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.payment.gateway.RazorPayServiceProvider;
import com.salesmanager.core.business.services.customer.checkoutAudit.CheckoutAuditFlowService;
import com.salesmanager.core.business.services.payments.gateway.razorpay.CustomerRazorPayOrderService;
import com.salesmanager.core.business.services.shoppingcart.CartService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.*;
import com.salesmanager.core.model.order.enums.RazorPayOrderStatus;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.payments.PaymentStatus;
import com.salesmanager.core.model.shoppingcart.Cart;
import com.salesmanager.core.model.shoppingcart.CartItem;
import com.salesmanager.shop.admin.controller.customer.membership.CustomerOrderFacade;
import com.salesmanager.shop.admin.controller.customer.membership.MembershipFacade;
import com.salesmanager.shop.admin.controller.customer.order.CustomerOrderStatusHistoryFacade;
import com.salesmanager.shop.cache.util.CacheUtil;
import com.salesmanager.shop.error.codes.CustomerErrorCodes;
import com.salesmanager.shop.error.codes.PaymentErrorCodes;
import com.salesmanager.shop.model.customer.*;
import com.salesmanager.shop.model.customer.mapper.CustomerRazorPayOrderMapper;
import com.salesmanager.shop.populator.catalog.PersistableRazorPayOrderPopulator;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.facade.cart.CartFacade;
import com.salesmanager.shop.store.facade.customer.CustomerFacade;
import java.util.Calendar;
import java.util.Objects;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("customerRazorPayFacade")
public class CustomerRazorPayFacadeImpl implements CustomerRazorPayFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRazorPayFacadeImpl.class);

  @Value("${razorPay.access.key}")
  private String accessKey;

  @Value("${razorPay.secret.key}")
  private String secretKey;

  @Inject private CustomerRazorPayOrderService customerRazorPayOrderService;
  @Inject private CustomerRazorPayOrderMapper customerRazorPayOrderMapper;
  @Inject private CustomerOrderFacade customerOrderFacade;
  @Inject private MembershipFacade membershipFacade;
  @Inject private CartFacade cartFacade;
  @Inject private CartService cartService;
  @Inject private CustomerOrderStatusHistoryFacade customerOrderStatusHistoryFacade;
  @Inject private CacheUtil cacheUtil;
  @Inject private PersistableRazorPayOrderPopulator persistableRazorPayOrderPopulator;
  @Inject private CustomerFacade customerFacade;
  @Inject private CustomerRazorPayFacade customerRazorPayFacade;
  @Inject private CheckoutAuditFlowService checkoutAuditFlowService;

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }

  @Override
  public PersistableRazorPayDetail getRazorPayDetail(String receipt) {
    PersistableRazorPayDetail persistableRazorPayDetail = new PersistableRazorPayDetail();
    persistableRazorPayDetail.setRazorPayAccessKey(accessKey);
    persistableRazorPayDetail.setRazorPaySecretKey(secretKey);
    CustomerRazorPayOrder customerRazorPayOrder = null;
    // TODO - replace receipt with order code, also append the value in erro logs- receipt is making
    // more sense to me by glance only able to verify
    try {
      customerRazorPayOrder = customerRazorPayOrderService.getByReceipt(receipt);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.RAZORPAY_GET_BY_RECEIPT_FAILURE.getErrorCode(),
          PaymentErrorCodes.RAZORPAY_GET_BY_RECEIPT_FAILURE.getErrorMessage() + receipt);
    }
    if (Objects.isNull(customerRazorPayOrder)) {
      LOGGER.error("customerRazorPayOrder not exists for receipt {}", receipt);
      throw new ResourceNotFoundException(
          "customerRazorPayOrder not exists for receipt  " + receipt);
    }
    persistableRazorPayDetail.setAmount((long) customerRazorPayOrder.getAmount());
    persistableRazorPayDetail.setOrderCode(receipt);
    persistableRazorPayDetail.setRazorPayOrderCode(customerRazorPayOrder.getOrderCode());
    persistableRazorPayDetail.setCurrency("INR");
    persistableRazorPayDetail.setRazorPayCustomerId(customerRazorPayOrder.getRazorPayCustomerId());
    persistableRazorPayDetail.setUserPhone(customerRazorPayOrder.getCustomerPhone());
    persistableRazorPayDetail.setFriendPhone(customerRazorPayOrder.getFriendPhone());
    persistableRazorPayDetail.setEmail(customerRazorPayOrder.getCustomerEmail());

    return persistableRazorPayDetail;
  }

  @Override
  public PersistableCustomerRazorPayOrder updateRazorPayDetail(
      PersistableCustomerRazorPayOrder persistableCustomerRazorPayOrder,
      MerchantStore merchantStore,
      boolean isWebhooks)
      throws Exception {
    CheckoutAuditFlow checkoutAuditFlow = new CheckoutAuditFlow();
    CustomerRazorPayOrder customerRazorPayOrder = null;
    try {
      RazorPayServiceProvider razorPayServiceProvider = new RazorPayServiceProvider();
      // TODO : If Payment got cancelled then do we need to mark Order as Cancelled, verify with
      // product
      if (!isWebhooks
          && (Objects.isNull(persistableCustomerRazorPayOrder.getPaymentId())
              || Objects.isNull(persistableCustomerRazorPayOrder.getSignature()))) {
        customerOrderFacade.updateOrderCustomerAfterPayment(
            persistableCustomerRazorPayOrder.getReceipt(), false);
        checkoutAuditFlow.setCustomerOrderCanceled(true);
        LOGGER.error(
            "RazorPay paymentId or signature could not be null for receipt",
            persistableCustomerRazorPayOrder.getReceipt());
        throwServiceRuntImeException(
            new ServiceException(),
            PaymentErrorCodes.RAZORPAY_ORDER_CANCELLED.getErrorCode(),
            PaymentErrorCodes.RAZORPAY_ORDER_CANCELLED.getErrorMessage());
      }
      LOGGER.info("RazorPay_issue orderCode :{} ", persistableCustomerRazorPayOrder.getReceipt());
      if (!isWebhooks) {
        boolean isVerified =
            razorPayServiceProvider.verifyPaymentSignature(
                persistableCustomerRazorPayOrder.getOrderCode(),
                persistableCustomerRazorPayOrder.getPaymentId(),
                persistableCustomerRazorPayOrder.getSignature());
        if (!isVerified) {
          throwServiceRuntImeException(
              new Exception(),
              PaymentErrorCodes.RAZORPAY_ORDER_UPDATE_FAILURE.getErrorCode(),
              PaymentErrorCodes.RAZORPAY_ORDER_UPDATE_FAILURE.getErrorMessage());
        }
        checkoutAuditFlow.setPaymentVerified(true);
      }
      // Todo : remove cartcode to ordercode in redis- done
      removeOrderCodeFromCache(persistableCustomerRazorPayOrder.getCustomerPhone());
      // Todo : remove cartcode to ordercode in redis
      customerRazorPayOrder = updateRazorPayOrder(persistableCustomerRazorPayOrder, isWebhooks);
      checkoutAuditFlow.setCustomerRazorpayOrderUpdated(true);
      CustomerOrder customerOrder =
          customerOrderFacade.updateOrderCustomerAfterPayment(
              persistableCustomerRazorPayOrder.getReceipt(), true);
      checkoutAuditFlow.setCustomerSaleOrderCreated(true);
      customerRewardPointConsumption(customerOrder);
      addRewardPointOnOrderCompletionAndUpdateCustomerFirstOrderPlaced(
          customerOrder, merchantStore);
      deleteItemFromCustomerCart(persistableCustomerRazorPayOrder.getCustomerPhone());
      updateCustomerOrderStatusHistory(customerOrder);
      //    //update first order placed flag for customer
      //    updateCustomerFirstOrderFlag(persistableCustomerRazorPayOrder, merchantStore);
      checkoutAuditFlow.setCustomerOrderUpdated(true);
      // notify customer by sms and email
      customerOrderFacade.sendOrderConfirmationMail(customerOrder);
      checkoutAuditFlow.setMailSent(true);
      customerOrderFacade.sendOrderConfirmationSms(customerOrder);
      checkoutAuditFlow.setSmsSent(true);
    } catch (Exception e) {
      throw new Exception("****RazorPay_issue UPDATE RAZORPAY ORDER ISSUE****" + e);
    } finally {
      CheckoutAuditFlow checkoutAuditFlowDb = null;
      try {
        checkoutAuditFlowDb =
            checkoutAuditFlowService.getByOrderCode(persistableCustomerRazorPayOrder.getReceipt());
      } catch (Exception e) {
        throwServiceRuntImeException(
            new Exception(),
            CustomerErrorCodes.CHECKOUT_AUDIT_GET_FAILURE.getErrorCode(),
            CustomerErrorCodes.CHECKOUT_AUDIT_GET_FAILURE.getErrorMessage()
                + persistableCustomerRazorPayOrder.getOrderCode());
      }
      if (Objects.nonNull(checkoutAuditFlowDb)) {
        checkoutAuditFlowDb.setCustomerOrderCanceled(checkoutAuditFlow.isCustomerOrderCanceled());
        checkoutAuditFlowDb.setPaymentVerified(checkoutAuditFlow.isPaymentVerified());
        checkoutAuditFlowDb.setCustomerRazorpayOrderUpdated(
            checkoutAuditFlow.isCustomerRazorpayOrderUpdated());
        checkoutAuditFlowDb.setCustomerSaleOrderCreated(
            checkoutAuditFlow.isCustomerSaleOrderCreated());
        checkoutAuditFlowDb.setCustomerOrderUpdated(checkoutAuditFlow.isCustomerOrderUpdated());
        checkoutAuditFlowDb.setMailSent(checkoutAuditFlow.isMailSent());
        checkoutAuditFlowDb.setSmsSent(checkoutAuditFlow.isSmsSent());
      }
      try {
        checkoutAuditFlowService.update(checkoutAuditFlowDb);
      } catch (Exception e) {
        throwServiceRuntImeException(
            new Exception(),
            CustomerErrorCodes.CHECKOUT_AUDIT_UPDATE_FAILURE.getErrorCode(),
            CustomerErrorCodes.CHECKOUT_AUDIT_UPDATE_FAILURE.getErrorMessage());
      }
    }
    return customerRazorPayOrderMapper.toPersistableCustomerRazorPayOrder(customerRazorPayOrder);
  }

  private void addRewardPointOnOrderCompletionAndUpdateCustomerFirstOrderPlaced(
      CustomerOrder customerOrder, MerchantStore merchantStore) {
    int friendPoint = 0;

    PersistableCustomer customer =
        customerFacade.getCustomerByUserName(customerOrder.getCustomerPhone());

    int customerRewardPoint = customerOrder.getEarnedPoints();
    // calculate 1% point of display price
    friendPoint = (int) Math.ceil((double) customerOrder.getDisplayPrice() / 100);

    // update first order placed flag for customer and 100 points for referrer on first order
    if (!customer.isFirstOrderPlaced()) {
      friendPoint = 100;
      customer.setFirstOrderPlaced(true);
      customerFacade.update(customer, merchantStore);
    }

    // reward 1% points from 2nd order placed
    membershipFacade.addRewardPoints(customerRewardPoint, customerOrder.getCustomerPhone(), false);
    if (StringUtils.isNotEmpty(customerOrder.getCustomerFriendPhone())) {
      membershipFacade.addRewardPoints(friendPoint, customerOrder.getCustomerFriendPhone(), true);
    }
  }

  @Override
  public Order createRazorPayOrder(int intValue, String orderCode) {
    RazorPayServiceProvider razorPayServiceProvider = new RazorPayServiceProvider();
    try {
      return razorPayServiceProvider.createOrder(intValue, orderCode);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.RAZORPAY_ORDER_CREATE_FAILURE.getErrorCode(),
          PaymentErrorCodes.RAZORPAY_ORDER_CREATE_FAILURE.getErrorMessage());
    }
    return null;
  }

  @Override
  public Customer createRazorPayCustomer(
      String customerName, String customerEmail, String customerPhone) {
    RazorPayServiceProvider razorPayServiceProvider = new RazorPayServiceProvider();
    Customer customer = null;
    try {
      customer = razorPayServiceProvider.createCustomer(customerName, customerEmail, customerPhone);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.RAZORPAY_ORDER_CREATE_FAILURE.getErrorCode(),
          PaymentErrorCodes.RAZORPAY_ORDER_CREATE_FAILURE.getErrorMessage());
    }
    return customer;
  }

  @Override
  public PersistableCustomerRazorPayOrder createRazorPayOrderByRazorPayOrder(
      Order razorPayOrder, PersistableCustomerOrder persistableCustomerOrder) {
    CustomerRazorPayOrder customerRazorPayOrder = null;
    try {
      customerRazorPayOrder =
          customerRazorPayOrderService.create(
              persistableRazorPayOrderPopulator.populateCustomerRazorPayOrder(
                  razorPayOrder, persistableCustomerOrder));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.OUR_SIDE_RAZORPAY_ORDER_CREATE_FAILURE.getErrorCode(),
          PaymentErrorCodes.OUR_SIDE_RAZORPAY_ORDER_CREATE_FAILURE.getErrorMessage());
    }
    return customerRazorPayOrderMapper.toPersistableCustomerRazorPayOrder(customerRazorPayOrder);
  }

  @Override
  public Refund refundAmountToCustomer(String orderCode) {
    Refund refund = null;
    RazorPayServiceProvider razorPayServiceProvider = new RazorPayServiceProvider();
    CustomerRazorPayOrder customerRazorPayOrder = getByReceipt(orderCode);
    try {
      refund =
          razorPayServiceProvider.refundOrder(
              customerRazorPayOrder.getAmount(),
              customerRazorPayOrder.getRazorPayPaymentId(),
              true);
    } catch (RazorpayException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.RAZORPAY_ORDER_REFUND.getErrorCode(),
          PaymentErrorCodes.RAZORPAY_ORDER_REFUND.getErrorMessage());
    }
    customerRazorPayOrder.setRefundId(refund.get("id").toString());
    customerRazorPayOrder.setRefundedAmount(Integer.parseInt(refund.get("amount").toString()));
    customerRazorPayOrder.setRefundCreatedAt(getCreatedDate(refund));
    if (OrderStatus.PROCESSED.getValue().equals(refund.get("status").toString())) {
      customerRazorPayOrder.setRefundStatus(OrderStatus.PROCESSED);
    }
    customerRazorPayOrder.setRefundSpeedProcessed(refund.get("speed_processed").toString());
    customerRazorPayOrder.setRefundSpeedRequested(refund.get("speed_requested").toString());
    try {
      customerRazorPayOrderService.update(customerRazorPayOrder);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.RAZORPAY_ORDER_UPDATE_FAILURE.getErrorCode(),
          PaymentErrorCodes.RAZORPAY_ORDER_UPDATE_FAILURE.getErrorMessage());
    }
    return refund;
  }

  private Long getCreatedDate(Refund refund) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(refund.get("created_at"));
    return cal.getTimeInMillis();
  }

  @Override
  public CustomerRazorPayOrder getByReceipt(String receipt) {
    CustomerRazorPayOrder customerRazorPayOrder = null;
    try {
      customerRazorPayOrder = customerRazorPayOrderService.getByReceipt(receipt);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.RAZORPAY_GET_BY_RECEIPT_FAILURE.getErrorCode(),
          PaymentErrorCodes.RAZORPAY_GET_BY_RECEIPT_FAILURE.getErrorMessage() + receipt);
    }
    return customerRazorPayOrder;
  }

  @Override
  public CustomerRazorPayOrder getByRazorPayOrder(String orderCode) {
    CustomerRazorPayOrder customerRazorPayOrder = null;
    try {
      customerRazorPayOrder = customerRazorPayOrderService.getByRazorPayOrderCode(orderCode);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.RAZORPAY_GET_BY_ORDER_CDOE_FAILURE.getErrorCode(),
          PaymentErrorCodes.RAZORPAY_GET_BY_ORDER_CDOE_FAILURE.getErrorMessage() + orderCode);
    }
    return customerRazorPayOrder;
  }

  @Override
  public void updateRazorPayDetailByWebhooks(String payload, MerchantStore merchantStore)
      throws Exception {
    LOGGER.info("payload :{}", payload);
    PersistableCustomerRazorPayOrder persistableCustomerRazorPayOrder = getPersistPojo(payload);
    LOGGER.info(
        "persistableCustomerRazorPayOrder :{}",
        new ObjectMapper().writeValueAsString(persistableCustomerRazorPayOrder));
    PersistableCustomer customer =
        customerFacade.getCustomerByUserName(persistableCustomerRazorPayOrder.getCustomerPhone());
    LOGGER.info("customer :{}", new ObjectMapper().writeValueAsString(customer));
    CustomerRazorPayOrder customerRazorPayOrder =
        customerRazorPayFacade.getByRazorPayOrder(persistableCustomerRazorPayOrder.getOrderCode());
    LOGGER.info(
        "customerRazorPayOrder :{}", new ObjectMapper().writeValueAsString(customerRazorPayOrder));

    persistableCustomerRazorPayOrder.setAmount(customerRazorPayOrder.getAmount());
    persistableCustomerRazorPayOrder.setAmountPaid(customerRazorPayOrder.getAmountPaid());
    persistableCustomerRazorPayOrder.setAmountDue(0L);
    persistableCustomerRazorPayOrder.setReceipt(customerRazorPayOrder.getReceipt());
    persistableCustomerRazorPayOrder.setPaymentId(persistableCustomerRazorPayOrder.getPaymentId());
    //    persistableCustomerRazorPayOrder.setSignature();
    persistableCustomerRazorPayOrder.setRazorPayOrderIdPostPayment(
        customerRazorPayOrder.getOrderCode());
    persistableCustomerRazorPayOrder.setRazorPayOrderStatus(
        customerRazorPayOrder.getRazorPayOrderStatus());
    persistableCustomerRazorPayOrder.setRazorPayCustomerId(customer.getRazorPayCustomerId());
    persistableCustomerRazorPayOrder.setCustomerPhone(customer.getUserName());
    persistableCustomerRazorPayOrder.setCustomerEmail(customer.getEmailAddress());
    persistableCustomerRazorPayOrder.setCustomerName(
        customer.getFirstName() + " " + customer.getLastName());
    persistableCustomerRazorPayOrder.setPaytmentAttempts(1);

    if (persistableCustomerRazorPayOrder.getEvent().equals("payment.captured")
        && !persistableCustomerRazorPayOrder
            .getRazorPayOrderStatus()
            .equals(RazorPayOrderStatus.PAID)) {
      LOGGER.info("Razorpay status: {}", persistableCustomerRazorPayOrder.getRazorPayOrderStatus());
      LOGGER.info("*****Order updating by webhooks******");
      LOGGER.info(
          "persistableCustomerRazorPayOrder :{}",
          new ObjectMapper().writeValueAsString(persistableCustomerRazorPayOrder));
      updateRazorPayDetail(persistableCustomerRazorPayOrder, merchantStore, true);
    }
  }

  private PersistableCustomerRazorPayOrder getPersistPojo(String payload) {
    PersistableCustomerRazorPayOrder persistableCustomerRazorPayOrder =
        new PersistableCustomerRazorPayOrder();
    JsonObject object = new JsonParser().parse(payload).getAsJsonObject();
    if (!object.get("event").isJsonNull()) {
      persistableCustomerRazorPayOrder.setEvent(object.get("event").getAsString());
    }
    JsonObject payloadObj =
        new JsonParser()
            .parse(payload)
            .getAsJsonObject()
            .get("payload")
            .getAsJsonObject()
            .get("payment")
            .getAsJsonObject()
            .get("entity")
            .getAsJsonObject();

    if (!payloadObj.get("id").isJsonNull()) {
      persistableCustomerRazorPayOrder.setPaymentId(payloadObj.get("id").getAsString());
    }

    if (!payloadObj.get("order_id").isJsonNull()) {
      persistableCustomerRazorPayOrder.setOrderCode(payloadObj.get("order_id").getAsString());
    }

    if (!payloadObj.get("contact").isJsonNull()) {
      persistableCustomerRazorPayOrder.setCustomerPhone(
          payloadObj.get("contact").getAsString().substring(3, 13));
    }
    return persistableCustomerRazorPayOrder;
  }

  private void removeOrderCodeFromCache(String phone) {
    cartFacade.freeCartByPhone(phone);
  }

  private void updateCustomerFirstOrderFlag(
      PersistableCustomerRazorPayOrder persistableCustomerRazorPayOrder,
      MerchantStore merchantStore) {
    PersistableCustomer customer =
        customerFacade.getCustomerByUserName(persistableCustomerRazorPayOrder.getCustomerPhone());
    if (!customer.isFirstOrderPlaced()) {
      customer.setFirstOrderPlaced(true);
      customerFacade.update(customer, merchantStore);
    }
  }

  private void updateCustomerOrderStatusHistory(CustomerOrder customerOrder) {
    PersistableCustomerOrderStatusHistory persistableCustomerOrderStatusHistory =
        new PersistableCustomerOrderStatusHistory();
    persistableCustomerOrderStatusHistory.setOrderCode(customerOrder.getOrderCode());
    persistableCustomerOrderStatusHistory.setCustomerOrderId(customerOrder.getId());
    // persistableCustomerOrderStatusHistory.setOrderedDate(Calendar.getInstance().getTimeInMillis());
    persistableCustomerOrderStatusHistory.setDeliveredDate(null);
    persistableCustomerOrderStatusHistory.setComments("order paid successfully.");
    persistableCustomerOrderStatusHistory.setStatus(OrderStatus.ORDERED);
    customerOrderStatusHistoryFacade.createOrUpdate(
        persistableCustomerOrderStatusHistory, null, customerOrder.getCustomerPhone());
  }

  private void deleteItemFromCustomerCart(String phone) {
    Cart cartModel = null;
    try {
      cartModel = cartService.getAllCartByCustomerPhone(phone);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.CART_ITEM_DELETE_FAILURE.getErrorCode(),
          PaymentErrorCodes.CART_ITEM_DELETE_FAILURE.getErrorMessage());
    }
    for (CartItem cartItem : cartModel.getCartItems()) {
      cartFacade.deleteCartItem(cartItem);
    }
  }

  private CustomerRazorPayOrder updateRazorPayOrder(
      PersistableCustomerRazorPayOrder persistableCustomerRazorPayOrder, boolean isWebhooks) {
    CustomerRazorPayOrder customerRazorPayOrder = null;
    try {
      customerRazorPayOrder =
          customerRazorPayOrderService.getByReceipt(persistableCustomerRazorPayOrder.getReceipt());
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.RAZORPAY_GET_BY_RECEIPT_FAILURE.getErrorCode(),
          PaymentErrorCodes.RAZORPAY_GET_BY_RECEIPT_FAILURE.getErrorMessage());
    }
    if (Objects.isNull(customerRazorPayOrder)) {
      LOGGER.error(
          "customerRazorPayOrder not exists for receipt {}",
          persistableCustomerRazorPayOrder.getReceipt());
      throw new ResourceNotFoundException(
          "customerRazorPayOrder not exists for receipt  "
              + persistableCustomerRazorPayOrder.getReceipt());
    }
    verifyRazorPayOrderAndUpdate(
        customerRazorPayOrder, persistableCustomerRazorPayOrder.getOrderCode());
    customerRazorPayOrder.setRazorPayPaymentId(persistableCustomerRazorPayOrder.getPaymentId());
    customerRazorPayOrder.setOrderCode(persistableCustomerRazorPayOrder.getOrderCode());
    customerRazorPayOrder.setSignature(persistableCustomerRazorPayOrder.getSignature());
    customerRazorPayOrder.setAmount(persistableCustomerRazorPayOrder.getAmount());
    customerRazorPayOrder.setAmountDue(persistableCustomerRazorPayOrder.getAmountDue());
    customerRazorPayOrder.setAmountPaid(persistableCustomerRazorPayOrder.getAmountPaid());
    customerRazorPayOrder.setCustomerEmail(persistableCustomerRazorPayOrder.getCustomerEmail());
    customerRazorPayOrder.setCustomerName(persistableCustomerRazorPayOrder.getCustomerName());
    customerRazorPayOrder.setCustomerPhone(persistableCustomerRazorPayOrder.getCustomerPhone());
    customerRazorPayOrder.setStatus(OrderStatus.ORDERED);
    customerRazorPayOrder.setWebhooksUpdated(isWebhooks);
    try {
      customerRazorPayOrder = customerRazorPayOrderService.update(customerRazorPayOrder);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.RAZORPAY_ORDER_UPDATE_FAILURE.getErrorCode(),
          PaymentErrorCodes.RAZORPAY_ORDER_UPDATE_FAILURE.getErrorMessage());
    }
    return customerRazorPayOrder;
  }

  private void customerRewardPointConsumption(CustomerOrder customerOrder) {
    int rewardPoint = customerOrder.getAppliedRewardPoint();
    if (rewardPoint > 0) {
      PersistableMembership membership =
          membershipFacade.getMembershipByPhone(customerOrder.getCustomerPhone());
      membership.setRewardPoint(membership.getRewardPoint() - rewardPoint);
      membershipFacade.update(membership, null);
    }
  }

  private void verifyRazorPayOrderAndUpdate(
      CustomerRazorPayOrder customerRazorPayOrder, String orderCode) {
    RazorPayServiceProvider razorPayServiceProvider = new RazorPayServiceProvider();
    Order razorPayOrder = null;
    try {
      razorPayOrder = razorPayServiceProvider.getOrder(orderCode);
    } catch (RazorpayException e) {
      throwServiceRuntImeException(
          e,
          PaymentErrorCodes.RAZORPAY_ORDER_UPDATE_FAILURE.getErrorCode(),
          PaymentErrorCodes.RAZORPAY_ORDER_UPDATE_FAILURE.getErrorMessage());
    }
    if (razorPayOrder.get("status").toString().equalsIgnoreCase(RazorPayOrderStatus.PAID.name())) {
      customerRazorPayOrder.setRazorPayOrderStatus(RazorPayOrderStatus.PAID);
      customerRazorPayOrder.setPaytmentAttempts(
          Integer.parseInt(razorPayOrder.get("attempts").toString()));
    }
  }

  // TODO : move to customerOrder facade , pass orderCode and flag successful- done
  private CustomerOrder updateOrderCustomerAfterPayment(
      PersistableCustomerRazorPayOrder persistableCustomerRazorPayOrder, boolean successful) {
    CustomerOrder customerOrder =
        customerOrderFacade.getByOrderCode(persistableCustomerRazorPayOrder.getReceipt());
    CustomerSaleOrder customerSaleOrder = null;
    if (successful) {
      // TODO - Pending
      customerSaleOrder = populateCustomerOrderToCustomerSaleOrder(customerOrder);
      customerOrder.setDatePurchased(Calendar.getInstance().getTimeInMillis());
      customerOrder.setStatus(OrderStatus.ORDERED);
      customerOrder.setPaymentStatus(PaymentStatus.SUCCESSFUL);
    } else {
      customerOrder.setStatus(OrderStatus.CANCELLED);
      customerOrder.setPaymentStatus(PaymentStatus.CANCELLED);
    }
    customerOrder.setOrderType(OrderType.ORDER);
    // todo : need to set require attribute..
    return customerOrderFacade.update(customerOrder);
  }

  private CustomerSaleOrder populateCustomerOrderToCustomerSaleOrder(CustomerOrder customerOrder) {
    CustomerSaleOrder customerSaleOrder = new CustomerSaleOrder();

    return customerSaleOrder;
  }

  // TODO : move to RazorPay service provider-done
  private boolean verifyPaymentSignature(
      String orderCode, String paymentId, String razorpaySignature) {
    RazorPayServiceProvider razorPayServiceProvider = new RazorPayServiceProvider();

    // code got deleted
    return false;
  }
}
