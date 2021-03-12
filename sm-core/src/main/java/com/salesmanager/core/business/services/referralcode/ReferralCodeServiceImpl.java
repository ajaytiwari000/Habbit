package com.salesmanager.core.business.services.referralcode;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.referralcode.ReferralCodeRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.common.ReferralCode;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("referralCodeService")
public class ReferralCodeServiceImpl extends SalesManagerEntityServiceImpl<Long, ReferralCode>
    implements ReferralCodeService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ReferralCodeServiceImpl.class);
  private ReferralCodeRepository referralCodeRepository;

  @Inject
  public ReferralCodeServiceImpl(ReferralCodeRepository referralCodeRepository) {
    super(referralCodeRepository);
    this.referralCodeRepository = referralCodeRepository;
  }

  @Override
  public ReferralCode getById(Long id) {
    return referralCodeRepository.getById(id).orElse(null);
  }

  @Override
  public ReferralCode getReferralCodeByCode(String referralCode) throws ServiceException {
    try {
      return referralCodeRepository.getReferralCodeByCode(referralCode).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public ReferralCode getReferralCodeByPhone(String phoneNumber) throws ServiceException {
    try {
      return referralCodeRepository.getReferralCodeByPhone(phoneNumber).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public List<ReferralCode> getAllReferralCodeList() throws ServiceException {
    try {
      return referralCodeRepository.getAllReferralCode().orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
