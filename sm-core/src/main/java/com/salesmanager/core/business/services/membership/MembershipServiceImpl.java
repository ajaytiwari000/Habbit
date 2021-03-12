package com.salesmanager.core.business.services.membership;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.MembershipRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.common.Membership;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("membershipService")
public class MembershipServiceImpl extends SalesManagerEntityServiceImpl<Long, Membership>
    implements MembershipService {
  private static final Logger LOGGER = LoggerFactory.getLogger(MembershipServiceImpl.class);
  private MembershipRepository membershipRepository;

  @Inject
  public MembershipServiceImpl(MembershipRepository membershipRepository) {
    super(membershipRepository);
    this.membershipRepository = membershipRepository;
  }

  @Override
  public Membership getById(Long id) {
    return membershipRepository.getById(id).orElse(null);
  }

  @Override
  public Membership getMembershipByPhone(String phone) throws ServiceException {
    try {
      return membershipRepository.getMembershipByphone(phone).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
