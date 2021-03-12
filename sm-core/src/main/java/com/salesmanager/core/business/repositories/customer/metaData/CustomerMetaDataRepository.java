package com.salesmanager.core.business.repositories.customer.metaData;

import com.salesmanager.core.model.customer.CustomerMetaData;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerMetaDataRepository extends JpaRepository<CustomerMetaData, Long> {

  @Query("select cmd from CustomerMetaData cmd where cmd.phone =?1")
  Optional<CustomerMetaData> getByPhone(String phone);

  @Query(
      "select cmd from CustomerMetaData cmd left join fetch cmd.auditSection cmda where cmd.mailSent = 0 "
          + "and cmda.dateCreated between (?1) and (?2)")
  Optional<List<CustomerMetaData>> getMetaDataList(Date from, Date to);
}
