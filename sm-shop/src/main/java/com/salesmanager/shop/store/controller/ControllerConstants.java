/** */
package com.salesmanager.shop.store.controller;

/**
 * Interface contain constant for Controller.These constant will be used throughout sm-shop to
 * providing constant values to various Controllers being used in the application.
 *
 * @author Umesh A
 */
public interface ControllerConstants {

  static final String REDIRECT = "redirect:";

  interface Tiles {
    interface ShoppingCart {
      static final String shoppingCart = "maincart";
    }

    interface Category {
      static final String category = "category";
    }

    interface Product {
      static final String product = "product";
    }

    interface Items {
      static final String items_manufacturer = "items.manufacturer";
    }

    interface Customer {
      static final String customer = "customer";
      static final String customerLogon = "customerLogon";
      static final String review = "review";
      static final String register = "register";
      static final String changePassword = "customerPassword";
      static final String customerOrders = "customerOrders";
      static final String customerOrder = "customerOrder";
      static final String Billing = "customerAddress";
      static final String EditAddress = "editCustomerAddress";
    }

    interface Content {
      static final String content = "content";
      static final String contactus = "contactus";
    }

    interface Pages {
      static final String notFound = "404";
      static final String timeout = "timeout";
    }

    interface Merchant {
      static final String contactUs = "contactus";
    }

    interface Checkout {
      static final String checkout = "checkout";
      static final String confirmation = "confirmation";
    }

    interface Search {
      static final String search = "search";
    }

    interface Error {
      static final String accessDenied = "accessDenied";
      static final String error = "error";
    }
  }

  interface Views {
    interface Controllers {
      interface Registration {
        String RegistrationPage = "shop/customer/registration.html";
      }
    }
  }
}
