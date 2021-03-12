package com.salesmanager.core.business.services.membershipcolor;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.membershipcolor.MembershipColorRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.MembershipColor;
import com.salesmanager.core.model.common.enumerator.TierType;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("membershipColorService")
public class MembershipColorServiceImpl extends SalesManagerEntityServiceImpl<Long, MembershipColor>
    implements MembershipColorService {

  MembershipColorRepository membershipColorRepository;

  @Inject
  public MembershipColorServiceImpl(MembershipColorRepository membershipColorRepository) {
    super(membershipColorRepository);
    this.membershipColorRepository = membershipColorRepository;
  }

  @Override
  public MembershipColor getById(Long id) {
    return membershipColorRepository.findById(id).orElse(null);
  }

  @Override
  public List<MembershipColor> getAllMembershipColor() throws ServiceException {
    try {
      return membershipColorRepository.findAll();
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public MembershipColor getByTierType(TierType name) throws ServiceException {
    try {
      return membershipColorRepository.getByTierType(name).orElse(null);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
