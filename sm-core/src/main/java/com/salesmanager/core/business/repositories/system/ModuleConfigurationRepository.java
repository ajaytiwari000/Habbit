package com.salesmanager.core.business.repositories.system;

import com.salesmanager.core.model.system.IntegrationModule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleConfigurationRepository extends JpaRepository<IntegrationModule, Long> {

  List<IntegrationModule> findByModule(String moduleName);

  IntegrationModule findByCode(String code);
}
