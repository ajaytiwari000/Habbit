package com.salesmanager.core.business.repositories.pincode;

import com.salesmanager.core.model.catalog.product.PinCode;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PinCodeRepository extends JpaRepository<PinCode, Long> {
  @Query("select distinct pc from PinCode pc")
  Optional<List<PinCode>> getAllPinCode();

  @Query("select distinct pc from PinCode pc where pc.id= ?1")
  Optional<PinCode> getById(Long id);

  @Query("select distinct pc from PinCode pc where pc.pinCode= ?1")
  Optional<PinCode> getPinCodeByCode(String pinCode);
}
