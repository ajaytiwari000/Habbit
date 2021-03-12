package com.salesmanager.core.business.services.pincode;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.pincode.PinCodeRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.PinCode;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("pinCodeService")
public class PinCodeServiceImpl extends SalesManagerEntityServiceImpl<Long, PinCode>
    implements PinCodeService {
  private static final Logger LOGGER = LoggerFactory.getLogger(PinCodeServiceImpl.class);
  private PinCodeRepository pinCodeRepository;

  @Inject
  public PinCodeServiceImpl(PinCodeRepository pinCodeRepository) {
    super(pinCodeRepository);
    this.pinCodeRepository = pinCodeRepository;
  }

  @Override
  public List<PinCode> getAllPinCode() throws ServiceException {
    try {
      return pinCodeRepository.getAllPinCode().orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public PinCode getById(Long id) {
    return pinCodeRepository.getById(id).orElse(null);
  }

  @Override
  public PinCode getPinCodeByCode(String pinCode) throws ServiceException {
    try {
      return pinCodeRepository.getPinCodeByCode(pinCode).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
