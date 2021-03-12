/** */
package com.salesmanager.shop.store.facade.order;

import com.razorpay.Customer;
import com.razorpay.Order;
import com.razorpay.Refund;
import com.salesmanager.core.business.HabbitCoreConstant;
import com.salesmanager.core.business.ShippingPartnerConstant;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.email.Email;
import com.salesmanager.core.business.modules.payment.gateway.RazorPayServiceProvider;
import com.salesmanager.core.business.modules.sms.constants.EmailType;
import com.salesmanager.core.business.modules.sms.constants.SMSType;
import com.salesmanager.core.business.modules.sms.model.Sms;
import com.salesmanager.core.business.services.customer.checkoutAudit.CheckoutAuditFlowService;
import com.salesmanager.core.business.services.membership.CustomerPreAllottedCheckoutQuotaService;
import com.salesmanager.core.business.services.order.CustomerOrderService;
import com.salesmanager.core.business.services.order.orderproduct.CustomerOrderProductService;
import com.salesmanager.core.business.services.payments.gateway.razorpay.CustomerRazorPayOrderService;
import com.salesmanager.core.business.services.system.EmailService;
import com.salesmanager.core.business.services.system.SmsService;
import com.salesmanager.core.model.customer.CustomerNotification;
import com.salesmanager.core.model.customer.CustomerOrderAddress;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.*;
import com.salesmanager.core.model.order.orderproduct.CustomerOrderProduct;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.order.orderstatus.TrackingUrlType;
import com.salesmanager.core.model.payments.PaymentStatus;
import com.salesmanager.core.model.shoppingcart.Cart;
import com.salesmanager.core.model.unicommerce.UnicommerceAuthenticationToken;
import com.salesmanager.shop.admin.controller.customer.membership.CustomerOrderFacade;
import com.salesmanager.shop.admin.controller.customer.membership.CustomerPreAllottedCheckoutQuotaFacade;
import com.salesmanager.shop.admin.controller.customer.membership.MembershipFacade;
import com.salesmanager.shop.admin.controller.customer.order.CustomerOrderStatusHistoryFacade;
import com.salesmanager.shop.admin.controller.saleOrder.CustomerSaleOrderFacade;
import com.salesmanager.shop.admin.controller.unicom.UnicommerceService;
import com.salesmanager.shop.cache.util.CacheUtil;
import com.salesmanager.shop.error.codes.CustomerErrorCodes;
import com.salesmanager.shop.error.codes.PaymentErrorCodes;
import com.salesmanager.shop.error.codes.UnicommerceErrorCodes;
import com.salesmanager.shop.model.customer.*;
import com.salesmanager.shop.model.customer.mapper.CustomerOrderMapper;
import com.salesmanager.shop.model.customer.mapper.CustomerOrderProductMapper;
import com.salesmanager.shop.model.customer.mapper.CustomerPreAllottedCheckoutQuotaMapper;
import com.salesmanager.shop.model.customer.mapper.CustomerRazorPayOrderMapper;
import com.salesmanager.shop.model.order.UnicommerceCancelSaleOrderRequest;
import com.salesmanager.shop.model.order.UnicommerceCreateSaleOrderRequest;
import com.salesmanager.shop.model.productAttribute.PersistableProduct;
import com.salesmanager.shop.model.productAttribute.PersistableProductAvailability;
import com.salesmanager.shop.model.shoppingcart.PersistableCart;
import com.salesmanager.shop.populator.catalog.PersistableCustomerOrderPopulator;
import com.salesmanager.shop.populator.catalog.PersistableRazorPayOrderPopulator;
import com.salesmanager.shop.populator.catalog.SalesOrderPopulator;
import com.salesmanager.shop.store.api.exception.ResourceDuplicateException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.product.facade.ProductFacade;
import com.salesmanager.shop.store.facade.cart.CartFacade;
import com.salesmanager.shop.store.facade.customer.CustomerFacade;
import com.salesmanager.shop.store.facade.customer.orderAddress.CustomerOrderAddressFacade;
import com.salesmanager.shop.store.facade.order.util.OrderUtil;
import com.salesmanager.shop.store.facade.payments.gateway.razorpay.CustomerRazorPayFacade;
import java.time.LocalDate;
import java.util.*;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("customerOrderFacade")
public class CustomerOrderFacadeImpl implements CustomerOrderFacade {

