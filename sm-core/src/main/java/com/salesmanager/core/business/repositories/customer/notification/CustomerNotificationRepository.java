package com.salesmanager.core.business.repositories.customer.notification;

import com.salesmanager.core.model.customer.CustomerNotification;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerNotificationRepository extends JpaRepository<CustomerNotification, Long> {
  @Query("select cn from CustomerNotification cn where cn.phone= ?1")
  Optional<CustomerNotification> getByPhone(String phone);
}
