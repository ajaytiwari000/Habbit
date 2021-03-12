package com.salesmanager.core.business.services.pincode;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.PinCode;
import java.util.List;

public interface PinCodeService extends SalesManagerEntityService<Long, PinCode> {
  List<PinCode> getAllPinCode() throws ServiceException;

  PinCode getById(Long id);

  PinCode getPinCodeByCode(String pinCode) throws ServiceException;
}
