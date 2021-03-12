package com.salesmanager.core.business.repositories.system;

import com.salesmanager.core.model.system.optin.Optin;
import com.salesmanager.core.model.system.optin.OptinType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OptinRepository extends JpaRepository<Optin, Long> {

  @Query("select distinct o from Optin as o  left join fetch o.merchant om where om.id = ?1")
  List<Optin> findByMerchant(Integer storeId);

  @Query(
      "select distinct o from Optin as o  left join fetch o.merchant om where om.id = ?1 and o.optinType = ?2")
  Optin findByMerchantAndType(Integer storeId, OptinType optinTyle);

  @Query(
      "select distinct o from Optin as o  left join fetch o.merchant om where om.id = ?1 and o.code = ?2")
  Optin findByMerchantAndCode(Integer storeId, String code);
}