  @Value("${unicom.flag}")
  private String unicomFlag;

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerOrderFacadeImpl.class);

  private static final Object lock = new Object();
  @Inject private CustomerOrderService customerOrderService;
  @Inject private CustomerOrderMapper customerOrderMapper;
  @Inject private ProductFacade productFacade;
  @Inject private CustomerOrderProductMapper customerOrderProductMapper;
  @Inject private CustomerOrderProductService customerOrderProductService;
  @Inject private CustomerRazorPayOrderService customerRazorPayOrderService;
  @Inject private CustomerRazorPayOrderMapper customerRazorPayOrderMapper;
  @Inject private CustomerPreAllottedCheckoutQuotaFacade cpcpFacade;
  @Inject private CustomerPreAllottedCheckoutQuotaService customerPreAllottedCheckoutQuotaService;
  @Inject private CustomerPreAllottedCheckoutQuotaMapper cpcpMapper;
  @Inject private CustomerFacade customerFacade;
  @Inject private CustomerRazorPayFacade razorPayFacade;
  @Inject private CacheUtil cacheUtil;
  @Inject private PersistableCustomerOrderPopulator persistableCustomerOrderPopulator;
  @Inject private PersistableRazorPayOrderPopulator persistableRazorPayOrderPopulator;
  @Inject private CustomerOrderStatusHistoryFacade customerOrderStatusHistoryFacade;
  @Inject private OrderUtil orderUtil;
  @Inject private CustomerOrderFacade customerOrderFacade;
  @Inject private CartFacade cartFacade;
  @Inject private MembershipFacade membershipFacade;
  @Inject private CustomerOrderAddressFacade customerOrderAddressFacade;
  @Inject private EmailService emailService;
  @Inject private SalesOrderPopulator salesOrderPopulator;
  @Inject private UnicommerceService unicommerceService;
  @Inject private CustomerSaleOrderFacade customerSaleOrderFacade;
  @Inject private SmsService smsService;
  @Inject private CheckoutAuditFlowService checkoutAuditFlowService;

  @Override
  public PersistableCustomerOrder create(
      PersistableCustomerOrder persistableCustomerOrder, MerchantStore merchantStore) {
    CustomerOrder customerOrder = null;
    try {
      if (StringUtils.isNotEmpty(persistableCustomerOrder.getOrderCode())) {
        customerOrder =
            customerOrderService.findByOrderCode(persistableCustomerOrder.getOrderCode());
      }
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ORDER_GET_BY_CODE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ORDER_GET_BY_CODE_FAILURE.getErrorMessage());
    }

    if (Objects.nonNull(customerOrder)) {
      LOGGER.error(
          "customerOrder Already exists for code {}", persistableCustomerOrder.getOrderCode());
      throw new ResourceDuplicateException(
          "customerOrder Already exists for code " + persistableCustomerOrder.getOrderCode());
    }
    customerOrder = customerOrderMapper.toCustomerOrder(persistableCustomerOrder);
    try {
      customerOrder = customerOrderService.create(customerOrder);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ORDER_CREATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ORDER_CREATE_FAILURE.getErrorMessage());
    }
    persistableCustomerOrder.setId(customerOrder.getId());
    return persistableCustomerOrder;
  }

  @Override
  public CustomerOrder update(CustomerOrder customerOrder) {
    try {
      customerOrder = customerOrderService.update(customerOrder);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ORDER_UPDATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ORDER_UPDATE_FAILURE.getErrorMessage());
    }
    return customerOrder;
  }

  @Override
  public CustomerOrder getByOrderCode(String orderCode) {
    CustomerOrder customerOrder = null;
    try {
      customerOrder = customerOrderService.findByOrderCode(orderCode);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ORDER_GET_BY_CODE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ORDER_GET_BY_CODE_FAILURE.getErrorMessage());
    }
    return customerOrder;
  }

  @Override
  public PersistableCustomerOrder checkoutCart(
      PersistableCheckoutCart persistableCheckoutCart, MerchantStore merchantStore, String phone)
      throws Exception {
    PersistableCustomerOrder customerOrder = new PersistableCustomerOrder();
    CheckoutAuditFlow checkoutAuditFlow = new CheckoutAuditFlow();
    try {
      // Todo : set cartcode and order in jedis to not support multicart - done

      // TODO : when user return from payment page or payment cancelled, what will happen with
      // cart/order, it will impact the multi device checkout support
      String newOrderCode = orderUtil.uniqueCustomerOrderCode();
      LOGGER.info("RazorPay_issue order code {}", newOrderCode);
      preValidateIfCartIsNotAlreadyCheckedOut(persistableCheckoutCart, newOrderCode);
      checkoutAuditFlow.setOrderCode(newOrderCode);

      PersistableCustomerOrder persistableCustomerOrder =
          persistableCustomerOrderPopulator
              .persistableCheckoutCartToPersistableCustomerOrderPopulator(persistableCheckoutCart);
      Customer razorPayCustomer;
      PersistableCart cart = persistableCheckoutCart.getCart();
      Order razorPayOrder;
      // Todo : set cartcode to ordercode in redis -done
      persistableCustomerOrder.setOrderCode(newOrderCode);

      if (StringUtils.isEmpty(persistableCheckoutCart.getCart().getRazorPayCustomerId())) {
        // create razor pay customer and update razorpaycustomerId in Customer entity
        Long startTime = new Date().getTime();
        LOGGER.info("CREATE RAZORPAY CUSTOMER API START TIME : {}", startTime);
        LOGGER.info(
            "RazorPay_issue Customer email {} and phone {}",
            cart.getCustomerEmail(),
            cart.getCustomerPhone());
        razorPayCustomer =
            razorPayFacade.createRazorPayCustomer(
                cart.getCustomerName(), cart.getCustomerEmail(), cart.getCustomerPhone());
        Long endTime = new Date().getTime();
        LOGGER.info("CREATE RAZORPAY CUSTOMER API END TIME : {}", endTime);
        LOGGER.info("CREATE RAZORPAY CUSTOMER API LATENCY : {}", endTime - startTime);
        LOGGER.info("RazorPay_issue customer id :{}", (String) razorPayCustomer.get("id"));

        // update customer with customer razorpayId
        startTime = new Date().getTime();
        LOGGER.info("UPDATE RAZORPAY CUS_ID IN CUSTOMER AND CART  START TIME : {}", startTime);

        PersistableCustomer persistableCustomer =
            customerFacade.getCustomerByUserName(cart.getCustomerPhone());
        persistableCustomer.setRazorPayCustomerId(razorPayCustomer.get("id"));
        customerFacade.update(persistableCustomer, merchantStore);
        // update cart with customer razorpayId
        Cart cartModel = cartFacade.getCartByPhone(phone);
        cartModel.setRazorPayCustomerId(razorPayCustomer.get("id"));
        cartFacade.update(cartModel);

        endTime = new Date().getTime();
        LOGGER.info("UPDATE RAZORPAY CUS_ID IN CUSTOMER AND CART END TIME : {}", endTime);
        LOGGER.info(
            "UPDATE RAZORPAY CUS_ID IN CUSTOMER AND CART  LATENCY : {}", endTime - startTime);

        persistableCheckoutCart.getCart().setRazorPayCustomerId(razorPayCustomer.get("id"));
      }
      LOGGER.info(
          "Razorpay_issue customer Id : {}",
          persistableCheckoutCart.getCart().getRazorPayCustomerId());
      persistableCustomerOrder.setRazorPayCustomerId(
          persistableCheckoutCart.getCart().getRazorPayCustomerId());

      // create order at RazorPay l-141 to L-155 move to razor pay facade Impl
      Long startTime = new Date().getTime();
      LOGGER.info("CREATE RAZORPAY ORDER API START TIME : {}", startTime);
      LOGGER.info(
          " RazorPay_issue Razorpay order request payload cus_id : {}, amount :{} order_code :{}",
          persistableCheckoutCart.getCart().getRazorPayCustomerId(),
          persistableCustomerOrder.getDisplayPrice().intValue(),
          persistableCustomerOrder.getOrderCode());
      razorPayOrder =
          razorPayFacade.createRazorPayOrder(
              persistableCustomerOrder.getDisplayPrice().intValue(),
              persistableCustomerOrder.getOrderCode());
      if (Objects.nonNull(razorPayOrder)) {
        //        LOGGER.info(
        //            "Razorpay order in response obj:{}",
        //            new ObjectMapper().writeValueAsString(razorPayOrder));
        LOGGER.info(
            " RazorPay_issue Razorpay order : {} , phone :{}",
            razorPayOrder.get("id").toString(),
            persistableCustomerOrder.getCustomerPhone());
        checkoutAuditFlow.setRazorpayOrderCreated(true);
        checkoutAuditFlow.setRazorPayOrderCode(razorPayOrder.get("id").toString());
      }
      Long endTime = new Date().getTime();
      LOGGER.info("CREATE RAZORPAY ORDER API END TIME : {}", endTime);
      LOGGER.info("CREATE RAZORPAY ORDER API LATENCY : {}", endTime - startTime);
      if (Objects.isNull(razorPayOrder)) {
        throwServiceRuntImeException(
            new Exception(),
            PaymentErrorCodes.RAZORPAY_ORDER_CREATE_FAILURE.getErrorCode(),
            PaymentErrorCodes.RAZORPAY_ORDER_CREATE_FAILURE.getErrorMessage());
      }

      // TODO : fix error code and error message should be specific to entity -done
      // create razorPayOrder
      startTime = new Date().getTime();
      LOGGER.info("CREATE CUSTOMER RAZORPAY ORDER API START TIME : {}", startTime);
      razorPayFacade.createRazorPayOrderByRazorPayOrder(razorPayOrder, persistableCustomerOrder);
      checkoutAuditFlow.setCustomerRazorpayOrderCreated(true);
      endTime = new Date().getTime();
      LOGGER.info("CREATE CUSTOMER RAZORPAY ORDER API END TIME : {}", endTime);
      LOGGER.info("CREATE CUSTOMER RAZORPAY ORDER API LATENCY : {}", endTime - startTime);
      // create customerOrder
      LOGGER.info("CREATE CUSTOMER ORDER API START ");
      createOrder(persistableCustomerOrder, merchantStore);
      LOGGER.info("CREATE CUSTOMER ORDER API END ");
      LOGGER.info(
          "RazorPay_issue customer order : {} phone {}",
          persistableCustomerOrder.getOrderCode(),
          persistableCustomerOrder.getCustomerPhone());
      checkoutAuditFlow.setCustomerOrderCreated(true);

      // update persistable object
      customerOrder.setOrderCode(persistableCustomerOrder.getOrderCode());
      customerOrder.setDisplayPrice(persistableCustomerOrder.getDisplayPrice());
      customerOrder.setGiftWrap(persistableCustomerOrder.isGiftWrap());
    } catch (Exception e) {
      throw new Exception("****CHECKOUT RAZORPAY ORDER ISSUE****" + e);
    } finally {
      try {
        checkoutAuditFlowService.create(checkoutAuditFlow);
      } catch (Exception e) {
        throwServiceRuntImeException(
            new Exception(),
            CustomerErrorCodes.CHECKOUT_AUDIT_CREATE_FAILURE.getErrorCode(),
            CustomerErrorCodes.CHECKOUT_AUDIT_CREATE_FAILURE.getErrorMessage());
      }
    }
    return customerOrder;
  }

  private void preValidateIfCartIsNotAlreadyCheckedOut(
      PersistableCheckoutCart persistableCheckoutCart, String newOrderCode) {
    synchronized (lock) {
      String orderCode =
          cacheUtil.getObjectFromCache(
              persistableCheckoutCart.getCart().getCartCode(), String.class);
      if (Objects.nonNull(orderCode)) {
        throwServiceRuntImeException(
            new ServiceException(),
            CustomerErrorCodes.CUSTOMER_MULTI_CART_FAILURE.getErrorCode(),
            CustomerErrorCodes.CUSTOMER_MULTI_CART_FAILURE.getErrorMessage());
      } else {
        cacheUtil.setObjectInCache(
            persistableCheckoutCart.getCart().getCustomerPhone(),
            persistableCheckoutCart.getCart().getCartCode());
        // cacheUtil.setObjectInCache(persistableCheckoutCart.getCart().getCartCode(),
        // newOrderCode);
      }
    }
  }

  private PersistableCustomerOrder createOrder(
      PersistableCustomerOrder persistableCustomerOrder, MerchantStore merchantStore)
      throws Exception {
    CustomerOrder customerOrder = customerOrderMapper.toCustomerOrder(persistableCustomerOrder);
    customerOrder.setOrderProducts(null);
    CustomerOrderAddress customerOrderAddress =
        getCustomerOrderAddress(persistableCustomerOrder.getShippingAddress());
    //    customerOrder.setShippingAddress(
    //        addressMapper.toAddress(persistableCustomerOrder.getShippingAddress()));
    //    customerOrder.setBillingAddress(
    //        addressMapper.toAddress(persistableCustomerOrder.getBillingAddress()));
    customerOrder.setShippingAddress(customerOrderAddress);
    customerOrder.setBillingAddress(customerOrderAddress);
    Long startTime = new Date().getTime();
    LOGGER.info("CREATE CUSTOMER ORDER API START TIME : {}", startTime);
    customerOrder = createCustomerOrder(customerOrder);
    Long endTime = new Date().getTime();
    LOGGER.info("CREATE CUSTOMER ORDER API END TIME : {}", endTime);
    LOGGER.info("CREATE CUSTOMER ORDER API LATENCY : {}", endTime - startTime);
    for (PersistableCustomerOrderProduct orderProduct :
        persistableCustomerOrder.getOrderProducts()) {
      createOrderProduct(customerOrder, orderProduct);
      deleteCustomerProductConsumptionPending(
          orderProduct, persistableCustomerOrder.getCustomerPhone(), merchantStore);
    }
    createCustomerOrderStatusHistory(customerOrder);
    return persistableCustomerOrder;
  }

  private CustomerOrderAddress getCustomerOrderAddress(PersistableCustomerOrderAddress address) {
    return customerOrderAddressFacade.convertOrderAddressFromAddress(address);
  }

  private CustomerOrderProduct createOrderProduct(
      CustomerOrder customerOrder,
      PersistableCustomerOrderProduct persistableCustomerOrderProduct) {
    CustomerOrderProduct orderProduct =
        customerOrderProductMapper.toCustomerOrderProduct(persistableCustomerOrderProduct);
    orderProduct.setCustomerOrder(customerOrder);
    try {
      orderProduct = customerOrderProductService.create(orderProduct);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ORDER_PRODUCT_CREATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ORDER_PRODUCT_CREATE_FAILURE.getErrorMessage());
    }
    return orderProduct;
  }

  private CustomerOrder createCustomerOrder(CustomerOrder customerOrder) {
    CustomerOrder order = null;
    try {
      order = customerOrderService.create(customerOrder);
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ORDER_CREATE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ORDER_CREATE_FAILURE.getErrorMessage());
    }
    return order;
  }

  private void deleteCustomerProductConsumptionPending(
      PersistableCustomerOrderProduct orderProduct, String phone, MerchantStore merchantStore) {
    PersistableCustomerPreAllottedCheckoutQuota persistableCustomerPreAllottedCheckoutQuota = null;
    try {
      persistableCustomerPreAllottedCheckoutQuota =
          cpcpFacade.getCustomerProductConsumptionPendingByCartItemCode(
              orderProduct.getCartItemCode(), phone);
      if (persistableCustomerPreAllottedCheckoutQuota.getCount()
          > orderProduct.getProductQuantity()) {
        PersistableProduct product = productFacade.getProductBySkuId(orderProduct.getSku(), null);
        PersistableProductAvailability persistableProductAvailability =
            product.getAvailabilities().iterator().next();
        if (Objects.nonNull(persistableProductAvailability.getProductQuantity())) {
          product
              .getAvailabilities()
              .iterator()
              .next()
              .setProductQuantity(
                  persistableProductAvailability.getProductQuantity()
                      + (persistableCustomerPreAllottedCheckoutQuota.getCount()
                          - orderProduct.getProductQuantity()));
          productFacade.updateProduct(product, merchantStore);
        }
      }
      customerPreAllottedCheckoutQuotaService.delete(
          cpcpMapper.toCustomerPreAllottedCheckoutQuota(
              persistableCustomerPreAllottedCheckoutQuota));
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_PRODUCT_CONSUMPTION_PENDING_DELETE_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_PRODUCT_CONSUMPTION_PENDING_DELETE_FAILURE.getErrorMessage());
    }
  }

  private void createCustomerOrderStatusHistory(CustomerOrder customerOrder) {
    PersistableCustomerOrderStatusHistory persistableCustomerOrderStatusHistory =
        new PersistableCustomerOrderStatusHistory();
    persistableCustomerOrderStatusHistory.setOrderCode(customerOrder.getOrderCode());
    persistableCustomerOrderStatusHistory.setCustomerOrderId(customerOrder.getId());
    persistableCustomerOrderStatusHistory.setOrderedDate(Calendar.getInstance().getTimeInMillis());
    persistableCustomerOrderStatusHistory.setDeliveredDate(null);
    persistableCustomerOrderStatusHistory.setComments("order created successfully.");
    persistableCustomerOrderStatusHistory.setStatus(OrderStatus.CREATED);
    customerOrderStatusHistoryFacade.createOrUpdate(
        persistableCustomerOrderStatusHistory, null, customerOrder.getCustomerPhone());
  }

  @Override
  public PersistableCustomerOrder getOrderDetails(String orderCode, MerchantStore merchantStore) {
    CustomerOrder customerOrder = getByOrderCode(orderCode);
    PersistableCustomerOrder persistableCustomerOrder = new PersistableCustomerOrder();
    //    return customerOrderMapper.toPersistableCustomerOrder(customerOrder);
    persistableCustomerOrderPopulator.cusomerOrderToPersistableCustomerOrderPopulator(
        customerOrder, persistableCustomerOrder);
    return persistableCustomerOrder;
  }

  @Override
  public CustomerOrder updateOrderCustomerAfterPayment(String receipt, boolean successful)
      throws Exception {
    CustomerOrder customerOrder = customerOrderFacade.getByOrderCode(receipt);
    UnicommerceCreateSaleOrderRequest customerSaleOrder = null;
    if (successful) {
      customerSaleOrder =
          salesOrderPopulator.populateCustomerOrderToCustomerSaleOrder(customerOrder);
      // create unicom sale order
      LOGGER.info("unicom flag {}", unicomFlag);
      if (Boolean.parseBoolean(unicomFlag)) {
        createUnicommerceSaleOrder(customerSaleOrder);
      }
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

  private void createUnicommerceSaleOrder(UnicommerceCreateSaleOrderRequest customerSaleOrder)
      throws Exception {
    UnicommerceAuthenticationToken token = null;
    CustomerSaleOrder saleOrder = null;
    try {
      Long startTime = new Date().getTime();
      LOGGER.info("CREATE UNICOM SALE ORDER API START TIME : {}", startTime);
      saleOrder = unicommerceService.createUnicommerceSaleOrder(customerSaleOrder, token);
      Long endTime = new Date().getTime();
      LOGGER.info("CREATE UNICOM SALE ORDER API END TIME : {}", endTime);
      LOGGER.info("CREATE UNICOM SALE ORDER API LATENCY : {}", endTime - startTime);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          UnicommerceErrorCodes.CREATE_UNICOM_SALE_ORDER.getErrorCode(),
          UnicommerceErrorCodes.CREATE_UNICOM_SALE_ORDER.getErrorMessage());
    }
    customerSaleOrderFacade.createCustomerSaleOrder(saleOrder);
  }

  // TODO : move to razor facad Impl-done
  private Order createRazorPayOrder(int intValue, String orderCode) {
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

  // TODO : move to razor facad Impl-done
  private Customer createRazorPayCustomer(
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

  // TODO : move to razor facad Impl- done
  private PersistableCustomerRazorPayOrder createRazorPayOrderByRazorPayOrder(
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
  public void orderCancel(String orderCode, MerchantStore merchantStore, OrderStatus orderStatus) {
    Refund razorPayRefund = razorPayFacade.refundAmountToCustomer(orderCode);
    // cancel sale order
    // todo : staging ignore
    cancelSaleOrder(orderCode);
    CustomerOrder customerOrder = getByOrderCode(orderCode);
    int friendPoint = (int) Math.ceil((double) customerOrder.getDisplayPrice() / 100);
    if (customerOrder.getAppliedRewardPoint() > 0) {
      membershipFacade.addRewardPoints(
          customerOrder.getAppliedRewardPoint(), customerOrder.getCustomerPhone(), false);
    }
    membershipFacade.consumeRewardPoints(
        customerOrder.getEarnedPoints(), customerOrder.getCustomerPhone(), false);
    if (StringUtils.isNotEmpty(customerOrder.getCustomerFriendPhone())) {
      membershipFacade.consumeRewardPoints(
          friendPoint, customerOrder.getCustomerFriendPhone(), true);
    }
    customerOrder.setStatus(OrderStatus.CANCELLED);
    update(customerOrder);
    sendMail(customerOrder);
  }

  private void cancelSaleOrder(String orderCode) {
    UnicommerceCancelSaleOrderRequest cancelSaleOrderRequest =
        new UnicommerceCancelSaleOrderRequest();
    cancelSaleOrderRequest.setSaleOrderCode(orderCode);
    UnicommerceAuthenticationToken token = unicommerceService.getUnicommerceAccessToken();
    try {
      unicommerceService.cancelSaleOrder(cancelSaleOrderRequest, token);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          UnicommerceErrorCodes.CANCEL_UNICOM_SALE_ORDER.getErrorCode(),
          UnicommerceErrorCodes.CANCEL_UNICOM_SALE_ORDER.getErrorMessage());
    }
  }

  private void sendMail(CustomerOrder customerOrder) {
    Email email = emailService.createMail(EmailType.ORDER_CANCEL);
    email.setTo(customerOrder.getCustomerEmailAddress());
    email.getTemplateTokens().put("ConsumerName", customerOrder.getCustomerName());
    email.getTemplateTokens().put("OrderID", customerOrder.getId().toString());
    try {
      CustomerNotification customerNotification =
          cacheUtil.getObjectFromCache(
              customerOrder.getCustomerPhone() + HabbitCoreConstant._NOTIFICATION,
              CustomerNotification.class);
      if (Objects.isNull(customerNotification)) {
        emailService.sendEmail(email);
      } else if (customerNotification.isEmailEnable()) {
        emailService.sendEmail(email);
      }
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ORDER_CANCEL_EMAIL_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ORDER_CANCEL_EMAIL_FAILURE.getErrorMessage());
    }
  }

  @Override
  public PersistableCustomerOrder getByOrderId(Long id, MerchantStore merchantStore) {
    CustomerOrder customerOrder = null;
    try {
      customerOrder = customerOrderService.getById(id);
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ORDER_GET_BY_ID_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ORDER_GET_BY_ID_FAILURE.getErrorMessage());
    }
    return customerOrderMapper.toPersistableCustomerOrder(customerOrder);
  }

  @Override
  public String getTrackingUrl(String orderCode) {
    String trackingUrl = null;
    TrackingUrlType trackingUrlType = null;
    // CustomerOrder customerOrder = getByOrderCode(orderCode);
    CustomerSaleOrder customerSaleOrder = customerSaleOrderFacade.getByCode(orderCode);
    String shippingProvider = customerSaleOrder.getShipperList().get(0).getShippingProvider();
    String trackingNumber = customerSaleOrder.getShipperList().get(0).getTrackingNumber();
    if (StringUtils.isNotEmpty(shippingProvider)
        && shippingProvider.equals(ShippingPartnerConstant.SHIPROCKET)) {
      trackingUrl = ShippingPartnerConstant.SHIPROCKET_BASE_URL + trackingNumber;
      trackingUrlType = TrackingUrlType.URL;
    } else if (StringUtils.isNotEmpty(shippingProvider)) {
      trackingUrl = trackingNumber.substring(0, 10);
      trackingUrlType = TrackingUrlType.PHONE_NUMBER;
    }
    CustomerOrder customerOrder = customerOrderFacade.getByOrderCode(orderCode);
    customerOrder.setStatus(
        Arrays.stream(OrderStatus.values())
            .filter(value -> value.getValue().equals(customerSaleOrder.getStatus()))
            .findFirst()
            .orElse(null));
    if (StringUtils.isNotEmpty(trackingNumber)) {
      customerOrder.setTrackingUrl(trackingUrl);
    }
    customerOrder = customerOrderFacade.update(customerOrder);
    updateCustomerOrderStatusHistory(customerOrder, trackingUrlType);
    if (StringUtils.isEmpty(trackingNumber)) {
      return HabbitCoreConstant.NOT_GENERATED;
    } else {
      return trackingUrl;
    }
  }

  private void updateCustomerOrderStatusHistory(
      CustomerOrder customerOrder, TrackingUrlType trackingUrlType) {
    PersistableCustomerOrderStatusHistory persistableCustomerOrderStatusHistory =
        new PersistableCustomerOrderStatusHistory();
    persistableCustomerOrderStatusHistory.setOrderCode(customerOrder.getOrderCode());
    persistableCustomerOrderStatusHistory.setTrackingUrl(customerOrder.getTrackingUrl());
    persistableCustomerOrderStatusHistory.setCustomerOrderId(customerOrder.getId());
    // persistableCustomerOrderStatusHistory.setOrderedDate(Calendar.getInstance().getTimeInMillis());
    persistableCustomerOrderStatusHistory.setDeliveredDate(null);
    persistableCustomerOrderStatusHistory.setComments("order paid successfully.");
    persistableCustomerOrderStatusHistory.setStatus(customerOrder.getStatus());
    persistableCustomerOrderStatusHistory.setTrackingUrlType(trackingUrlType);
    customerOrderStatusHistoryFacade.createOrUpdate(
        persistableCustomerOrderStatusHistory, null, customerOrder.getCustomerPhone());
  }

  @Override
  public void sendOrderShippedMail(CustomerSaleOrder customerSaleOrder) {
    CustomerOrder customerOrder = getByOrderCode(customerSaleOrder.getCode());
    Email email = emailService.createMail(EmailType.ORDER_SHIPPED);
    email.setTo(customerOrder.getCustomerEmailAddress());
    email.getTemplateTokens().put("ConsumerName", customerOrder.getCustomerName());
    email.getTemplateTokens().put("OrderID", customerOrder.getId().toString());
    // Todo : need to update EstimatedDeliveryTime
    email.getTemplateTokens().put("EstimatedDeliveryTime", "EstimatedDeliveryTime");
    try {
      CustomerNotification customerNotification =
          cacheUtil.getObjectFromCache(
              customerOrder.getCustomerPhone() + HabbitCoreConstant._NOTIFICATION,
              CustomerNotification.class);
      if (Objects.isNull(customerNotification)) {
        emailService.sendEmail(email);
      } else if (customerNotification.isEmailEnable()) {
        emailService.sendEmail(email);
      }
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ORDER_SHIPPED_EMAIL_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ORDER_SHIPPED_EMAIL_FAILURE.getErrorMessage());
    }
  }

  @Override
  public void sendOrderDeliveredMail(CustomerSaleOrder customerSaleOrder) {
    CustomerOrder customerOrder = getByOrderCode(customerSaleOrder.getCode());
    Email email = emailService.createMail(EmailType.ORDER_DELIVERED);
    email.setTo(customerOrder.getCustomerEmailAddress());
    email.getTemplateTokens().put("ConsumerName", customerOrder.getCustomerName());
    email.getTemplateTokens().put("OrderID", customerOrder.getId().toString());
    // Todo : need to update orderPlacedHabbitPointsEarned
    email
        .getTemplateTokens()
        .put("orderPlacedHabbitPointsEarned", String.valueOf(customerOrder.getEarnedPoints()));
    try {
      CustomerNotification customerNotification =
          cacheUtil.getObjectFromCache(
              customerOrder.getCustomerPhone() + HabbitCoreConstant._NOTIFICATION,
              CustomerNotification.class);
      if (Objects.isNull(customerNotification)) {
        emailService.sendEmail(email);
      } else if (customerNotification.isEmailEnable()) {
        emailService.sendEmail(email);
      }
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ORDER_DELIVERED_MAIL_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ORDER_DELIVERED_MAIL_FAILURE.getErrorMessage());
    }
  }

  @Override
  public void sendOrderConfirmationSms(CustomerOrder customerOrder) {
    Map<String, Object> model = new HashMap();
    model.put("orderId", customerOrder.getId());
    model.put("items", customerOrder.getOrderProducts().size());
    model.put("displayPrice", customerOrder.getDisplayPrice());
    try {
      CustomerNotification customerNotification =
          cacheUtil.getObjectFromCache(
              customerOrder.getCustomerPhone() + HabbitCoreConstant._NOTIFICATION,
              CustomerNotification.class);
      if (Objects.isNull(customerNotification)) {
        smsService.sendSms(
            new Sms(customerOrder.getCustomerPhone(), SMSType.ORDER_CONFIRMATION, model));
      } else if (customerNotification.isSmsEnable()) {
        smsService.sendSms(
            new Sms(customerOrder.getCustomerPhone(), SMSType.ORDER_CONFIRMATION, model));
      }
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.SEND_ORDER_CONFIRMED_SMS_FAILURE_THROUGH_KARIX.getErrorCode(),
          CustomerErrorCodes.SEND_ORDER_CONFIRMED_SMS_FAILURE_THROUGH_KARIX.getErrorMessage()
              + customerOrder.getCustomerPhone());
    }
  }

  @Override
  public void sendOrderShippedSms(CustomerSaleOrder customerSaleOrder) {
    CustomerOrder customerOrder = getByOrderCode(customerSaleOrder.getCode());
    Map<String, Object> model = new HashMap();
    model.put("orderId", customerOrder.getId());
    // todo : need to update estimateDays(send if only found in unicom response)
    // model.put("estimateDays", "6-10");
    // model.put("trackingUrl", customerOrderFacade.getTrackingUrl(customerOrder.getOrderCode()));
    try {
      CustomerNotification customerNotification =
          cacheUtil.getObjectFromCache(
              customerOrder.getCustomerPhone() + HabbitCoreConstant._NOTIFICATION,
              CustomerNotification.class);
      if (Objects.isNull(customerNotification)) {
        smsService.sendSms(new Sms(customerOrder.getCustomerPhone(), SMSType.ORDER_SHIPPED, model));
      } else if (customerNotification.isSmsEnable()) {
        smsService.sendSms(new Sms(customerOrder.getCustomerPhone(), SMSType.ORDER_SHIPPED, model));
      }
    } catch (ServiceException e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.SEND_ORDER_SHIPPED_SMS_FAILURE_THROUGH_KARIX.getErrorCode(),
          CustomerErrorCodes.SEND_ORDER_SHIPPED_SMS_FAILURE_THROUGH_KARIX.getErrorMessage()
              + customerOrder.getCustomerPhone());
    }
  }

  @Override
  public void sendOrderConfirmationMail(CustomerOrder customerOrder) {
    Email email = emailService.createMail(EmailType.ORDER_CONFIRMED);
    email.setTo(customerOrder.getCustomerEmailAddress());
    email.getTemplateTokens().put("ConsumerName", customerOrder.getCustomerName());
    email.getTemplateTokens().put("OrderID", customerOrder.getId().toString());
    email.getTemplateTokens().put("OrderDate", String.valueOf(LocalDate.now()));
    email.getTemplateTokens().put("PaidAmount", String.valueOf(customerOrder.getDisplayPrice()));
    try {
      CustomerNotification customerNotification =
          cacheUtil.getObjectFromCache(
              customerOrder.getCustomerPhone() + HabbitCoreConstant._NOTIFICATION,
              CustomerNotification.class);
      if (Objects.isNull(customerNotification)) {
        emailService.sendEmail(email);
      } else if (customerNotification.isEmailEnable()) {
        emailService.sendEmail(email);
      }
    } catch (Exception e) {
      throwServiceRuntImeException(
          e,
          CustomerErrorCodes.CUSTOMER_ORDER_CONFIRM_EMAIL_FAILURE.getErrorCode(),
          CustomerErrorCodes.CUSTOMER_ORDER_CONFIRM_EMAIL_FAILURE.getErrorMessage());
    }
  }

  private void throwServiceRuntImeException(Exception e, String errorCode, String errorMsg)
      throws ServiceRuntimeException {
    LOGGER.error(errorCode + " : " + errorMsg, e);
    throw new ServiceRuntimeException(errorCode, errorMsg, e);
  }
}
