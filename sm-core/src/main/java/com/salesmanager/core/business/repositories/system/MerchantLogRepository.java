package com.salesmanager.core.business.repositories.system;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.system.MerchantLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantLogRepository extends JpaRepository<MerchantLog, Long> {

  public List<MerchantLog> findByStore(MerchantStore store);
}
