package com.salesmanager.shop.store.facade.currency;

import com.salesmanager.core.model.reference.currency.Currency;
import java.util.List;

public interface CurrencyFacade {

  List<Currency> getList();
}
